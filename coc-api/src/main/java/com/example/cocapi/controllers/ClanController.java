package com.example.cocapi.controllers;

import com.example.cocapi.models.Clan;
import com.example.cocapi.models.Player;
import com.example.cocapi.proxies.ClanProxy;
import com.example.cocapi.services.WarService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ClanController {
    private final ClanProxy clanProxy;
    private final WarService warService;

    public ClanController(ClanProxy clanProxy, WarService warService) {
        this.clanProxy = clanProxy;
        this.warService = warService;
    }

    @GetMapping("/clan")
    public Mono<Clan> getClan(@RequestParam String tag) {
        return clanProxy.createClanObject(tag)
                .flatMap(clan -> {
                    List<String> playerTags = clan.getMemberList().stream()
                            .map(Player::getTag)
                            .collect(Collectors.toList());
                    return warService.retrieveWarStats(playerTags)
                            .collectList()
                            .map(player -> {
                                Map<String, Player> playerWarMap = player.stream()
                                        .collect(Collectors.toMap(Player::getTag, p -> p));

                                for (Player currPlayer : clan.getMemberList()) {
                                    Player stats = playerWarMap.get(currPlayer.getTag());
                                    currPlayer.setTotalAttacks(stats.getTotalAttacks());
                                    currPlayer.setTotalPercentage(stats.getTotalPercentage());
                                    currPlayer.setTotalStars(stats.getTotalStars());
                                    currPlayer.setNumAttacks(stats.getNumAttacks());
                                }

                                return clan;
                            });
                });
    }
}
