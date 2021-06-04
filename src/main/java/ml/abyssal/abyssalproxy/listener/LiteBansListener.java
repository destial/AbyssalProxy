package ml.abyssal.abyssalproxy.listener;

import litebans.api.Entry;
import litebans.api.Events;
import ml.abyssal.abyssalproxy.AbyssalProxy;
import ml.abyssal.abyssalproxy.events.BanEvent;
import ml.abyssal.abyssalproxy.events.IPBanEvent;
import ml.abyssal.abyssalproxy.events.KickEvent;
import ml.abyssal.abyssalproxy.events.WarnEvent;
import ml.abyssal.abyssalproxy.events.MuteEvent;

public class LiteBansListener extends Events.Listener {
    public LiteBansListener() {}

    @Override
    public void entryAdded(Entry entry) {
        if (entry.isSilent()) return;
        switch (entry.getType()) {
            case "ban":
                if (entry.isIpban()) {
                    AbyssalProxy.getInstance().getProxy().getPluginManager().callEvent(new IPBanEvent(entry));
                } else {
                    AbyssalProxy.getInstance().getProxy().getPluginManager().callEvent(new BanEvent(entry));
                }
                break;
            case "kick":
                AbyssalProxy.getInstance().getProxy().getPluginManager().callEvent(new KickEvent(entry));
                break;
            case "warn":
                AbyssalProxy.getInstance().getProxy().getPluginManager().callEvent(new WarnEvent(entry));
                break;
            case "mute":
                AbyssalProxy.getInstance().getProxy().getPluginManager().callEvent(new MuteEvent(entry));
                break;
        }
    }
}
