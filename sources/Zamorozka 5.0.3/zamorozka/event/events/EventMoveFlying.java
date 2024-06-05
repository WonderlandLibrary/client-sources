package zamorozka.event.events;

import zamorozka.event.Event;

public final class EventMoveFlying
        extends Event {

    private float yaw;

    public EventMoveFlying(float yaw) {
        this.yaw = yaw;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

}
