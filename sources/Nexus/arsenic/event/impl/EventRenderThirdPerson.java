package arsenic.event.impl;

import arsenic.event.types.Event;

public class EventRenderThirdPerson implements Event {
    private float yaw;
    private float pitch;
    private float prevYaw;

    private float prevPitch;
    public EventRenderThirdPerson(float yaw, float pitch, float prevYaw, float prevPitch) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.prevYaw = prevYaw;
        this.prevPitch = prevPitch;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPrevYaw() {
        return prevYaw;
    }

    public void setPrevYaw(float prevYaw) {
        this.prevYaw = prevYaw;
    }

    public float getPrevPitch() {
        return prevPitch;
    }

    public void setPrevPitch(float prevPitch) {
        this.prevPitch = prevPitch;
    }
}
