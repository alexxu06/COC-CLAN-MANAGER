package com.example.cocapi.models;

import java.sql.Timestamp;
import java.util.List;

public class Clan {
    private String tag;
    private String name;
    private String description;
    private ClanImg badgeUrls;
    private String clanImg;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ClanImg getBadgeUrls() {
        return badgeUrls;
    }

    public void setBadgeUrls(ClanImg badgeUrls) {
        this.badgeUrls = badgeUrls;
        setClanImg(badgeUrls.getLarge());
    }

    public void setClanImg(String clanImg) {
        this.clanImg = clanImg;
    }

    public String getClanImg() {
        return clanImg;
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
