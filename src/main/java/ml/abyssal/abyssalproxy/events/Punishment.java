package ml.abyssal.abyssalproxy.events;

import litebans.api.Entry;
import ml.abyssal.abyssalproxy.types.PunishmentType;
import ml.abyssal.abyssalproxy.utils.LitebansDatabase;
import net.md_5.bungee.api.plugin.Event;

public class Punishment extends Event {
    protected PunishmentType type;
    private final String victim;
    private final String executor;
    private final String duration;
    private final String reason;
    public Punishment(Entry entry) {
        super();
        if (!entry.isPermanent()) {
            duration = entry.getDurationString();
        } else {
            duration = "Forever";
        }
        type = PunishmentType.UNKNOWN;

        victim = LitebansDatabase.getPlayerName(entry.getUuid());

        if (entry.getExecutorName() == null || entry.getExecutorName().equalsIgnoreCase("Console")) {
            executor = "Console";
        } else {
            executor = entry.getExecutorName();
        }
        reason = entry.getReason();
    }

    public String getExecutor() {
        return executor;
    }

    public String getVictim() {
        return victim;
    }

    public PunishmentType getType() {
        return type;
    }

    public String getDuration() {
        return duration;
    }

    public String getReason() {
        return reason;
    }
}
