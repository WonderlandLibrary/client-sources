/*
 * Decompiled with CFR 0.145.
 */
package com.darkmagician6.eventapi.events.callables;

import com.darkmagician6.eventapi.events.Cancellable;
import com.darkmagician6.eventapi.events.Event;

public abstract class EventCancellable
implements Event,
Cancellable {
    private boolean cancelled;

    protected EventCancellable() {
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean state) {
        this.cancelled = state;
    }
}

