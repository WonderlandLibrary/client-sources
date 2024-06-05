package club.mega.event.impl;

import club.mega.event.Event;

public class EventMoveFlying extends Event {

    private float yaw;

    public EventMoveFlying(final float yaw) {
        this.yaw = yaw;
    }

    public final float getYaw() {
        return yaw;
    }

    public final void setYaw(final float yaw) {
        this.yaw = yaw;
    }

}
