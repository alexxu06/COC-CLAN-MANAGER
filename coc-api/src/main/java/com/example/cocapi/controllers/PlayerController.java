package com.example.cocapi.controllers;

import com.example.cocapi.models.Player;
import com.example.cocapi.models.war.War;
import com.example.cocapi.proxies.PlayerProxy;
import com.example.cocapi.proxies.WarProxy;
import com.example.cocapi.services.WarService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class PlayerController {
    private final WarService warService;
    private final PlayerProxy playerProxy;
    private final WarProxy warProxy;

    public PlayerController(WarService warService, PlayerProxy playerProxy, WarProxy warProxy) {
        this.warService = warService;
        this.playerProxy = playerProxy;
        this.warProxy = warProxy;
    }

    @GetMapping("/player")
    public Player getPlayer(@RequestParam String tag) {
        Player player = playerProxy.getPlayer(tag);
        Player stats = warService.retrieveWarStats(Arrays.asList(tag)).get(0);

        player.setTotalAttacks(stats.getTotalAttacks());
        player.setTotalPercentage(stats.getTotalPercentage());
        player.setTotalStars(stats.getTotalStars());
        player.setNumAttacks(stats.getNumAttacks());
        player.setWars(warProxy.getWars(tag));

        return player;
    }
}
