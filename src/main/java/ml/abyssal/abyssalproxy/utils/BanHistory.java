package ml.abyssal.abyssalproxy.utils;

public class BanHistory {
    private final String reason;
    private final String bannedBy;
    private final long duration;
    private final long until;
    private final long id;
    private boolean active;
    public BanHistory(String reason, String bannedByUUID, long duration, long until, long id, boolean active) {
        this.reason = reason;
        this.bannedBy = LitebansDatabase.getPlayerName(bannedByUUID);
        this.duration = duration;
        this.until = until;
        this.id = id;
        this.active = active;
    }

    public String getReason() {
        return reason;
    }

    public boolean isActive() {
        return active;
    }

    public long getDuration() {
        return duration;
    }

    public long getId() {
        return id;
    }

    public long getUntil() {
        return until;
    }

    public String getBannedBy() {
        return bannedBy;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
