package com.example.cocapi.controllers;

import com.example.cocapi.models.Player;
import com.example.cocapi.models.war.War;
import com.example.cocapi.proxies.PlayerProxy;
import com.example.cocapi.proxies.WarProxy;
import com.example.cocapi.services.TagService;
import com.example.cocapi.services.WarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.List;

@RestController
public class PlayerController {
    private final WarService warService;
    private final PlayerProxy playerProxy;
    private final WarProxy warProxy;
    private final TagService tagService;

    public PlayerController(WarService warService, PlayerProxy playerProxy, WarProxy warProxy, TagService tagService) {
        this.warService = warService;
        this.playerProxy = playerProxy;
        this.warProxy = warProxy;
        this.tagService = tagService;
    }

    @GetMapping("/player-wars")
    public ResponseEntity<?> getPlayerWars(@RequestParam String tag) {

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(warProxy.getWars(tagService.prepTag(tag)));
    }
}
