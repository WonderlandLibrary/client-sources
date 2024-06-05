package gq.vapu.czfclient.API.eventapi.events.callables;

import gq.vapu.czfclient.API.eventapi.events.Cancellable;
import gq.vapu.czfclient.API.eventapi.events.Event;

public abstract class EventCancellable implements Event, Cancellable {
    private boolean cancelled;

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean state) {
        this.cancelled = state;
    }
}
