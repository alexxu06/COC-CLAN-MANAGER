package com.example.cocapi.models.war;

public class MemberData {
    private String tag;
    private String name;
    private int townhallLevel;
    private int mapPosition;

    public MemberData() {}

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

    public int getTownhallLevel() {
        return townhallLevel;
    }

    public void setTownhallLevel(int townHallLevel) {
        this.townhallLevel = townHallLevel;
    }

    public int getMapPosition() {
        return mapPosition;
    }

    public void setMapPosition(int mapPosition) {
        this.mapPosition = mapPosition;
    }
}
