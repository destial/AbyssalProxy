package ml.abyssal.abyssalproxy.events;

import litebans.api.Entry;
import ml.abyssal.abyssalproxy.types.PunishmentType;

public class WarnEvent extends Punishment {
    public WarnEvent(Entry entry) {
        super(entry);
        this.type = PunishmentType.WARN;
    }
}
