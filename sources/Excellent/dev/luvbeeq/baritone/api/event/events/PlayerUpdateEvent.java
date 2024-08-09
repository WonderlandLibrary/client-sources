package dev.luvbeeq.baritone.api.event.events;

import dev.luvbeeq.baritone.api.event.events.type.EventState;

/**
 * @author Brady
 * @since 8/21/2018
 */
public final class PlayerUpdateEvent {

    /**
     * The state of the event
     */
    private final EventState state;

    public PlayerUpdateEvent(EventState state) {
        this.state = state;
    }

    /**
     * @return The state of the event
     */
    public final EventState getState() {
        return this.state;
    }
}
