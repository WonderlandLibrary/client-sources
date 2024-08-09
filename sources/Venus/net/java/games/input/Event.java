/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import net.java.games.input.Component;

public final class Event {
    private Component component;
    private float value;
    private long nanos;

    public final void set(Event event) {
        this.set(event.getComponent(), event.getValue(), event.getNanos());
    }

    public final void set(Component component, float f, long l) {
        this.component = component;
        this.value = f;
        this.nanos = l;
    }

    public final Component getComponent() {
        return this.component;
    }

    public final float getValue() {
        return this.value;
    }

    public final long getNanos() {
        return this.nanos;
    }

    public final String toString() {
        return "Event: component = " + this.component + " | value = " + this.value;
    }
}

