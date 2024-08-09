package ru.FecuritySQ.event.imp;

import ru.FecuritySQ.event.Event;

public class WalkingUpdateEvent extends Event {

    private double x, y, z;
    private float yaw, pitch;

    private boolean ground;

    public WalkingUpdateEvent(double x, double y, double z, float yaw, float pitch, boolean ground){
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.ground = ground;
    }

    public double getPosX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getPosY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getPosZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public boolean isGround() {
        return ground;
    }

    public void setGround(boolean ground) {
        this.ground = ground;
    }
}
