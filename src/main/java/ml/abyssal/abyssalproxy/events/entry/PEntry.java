package ml.abyssal.abyssalproxy.events.entry;

import litebans.api.Entry;
import org.jetbrains.annotations.Nullable;

public class PEntry extends Entry {
    public PEntry(long id, String type, @Nullable String uuid, @Nullable String ip, String reason, @Nullable String executorUUID, @Nullable String executorName, @Nullable String removedByUUID, @Nullable String removedByName, long dateStart, long dateEnd, String serverScope, String serverOrigin, boolean silent, boolean ipban, boolean active) {
        super(id, type, uuid, ip, reason, executorUUID, executorName, removedByUUID, removedByName, dateStart, dateEnd, serverScope, serverOrigin, silent, ipban, active);
    }

    @Override
    public long getDuration() {
        return 0;
    }

    @Override
    public String getDurationString() {
        return null;
    }

    @Override
    public long getRemainingDuration(long currentTime) {
        return 0;
    }

    @Override
    public String getRemainingDurationString(long currentTime) {
        return null;
    }

    @Override
    public boolean isExpired(long currentTime) {
        return false;
    }

    @Override
    public boolean isPermanent() {
        return false;
    }
}
