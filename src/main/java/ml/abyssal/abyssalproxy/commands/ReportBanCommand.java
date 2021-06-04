package ml.abyssal.abyssalproxy.commands;

import ml.abyssal.abyssalproxy.AbyssalProxy;
import ml.abyssal.abyssalproxy.utils.Parser;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReportBanCommand extends Command implements TabExecutor {
    public ReportBanCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) return;

        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (!player.hasPermission("abyssal.punish")) return;

        if (args.length < 1) {
            player.sendMessage(TextComponent.fromLegacyText(
                    Parser.color(AbyssalProxy.getInstance().getReportManager().getBanUsage())));
            return;
        }

        String targetName = args[0];
        ProxiedPlayer target = AbyssalProxy.getInstance().getProxy().getPlayer(targetName);
        AbyssalProxy.getInstance().getReportManager().add(target);

        player.sendMessage(TextComponent.fromLegacyText(
                Parser.color(AbyssalProxy.getInstance().getReportManager().getUserBanned().replace("{player}", target.getName()))));
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) return new ArrayList<>();

        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (!player.hasPermission("abyssal.punish")) return new ArrayList<>();

        List<String> list = new ArrayList<>();
        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
            list.add(p.getName());
        }
        String current = args[0];
        if (current.isEmpty()) return list;
        return list.stream().filter(s -> s.toLowerCase().startsWith(current)).collect(Collectors.toList());
    }
}