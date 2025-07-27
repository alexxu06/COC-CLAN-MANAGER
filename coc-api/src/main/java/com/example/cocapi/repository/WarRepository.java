package com.example.cocapi.repository;

import com.example.cocapi.models.Player;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class WarRepository {
    private final JdbcTemplate jdbc;
    private final RowMapper<Player> playerRowMapper;

    public WarRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;

        playerRowMapper = (r, i) -> {
            Player player = new Player();
            player.setTag(r.getString("player_tag"));
            player.setTotalStars(r.getInt("total_star"));
            player.setTotalPercentage(r.getInt("total_percentage"));
            player.setNumAttacks(r.getInt("num_attacks"));
            player.setTotalAttacks(r.getInt("total_attacks"));

            return player;
        };
    }

    public void storePlayers(String tag, int totalStar,
                             int totalPercentage, int numAttacks,
                             int totalAttacks) {
        jdbc.update("INSERT INTO player VALUES (?, ?, ?, ?, ?)",
                tag,
                totalStar,
                totalPercentage,
                numAttacks,
                totalAttacks
        );
    }

    // find all players from playerTags
    public List<Player> findPlayers(List<String> playerTags) {
        String placeholders = playerTags.stream()
                .map(tag -> "?")
                .collect(Collectors.joining(","));

        return jdbc.query(
            "SELECT * FROM player WHERE player_tag IN (" + placeholders + ")",
            playerRowMapper,
            playerTags.toArray());
    }



}
