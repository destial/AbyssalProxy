package ml.abyssal.abyssalproxy.commands;

import ml.abyssal.abyssalproxy.AbyssalProxy;
import ml.abyssal.abyssalproxy.utils.Parser;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;

public class ReportUnbanCommand extends Command implements TabExecutor {

    public ReportUnbanCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) return;

        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (!player.hasPermission("abyssal.punish")) return;

        if (args.length < 1) {
            player.sendMessage(TextComponent.fromLegacyText(
                    Parser.color(AbyssalProxy.getInstance().getReportManager().getUnbanUsage())));
            return;
        }

        String targetName = args[0];
        ProxiedPlayer target = AbyssalProxy.getInstance().getProxy().getPlayer(targetName);
        AbyssalProxy.getInstance().getReportManager().remove(target.getName());

        player.sendMessage(TextComponent.fromLegacyText(
                Parser.color(AbyssalProxy.getInstance().getReportManager().getUserUnbanned().replace("{player}", target.getName()))));
        return;
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) return new ArrayList<>();

        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (!player.hasPermission("abyssal.punish")) return new ArrayList<>();

        return new ArrayList<>(AbyssalProxy.getInstance().getReportManager().getReportBanned());
    }
}
