package com.example.cocapi.models;

public class Player {
    private String name;
    private int donations;
    private int clanRank;

    public Player() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDonations() {
        return donations;
    }

    public void setDonations(int donations) {
        this.donations = donations;
    }

    public int getClanRank() {
        return clanRank;
    }

    public void setClanRank(int clanRank) {
        this.clanRank = clanRank;
    }
}
