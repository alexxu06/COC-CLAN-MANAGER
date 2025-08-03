package com.example.cocapi.controllers;

import com.example.cocapi.models.Clan;
import com.example.cocapi.models.Player;
import com.example.cocapi.proxies.ClanProxy;
import com.example.cocapi.services.WarService;
import org.springframework.web.bind.annotation.*;

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
    public Clan getClan(@RequestParam String tag) {
        Clan clan = clanProxy.createClanObject(tag);
        List<Player> members = clan.getMemberList();

        List<String> playerTags = members
                .stream()
                .map(Player::getTag)
                .toList();

        // Difficult to combine war stats with players within warService due to WarRepository RowMapper
        // which returns new set of players with only wars stats (no name, donations, etc.)
        // so I'm doing it here
        List<Player> playersWithWarStats = warService.retrieveWarStats(playerTags);

        Map<String, Player> playerTagMap = playersWithWarStats
                .stream()
                .collect(Collectors.toMap(Player::getTag, player -> player));

        for (Player player : members) {
            Player stats = playerTagMap.get(player.getTag());
            player.setTotalAttacks(stats.getTotalAttacks());
            player.setTotalPercentage(stats.getTotalPercentage());
            player.setTotalStars(stats.getTotalStars());
            player.setNumAttacks(stats.getNumAttacks());
        }

        return clan;
    }
}
