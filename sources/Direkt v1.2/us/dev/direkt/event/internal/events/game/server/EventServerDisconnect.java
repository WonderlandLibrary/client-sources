package us.dev.direkt.event.internal.events.game.server;

import us.dev.direkt.event.Event;

import java.util.Optional;

/**
 * @author Foundry
 */
public class EventServerDisconnect implements Event {
    private String message;

    public EventServerDisconnect() {
    }

    public EventServerDisconnect(String message) {
        this.message = message;
    }

    public Optional<String> getMessage() {
        return Optional.ofNullable(this.message);
    }
}
