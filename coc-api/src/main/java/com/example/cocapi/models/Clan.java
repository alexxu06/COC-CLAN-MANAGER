package com.example.cocapi.models;

import java.sql.Timestamp;
import java.util.List;

public class Clan {
    private String tag;
    private String name;
    private Timestamp lastChecked;
    private List<Player> memberList;

    public Clan() {
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getLastChecked() {
        return lastChecked;
    }

    public void setLastChecked(Timestamp lastChecked) {
        this.lastChecked = lastChecked;
    }

    public List<Player> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Player> memberList) {
        PlayerClan clan = new PlayerClan();
        clan.setTag(tag);
        for (Player member : memberList) {
            member.setClan(clan);
        }
        this.memberList = memberList;
    }
}
