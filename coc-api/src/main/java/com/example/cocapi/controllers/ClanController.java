package com.example.cocapi.controllers;

import com.example.cocapi.models.Clan;
import com.example.cocapi.models.Player;
import com.example.cocapi.proxies.ClanProxy;
import com.example.cocapi.repository.WarRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ClanController {
    private final ClanProxy clanProxy;
    private final WarRepository warRepository;

    public ClanController(ClanProxy clanProxy, WarRepository warRepository) {
        this.clanProxy = clanProxy;
        this.warRepository = warRepository;
    }

    @GetMapping("/clan")
    public Mono<Clan> getClan(@RequestParam String tag) {
        return clanProxy.createClanObject(tag)
                .doOnNext(clan -> {
                    for (Player player : clan.getMemberList()) {
                        warRepository.storePlayer(player);
                    }
                });
    }
}
