package net.valux.legacy.proxy.entity;

import java.util.UUID;

public class Player {

    private UUID uuid;
    private String username;
    private Rank rank;

    public Player(UUID u) {
        this.uuid = u;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String s) {
        this.username = s;
    }

    public Rank getRank() {
        return this.rank;
    }

    public void setRank(Rank r) {
        this.rank = r;
    }

}
