package wtf.diablo.client.event.impl.client.renderering;

import wtf.diablo.client.event.api.AbstractEvent;

public final class WorldTimeEvent extends AbstractEvent {
    private long time;

    public WorldTimeEvent(final long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(final long time) {
        this.time = time;
    }
}

