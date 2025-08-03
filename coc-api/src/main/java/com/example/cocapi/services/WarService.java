package com.example.cocapi.services;

import com.example.cocapi.models.Player;
import com.example.cocapi.models.war.Attack;
import com.example.cocapi.models.war.War;
import com.example.cocapi.proxies.WarProxy;
import com.example.cocapi.repository.WarRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
    public List<Player> retrieveWarStats(List<String> playerTags) {
        List<Player> playersInDatabase = warRepository.findPlayers(playerTags);

        Set<String> playersInDatabaseTags = playersInDatabase
                .stream()
                .map(Player::getTag)
                .collect(Collectors.toSet());

        // Unfortunately COC API only allows 1 player info per API request aka cannot batch ;(
        // Use concurrent virtual threading to speed up process
        try (ExecutorService service = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<Player>> playerThreads = playerTags.stream()
                    .filter(tag -> !playersInDatabaseTags.contains(tag))
                    .map(tag ->
                            service.submit(() -> calcAndStoreWarStats(tag)))
                    .toList();

            for (Future<Player> playerThread : playerThreads) {
                Player player = playerThread.get();
                playersInDatabase.add(player);
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return playersInDatabase;
    }

    // Add up stars, percentage, num attacks and total attacks from all wars for a given player
    private Player calcAndStoreWarStats(String tag) {
        List<War> wars = warProxy.getWars(tag);
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
}
