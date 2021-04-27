package ml.abyssal.abyssalproxy.events;

import litebans.api.Entry;
import ml.abyssal.abyssalproxy.types.PunishmentType;

public class BanEvent extends Punishment {
    public BanEvent(Entry entry) {
        super(entry);
        this.type = PunishmentType.BAN;
    }
}
