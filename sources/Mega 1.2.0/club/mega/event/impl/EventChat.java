package club.mega.event.impl;

import club.mega.event.Event;

public class EventChat extends Event {

    private final String message;

    public EventChat(final String message) {
        this.message = message;
    }

    public final String getMessage() {
        return message;
    }

}
