package ml.abyssal.abyssalproxy.listener;

import ml.abyssal.abyssalproxy.AbyssalProxy;
import ml.abyssal.abyssalproxy.events.BanEvent;
import ml.abyssal.abyssalproxy.events.IPBanEvent;
import ml.abyssal.abyssalproxy.events.KickEvent;
import ml.abyssal.abyssalproxy.events.WarnEvent;
import ml.abyssal.abyssalproxy.events.MuteEvent;
import ml.abyssal.abyssalproxy.events.JailEvent;
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
