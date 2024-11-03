package dev.star.event.impl.player;

import dev.star.event.Event;

public class ClickEvent extends Event {
    boolean fake;

    public ClickEvent(boolean fake) {
        this.fake = fake;
    }

    public boolean isFake() {
        return fake;
    }
}
