package wtf.diablo.irc.event;

import wtf.diablo.client.event.api.AbstractEvent;

public final class IrcIncomingMessageEvent extends AbstractEvent {
    private final String message;
    private final String sender;

    public IrcIncomingMessageEvent(final String message, final String sender) {
        this.message = message;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }
}
