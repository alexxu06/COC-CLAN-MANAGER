package com.example.cocapi.models;

import com.example.cocapi.models.war.War;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class Player {
    private String name;
    private String tag;
    private int donations;
    private int clanRank;
    private int totalStars;
    private int totalPercentage;
    private int numAttacks; // number of times player has attacked
    private int totalAttacks; // total attacks player could have done
    private Timestamp warEndTime; // most recent war endTime
    private List<War> wars;
    private String clanTag;
    private PlayerClan clan;

    public Player() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {return tag;}

    public void setTag(String tag) {
        this.tag = tag;
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

    public List<War> getWars() {
        return wars;
    }

    public void setWars(List<War> wars) {
        this.wars = wars;
    }

    // FROM DATABASE
    public int getTotalStars() {
        return totalStars;
    }

    public void setTotalStars(int totalStars) {
        this.totalStars = totalStars;
    }

    public int getTotalPercentage() {
        return totalPercentage;
    }

    public void setTotalPercentage(int totalPercentage) {
        this.totalPercentage = totalPercentage;
    }

    public int getNumAttacks() {
        return numAttacks;
    }

    public void setNumAttacks(int numAttacks) {
        this.numAttacks = numAttacks;
    }

    public int getTotalAttacks() {
        return totalAttacks;
    }

    public void setTotalAttacks(int totalAttacks) {
        this.totalAttacks = totalAttacks;
    }

    public Timestamp getWarEndTime() {
        return warEndTime;
    }

    public void setWarEndTime(Timestamp warEndTime) {
        this.warEndTime = warEndTime;
    }

    public String getClanTag() {
        if (clanTag != null) {
            return clanTag;
        } else {
            return clan.getTag();
        }
    }

    public void setClanTag(String clanTag) {
        this.clanTag = clanTag;
    }

    public PlayerClan getClan() {
        return clan;
    }

    public void setClan(PlayerClan clan) {
        this.clan = clan;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(tag, player.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tag);
    }
}
