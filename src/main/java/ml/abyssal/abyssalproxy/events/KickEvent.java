package ml.abyssal.abyssalproxy.events;

import litebans.api.Entry;
import ml.abyssal.abyssalproxy.types.PunishmentType;

public class KickEvent extends Punishment {
    public KickEvent(Entry entry) {
        super(entry);
        this.type = PunishmentType.KICK;
    }
}
