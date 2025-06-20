package com.example.cocapi.controllers;

import com.example.cocapi.models.war.WarResponse;
import com.example.cocapi.proxies.PlayerWarProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class PlayerController {
    private final PlayerWarProxy playerWarProxy;

    public PlayerController(PlayerWarProxy playerProxy) {
        this.playerWarProxy = playerProxy;
    }

    @GetMapping("/player")
    public Mono<WarResponse> getClan(@RequestParam String tag) {
        return playerWarProxy.getWars(tag);
    }
}
