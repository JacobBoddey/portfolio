package net.valux.legacy.proxy.listener;

import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class PreLogin implements Listener {

    @EventHandler
    public void onPreLogin(PreLoginEvent event) {
        UUID uuid = event.getConnection().getUniqueId();
        //Check if banned
    }

}
