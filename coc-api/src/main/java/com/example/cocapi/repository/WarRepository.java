package com.example.cocapi.repository;

import com.example.cocapi.models.Player;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
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
                             int totalAttacks, Timestamp mostRecentWarEndDateTime) {
        // store, otherwise if exists update
        jdbc.update("INSERT INTO player VALUES (?, ?, ?, ?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                        "total_star = total_star + VALUES(total_star), " +
                        "total_percentage = total_percentage + VALUES(total_percentage)," +
                        "num_attacks = num_attacks + VALUES(num_attacks)," +
                        "total_attacks = total_attacks + VALUES(total_attacks)," +
                        "most_recent_war_endtime = VALUES(most_recent_war_endtime)",
                tag,
                totalStar,
                totalPercentage,
                numAttacks,
                totalAttacks,
                mostRecentWarEndDateTime
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

    // get latest war
    public Timestamp getLatestWarDateTime(String tag) {
        return jdbc.queryForObject("SELECT most_recent_war_endtime FROM player WHERE player_tag = ?",
                Timestamp.class,
                tag);
    }

    // get all players from database
    public List<Player> findAll() {
        return jdbc.query(
                "SELECT * FROM player",
                playerRowMapper);
    }

}
