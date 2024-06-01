package io.github.liticane.clients.feature.event.impl.render;

import io.github.liticane.clients.feature.event.Event;

public class RenderTickEvent extends Event {
    private final float ticks;

    public RenderTickEvent(float ticks) {
        this.ticks = ticks;
    }

    public float getTicks() {
        return ticks;
    }
}
