package com.example.cocapi.repository;

import com.example.cocapi.models.Clan;
import com.example.cocapi.models.Player;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Repository
public class ClanRepository {
    private final JdbcTemplate jdbc;
    private final RowMapper<Clan> clanRowMapper;
    private final PlayerRepository playerRepository;

    public ClanRepository(JdbcTemplate jdbc, PlayerRepository playerRepository) {
        this.jdbc = jdbc;

        clanRowMapper = (r, i) -> {
            Clan clan = new Clan();
            clan.setTag(r.getString("clan_tag"));
            clan.setName(r.getString("clan_name"));
            clan.setLastChecked(r.getTimestamp("last_checked"));
            return clan;
        };
        this.playerRepository = playerRepository;
    }

    public void storeClan(Clan clan) {
        String sql = "INSERT INTO clan VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE clan_name = clan_name";
        String tag = clan.getTag();
        String name = clan.getName();

        jdbc.update(sql, tag, name, Timestamp.from(Instant.now()));
    }

    public Clan findClan(String tag) {
        String sql = "SELECT * FROM clan WHERE clan_tag=?";

        Clan clan = jdbc.queryForObject(sql, clanRowMapper, "#"+tag);
        List<Player> members = playerRepository.findPlayersByClan(tag);
        clan.setMemberList(members);

        return clan;
    }

    public void updateTime(String clanTag) {
        String sql = "UPDATE clan SET last_checked = ? WHERE clan_tag = ?";

        jdbc.update(sql, Timestamp.from(Instant.now()), clanTag);
    }
}
