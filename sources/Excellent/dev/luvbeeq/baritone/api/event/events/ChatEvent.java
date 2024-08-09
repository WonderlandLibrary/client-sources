package dev.luvbeeq.baritone.api.event.events;

import dev.luvbeeq.baritone.api.event.events.type.Cancellable;

/**
 * @author Brady
 * @since 8/1/2018
 */
public final class ChatEvent extends Cancellable {

    /**
     * The message being sent
     */
    private final String message;

    public ChatEvent(String message) {
        this.message = message;
    }

    /**
     * @return The message being sent
     */
    public final String getMessage() {
        return this.message;
    }
}
