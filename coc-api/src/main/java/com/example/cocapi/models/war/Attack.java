package com.example.cocapi.models.war;

public class Attack {
    private int stars;
    private int destructionPercentage;
    private Defender defender;

    public Attack() {}

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getDestructionPercentage() {
        return destructionPercentage;
    }

    public void setDestructionPercentage(int destructionPercentage) {
        this.destructionPercentage = destructionPercentage;
    }

    public Defender getDefender() {
        return defender;
    }

    public void setDefender(Defender defender) {
        this.defender = defender;
    }
}
