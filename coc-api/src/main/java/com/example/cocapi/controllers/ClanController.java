package com.example.cocapi.controllers;

import com.example.cocapi.models.Clan;
import com.example.cocapi.proxies.ClanProxy;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class ClanController {
    private final ClanProxy clanProxy;

    public ClanController(ClanProxy clanProxy) {
        this.clanProxy = clanProxy;
    }

    @GetMapping("/clan")
    public Mono<Clan> getClan(@RequestParam String tag) {
        return clanProxy.createClan(tag);
    }

    @GetMapping("/test")
    public String getString() {
        return "Its working";
    }
}
