package ml.abyssal.abyssalproxy.managers;

import ml.abyssal.abyssalproxy.AbyssalProxy;
import ml.abyssal.abyssalproxy.events.StaffMsgEvent;
import ml.abyssal.abyssalproxy.utils.Parser;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;

public class DiscordManager implements EventListener {
    public final String TOKEN;
    public final String STAFF_CHANNEL_ID;
    public final String GUILD_ID;
    public final String discordToMcFormat;
    public final String mcToDiscordFormat;
    private static JDA jda;
    public DiscordManager() {
        Configuration config = AbyssalProxy.getInstance().getConfigManager().getConfig();
        TOKEN = config.getString("staff.discord-token", "");
        STAFF_CHANNEL_ID = config.getString("staff.discord-staff-channel-id", "");
        GUILD_ID = config.getString("staff.discord-guild-id", "");
        discordToMcFormat = Parser.color(config.getString("staff.discord-to-mc-format", ""));
        mcToDiscordFormat = config.getString("staff.mc-to-discord-format", "");
        try {
            jda = JDABuilder.createDefault(TOKEN).build();
            jda.awaitReady();
            jda.addEventListener(this);
        } catch (LoginException | InterruptedException e) {
            jda = null;
            e.printStackTrace();
        }
    }

    public void sendToMC(Member member, String message) {
        if (discordToMcFormat.isEmpty()) return;

        String format = discordToMcFormat.replace("{player}", member.getEffectiveName()).replace("{message}", message);
        AbyssalProxy.getInstance().getProxy().getPluginManager().callEvent(new StaffMsgEvent(member, message, format));
    }

    public void sendToDiscord(ProxiedPlayer player, String message) {
        if (mcToDiscordFormat.isEmpty()) {
            AbyssalProxy.getInstance().getLogger().warning("MC To Discord format is empty!");
            return;
        }

        if (jda == null) return;
        String format = mcToDiscordFormat.replace("{player}", player.getName()).replace("{message}", message);
        Guild guild = jda.getGuildById(GUILD_ID);
        if (guild == null) {
            AbyssalProxy.getInstance().getLogger().warning("Unknown guild of id: " + GUILD_ID);
            return;
        }

        TextChannel channel = guild.getTextChannelById(STAFF_CHANNEL_ID);
        if (channel == null) {
            AbyssalProxy.getInstance().getLogger().warning("Unknown text channel of id: " + STAFF_CHANNEL_ID);
            return;
        }

        try {
            Message m = new MessageBuilder().setContent(format).build();
            channel.sendMessage(m).queue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JDA getJDA() {
        return jda;
    }

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof MessageReceivedEvent) {
            MessageReceivedEvent e = (MessageReceivedEvent) event;
            if (e.getAuthor().isBot()) return;
            if (e.getChannel().getId().equals(STAFF_CHANNEL_ID)) {
                sendToMC(e.getMember(), e.getMessage().getContentDisplay());
            }
        }
    }
}
