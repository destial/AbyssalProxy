package ml.abyssal.abyssalproxy.events;

import net.md_5.bungee.api.plugin.Event;

public class JailEvent extends Event {
    private final String sender;
    private final String target;
    private final String time;
    private final String reason;
    public JailEvent(String sender, String target, String time, String reason) {
        super();
        this.reason = reason;
        this.sender = sender;
        this.target = target;
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public String getTarget() {
        return target;
    }

    public String getTime() {
        return time;
    }

    public String getReason() {
        return reason;
    }
}
