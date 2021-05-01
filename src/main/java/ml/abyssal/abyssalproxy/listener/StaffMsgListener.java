package ml.abyssal.abyssalproxy.listener;

import ml.abyssal.abyssalproxy.AbyssalProxy;
import ml.abyssal.abyssalproxy.events.StaffMsgEvent;
import ml.abyssal.abyssalproxy.utils.Parser;
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
            sender.sendMessage(TextComponent.fromLegacyText(
                    Parser.color("&cThis server currently has staff-chats disabled. Contact an administrator to enable it!")));
            return;
        }

        String message = e.getMessage().substring(AbyssalProxy.getInstance().getConfigManager().getStaffPrefix().length());
        String format = AbyssalProxy.getInstance().getConfigManager().getStaffFormat();
        format = format.replace("{player}", sender.getName()).replace("{message}", message);
        StaffMsgEvent event = new StaffMsgEvent(sender, message, format);
        ProxyServer.getInstance().getPluginManager().callEvent(event);
    }

    @EventHandler
    public void onStaffMsg(StaffMsgEvent e) {
        for (ProxiedPlayer player : AbyssalProxy.getInstance().getProxy().getPlayers()) {
            if (player.hasPermission("abyssal.staff")) {
                if (AbyssalProxy.getInstance().getConfigManager().isDisabledServer(player.getServer().getInfo().getName())) continue;

                player.sendMessage(TextComponent.fromLegacyText(e.getFormat()));
            }
        }
        if (!e.isDiscord()) {
            AbyssalProxy.getInstance().getDiscordManager().sendToDiscord(e.getIngamePlayer(), e.getMessage());
        }
    }
}
