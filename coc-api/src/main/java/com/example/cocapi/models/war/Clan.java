package com.example.cocapi.models.war;

import java.util.Map;

public class Clan {
    private String tag;
    private String name;
    private Map<String, String> badgeUrls;
    private int clanLevel;
    private int stars;
    private int destructionPercentage;

    public Clan() {}

    public String getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getBadgeUrls() {
        return badgeUrls;
    }

    public int getStars() {
        return stars;
    }

    public int getDestructionPercentage() {
        return destructionPercentage;
    }

    public int getClanLevel() {
        return clanLevel;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setDestructionPercentage(int destructionPercentage) {
        this.destructionPercentage = destructionPercentage;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public void setClanLevel(int clanLevel) {
        this.clanLevel = clanLevel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBadgeUrls(Map<String, String> badgeUrls) {
        this.badgeUrls = badgeUrls;
    }
}
