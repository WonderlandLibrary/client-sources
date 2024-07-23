package io.github.liticane.monoxide.listener.event.game;

import io.github.liticane.monoxide.listener.event.Event;

public class GameEndEvent extends Event {

    private final boolean isWin;

    public GameEndEvent(boolean isWin) {
        this.isWin = isWin;
    }

    public boolean isWin() {
        return isWin;
    }
}
