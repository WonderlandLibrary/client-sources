package io.github.nevalackin.radium.event.impl.packet;

import io.github.nevalackin.radium.event.Event;

public final class DisconnectEvent implements Event {

    private final String reason;

    public DisconnectEvent(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
