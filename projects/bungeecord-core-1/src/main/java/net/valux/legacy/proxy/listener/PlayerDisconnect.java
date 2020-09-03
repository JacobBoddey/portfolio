package net.valux.legacy.proxy.listener;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.valux.legacy.proxy.Proxy;

public class PlayerDisconnect implements Listener {

    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        Proxy.getMessagingService().sendMessage("/network/player/leave", "{uuid: " + player.getUniqueId().toString() + ", username: " + player.getName() + ", proxy: " + "EU:LEGACY:PROXY:1" + "}");
        Proxy.unloadPlayer(player.getUniqueId());
    }

}
