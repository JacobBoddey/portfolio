package net.valux.legacy.proxy;

import com.mongodb.client.FindIterable;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.valux.legacy.proxy.db.Mongo;
import net.valux.legacy.proxy.entity.Rank;
import net.valux.legacy.proxy.messaging.Mqtt;
import net.valux.legacy.proxy.cmd.*;
import net.valux.legacy.proxy.entity.Player;
import net.valux.legacy.proxy.listener.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Proxy extends Plugin {

    public static Proxy proxy;
    public static Mqtt mqtt;
    public static Mongo mongo;

    public static ConcurrentHashMap<UUID, Player> players;
    public static ArrayList<Rank> ranks;

    @Override
    public void onEnable() {

        proxy = this;

        players = new ConcurrentHashMap<UUID, Player>();

        mqtt = new Mqtt();
        mongo = new Mongo();

        registerCommands();
        getLogger().info("Registered commands");

        registerListeners();
        getLogger().info("Registered listeners");

        loadRanks();
        getLogger().info("Loaded " + ranks.size() + " ranks from the database");

    }

    public void registerCommands() {
        PluginManager pm = getProxy().getPluginManager();
        pm.registerCommand(this, new ServerCmd());
        pm.registerCommand(this, new AlertCmd());
        pm.registerCommand(this, new SendCmd());
        pm.registerCommand(this, new FindCmd());
        pm.registerCommand(this, new ListCmd());
        pm.registerCommand(this, new KickCmd());
        pm.registerCommand(this, new BanCmd());
    }

    public void registerListeners() {
        PluginManager pm = getProxy().getPluginManager();
        pm.registerListener(this, new PlayerDisconnect());
        pm.registerListener(this, new PostLogin());
        pm.registerListener(this, new ProxyPing());
        pm.registerListener(this, new PreLogin());
    }

    public void loadRanks() {
        ranks = new ArrayList<Rank>();
        Document search = new Document();
        FindIterable<Document> result = getDatabaseService().find(Mongo.Database.SHARED, "ranks", search);
        for (Document d : result) {
            Rank r = new Rank(d.getString("rank"));
            r.setTag(d.getString("tag"));
            r.setStaff(d.getBoolean("staff"));
            ranks.add(r);
        }
    }

    public static Rank getRank(String name) {
        for (Rank r : ranks) {
            if (r.getId().equalsIgnoreCase(name)) {
                return r;
            }
        }
        return null;
    }

    public static Collection<Player> getPlayers() {
        return players.values();
    }

    public static Player getPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public static Player loadPlayer(UUID uuid) {

        Player player = new Player(uuid);

        Document search = new Document("uuid", uuid);
        Document result = getDatabaseService().findOne(Mongo.Database.SHARED, "playerData", search);

        if (result == null) {
            Document info = new Document("uuid", uuid).append("rank", "MEMBER");
            getDatabaseService().insertDocument(Mongo.Database.SHARED, "playerData", info);
            player.setRank(getRank("MEMBER"));
        }
        else {
            player.setRank(getRank(result.getString("rank")));
        }

        players.put(uuid, player);
        return player;

    }

    public static void unloadPlayer(UUID uuid) {
        players.remove(uuid);
    }

    public static void savePlayer(UUID uuid) {

        Player p = players.get(uuid);

    }

    public static Mqtt getMessagingService() {
        return mqtt;
    }

    public static Mongo getDatabaseService() {
        return mongo;
    }

}
