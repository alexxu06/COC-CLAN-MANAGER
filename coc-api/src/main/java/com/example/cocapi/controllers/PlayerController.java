package com.example.cocapi.controllers;

import com.example.cocapi.models.Player;
import com.example.cocapi.models.war.WarResponse;
import com.example.cocapi.proxies.PlayerWarProxy;
import com.example.cocapi.repository.WarRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class PlayerController {
    private final PlayerWarProxy playerWarProxy;
    private final WarRepository warRepository;

    public PlayerController(PlayerWarProxy playerProxy, WarRepository warRepository) {
        this.playerWarProxy = playerProxy;
        this.warRepository = warRepository;
    }

//    @GetMapping("/player")
//    public Mono<WarResponse> getClan(@RequestParam String tag) {
//        return playerWarProxy.getWars(tag);
//    }

    @GetMapping("/player")
    public Player getClan(@RequestParam String tag) {
        return warRepository.retrieveWarStats(tag);
//        return playerWarProxy.getWars(tag);
    }
}
