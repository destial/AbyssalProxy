package ml.abyssal.abyssalproxy;

import com.google.common.io.ByteStreams;
import litebans.api.Events;
import ml.abyssal.abyssalproxy.commands.ReloadCommand;
import ml.abyssal.abyssalproxy.commands.ReportBanCommand;
import ml.abyssal.abyssalproxy.commands.ReportCommand;
import ml.abyssal.abyssalproxy.commands.ReportUnbanCommand;
import ml.abyssal.abyssalproxy.listener.*;
import ml.abyssal.abyssalproxy.managers.ConfigManager;
import ml.abyssal.abyssalproxy.managers.DiscordManager;
import ml.abyssal.abyssalproxy.managers.ReportManager;
import ml.abyssal.abyssalproxy.managers.WebhookManager;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;

public final class AbyssalProxy extends Plugin {

    private static AbyssalProxy instance;
    private WebhookManager webhookManager;
    private ConfigManager configManager;
    private ReportManager reportManager;
    private DiscordManager discordManager;
    public static final String CHANNEL = "abyssal:proxy";

    @Override
    public void onEnable() {
        instance = this;
        getProxy().registerChannel(CHANNEL);
        registerListeners();
        reload();
        registerCommands();
        webhookManager = new WebhookManager();
        discordManager = new DiscordManager();

        getLogger().info("Enabling Abyssal Proxy " + getVersion());
    }

    @Override
    public void onDisable() {
        getProxy().unregisterChannel(CHANNEL);
        if (DiscordManager.getJDA() != null) {
            DiscordManager.getJDA().shutdown();
        }
        unregisterListeners();
        unregisterCommands();
        getLogger().info("Disabling Abyssal Proxy " + getVersion());
    }

    public void reload() {
        registerConfig();
        getLogger().info("Reloaded Abyssal Proxy");
    }

    private void registerListeners() {
        Events.get().register(new LiteBansListener());
        getProxy().getPluginManager().registerListener(this, new PluginMessageListener());
        getProxy().getPluginManager().registerListener(this, new PunishmentListener());
        getProxy().getPluginManager().registerListener(this, new StaffMsgListener());
        getProxy().getPluginManager().registerListener(this, new ReportListener());
    }

    private void registerCommands() {
        getProxy().getPluginManager().registerCommand(this, new ReloadCommand("proxyreload", "abyssal.proxy.reload", "preload"));
        getProxy().getPluginManager().registerCommand(this, new ReportCommand("report"));
        getProxy().getPluginManager().registerCommand(this, new ReportBanCommand("reportban"));
        getProxy().getPluginManager().registerCommand(this, new ReportUnbanCommand("reportunban"));
    }

    private void registerConfig(){
        if (!getDataFolder().exists()) getDataFolder().mkdir();
        File configFile = new File(getDataFolder(), "config.yml");
        File reportFile = new File(getDataFolder(), "reports.yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                try (InputStream is = getResourceAsStream("config.yml");
                     OutputStream os = new FileOutputStream(configFile)) {
                    ByteStreams.copy(is, os);
                }
            } catch (IOException e) {
                getLogger().severe("Unable to create configuration file");
            }
        }
        if (!reportFile.exists()) {
            try {
                configFile.createNewFile();
                try (InputStream is = getResourceAsStream("reports.yml");
                     OutputStream os = new FileOutputStream(reportFile)) {
                    ByteStreams.copy(is, os);
                }
            } catch (IOException e) {
                getLogger().severe("Unable to create reports file");
            }
        }

        try {
            configManager = new ConfigManager(ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml")));
            reportManager = new ReportManager(ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "reports.yml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void unregisterListeners() {
        getProxy().getPluginManager().unregisterListeners(this);
    }

    private void unregisterCommands() {
        getProxy().getPluginManager().unregisterCommands(this);
    }

    public static AbyssalProxy getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public WebhookManager getWebhookManager() {
        return webhookManager;
    }

    public ReportManager getReportManager() {
        return reportManager;
    }

    public DiscordManager getDiscordManager() {
        return discordManager;
    }

    public String getVersion() {
        return "v0.6";
    }
}
