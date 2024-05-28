package arsenic.event.impl;

import arsenic.event.types.Event;

public class EventLook implements Event {

    private float yaw, pitch;
    private boolean modified;

    public EventLook(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
        modified = true;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
        modified = true;
    }

    public boolean hasBeenModified() {
        return modified;
    }
}