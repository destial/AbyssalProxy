package ml.abyssal.abyssalproxy.events;

import litebans.api.Entry;
import ml.abyssal.abyssalproxy.types.PunishmentType;
import ml.abyssal.abyssalproxy.utils.LitebansDatabase;
import ml.abyssal.abyssalproxy.utils.Parser;
import net.md_5.bungee.api.plugin.Event;

public class Punishment extends Event {
    protected PunishmentType type;
    private final String victim;
    private final String executor;
    private final String duration;
    private final String reason;
    private final long milliseconds;
    private final long until;
    private final boolean active;
    public Punishment(Entry entry) {
        super();
        if (!entry.isPermanent()) {
            duration = entry.getDurationString();
        } else {
            duration = "Forever";
        }
        type = PunishmentType.UNKNOWN;

        victim = LitebansDatabase.getPlayerName(entry.getUuid());

        executor = entry.getExecutorName() != null ? entry.getExecutorName() : "Console";
        reason = entry.getReason();
        active = true;
        milliseconds = entry.getDateStart();
        until = entry.getDateEnd();
    }
    public Punishment(PunishmentType type, String victim, String executor, long milliseconds, long until, String reason, boolean active) {
        this.type = type;
        this.victim = victim;
        this.reason = reason;
        this.executor = executor;
        this.duration = Parser.toDate(until - milliseconds);
        this.active = active;
        this.milliseconds = milliseconds;
        this.until = until;
    }

    public boolean isActive() {
        return active;
    }

    public long getUntil() {
        return until;
    }

    public long getMilliseconds() {
        return this.milliseconds;
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
