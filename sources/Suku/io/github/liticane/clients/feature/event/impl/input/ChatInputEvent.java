package io.github.liticane.clients.feature.event.impl.input;

import io.github.liticane.clients.feature.event.Event;

public final class ChatInputEvent extends Event {
    private String message;

    public ChatInputEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}