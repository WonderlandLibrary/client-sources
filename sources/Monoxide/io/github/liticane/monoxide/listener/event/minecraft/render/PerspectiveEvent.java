package io.github.liticane.monoxide.listener.event.minecraft.render;

import io.github.liticane.monoxide.listener.event.Event;

public class PerspectiveEvent
        extends Event {
    private float aspect;
    private final boolean hand;

    public PerspectiveEvent(float f) {
        this.aspect = f;
        this.hand = false;
    }

    public PerspectiveEvent(float f, boolean hand) {
        this.aspect = f;
        this.hand = hand;
    }

    public float getAspect() {
        return this.aspect;
    }

    public void setAspect(float f) {
        this.aspect = f;
    }

    public boolean isHand() {
        return hand;
    }
}