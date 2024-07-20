/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.event.types;

import org.w3c.dom.events.Event;
import ru.govno.client.event.Cancellable;

public abstract class EventCancellable
implements Event,
Cancellable {
    private boolean cancelled;

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean state) {
        this.cancelled = state;
    }
}

