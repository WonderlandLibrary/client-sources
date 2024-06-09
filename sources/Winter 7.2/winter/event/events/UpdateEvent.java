/*
 * Decompiled with CFR 0_122.
 */
package winter.event.events;

import winter.event.Event;

public class UpdateEvent
extends Event {
    public double y;
    public float yaw;
    public float pitch;
    public boolean pre;
    public boolean ground;

    public UpdateEvent(double y2, float yaw, float pitch, boolean ground, boolean pre) {
        this.y = y2;
        this.yaw = yaw;
        this.pitch = pitch;
        this.pre = pre;
        this.ground = ground;
    }

    public boolean ground() {
        return this.ground;
    }

    public void ground(boolean newGround) {
        this.ground = newGround;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double newy) {
        this.y = newy;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float newyaw) {
        this.yaw = newyaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float newpitch) {
        this.pitch = newpitch;
    }

    public boolean isPre() {
        return this.pre;
    }
}

