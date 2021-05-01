package ml.abyssal.abyssalproxy.listener;

import ml.abyssal.abyssalproxy.AbyssalProxy;
import ml.abyssal.abyssalproxy.events.ReportEvent;
import ml.abyssal.abyssalproxy.utils.Parser;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ReportListener implements Listener {

    @EventHandler
    public void onReport(ReportEvent e) {
        for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
            if (!player.hasPermission("abyssal.staff")) continue;
            player.sendMessage(TextComponent.fromLegacyText(Parser.report(e, AbyssalProxy.getInstance().getConfigManager().getReportStaff())));
        }
        AbyssalProxy.getInstance().getWebhookManager().report(e);
    }
}
