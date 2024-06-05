package net.shoreline.client.impl.event;

import net.shoreline.client.api.event.Cancelable;
import net.shoreline.client.api.event.Event;

/**
 *
 */
@Cancelable
public class FramerateLimitEvent extends Event {
    private int framerateLimit;

    public int getFramerateLimit() {
        return framerateLimit;
    }

    public void setFramerateLimit(int framerateLimit) {
        this.framerateLimit = framerateLimit;
    }
}
