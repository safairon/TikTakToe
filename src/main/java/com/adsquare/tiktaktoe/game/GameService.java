package com.adsquare.tiktaktoe.game;

import com.adsquare.tiktaktoe.game.dto.GameEntity;
import com.adsquare.tiktaktoe.game.dto.PlayInfo;
import com.adsquare.tiktaktoe.game.dto.Player;
import com.adsquare.tiktaktoe.game.exception.GameIsEndedException;
import com.adsquare.tiktaktoe.game.exception.PlayingOnOccupiedPositionException;
import com.adsquare.tiktaktoe.game.exception.PlayingOutOfTurnException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public GameEntity playGame(Long gameId, PlayInfo playInfo) {
        long id = null != gameId ? gameId : 0;
        GameEntity gameEntity = gameRepository.findById(id).orElse(new GameEntity());

        checkGameIsEnded(gameEntity);

        checkPlayingOfTurn(gameEntity.getLastPlayer(), playInfo.getPlayer());

        checkTheCellIsOccupied(gameEntity.getGameStatus(), playInfo.getRow(), playInfo.getColumn());

        gameEntity.getGameStatus()[playInfo.getRow()][playInfo.getColumn()] = playInfo.getPlayer();
        gameEntity.setLastPlayer(playInfo.getPlayer());
        gameEntity.setLastUpdated(new Date());

        if(doesPlayerWin(gameEntity.getGameStatus(), playInfo)) {
            gameEntity.setWinner(playInfo.getPlayer());
            gameEntity.setEnded(true);
        }

        if(!gameEntity.isEnded()) {
            gameEntity.setEnded(isAllCellsOccupied(gameEntity.getGameStatus()));
        }

        gameRepository.save(gameEntity);

        return gameEntity;
    }

    private void checkGameIsEnded(GameEntity gameEntity) {
        if(gameEntity.isEnded()) {
            throw new GameIsEndedException(gameEntity.getId());
        }
    }

    private void checkTheCellIsOccupied(Player[][] gameStatus, int row, int column) {
        if(gameStatus[row][column] != null) {
            throw new PlayingOnOccupiedPositionException(row, column);
        }
    }

    private void checkPlayingOfTurn(Player lastPlayer, Player currentPlayer) {
        if(null != lastPlayer && lastPlayer.equals(currentPlayer)) {
            throw new PlayingOutOfTurnException(currentPlayer);
        }
    }
    
    private boolean doesPlayerWin(Player[][] gameStatus, PlayInfo info) {
        boolean r = true, c = true, d1 = true, d2 = true;;
        for (int i = 0; i < GameEntity.BOARD_SIZE; i++) {
            if(!info.getPlayer().equals(gameStatus[info.getRow()][i])) {
                r = false;
            }

            if(!info.getPlayer().equals(gameStatus[i][info.getColumn()])) {
                c = false;
            }

            if(!info.getPlayer().equals(gameStatus[i][i])) {
                d1 = false;
            }

            if(!info.getPlayer().equals(gameStatus[i][2-i])) {
                d2 = false;
            }
        }

        return r || c | d1 || d2;
    }

    private boolean isAllCellsOccupied(Player[][] gameStatus) {
        for (int i = 0; i < GameEntity.BOARD_SIZE; i++) {
            for (int j = 0; j < GameEntity.BOARD_SIZE; j++) {
                if(null == gameStatus[i][j]) {
                    return false;
                }
            }

        }

        return true;
    }
}
