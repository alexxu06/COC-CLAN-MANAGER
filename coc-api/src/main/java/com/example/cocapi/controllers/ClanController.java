package com.example.cocapi.controllers;

import com.example.cocapi.models.Clan;
import com.example.cocapi.services.ClanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClanController {
    private final ClanService clanService;

    public ClanController(ClanService clanService) {
        this.clanService = clanService;
    }

    @GetMapping("/clan")
    public ResponseEntity<Clan> getClan(@RequestParam String tag) {
        Clan clan = clanService.getClan(tag);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(clan);
    }
}
