package club.mega.event.impl;

import club.mega.event.Event;

public class EventVelocity extends Event {

    private float yaw;

    public EventVelocity(final float yaw) {
        this.yaw = yaw;
    }

    public final float getYaw() {
        return yaw;
    }

    public final void setYaw(final float yaw) {
        this.yaw = yaw;
    }

}
