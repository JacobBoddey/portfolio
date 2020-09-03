package net.valux.legacy.proxy.entity;

public class Rank {

    private String id;
    private String tag;
    private boolean staff;

    public Rank(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String s) {
        this.tag = s;
    }

    public boolean isStaff() {
        return this.staff;
    }

    public void setStaff(boolean b) {
        this.staff = b;
    }

}
