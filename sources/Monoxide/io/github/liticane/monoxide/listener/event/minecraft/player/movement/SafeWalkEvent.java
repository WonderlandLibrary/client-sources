package io.github.liticane.monoxide.listener.event.minecraft.player.movement;

import io.github.liticane.monoxide.listener.event.Event;

public class SafeWalkEvent extends Event {
    private boolean safe;

    public SafeWalkEvent(boolean safe) {
        this.safe = safe;
    }

    public boolean isSafe() {
        return safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }
}
