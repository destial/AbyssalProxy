package ml.abyssal.abyssalproxy.listener;

import ml.abyssal.abyssalproxy.AbyssalProxy;
import ml.abyssal.abyssalproxy.events.StaffMsgEvent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class StaffMsgListener implements Listener {
    public StaffMsgListener() {}

    @EventHandler
    public void onChat(ChatEvent e) {
        if (!(e.getSender() instanceof ProxiedPlayer)) return;
        if (e.isCommand() || e.isProxyCommand()) return;

        if (!e.getMessage().startsWith(AbyssalProxy.getInstance().getConfigManager().getStaffPrefix())) return;

        ProxiedPlayer sender = (ProxiedPlayer) e.getSender();
        if (!sender.hasPermission("abyssal.staff")) return;

        e.setCancelled(true);
        if (AbyssalProxy.getInstance().getConfigManager().isDisabledServer(sender.getServer().getInfo().getName())) {
            sender.sendMessage(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&',
                    "&cThis server currently has staff-chats disabled. Contact an administrator to enable it!")));
            return;
        }

        String message = e.getMessage().substring(AbyssalProxy.getInstance().getConfigManager().getStaffPrefix().length());
        StaffMsgEvent event = ProxyServer.getInstance().getPluginManager().callEvent(new StaffMsgEvent(sender, message));
        if (event.isCancelled()) return;

        String format = AbyssalProxy.getInstance().getConfigManager().getStaffFormat();
        format = format.replace("{player}", event.getStaffName()).replace("{message}", event.getMessage());
        for (ProxiedPlayer player : AbyssalProxy.getInstance().getProxy().getPlayers()) {
            if (player.hasPermission("abyssal.staff")) {
                if (AbyssalProxy.getInstance().getConfigManager().isDisabledServer(player.getServer().getInfo().getName())) continue;

                player.sendMessage(TextComponent.fromLegacyText(format));
            }
        }
    }
}
