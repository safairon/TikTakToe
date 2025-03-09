
CREATE TABLE game (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    last_player ENUM('X', 'O') DEFAULT NULL,
    winner ENUM('X', 'O') DEFAULT NULL,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_ended BOOLEAN NOT NULL DEFAULT FALSE,
    game_status JSON NOT NULL
);