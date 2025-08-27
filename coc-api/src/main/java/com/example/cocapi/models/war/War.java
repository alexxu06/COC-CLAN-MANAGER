package com.example.cocapi.models.war;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class War {
    @JsonProperty("war_data")
    private WarData warData;
    @JsonProperty("member_data")
    private MemberData memberData;
    private List<Attack> attacks;

    public War() {

    }

    public WarData getWar_data() {
        return warData;
    }

    public List<Attack> getAttacks() {
        return attacks;
    }

    public MemberData getMemberData() {
        return memberData;
    }

    public void setMemberData(MemberData memberData) {
        this.memberData = memberData;
    }

    public void setAttacks(List<Attack> attacks) {
        this.attacks = attacks;
    }

    public void setWar_data(WarData warData) {
        this.warData = warData;
    }
}
