package com.example.cocapi.models.war;

public class WarData {
    private int teamSize;
    private String startTime;
    private String endTime;
    private Clan clan;
    private Clan opponent;
    private String type;

    public WarData() {}

    public int getTeamSize() {
        return teamSize;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getType() {
        return type;
    }

    public Clan getOpponent() {
        return opponent;
    }

    public Clan getClan() {
        return clan;
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOpponent(Clan opponent) {
        this.opponent = opponent;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
