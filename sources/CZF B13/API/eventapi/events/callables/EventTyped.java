package gq.vapu.czfclient.API.eventapi.events.callables;

import gq.vapu.czfclient.API.eventapi.events.Event;
import gq.vapu.czfclient.API.eventapi.events.Typed;

public abstract class EventTyped implements Event, Typed {
    private final byte type;

    protected EventTyped(byte eventType) {
        this.type = eventType;
    }

    public byte getType() {
        return this.type;
    }
}
