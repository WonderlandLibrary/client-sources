package dev.event.impl.game;

import dev.event.Event;


public class TickEvent extends Event {

    private final int ticks;

    public TickEvent(int ticks) {
        this.ticks = ticks;
    }


    public int getTicks() {
        return ticks;
    }

}
