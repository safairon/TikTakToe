package com.adsquare.tiktaktoe.game;

import com.adsquare.tiktaktoe.game.dto.GameEntity;
import com.adsquare.tiktaktoe.game.dto.PlayInfo;
import com.adsquare.tiktaktoe.game.dto.Player;
import com.adsquare.tiktaktoe.game.exception.GameIsEndedException;
import com.adsquare.tiktaktoe.game.exception.PlayingOnOccupiedPositionException;
import com.adsquare.tiktaktoe.game.exception.PlayingOutOfTurnException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = GameService.class)
public class GameServiceTest {

    @Autowired
    GameService gameService;

    @MockitoBean
    GameRepository gameRepository;

    @ParameterizedTest
    @MethodSource("prepareExceptionalSamples")
    void exceptionalSituationsTest(GameEntity entity, PlayInfo info, Class<?> exceptionType, String exceptionMessage) {
        when(gameRepository.findById(any())).thenReturn(Optional.of(entity));

        thenThrownBy(() -> gameService.playGame(entity.getId(), info))
                .isInstanceOf(exceptionType)
                .hasMessage(exceptionMessage);
    }

    private static Stream<Arguments> prepareExceptionalSamples() {
        return Stream.of(
            Arguments.of(
                new GameEntity(1L, Player.O, null, new Date(), new Date(), true, null),
                new PlayInfo(Player.O, 0, 0),
                GameIsEndedException.class,
                "Game with this id=1 is ended and can not be continued to play."
            ),
            Arguments.of(
                new GameEntity(1L, Player.O, null, new Date(), new Date(), false,
                        new Player[][]{{Player.O, null, null}, {null, null, null}, {null, null, null}}),
                new PlayInfo(Player.O, 0, 1),
                PlayingOutOfTurnException.class,
                "Player:O is playing out of the turn"
            ),
            Arguments.of(
                new GameEntity(1L, Player.O, null, new Date(), new Date(), false,
                        new Player[][]{{Player.O, null, null}, {null, null, null}, {null, null, null}}),
                new PlayInfo(Player.X, 0, 0),
                PlayingOnOccupiedPositionException.class,
                "This row=0 column=0 is occupied."
            )
        );
    }

    @Test
    void allCellsIsOccupiedTest() {
        GameEntity entity =
            new GameEntity(1L, Player.X, null, new Date(), new Date(), false,
                new Player[][]{{Player.O, Player.O, Player.X}, {Player.X, Player.O, null}, {Player.O, Player.X, Player.X}});

        PlayInfo info = new PlayInfo(Player.O, 1, 2);

        when(gameRepository.findById(any())).thenReturn(Optional.of(entity));
        when(gameRepository.save(any())).thenReturn(null);

        entity = gameService.playGame(entity.getId(), info);

        then(entity)
            .extracting("isEnded", "gameStatus")
            .containsExactly(true, new Player[][]{{Player.O, Player.O, Player.X}, {Player.X, Player.O, Player.O}, {Player.O, Player.X, Player.X}});
    }

    @ParameterizedTest
    @MethodSource("prepareWinningSamples")
    void winningSituationsTest(GameEntity entity, PlayInfo info) {
        when(gameRepository.findById(any())).thenReturn(Optional.of(entity));
        when(gameRepository.save(any())).thenReturn(null);

        entity = gameService.playGame(entity.getId(), info);

        then(entity)
                .extracting("isEnded", "winner")
                .containsExactly(true, Player.O);
    }

    private static Stream<Arguments> prepareWinningSamples() {
        return Stream.of(
            Arguments.of(
                new GameEntity(1L, Player.X, null, new Date(), new Date(), false,
                    new Player[][]{{Player.O, Player.O, null}, {Player.X, Player.X, null}, {null, null, null}}),
                new PlayInfo(Player.O, 0, 2)
            ),
            Arguments.of(
                new GameEntity(1L, Player.X, null, new Date(), new Date(), false,
                    new Player[][]{{Player.O, Player.X, null}, {Player.O, Player.X, null}, {null, null, null}}),
                new PlayInfo(Player.O, 2, 0)
            ),
            Arguments.of(
                new GameEntity(1L, Player.X, null, new Date(), new Date(), false,
                    new Player[][]{{Player.O, Player.X, null}, {Player.X, Player.O, null}, {null, null, null}}),
                new PlayInfo(Player.O, 2, 2)
            ),
            Arguments.of(
                new GameEntity(1L, Player.X, null, new Date(), new Date(), false,
                    new Player[][]{{null, Player.X, Player.O}, {Player.X, Player.O, null}, {null, null, null}}),
                new PlayInfo(Player.O, 2, 0)
            )
        );
    }

}
