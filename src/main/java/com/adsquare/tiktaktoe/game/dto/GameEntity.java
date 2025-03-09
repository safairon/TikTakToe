package com.adsquare.tiktaktoe.game.dto;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.Date;

@Data
@Entity
@Table(name = "game")
@AllArgsConstructor
@NoArgsConstructor
public class GameEntity {

    public final static int BOARD_SIZE = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "last_player")
    private Player lastPlayer;

    @Enumerated(EnumType.STRING)
    @Column
    private Player winner = null;

    @Column
    private Date created = new Date();

    @Column(name = "last_updated")
    private Date lastUpdated;

    @Column(name = "is_ended")
    private boolean isEnded = false;

    @Type(JsonType.class)
    @Column(columnDefinition = "JSON")
    private Player[][] gameStatus = new Player[BOARD_SIZE][BOARD_SIZE];

}
