package com.adsquare.tiktaktoe.game.exception;

import com.adsquare.tiktaktoe.game.dto.Player;

public class PlayingOutOfTurnException extends GameAbstractException {
    public PlayingOutOfTurnException(Player player) {
        super("Player:" + player + " is playing out of the turn" );
    }
}
