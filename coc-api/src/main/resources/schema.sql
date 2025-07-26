CREATE TABLE IF NOT EXISTS player (
    player_tag VARCHAR(255) PRIMARY KEY NOT NULL,
    player_name VARCHAR(255) NOT NULL,
    total_star INT,
    total_percentage INT,
    num_attacks INT,
    total_attacks INT
);