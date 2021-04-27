package ml.abyssal.abyssalproxy.events;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;

public class StaffMsgEvent extends Event implements Cancellable {
    private final ProxiedPlayer staffMember;
    private String message;
    private boolean cancelled;
    public StaffMsgEvent(ProxiedPlayer player, String message) {
        this.staffMember = player;
        this.message = message;
        cancelled = false;
    }

    public ProxiedPlayer getStaffMember() {
        return staffMember;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getStaffName() {
        return getStaffMember().getName();
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
