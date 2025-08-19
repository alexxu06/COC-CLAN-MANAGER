package com.example.cocapi.services;

import com.example.cocapi.models.Clan;
import com.example.cocapi.models.Player;
import com.example.cocapi.proxies.ClanProxy;
import com.example.cocapi.repository.ClanRepository;
import com.example.cocapi.repository.WarRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClanService {
    private final ClanRepository clanRepository;
    private final ClanProxy clanProxy;
    private final WarService warService;
    private final WarRepository warRepository;

    public ClanService(ClanRepository clanRepository, ClanProxy clanProxy, WarService warService, WarRepository warRepository) {
        this.clanRepository = clanRepository;
        this.clanProxy = clanProxy;
        this.warService = warService;
        this.warRepository = warRepository;
    }

    public Clan getClan(String clanTag) {
        Clan clan;
        List<Player> members = List.of();

        // fetch clan from database, if empty then add to database
        try {
            clan = clanRepository.findClan(clanTag);
            members = warRepository.findPlayersByClan(clan.getTag());
        } catch (EmptyResultDataAccessException e) {
            clan = clanProxy.createClanObject(clanTag);
            clanRepository.storeClan(clan);
            members = clan.getMemberList();
        }

        // if accessed in last 24 hours, retrieve from database, otherwise update
        // 24 hours because wars are around 1-2 days
        if (hasEnoughTimePassed(24, clan)) {
            clan.setMemberList(updateMembers(members));
            clanRepository.updateTime(clan.getTag());
        } else {
            clan.setMemberList(members);
        }

        return clan;
    }

    public Boolean hasEnoughTimePassed(int hours, Clan clan) {
        if (clan.getLastChecked() == null) {
            return true;
        }

        Instant lastChecked = clan.getLastChecked().toInstant();
        Instant now = Instant.now();
        Duration duration = Duration.between(lastChecked, now);
        System.out.println(duration.toHours());
        return duration.toHours() >= hours;
    }

    public List<Player> updateMembers(List<Player> members) {
        // no members in the clan
        if (members.isEmpty()) {
            return members;
        }

        List<Player> updatedMembers = warService.retrieveWarStats(members);

        return updatedMembers;
    }

}
