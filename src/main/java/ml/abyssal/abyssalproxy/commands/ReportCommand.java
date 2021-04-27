package ml.abyssal.abyssalproxy.commands;

import ml.abyssal.abyssalproxy.AbyssalProxy;
import ml.abyssal.abyssalproxy.events.ReportEvent;
import ml.abyssal.abyssalproxy.utils.Parser;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ReportCommand extends Command {
    public ReportCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) return;

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (ReportEvent.reporters.contains(player.getUniqueId())) {
            player.sendMessage(TextComponent.fromLegacyText(
                    ChatColor.translateAlternateColorCodes('&',
                    AbyssalProxy.getInstance().getConfigManager().getReportCooldown())));
            return;
        }

        if (args.length < 2) {
            player.sendMessage(TextComponent.fromLegacyText(
                    ChatColor.translateAlternateColorCodes('&',
                    AbyssalProxy.getInstance().getConfigManager().getReportUsage())));
            return;
        }

        String targetName = args[0];
        ProxiedPlayer target = AbyssalProxy.getInstance().getProxy().getPlayer(targetName);

        if (target == null) {
            player.sendMessage(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', "&cPlayer not found!")));
            player.sendMessage(TextComponent.fromLegacyText(
                    ChatColor.translateAlternateColorCodes('&',
                            AbyssalProxy.getInstance().getConfigManager().getReportUsage())));
            return;
        }
        String reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        ReportEvent reportEvent = new ReportEvent(player, target, reason);
        player.sendMessage(TextComponent.fromLegacyText(
                ChatColor.translateAlternateColorCodes('&',
                        Parser.report(reportEvent, AbyssalProxy.getInstance().getConfigManager().getReportSuccess()))));
        AbyssalProxy.getInstance().getProxy().getPluginManager().callEvent(reportEvent);

        ReportEvent.reporters.add(player.getUniqueId());
        UUID uuid = player.getUniqueId();
        ProxyServer.getInstance().getScheduler().schedule(AbyssalProxy.getInstance(), () -> ReportEvent.reporters.remove(uuid), 0, 3, TimeUnit.MINUTES);
    }
}
