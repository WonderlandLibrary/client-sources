package wtf.diablo.irc.event;

import wtf.diablo.client.event.api.AbstractEvent;

public final class IrcConnectedMessageEvent extends AbstractEvent {
    private final String message;

    public IrcConnectedMessageEvent(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
