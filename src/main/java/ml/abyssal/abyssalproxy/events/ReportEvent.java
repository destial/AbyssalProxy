package ml.abyssal.abyssalproxy.events;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

import java.util.ArrayList;
import java.util.UUID;

public class ReportEvent extends Event {

    public final static ArrayList<UUID> reporters = new ArrayList<>();

    private final ProxiedPlayer reporter;
    private final ProxiedPlayer target;
    private final String reason;
    public ReportEvent(ProxiedPlayer reporter, ProxiedPlayer target, String reason) {
        this.reporter = reporter;
        this.target = target;
        this.reason = reason;
        reporters.add(reporter.getUniqueId());
    }

    public String getReason() {
        return reason;
    }

    public ProxiedPlayer getReporter() {
        return reporter;
    }

    public ProxiedPlayer getTarget() {
        return target;
    }

    public ServerInfo getServer() {
        return reporter.getServer().getInfo();
    }
}
