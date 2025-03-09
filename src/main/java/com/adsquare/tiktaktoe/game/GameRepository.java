package com.adsquare.tiktaktoe.game;

import com.adsquare.tiktaktoe.game.dto.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {
}
