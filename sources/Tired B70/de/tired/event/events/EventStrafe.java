package de.tired.event.events;

import de.tired.event.Event;

public class EventStrafe extends Event {
    final float strafe;
    final float forward;
    final float friction;
    boolean cancelled;

    public EventStrafe(float strafe, float forward, float friction) {
        this.strafe = strafe;
        this.forward = forward;
        this.friction = friction;
    }

    public float getStrafe() {
        return this.strafe;
    }

    public float getForward() {
        return this.forward;
    }

    public float getFriction() {
        return this.friction;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean state) {
        this.cancelled = state;
    }
}
