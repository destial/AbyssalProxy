package ml.abyssal.abyssalproxy.managers;

import ml.abyssal.abyssalproxy.AbyssalProxy;
import ml.abyssal.abyssalproxy.utils.Parser;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.config.Configuration;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager {
    private final Configuration config;
    private final String webhookID;
    private final String webhookToken;
    private final String title;
    private final String description;
    private final String footer;
    private final boolean displayDate;
    private final String staffPrefix;
    private final String staffFormat;
    private final String reportUsage;
    private final String reportSuccess;
    private final String reportStaff;
    private final String reportCooldown;
    private final String reportId;
    private final String reportToken;
    private final String reportDiscordTitle;
    private final String reportDiscordDescription;
    private final String reportDiscordFooter;
    private final boolean reportDiscordDisplayDate;
    private final List<ServerInfo> disabledServers;
    private final List<Map<String, Serializable>> fields;
    private final List<Map<String, Serializable>> reportDiscordFields;

    public ConfigManager(Configuration config) {
        this.config = config;
        webhookID = config.getString("webhook-id", "");
        webhookToken = config.getString("webhook-token", "");
        title = config.getString("format.title", "");
        description = config.getString("format.description", "");
        footer = config.getString("format.footer", "");
        displayDate = config.getBoolean("format.display-date", false);
        fields = new ArrayList<>();

        staffPrefix = config.getString("staff.prefix", "@");
        staffFormat = config.getString("staff.format", "&d[STAFFCHAT] {player} &8>> &d{message}");

        disabledServers = new ArrayList<>();
        for (String server : config.getStringList("staff.disabled-servers")) {
            ServerInfo s = AbyssalProxy.getInstance().getProxy().getServersCopy().get(server);
            if (s != null) disabledServers.add(s);
        }

        for (String key : config.getSection("format.fields").getKeys()) {
            String name = config.getString("format.fields." + key + ".name", "");
            String value = config.getString("format.fields." + key + ".value", "");
            boolean inline = config.getBoolean("format.fields." + key + ".inline", false);
            if (name.isEmpty() || value.isEmpty()) continue;

            Map<String, Serializable> field = new HashMap<>();
            field.put("name", name);
            field.put("value", value);
            field.put("inline", inline);
            fields.add(field);
        }

        reportUsage = Parser.color(config.getString("report.usage"));
        reportSuccess = Parser.color(config.getString("report.success"));
        reportStaff = Parser.color(config.getString("report.staff"));
        reportId = config.getString("report-webhook-id");
        reportToken = config.getString("report-webhook-token");
        reportCooldown = Parser.color(config.getString("report.cooldown"));
        reportDiscordTitle = config.getString("report.discord.title");
        reportDiscordDescription = config.getString("report.discord.description");
        reportDiscordDisplayDate = config.getBoolean("report.discord.display-date");
        reportDiscordFooter = config.getString("report.discord.footer");

        reportDiscordFields = new ArrayList<>();
        for (String key : config.getSection("report.discord.fields").getKeys()) {
            String name = config.getString("report.discord.fields." + key + ".name", "");
            String value = config.getString("report.discord.fields." + key + ".value", "");
            boolean inline = config.getBoolean("report.discord.fields." + key + ".inline", false);
            if (name.isEmpty() || value.isEmpty()) continue;

            Map<String, Serializable> field = new HashMap<>();
            field.put("name", name);
            field.put("value", value);
            field.put("inline", inline);
            reportDiscordFields.add(field);
        }
    }

    public String getReportCooldown() {
        return reportCooldown;
    }

    public boolean isReportDiscordDisplayDate() {
        return reportDiscordDisplayDate;
    }

    public String getReportDiscordDescription() {
        return reportDiscordDescription;
    }

    public String getReportDiscordFooter() {
        return reportDiscordFooter;
    }

    public String getReportDiscordTitle() {
        return reportDiscordTitle;
    }

    public List<Map<String, Serializable>> getReportDiscordFields() {
        return reportDiscordFields;
    }

    public String getReportId() {
        return reportId;
    }

    public String getReportToken() {
        return reportToken;
    }

    public String getReportStaff() {
        return reportStaff;
    }

    public String getReportSuccess() {
        return reportSuccess;
    }

    public String getReportUsage() {
        return reportUsage;
    }

    public Color getReportColor() {
        String colorHex = config.getString("report.discord.color", "#800080");
        Color reportColor = new Color(128, 0, 128);
        if (colorHex.startsWith("#") && colorHex.length() == 7) {
            String r = colorHex.substring(1, 3);
            String g = colorHex.substring(3, 5);
            String b = colorHex.substring(5);

            int red = Parser.getDecimal(r);
            int green = Parser.getDecimal(g);
            int blue = Parser.getDecimal(b);
            reportColor = new Color(Math.min(red, 255), Math.min(green, 255), Math.min(blue, 255));
        }
        return reportColor;
    }

    public Color getBanColor() {
        String colorHex = config.getString("format.ban-color", "#800080");
        Color banColor = new Color(128, 0, 128);
        if (colorHex.startsWith("#") && colorHex.length() == 7) {
            String r = colorHex.substring(1, 3);
            String g = colorHex.substring(3, 5);
            String b = colorHex.substring(5);

            int red = Parser.getDecimal(r);
            int green = Parser.getDecimal(g);
            int blue = Parser.getDecimal(b);
            banColor = new Color(Math.min(red, 255), Math.min(green, 255), Math.min(blue, 255));
        }
        return banColor;
    }

    public Color getIpBanColor() {
        String colorHex = config.getString("format.ipban-color", "#800080");
        Color ipBanColor = new Color(128, 0, 128);
        if (colorHex.startsWith("#") && colorHex.length() == 7) {
            String r = colorHex.substring(1, 3);
            String g = colorHex.substring(3, 5);
            String b = colorHex.substring(5);

            int red = Parser.getDecimal(r);
            int green = Parser.getDecimal(g);
            int blue = Parser.getDecimal(b);
            ipBanColor = new Color(Math.min(red, 255), Math.min(green, 255), Math.min(blue, 255));
        }
        return ipBanColor;
    }

    public Color getJailColor() {
        String colorHex = config.getString("format.jail-color", "#800080");
        Color jailColor = new Color(128, 0, 128);
        if (colorHex.startsWith("#") && colorHex.length() == 7) {
            String r = colorHex.substring(1, 3);
            String g = colorHex.substring(3, 5);
            String b = colorHex.substring(5);

            int red = Parser.getDecimal(r);
            int green = Parser.getDecimal(g);
            int blue = Parser.getDecimal(b);
            jailColor = new Color(Math.min(red, 255), Math.min(green, 255), Math.min(blue, 255));
        }
        return jailColor;
    }

    public Color getKickColor() {
        String colorHex = config.getString("format.kick-color", "#800080");
        Color kickColor = new Color(128, 0, 128);
        if (colorHex.startsWith("#") && colorHex.length() == 7) {
            String r = colorHex.substring(1, 3);
            String g = colorHex.substring(3, 5);
            String b = colorHex.substring(5);

            int red = Parser.getDecimal(r);
            int green = Parser.getDecimal(g);
            int blue = Parser.getDecimal(b);
            kickColor = new Color(Math.min(red, 255), Math.min(green, 255), Math.min(blue, 255));
        }
        return kickColor;
    }

    public Color getMuteColor() {
        String colorHex = config.getString("format.mute-color", "#800080");
        Color muteColor = new Color(128, 0, 128);
        if (colorHex.startsWith("#") && colorHex.length() == 7) {
            String r = colorHex.substring(1, 3);
            String g = colorHex.substring(3, 5);
            String b = colorHex.substring(5);

            int red = Parser.getDecimal(r);
            int green = Parser.getDecimal(g);
            int blue = Parser.getDecimal(b);
            muteColor = new Color(Math.min(red, 255), Math.min(green, 255), Math.min(blue, 255));
        }
        return muteColor;
    }

    public Color getWarnColor() {
        String colorHex = config.getString("format.warn-color", "#800080");
        Color warnColor = new Color(128, 0, 128);
        if (colorHex.startsWith("#") && colorHex.length() == 7) {
            String r = colorHex.substring(1, 3);
            String g = colorHex.substring(3, 5);
            String b = colorHex.substring(5);

            int red = Parser.getDecimal(r);
            int green = Parser.getDecimal(g);
            int blue = Parser.getDecimal(b);
            warnColor = new Color(Math.min(red, 255), Math.min(green, 255), Math.min(blue, 255));
        }
        return warnColor;
    }

    public String getStaffPrefix() {
        return staffPrefix;
    }

    public String getStaffFormat() {
        return Parser.color(staffFormat);
    }

    public Configuration getConfig() {
        return config;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getWebhookID() {
        return webhookID;
    }

    public String getWebhookToken() {
        return webhookToken;
    }

    public List<Map<String, Serializable>> getFields() {
        return fields;
    }

    public boolean isDisplayDate() {
        return displayDate;
    }

    public String getFooter() {
        return footer;
    }

    public List<ServerInfo> getDisabledServers() {
        return disabledServers;
    }

    public boolean isDisabledServer(String name) {
        for (ServerInfo server : disabledServers) {
            if (server.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}
