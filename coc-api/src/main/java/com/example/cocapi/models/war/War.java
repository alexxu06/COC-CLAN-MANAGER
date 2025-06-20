package com.example.cocapi.models.war;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class War {
    @JsonProperty("war_data")
    private WarData warData;
    private List<Attack> attacks;

    public War() {

    }

    public WarData getWar_data() {
        return warData;
    }

    public List<Attack> getAttacks() {
        return attacks;
    }

    public void setAttacks(List<Attack> attacks) {
        this.attacks = attacks;
    }

    public void setWar_data(WarData warData) {
        this.warData = warData;
    }
}
