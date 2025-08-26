package com.example.cocapi.repository;

import com.example.cocapi.models.Player;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PlayerRepository {
    private final JdbcTemplate jdbc;
    private final RowMapper<Player> playerRowMapper;

    public PlayerRepository(JdbcTemplate jdbc) {
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
                    "(player_tag, total_star, total_percentage, num_attacks, " +
                    "total_attacks, most_recent_war_endtime, clan_tag) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

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
                ps.setString(7, player.getClanTag());
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
        String sql = "SELECT * FROM player WHERE player_tag IN (" + sqlPlaceholder(playerTags) + ")";

        return jdbc.query(
                sql,
                playerRowMapper,
                playerTags.toArray());
    }

    // find all players in a clan
    public List<Player> findPlayersByClan(String clanTag) {
        String sql = "SELECT * FROM player WHERE clan_tag = ?";

        return jdbc.query(
                sql,
                playerRowMapper,
                clanTag);
    }

    // return only players from given players that are in database
    public List<Player> findPlayerInDatabase(List<String> playerTags) {
        String placeHolder = sqlPlaceholder(playerTags);
        String sql = "SELECT * FROM player " +
                    "WHERE player_tag IN (" + placeHolder + ")";

        return jdbc.query(sql, playerRowMapper, playerTags.toArray());
    }

    public void removePlayersFromCLan(List<String> playerTags) {
        String sql = "UPDATE player SET clan_tag = NULL WHERE player_tag IN (" + sqlPlaceholder(playerTags) + ")";

        jdbc.update(sql, playerTags.toArray());
    }

    public void updateClanTag(List<String> playerTags, String clanTag) {
        String placeHolder = sqlPlaceholder(playerTags);
        playerTags.addFirst(clanTag);
        String sql = "UPDATE player SET clan_tag = ? WHERE player_tag IN (" + placeHolder + ")";

        jdbc.update(sql, playerTags.toArray());
    }

    // Update multiple rows with 1 query, more efficient than batching
    public String sqlPlaceholder(List<String> playerTags) {
        return playerTags.stream()
                .map(tag -> "?")
                .collect(Collectors.joining(","));
    }
}
