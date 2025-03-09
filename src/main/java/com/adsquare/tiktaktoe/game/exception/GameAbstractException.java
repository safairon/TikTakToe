package com.adsquare.tiktaktoe.game.exception;

public abstract class GameAbstractException extends RuntimeException {
    public GameAbstractException(String message) {
        super(message);
    }
}
