package ml.abyssal.abyssalproxy.commands;

import ml.abyssal.abyssalproxy.AbyssalProxy;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ReloadCommand extends Command {
    public ReloadCommand(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            AbyssalProxy.getInstance().reload();
            sender.sendMessage(TextComponent.fromLegacyText(
                    ChatColor.translateAlternateColorCodes(
                            '&',
                            "&aReloaded &5Abyssal Proxy")));
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (!player.hasPermission("abyssal.proxy.reload")) return;

        AbyssalProxy.getInstance().reload();
        player.sendMessage(
            TextComponent.fromLegacyText(
                ChatColor.translateAlternateColorCodes(
                    '&',
                    "&aReloaded &5Abyssal Proxy")));
    }
}
