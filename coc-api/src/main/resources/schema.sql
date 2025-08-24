CREATE TABLE IF NOT EXISTS clan (
    clan_tag VARCHAR(255) PRIMARY KEY NOT NULL,
    clan_name VARCHAR(255),
    last_checked DATETIME
);

CREATE TABLE IF NOT EXISTS player (
    player_tag VARCHAR(255) PRIMARY KEY NOT NULL,
    total_star INT,
    total_percentage INT,
    num_attacks INT,
    total_attacks INT,
    most_recent_war_endtime DATETIME,
    clan_tag VARCHAR(255),
    FOREIGN KEY (clan_tag) REFERENCES clan(clan_tag)
);