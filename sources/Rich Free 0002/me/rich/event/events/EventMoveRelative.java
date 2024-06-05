package me.rich.event.events;

import me.rich.event.Event;

public class EventMoveRelative extends Event {
    public float strafe, up, forward, friction;
    public float yaw;

    public EventMoveRelative(float strafe, float up, float forward, float friction, float yaw) {
        this.strafe = strafe;
        this.up = up;
        this.forward = forward;
        this.friction = friction;
        this.yaw = yaw;
    }
}
