package wtf.diablo.irc.event;

import wtf.diablo.client.event.api.AbstractEvent;

public final class IrcErrorEvent extends AbstractEvent {
    private final String message;

    public IrcErrorEvent(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
