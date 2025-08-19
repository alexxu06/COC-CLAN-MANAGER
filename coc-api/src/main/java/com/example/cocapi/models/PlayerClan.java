package com.example.cocapi.models;

// Same as Clan Object, except for player only. I made this to avoid bi-directional relationship
// (which appeared because of differences between /player and /clan endpoints from external api)
// between Player and Clan. I have no idea if this is the best way to deal with this, but it's the
// best solution I can think of...
public class PlayerClan {
    private String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String clanTag) {
        this.tag = clanTag;
    }
}
