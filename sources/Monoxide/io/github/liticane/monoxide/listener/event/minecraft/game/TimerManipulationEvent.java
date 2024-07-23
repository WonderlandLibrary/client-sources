package io.github.liticane.monoxide.listener.event.minecraft.game;

import io.github.liticane.monoxide.listener.event.Event;

public class TimerManipulationEvent extends Event {
    private long time;

    public TimerManipulationEvent(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
