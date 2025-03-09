package com.adsquare.tiktaktoe.game;

import com.adsquare.tiktaktoe.game.dto.GameEntity;
import com.adsquare.tiktaktoe.game.dto.PlayInfo;
import com.adsquare.tiktaktoe.game.exception.GameAbstractException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping({"/play", "/play/{gameId}"})
    public GameEntity playGame(@PathVariable(required = false) Long gameId,
                               @Valid @RequestBody PlayInfo playInfo) {
        return gameService.playGame(gameId, playInfo);
    }
}
