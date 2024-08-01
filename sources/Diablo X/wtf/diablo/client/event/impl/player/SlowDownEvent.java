package wtf.diablo.client.event.impl.player;

import wtf.diablo.client.event.api.AbstractEvent;

public final class SlowDownEvent extends AbstractEvent {

    private final Mode mode;

    public SlowDownEvent(final Mode mode) {
        this.mode = mode;
    }

    public Mode getMode() {
        return mode;
    }

    public enum Mode {
        Item, Sprint
    }
}
