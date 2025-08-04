package com.example.cocapi.services;

import com.example.cocapi.models.Player;
import com.example.cocapi.models.war.Attack;
import com.example.cocapi.models.war.War;
import com.example.cocapi.proxies.WarProxy;
import com.example.cocapi.repository.WarRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
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

    // retrieves war stats for a single player
    public Player retrieveWarStats(String playerTag) {
        List<Player> playerStats = retrieveWarStats(Arrays.asList(playerTag));
        return playerStats.getFirst();
    }

    // retrieves war stats in bulk (used when searching by clan)
    public List<Player> retrieveWarStats(List<String> playerTags) {
        // This will only return playerTags of players who are stored in the database
        List<Player> playersInDatabase = warRepository.findPlayers(playerTags);

        Set<String> playersInDatabaseTags = playersInDatabase
                .stream()
                .map(Player::getTag)
                .collect(Collectors.toSet());

        updateStoredPlayers(playersInDatabase);

        InsertNewPlayers(playerTags, playersInDatabaseTags);

        // return new and updated players
        return warRepository.findPlayers(playerTags);
    }

    // Retrieve database stats and combine with new (as opposed to requesting entire war history)
    // smaller api requests better performance
    private void updateStoredPlayers(List<Player> playersInDatabase) {
        try (ExecutorService service = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<Player>> playerThreads = playersInDatabase.stream()
                    .map(player -> service.submit(() -> updateWarStats(player)))
                    .toList();

            List<Player> updatedPlayers = new ArrayList<>();

            for (Future<Player> playerThread : playerThreads) {
                Player updatedPlayer = playerThread.get();
                if (updatedPlayer != null) {
                    updatedPlayers.add(updatedPlayer);
                }
            }

            warRepository.updateWars(updatedPlayers);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    // Unfortunately COC API only allows 1 player info per API request aka cannot batch ;(
    // Use concurrent virtual threading to speed up process
    private void InsertNewPlayers(List<String> playerTags, Set<String> playersInDatabaseTags) {
        try (ExecutorService service = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<Player>> playerThreads = playerTags.stream()
                    .filter(tag -> !playersInDatabaseTags.contains(tag))
                    .map(tag ->
                            service.submit(() -> calculateWarStats(tag, warProxy.getWars(tag))))
                    .toList();

            List<Player> newPlayers = new ArrayList<>();

            for (Future<Player> playerThread : playerThreads) {
                newPlayers.add(playerThread.get());
            }

            warRepository.storePlayers(newPlayers);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Player updateWarStats(Player player) {
        List<War> recentWars = warProxy.getRecentWars(player);

        if (!recentWars.isEmpty()) {
            return calculateWarStats(player.getTag(), recentWars);
        }

        // if no new wars
        return null;
    }

    // Add up stars, percentage, num attacks and total attacks from all wars for a given player
    private Player calculateWarStats(String tag, List<War> wars) {
        int totalStar = 0;
        int totalPercentage = 0;
        int numAttacks = 0;
        int totalAttacks = 0;
        // get first war's endtime (most recent)
        Timestamp mostRecentWarEndDateTime = dateConvertService.getMostRecentWarEndTime(wars);

        for (War war : wars) {
            List<Attack> attacks = war.getAttacks();
            numAttacks += attacks.size();
            // random are normal wars (2 attacks). Otherwise, cwl (1 attack)
            totalAttacks += (war.getWar_data().getType().equals("random")) ? 2 : 1;
            for (Attack attack : attacks) {
                totalStar += attack.getStars();
                totalPercentage += attack.getDestructionPercentage();
            }
        }

        Player player = new Player();
        player.setTag(tag);
        player.setTotalStars(totalStar);
        player.setTotalPercentage(totalPercentage);
        player.setNumAttacks(numAttacks);
        player.setTotalAttacks(totalAttacks);
        player.setWarEndTime(mostRecentWarEndDateTime);
        return player;
    }
}
