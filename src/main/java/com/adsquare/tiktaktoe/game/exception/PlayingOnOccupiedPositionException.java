package com.adsquare.tiktaktoe.game.exception;

public class PlayingOnOccupiedPositionException extends GameAbstractException {
    public PlayingOnOccupiedPositionException(int row, int column) {
        super("This row=" + row + " column=" + column + " is occupied.");
    }
}
