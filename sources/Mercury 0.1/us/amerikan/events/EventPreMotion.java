/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.events;

import com.darkmagician6.eventapi.events.Event;

public class EventPreMotion
implements Event {
    public float yaw;
    public float pitch;
    public boolean onGround;
    public double posX;
    public double posY;
    public double posZ;

    public EventPreMotion(double posX, double posY, double posZ, float yaw, float pitch, boolean onGround) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.onGround = onGround;
    }

    public float getPitch() {
        return this.pitch;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public double getPosY() {
        return this.posY;
    }
}

