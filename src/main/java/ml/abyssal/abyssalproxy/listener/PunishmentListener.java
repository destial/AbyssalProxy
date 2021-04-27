package ml.abyssal.abyssalproxy.listener;

import ml.abyssal.abyssalproxy.AbyssalProxy;
import ml.abyssal.abyssalproxy.events.*;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PunishmentListener implements Listener {
    public PunishmentListener() {}

    @EventHandler
    public void onBan(BanEvent e) {
        AbyssalProxy.getInstance().getWebhookManager().punish(e);
    }

    @EventHandler
    public void onIPBan(IPBanEvent e) {
        AbyssalProxy.getInstance().getWebhookManager().punish(e);
    }

    @EventHandler
    public void onKick(KickEvent e) {
        AbyssalProxy.getInstance().getWebhookManager().punish(e);
    }

    @EventHandler
    public void onWarn(WarnEvent e) {
        AbyssalProxy.getInstance().getWebhookManager().punish(e);
    }

    @EventHandler
    public void onMute(MuteEvent e) {
        AbyssalProxy.getInstance().getWebhookManager().punish(e);
    }

    @EventHandler
    public void onJail(JailEvent e) {
        AbyssalProxy.getInstance().getWebhookManager().jail(e);
    }
}
