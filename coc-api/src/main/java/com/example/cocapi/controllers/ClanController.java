package com.example.cocapi.controllers;

import com.example.cocapi.models.Clan;
import com.example.cocapi.services.ClanService;
import com.example.cocapi.services.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClanController {
    private final ClanService clanService;
    private final TagService tagService;

    public ClanController(ClanService clanService, TagService tagService) {
        this.clanService = clanService;
        this.tagService = tagService;
    }

    @GetMapping("/clan")
    public ResponseEntity<Clan> getClan(@RequestParam String tag) {
        Clan clan = clanService.getClan(tagService.prepTag(tag));

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(clan);
    }
}
