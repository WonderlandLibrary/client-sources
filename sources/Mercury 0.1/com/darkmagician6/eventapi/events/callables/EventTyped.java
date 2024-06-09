/*
 * Decompiled with CFR 0.145.
 */
package com.darkmagician6.eventapi.events.callables;

import com.darkmagician6.eventapi.events.Event;
import com.darkmagician6.eventapi.events.Typed;

public abstract class EventTyped
implements Event,
Typed {
    private final byte type;

    protected EventTyped(byte eventType) {
        this.type = eventType;
    }

    @Override
    public byte getType() {
        return this.type;
    }
}

