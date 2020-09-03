package net.valux.legacy.proxy.listener;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyPing implements Listener {

    public static TextComponent text = new TextComponent("§b§lValux §f* §7Follow us §b@ValuxNetwork§7. (LEGACY)            §f* §2§lEU Proxy §f* §aOnline Shop - store.valux.net :)");

    @EventHandler
    public void onProxyPing(ProxyPingEvent event) {

        ServerPing ping = event.getResponse();

        ping.setDescriptionComponent(text);

        ServerPing.Players players = ping.getPlayers();
        players.setMax(players.getOnline() + 1);
        ping.setPlayers(players);

        event.setResponse(ping);

    }

}
