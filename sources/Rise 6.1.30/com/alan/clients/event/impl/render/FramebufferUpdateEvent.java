package com.alan.clients.event.impl.render;

import com.alan.clients.event.Event;

public final class FramebufferUpdateEvent implements Event {

    private final int width, height;

    public FramebufferUpdateEvent(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
