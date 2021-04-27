package ml.abyssal.abyssalproxy.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import ml.abyssal.abyssalproxy.AbyssalProxy;
import ml.abyssal.abyssalproxy.events.JailEvent;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PluginMessageListener implements Listener {
    public PluginMessageListener() {}

    @EventHandler
    public void onMessageReceived(PluginMessageEvent e) {
        if (!e.getTag().equalsIgnoreCase(AbyssalProxy.CHANNEL)) return;

        ByteArrayDataInput input = ByteStreams.newDataInput(e.getData());
        ProxyServer proxyServer = ProxyServer.getInstance();
        String type = input.readUTF();

        if (type.equalsIgnoreCase("litebans")) {
            String playerExecuting = input.readUTF();
            String commandToExecute = input.readUTF();

            ProxiedPlayer proxiedPlayer = proxyServer.getPlayer(playerExecuting);
            if (proxiedPlayer != null) {
                proxyServer.getPluginManager().dispatchCommand(proxiedPlayer, commandToExecute);
            }
            return;
        }

        if (type.equalsIgnoreCase("cmi")) {
            String playerExecuting = input.readUTF();
            String targetedPlayer = input.readUTF();
            String time = input.readUTF();
            String reason = input.readUTF();

            proxyServer.getPluginManager().callEvent(new JailEvent(playerExecuting, targetedPlayer, time, reason));
            return;
        }
    }
}
