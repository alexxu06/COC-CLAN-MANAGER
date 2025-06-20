package com.example.cocapi.models.war;

import java.util.List;

public class WarResponse {
    private List<War> items;

    public WarResponse() {}

    public List<War> getItems() {
        return items;
    }

    public void setItems(List<War> items) {
        this.items = items;
    }
}
