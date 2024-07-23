package io.github.liticane.monoxide.listener.event.game;

import io.github.liticane.monoxide.listener.event.Event;

public class KilledPlayerEvent extends Event {

    private final boolean playerFound;
    private final String player;

    public KilledPlayerEvent(boolean playerFound, String player) {
        this.playerFound = playerFound;
        this.player = player;
    }

    public boolean isPlayerFound() {
        return playerFound;
    }

    public String getPlayer() {
        return player;
    }
}
