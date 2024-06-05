package net.shoreline.client.impl.manager.player.rotation;

public class Rotation {
    private final int priority;
    private float yaw, pitch;

    public Rotation(int priority, float yaw, float pitch) {
        this.priority = priority;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public int getPriority() {
        return priority;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }
}
