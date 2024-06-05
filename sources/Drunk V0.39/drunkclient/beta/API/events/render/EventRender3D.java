/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.API.events.render;

import drunkclient.beta.API.Event;

public class EventRender3D
extends Event {
    private float ticks;

    public EventRender3D() {
    }

    public EventRender3D(float ticks) {
        this.ticks = ticks;
    }

    public float getPartialTicks() {
        return this.ticks;
    }
}

