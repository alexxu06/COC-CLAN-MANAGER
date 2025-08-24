package com.example.cocapi.services;

import com.example.cocapi.models.Clan;
import com.example.cocapi.models.Player;
import com.example.cocapi.proxies.ClanProxy;
import com.example.cocapi.repository.ClanRepository;
import com.example.cocapi.repository.PlayerRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClanService {
    private final ClanRepository clanRepository;
    private final ClanProxy clanProxy;
    private final WarService warService;
    private final PlayerRepository playerRepository;

    public ClanService(ClanRepository clanRepository, ClanProxy clanProxy, WarService warService, PlayerRepository playerRepository) {
        this.clanRepository = clanRepository;
        this.clanProxy = clanProxy;
        this.warService = warService;
        this.playerRepository = playerRepository;
    }

    @Transactional
    public Clan getClan(String clanTag) {
        Clan clan = clanProxy.createClanObject(clanTag);
        List<Player> members = clan.getMemberList();

        // fetch clan from database, if empty then add to database
        try {
            clan.setLastChecked(clanRepository.findClan(clanTag).getLastChecked());
        } catch (EmptyResultDataAccessException e) {
            clanRepository.storeClan(clan);
        }

        removePlayersNotInClan(clan);

        // if accessed in last 24 hours, retrieve from database, otherwise update
        // 24 hours because wars are around 1-2 days
        if (hasEnoughTimePassed(24, clan)) {
            updateMembers(members);
            clanRepository.updateTime(clanTag);
        } else {
            AddWarStats(members, clanTag);
        }

        return clan;
    }

    public void AddWarStats(List<Player> members, String clanTag) {
        List<Player> warStats = playerRepository.findPlayersByClan("#" + clanTag);
        Map<String, Player> warStatsMap = warStats
                .stream()
                .collect(Collectors.toMap(Player::getTag, player -> player));

        for (Player player : members) {
            Player stats = warStatsMap.get(player.getTag());
            player.setTotalAttacks(stats.getTotalAttacks());
            player.setTotalPercentage(stats.getTotalPercentage());
            player.setTotalStars(stats.getTotalStars());
            player.setNumAttacks(stats.getNumAttacks());
            player.setWarEndTime(stats.getWarEndTime());
        }
    }

    public Boolean hasEnoughTimePassed(int hours, Clan clan) {
        if (clan.getLastChecked() == null) {
            return true;
        }

        Instant lastChecked = clan.getLastChecked().toInstant();
        Instant now = Instant.now();
        Duration duration = Duration.between(lastChecked, now);

        return duration.toHours() >= hours;
    }

    public void updateMembers(List<Player> members) {
        // no members in the clan
        if (members.isEmpty()) {
            return;
        }

        warService.retrieveWarStats(members);
    }

    // if any players have left the clan, update database accordingly
    public void removePlayersNotInClan(Clan clan) {
        List<Player> members = clan.getMemberList();
        List<Player> currentMembers = playerRepository.findPlayersByClan(clan.getTag());;
        List<String> notInClan = new ArrayList<>();
        List<String> switchedClan = new ArrayList<>();

        // remove players who have left the clan by setting clan_tag to null
        for (Player member : members) {
            if (!currentMembers.contains(member)) {
                notInClan.add(member.getTag());
            }
        }

        // if player was in another clan previously, switch clan_tag
        for (Player currentMember: currentMembers) {
            if (!members.contains(currentMember)) {
                switchedClan.add(currentMember.getTag());
            }
        }

        if (!notInClan.isEmpty()) {
            playerRepository.removePlayersFromCLan(notInClan);
        }

        if (!switchedClan.isEmpty()) {
            playerRepository.updateClanTag(switchedClan, clan.getTag());
        }
    }

}
