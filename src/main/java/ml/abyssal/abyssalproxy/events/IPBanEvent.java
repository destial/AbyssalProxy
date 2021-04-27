package ml.abyssal.abyssalproxy.events;

import litebans.api.Entry;
import ml.abyssal.abyssalproxy.types.PunishmentType;

public class IPBanEvent extends Punishment {
    public IPBanEvent(Entry entry) {
        super(entry);
        this.type = PunishmentType.IPBAN;
    }
}
