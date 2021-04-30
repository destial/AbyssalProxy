package ml.abyssal.abyssalproxy.events;

import net.dv8tion.jda.api.entities.Member;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;

public class StaffMsgEvent extends Event implements Cancellable {
    private final ProxiedPlayer ingamePlayer;
    private final Member discordMember;
    private String message;
    private final String format;
    private boolean cancelled;
    public StaffMsgEvent(ProxiedPlayer player, String message, String format) {
        this.message = message;
        this.format = format;
        ingamePlayer = player;
        discordMember = null;
        cancelled = false;
    }

    public StaffMsgEvent(Member player, String message, String format) {
        this.format = format;
        this.message = message;
        ingamePlayer = null;
        discordMember = player;
        cancelled = false;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getStaffName() {
        return ingamePlayer != null ? ingamePlayer.getName() : discordMember.getEffectiveName();
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    public String getFormat() {
        return format;
    }

    public boolean isDiscord() {
        return discordMember != null;
    }

    public ProxiedPlayer getIngamePlayer() {
        return ingamePlayer;
    }

    public Member getDiscordMember() {
        return discordMember;
    }
}
