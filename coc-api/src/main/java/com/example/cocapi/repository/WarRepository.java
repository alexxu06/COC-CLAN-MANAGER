package com.example.cocapi.repository;

import com.example.cocapi.models.Player;
import com.example.cocapi.models.war.Attack;
import com.example.cocapi.models.war.War;
import com.example.cocapi.models.war.WarResponse;
import com.example.cocapi.proxies.PlayerProxy;
import com.example.cocapi.proxies.PlayerWarProxy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class WarRepository {
    private final JdbcTemplate jdbc;
    private final PlayerWarProxy playerWarProxy;
    private final PlayerProxy playerProxy;

    public WarRepository(JdbcTemplate jdbc, PlayerWarProxy playerWarProxy, PlayerProxy playerProxy) {
        this.jdbc = jdbc;
        this.playerWarProxy = playerWarProxy;
        this.playerProxy = playerProxy;
    }

    public void storePlayer(Player player) {

    }

    public Player retrieveWarStats(String playerTag) {
        RowMapper<Player> playerRowMapper = (r, i) -> {
            Player player = new Player();
            player.setTag(r.getString("player_tag"));
            player.setName(r.getString("player_name"));
            player.setTotalStars(r.getInt("total_star"));
            player.setTotalPercentage(r.getInt("total_percentage"));
            player.setNumAttacks(r.getInt("num_attacks"));
            player.setTotalAttacks(r.getInt("total_attacks"));

            return player;
        };

        // will get player war stats if exists, else insert new player into database
        try {
            return jdbc.queryForObject(
                    "SELECT * FROM player WHERE player_tag = ?",
                    playerRowMapper,
                    playerTag);
        } catch (EmptyResultDataAccessException e) {
            int totalStar = 0;
            int totalPercentage = 0;
            int numAttacks = 0;
            int totalAttacks = 0;
            String name = playerProxy.getPlayer(playerTag).block().getName();

            // make api call to get player + war data
            WarResponse warResponse = playerWarProxy.getWars(playerTag).block();

            for (War item : warResponse.getItems()) {
                numAttacks += item.getAttacks().size();
                // random are normal wars (2 attacks). Otherwise, cwl (1 attack)
                totalAttacks += (item.getWar_data().getType().equals("random")) ? 2 : 1;
                for (Attack attack : item.getAttacks()) {
                    totalStar += attack.getStars();
                    totalPercentage += attack.getDestructionPercentage();
                }
            }

            jdbc.update("INSERT INTO player VALUES (?, ?, ?, ?, ?, ?)",
                    playerTag,
                    name,
                    totalStar,
                    totalPercentage,
                    numAttacks,
                    totalAttacks
                    );

            return jdbc.queryForObject(
//                    "SELECT total_star, total_percentage, num_attacks, total_attacks FROM player WHERE player_tag = ?",
                    "SELECT * FROM player WHERE player_tag = ?",
                    playerRowMapper,
                    playerTag);
        }
    }
}
