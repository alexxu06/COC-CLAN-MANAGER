package com.example.cocapi.models.war;

import java.util.Map;

public class Attack {
    private int stars;
    private int destructionPercentage;
    private Map<String, String> defender;

    public Attack() {}

    public int getStars() {
        return stars;
    }

    public int getDestructionPercentage() {
        return destructionPercentage;
    }

    public Map<String, String> getDefender() {
        return defender;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public void setDestructionPercentage(int destructionPercentage) {
        this.destructionPercentage = destructionPercentage;
    }

    public void setDefender(Map<String, String> defender) {
        this.defender = defender;
    }
}
