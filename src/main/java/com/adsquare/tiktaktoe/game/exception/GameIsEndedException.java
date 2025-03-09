package com.adsquare.tiktaktoe.game.exception;

public class GameIsEndedException extends GameAbstractException {

    public GameIsEndedException(Long gameId) {
        super("Game with this id=" + gameId + " is ended and can not be continued to play.");
    }
}
