package io.github.liticane.clients.feature.event.impl.render;

import io.github.liticane.clients.feature.event.Event;

public class Render3DEvent extends Event {
    private float ticks;

    public Render3DEvent(float ticks) {
        this.ticks = ticks;
    }

    public float getTicks() {
        return ticks;
    }

    public void setTicks(float ticks) {
        this.ticks = ticks;
    }
}
