package com.example.cocapi.repository;

import com.example.cocapi.models.Player;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
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
            player.setWarEndTime(r.getTimestamp("most_recent_war_endtime"));
            return player;
        };
    }

    public void storePlayers(List<Player> players) {
        String sql = "INSERT INTO player " +
                    "(player_tag, total_star, total_percentage, num_attacks, total_attacks, most_recent_war_endtime) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

        jdbc.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Player player = players.get(i);
                ps.setString(1, player.getTag());
                ps.setInt(2, player.getTotalStars());
                ps.setInt(3, player.getTotalPercentage());
                ps.setInt(4, player.getNumAttacks());
                ps.setInt(5, player.getTotalAttacks());
                ps.setTimestamp(6, player.getWarEndTime());
            }

            @Override
            public int getBatchSize() {
                return players.size();
            }
        });
    }

    public void updateWars(List<Player> stats) {
        String sql = "UPDATE player " +
                    "SET total_star = total_star + ?," +
                    "total_percentage = total_percentage + ?," +
                    "num_attacks = num_attacks + ?," +
                    "total_attacks = total_attacks + ?," +
                    "most_recent_war_endtime = ? " +
                    "WHERE player_tag=?";

        jdbc.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Player newStat = stats.get(i);
                ps.setInt(1, newStat.getTotalStars());
                ps.setInt(2, newStat.getTotalPercentage());
                ps.setInt(3, newStat.getNumAttacks());
                ps.setInt(4, newStat.getTotalAttacks());
                ps.setTimestamp(5, newStat.getWarEndTime());
                ps.setString(6, newStat.getTag());
            }

            @Override
            public int getBatchSize() {
                return stats.size();
            }
        });
    }

    // find single player
    public Player findPlayer(String playerTag) {
        List<Player> result = findPlayers(Arrays.asList(playerTag));
        return result.getFirst();
    }

    // find all players from playerTags
    public List<Player> findPlayers(List<String> playerTags) {
        String placeholders = playerTags.stream()
                .map(tag -> "?")
                .collect(Collectors.joining(","));
        String sql = "SELECT * FROM player WHERE player_tag IN (" + placeholders + ")";

        return jdbc.query(
                sql,
                playerRowMapper,
                playerTags.toArray());
    }

    // get all players from database
    public List<Player> findAll() {
        String sql = "SELECT * FROM player";
        return jdbc.query(
                sql,
                playerRowMapper);
    }

}
