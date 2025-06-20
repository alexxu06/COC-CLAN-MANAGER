package com.example.cocapi.models;

import java.util.List;

public class Clan {
    private String tag;
    private String name;
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

    public List<Player> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Player> memberList) {
        this.memberList = memberList;
    }
}
