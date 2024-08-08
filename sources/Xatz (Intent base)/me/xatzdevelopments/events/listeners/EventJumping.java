package me.xatzdevelopments.events.listeners;

import me.xatzdevelopments.events.Event;

public class EventJumping extends Event {

    private float yaw;

    public EventJumping(float yaw) {
        this.yaw = yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getYaw() {
        return this.yaw;
    }

}
