/*
 * Decompiled with CFR 0.145.
 */
package com.darkmagician6.eventapi.events;

import com.darkmagician6.eventapi.events.Event;

public abstract class EventStoppable
implements Event {
    private boolean stopped;

    protected EventStoppable() {
    }

    public void stop() {
        this.stopped = true;
    }

    public boolean isStopped() {
        return this.stopped;
    }
}

