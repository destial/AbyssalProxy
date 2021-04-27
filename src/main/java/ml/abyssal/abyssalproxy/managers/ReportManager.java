package ml.abyssal.abyssalproxy.managers;

import ml.abyssal.abyssalproxy.AbyssalProxy;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReportManager {
    private final List<String> reportBanned;
    private final String bannedMessage;
    private final String userBanned;
    private final String userUnbanned;
    private final String banUsage;
    private final String unbanUsage;
    private final List<String> defaultReasons;

    private final Configuration config;
    public ReportManager(Configuration config) {
        reportBanned = new ArrayList<>();
        List<String> banned = config.getStringList("report-banned");
        reportBanned.addAll(banned);

        bannedMessage = ChatColor.translateAlternateColorCodes('&', config.getString("banned-message"));
        userBanned = ChatColor.translateAlternateColorCodes('&', config.getString("user-banned"));
        userUnbanned = ChatColor.translateAlternateColorCodes('&', config.getString("user-unbanned"));
        banUsage = ChatColor.translateAlternateColorCodes('&', config.getString("ban-usage"));
        unbanUsage = ChatColor.translateAlternateColorCodes('&', config.getString("unban-usage"));

        defaultReasons = config.getStringList("default-report-reasons");

        this.config = config;
    }

    public List<String> getReportBanned() {
        return reportBanned;
    }

    public void add(ProxiedPlayer player) {
        reportBanned.add(player.getName());
        config.set("report-banned", reportBanned);
        save();
    }

    public void remove(String name) {
        reportBanned.remove(name);
        config.set("report-banned", reportBanned);
        save();
    }

    public void save() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(AbyssalProxy.getInstance().getDataFolder(), "reports.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isReportBanned(ProxiedPlayer player) {
        return reportBanned.contains(player.getName());
    }

    public List<String> getDefaultReasons() {
        return defaultReasons;
    }

    public String getBannedMessage() {
        return bannedMessage;
    }

    public String getBanUsage() {
        return banUsage;
    }

    public String getUnbanUsage() {
        return unbanUsage;
    }

    public String getUserBanned() {
        return userBanned;
    }

    public String getUserUnbanned() {
        return userUnbanned;
    }
}
