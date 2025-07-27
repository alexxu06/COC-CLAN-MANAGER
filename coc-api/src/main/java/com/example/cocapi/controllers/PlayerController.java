package com.example.cocapi.controllers;

import com.example.cocapi.models.Player;
import com.example.cocapi.models.war.War;
import com.example.cocapi.proxies.WarProxy;
import com.example.cocapi.services.WarService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Arrays;

@RestController
public class PlayerController {
    private final WarProxy playerWarProxy;
    private final WarService warService;

    public PlayerController(WarProxy playerProxy, WarService warService) {
        this.playerWarProxy = playerProxy;
        this.warService = warService;
    }

    @GetMapping("/player-wars")
    public Flux<War> getWars(@RequestParam String tag) {
        return playerWarProxy.getWars(tag);
    }

    @GetMapping("/player")
    public Flux<Player> getClan(@RequestParam String tag) {
        return warService.retrieveWarStats(Arrays.asList(tag));
    }
}
