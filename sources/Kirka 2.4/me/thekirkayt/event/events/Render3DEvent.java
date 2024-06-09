/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.event.events;

import me.thekirkayt.event.Event;

public class Render3DEvent
extends Event {
    private float partialTicks;

    public Render3DEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

    public void setPartialTicks(float partialTicks) {
        this.partialTicks = partialTicks;
    }
}

