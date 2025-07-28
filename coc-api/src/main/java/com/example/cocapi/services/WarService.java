package com.example.cocapi.services;

import com.example.cocapi.models.Player;
import com.example.cocapi.models.war.Attack;
import com.example.cocapi.models.war.War;
import com.example.cocapi.proxies.WarProxy;
import com.example.cocapi.repository.WarRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.sql.Array;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WarService {
    private final WarProxy warProxy;
    private final WarRepository warRepository;
    private final DateConvertService dateConvertService;

    public WarService(WarProxy warProxy, WarRepository warRepository,
                      DateConvertService dateConvertService) {
        this.warProxy = warProxy;
        this.warRepository = warRepository;
        this.dateConvertService = dateConvertService;
    }

    // retrieves war stats in bulk (used when searching by clan)
    public Flux<Player> retrieveWarStats(List<String> playerTags) {
        List<Player> playersInDatabase = warRepository.findPlayers(playerTags);

        Set<String> playersInDatabaseTags = playersInDatabase.stream()
                .map(Player::getTag)
                .collect(Collectors.toSet());

        List<String> missingPlayerTags = playerTags.stream()
                .filter(tag -> !playersInDatabaseTags.contains(tag))
                .toList();

        Flux<Player> missingPlayers = Flux.fromIterable(missingPlayerTags)
                // Unfortunately COC API only allows 1 player info per API request aka cannot batch ;(
                .flatMap(tag -> warProxy.getWars(tag)
                        .collectList()
                        .publishOn(Schedulers.boundedElastic())
                        .map(wars -> calculateWarStats(tag, wars)));

        return Flux.concat(Flux.fromIterable(playersInDatabase), missingPlayers);
    }

    // Add up stars, percentage, num attacks and total attacks from all wars for a given player
    private Player calculateWarStats(String tag, List<War> wars) {
        int totalStar = 0;
        int totalPercentage = 0;
        int numAttacks = 0;
        int totalAttacks = 0;
        // get first war's endtime (most recent)
        Timestamp mostRecentWarEndDateTime = dateConvertService.convertDate(wars);

        for (War war : wars) {
            numAttacks += war.getAttacks().size();
            // random are normal wars (2 attacks). Otherwise, cwl (1 attack)
            totalAttacks += (war.getWar_data().getType().equals("random")) ? 2 : 1;
            for (Attack attack : war.getAttacks()) {
                totalStar += attack.getStars();
                totalPercentage += attack.getDestructionPercentage();
            }
        }

        warRepository.storePlayers(tag, totalStar, totalPercentage,
                numAttacks, totalAttacks, mostRecentWarEndDateTime);

        Player player = new Player();
        player.setTag(tag);
        player.setTotalStars(totalStar);
        player.setTotalPercentage(totalPercentage);
        player.setNumAttacks(numAttacks);
        player.setTotalAttacks(totalAttacks);

        return player;
    }

    // will update all players at midnight with any wars that ended the previous day
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateWars() {
        List<Player> allPlayers = warRepository.findAll();

        Flux.fromIterable(allPlayers)
                .flatMap(player -> warProxy.getSingleWar(player.getTag())
                        .collectList()
                        .publishOn(Schedulers.boundedElastic())
                        // update war stats
                        .map(wars -> {
                            if (!wars.isEmpty()) {
                                War latestWar = wars.get(0);
                                Timestamp warEndTime = dateConvertService.convertDate(Arrays.asList(latestWar));
                                Timestamp storedWarEndTime = warRepository.getLatestWarDateTime(player.getTag());

                                if (storedWarEndTime == null || warEndTime.after(storedWarEndTime)) {
                                    calculateWarStats(player.getTag(), Arrays.asList(latestWar));
                                }
                            }

                            return Mono.empty();
                        }))
                .subscribe();


    }
}
