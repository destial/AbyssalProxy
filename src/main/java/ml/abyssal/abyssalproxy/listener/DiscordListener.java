package ml.abyssal.abyssalproxy.listener;

import ml.abyssal.abyssalproxy.AbyssalProxy;
import ml.abyssal.abyssalproxy.events.Punishment;
import ml.abyssal.abyssalproxy.utils.LitebansDatabase;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.util.List;

public class DiscordListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent e) {
        if (!e.getMessage().getContentDisplay().startsWith(AbyssalProxy.getInstance().getDiscordManager().DISCORD_PREFIX)) return;
        if (e.getMember() == null || e.getAuthor().isBot() || !e.getMember().hasPermission(Permission.KICK_MEMBERS)) return;

        String command = e.getMessage().getContentDisplay().substring(AbyssalProxy.getInstance().getDiscordManager().DISCORD_PREFIX.length()).toLowerCase().trim();
        if (command.startsWith("history")) {
            String playerName = command.substring("history".length()).trim();
            if (playerName.isEmpty()) {
                e.getChannel().sendMessage(new EmbedBuilder().setTitle("Usage is: " + AbyssalProxy.getInstance().getDiscordManager().DISCORD_PREFIX + "history [playerName]").setColor(Color.RED).build()).queue();
                return;
            }
            sendHistory(playerName, e.getChannel());
        }
    }

    private void sendHistory(String playerName, TextChannel channel) {
        String uuid = LitebansDatabase.getUUID(playerName);
        if (uuid == null) {
            channel.sendMessage(new EmbedBuilder().setTitle("Invalid user!").setColor(Color.RED).build()).queue();
            return;
        }
        List<Punishment> history = LitebansDatabase.getHistory(uuid);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("History of " + LitebansDatabase.getPlayerName(uuid));
        builder.setFooter("Will only show latest 24 history");
        builder.setColor(Color.PINK);
        int i = 0;
        for (Punishment p : history) {
            ++i;
            if (i >= 24) break;
            builder.addField(p.getType().name() + " (" + p.getDuration() + ") " + (p.isActive() ? "Active" : "Inactive"), p.getReason() + " *(" + p.getExecutor() + ")*", false);
        }
        channel.sendMessage(builder.build()).queue();
    }
}
