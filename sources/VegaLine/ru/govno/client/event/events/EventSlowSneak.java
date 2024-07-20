/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.event.events;

import ru.govno.client.event.Event;

public class EventSlowSneak
extends Event {
    public double slowFactor;

    public EventSlowSneak(double slowFactor) {
        this.slowFactor = slowFactor;
    }

    public void setSlowFactor(double slowFactor) {
        this.slowFactor = slowFactor;
    }

    public double getSlowFactor() {
        return this.slowFactor;
    }
}

