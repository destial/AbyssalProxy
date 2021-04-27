package ml.abyssal.abyssalproxy.events;

import litebans.api.Entry;
import ml.abyssal.abyssalproxy.types.PunishmentType;

public class MuteEvent extends Punishment {
    public MuteEvent(Entry entry) {
        super(entry);
        this.type = PunishmentType.MUTE;
    }
}
