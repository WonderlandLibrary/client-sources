package wtf.diablo.client.event.impl.player.chat;

import wtf.diablo.client.event.api.AbstractEvent;

public final class ChatEvent extends AbstractEvent {
private final String message;

    public ChatEvent(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
