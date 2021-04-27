package ml.abyssal.abyssalproxy.managers;

import ml.abyssal.abyssalproxy.AbyssalProxy;
import ml.abyssal.abyssalproxy.events.JailEvent;
import ml.abyssal.abyssalproxy.events.Punishment;
import ml.abyssal.abyssalproxy.events.ReportEvent;
import ml.abyssal.abyssalproxy.utils.DiscordWebhook;
import ml.abyssal.abyssalproxy.utils.Parser;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;

public class WebhookManager {
    private static final String WEBHOOK_URL = "https://discord.com/api/webhooks/";
    private final String id;
    private final String token;

    private final String reportId;
    private final String reportToken;
    public WebhookManager() {
        id = AbyssalProxy.getInstance().getConfigManager().getWebhookID();
        token = AbyssalProxy.getInstance().getConfigManager().getWebhookToken();
        reportId = AbyssalProxy.getInstance().getConfigManager().getReportId();
        reportToken = AbyssalProxy.getInstance().getConfigManager().getReportToken();
    }

    public void punish(Punishment punishment) {
        if (id.isEmpty() || token.isEmpty()) return;

        AbyssalProxy.getInstance().getProxy().getScheduler().runAsync(AbyssalProxy.getInstance(), () -> {
            try {
                DiscordWebhook webhook = new DiscordWebhook(WEBHOOK_URL + id + "/" + token);

                DiscordWebhook.EmbedObject embed = new DiscordWebhook.EmbedObject();
                if (!AbyssalProxy.getInstance().getConfigManager().getTitle().isEmpty())
                    embed.setTitle(Parser.punishment(punishment, AbyssalProxy.getInstance().getConfigManager().getTitle()));

                if (!AbyssalProxy.getInstance().getConfigManager().getDescription().isEmpty())
                    embed.setDescription(Parser.punishment(punishment, AbyssalProxy.getInstance().getConfigManager().getDescription()));

                if (!AbyssalProxy.getInstance().getConfigManager().getFooter().isEmpty())
                    embed.setFooter(Parser.punishment(punishment, AbyssalProxy.getInstance().getConfigManager().getFooter()), null);

                if (AbyssalProxy.getInstance().getConfigManager().isDisplayDate())
                    embed.setTimestamp(new Date());

                embed.setColor(Parser.punishmentColor(punishment.getType()));

                for (Map<String, Serializable> field : AbyssalProxy.getInstance().getConfigManager().getFields()) {
                    String name = (String) field.get("name");
                    String value = (String) field.get("value");
                    if (name.isEmpty() || value.isEmpty()) continue;

                    embed.addField(Parser.punishment(punishment, name), Parser.punishment(punishment, value), (boolean) field.get("inline"));
                }

                webhook.addEmbed(embed);
                webhook.execute();
            } catch (IOException e) {
                AbyssalProxy.getInstance().getLogger().log(Level.WARNING, "Error while sending ban event!", e);
            }
        });
    }

    public void jail(JailEvent punishment) {
        if (id.isEmpty() || token.isEmpty()) return;

        AbyssalProxy.getInstance().getProxy().getScheduler().runAsync(AbyssalProxy.getInstance(), () -> {
            try {
                DiscordWebhook webhook = new DiscordWebhook(WEBHOOK_URL + id + "/" + token);

                DiscordWebhook.EmbedObject embed = new DiscordWebhook.EmbedObject();
                if (!AbyssalProxy.getInstance().getConfigManager().getTitle().isEmpty())
                    embed.setTitle(Parser.jail(punishment, AbyssalProxy.getInstance().getConfigManager().getTitle()));

                if (!AbyssalProxy.getInstance().getConfigManager().getDescription().isEmpty())
                    embed.setDescription(Parser.jail(punishment, AbyssalProxy.getInstance().getConfigManager().getDescription()));

                if (!AbyssalProxy.getInstance().getConfigManager().getFooter().isEmpty())
                    embed.setFooter(Parser.jail(punishment, AbyssalProxy.getInstance().getConfigManager().getFooter()), null);

                if (AbyssalProxy.getInstance().getConfigManager().isDisplayDate())
                    embed.setTimestamp(new Date());

                embed.setColor(AbyssalProxy.getInstance().getConfigManager().getJailColor());

                for (Map<String, Serializable> field : AbyssalProxy.getInstance().getConfigManager().getFields()) {
                    String name = (String) field.get("name");
                    String value = (String) field.get("value");
                    if (name.isEmpty() || value.isEmpty()) continue;

                    embed.addField(Parser.jail(punishment, name), Parser.jail(punishment, value), (boolean) field.get("inline"));
                }

                webhook.addEmbed(embed);
                webhook.execute();
            } catch (IOException e) {
                AbyssalProxy.getInstance().getLogger().log(Level.WARNING, "Error while sending jail event!", e);
            }
        });
    }

    public void report(ReportEvent report) {
        if (reportToken.isEmpty() || reportId.isEmpty()) return;

        AbyssalProxy.getInstance().getProxy().getScheduler().runAsync(AbyssalProxy.getInstance(), () -> {
            try {
                DiscordWebhook webhook = new DiscordWebhook(WEBHOOK_URL + reportId + "/" + reportToken);
                DiscordWebhook.EmbedObject embed = new DiscordWebhook.EmbedObject();
                if (!AbyssalProxy.getInstance().getConfigManager().getReportDiscordTitle().isEmpty())
                    embed.setTitle(Parser.report(report, AbyssalProxy.getInstance().getConfigManager().getReportDiscordTitle()));

                if (!AbyssalProxy.getInstance().getConfigManager().getReportDiscordDescription().isEmpty())
                    embed.setDescription(Parser.report(report, AbyssalProxy.getInstance().getConfigManager().getReportDiscordDescription()));

                if (!AbyssalProxy.getInstance().getConfigManager().getReportDiscordFooter().isEmpty())
                    embed.setFooter(Parser.report(report, AbyssalProxy.getInstance().getConfigManager().getReportDiscordFooter()), null);

                if (AbyssalProxy.getInstance().getConfigManager().isReportDiscordDisplayDate())
                    embed.setTimestamp(new Date());

                embed.setColor(AbyssalProxy.getInstance().getConfigManager().getReportColor());

                for (Map<String, Serializable> field : AbyssalProxy.getInstance().getConfigManager().getReportDiscordFields()) {
                    String name = (String) field.get("name");
                    String value = (String) field.get("value");
                    if (name.isEmpty() || value.isEmpty()) continue;

                    embed.addField(Parser.report(report, name), Parser.report(report, value), (boolean) field.get("inline"));
                }

                webhook.addEmbed(embed);
                webhook.execute();
            } catch (IOException e) {
                AbyssalProxy.getInstance().getLogger().log(Level.WARNING, "Error while sending report event!", e);
            }
        });
    }
}
