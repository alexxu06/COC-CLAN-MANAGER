package com.example.cocapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cocapi.proxies.WarProxy;
import com.example.cocapi.services.TagService;

@RestController
public class PlayerController {
    private final WarProxy warProxy;
    private final TagService tagService;

    public PlayerController(WarProxy warProxy, TagService tagService) {
        this.warProxy = warProxy;
        this.tagService = tagService;
    }

    @CrossOrigin
    @GetMapping("/player-wars")
    public ResponseEntity<?> getPlayerWars(@RequestParam String tag,
                                           @RequestParam(required = false, defaultValue = "20") int limit) {

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(warProxy.getWars(tagService.prepTag(tag), limit));
    }
}
