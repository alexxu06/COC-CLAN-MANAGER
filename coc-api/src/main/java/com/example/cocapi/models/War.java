package com.example.cocapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class War {
    private String teamSize;
    @JsonProperty("clan")
    private Clan opponentClan;

    public War() {

    }

    public String getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(String teamSize) {
        this.teamSize = teamSize;
    }

    public Clan getOpponentClan() {
        return opponentClan;
    }

    public void setOpponentClan(Clan opponentClan) {
        this.opponentClan = opponentClan;
    }
}
