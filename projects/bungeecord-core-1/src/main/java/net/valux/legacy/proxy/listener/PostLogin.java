package net.valux.legacy.proxy.listener;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.valux.legacy.proxy.Proxy;
import net.valux.legacy.proxy.entity.Player;

public class PostLogin implements Listener {

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {

        ProxiedPlayer proxiedPlayer = event.getPlayer();
        Proxy.getMessagingService().sendMessage("/network/player/join", "{uuid: " + proxiedPlayer.getUniqueId().toString() + ", username: " + proxiedPlayer.getName() + ", proxy: " + "EU:LEGACY:PROXY:1" + "}");
        Player p = Proxy.loadPlayer(proxiedPlayer.getUniqueId());
        p.setUsername(proxiedPlayer.getName());

    }

}
