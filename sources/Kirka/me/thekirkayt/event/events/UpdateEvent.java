/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.event.events;

import me.thekirkayt.event.Event;

public class UpdateEvent
extends Event {
    public Event.State state;
    public float yaw;
    public float pitch;
    public double y;
    public boolean onground;
    public boolean alwaysSend;

    public UpdateEvent() {
        this.state = Event.State.POST;
    }

    public UpdateEvent(double y, float yaw, float pitch, boolean ground) {
        this.state = Event.State.PRE;
        this.yaw = yaw;
        this.pitch = pitch;
        this.y = y;
        this.onground = ground;
    }

    public Event.State getState() {
        return this.state;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public double getY() {
        return this.y;
    }

    public boolean isOnground() {
        return this.onground;
    }

    public boolean shouldAlwaysSend() {
        return this.alwaysSend;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setGround(boolean ground) {
        this.onground = ground;
    }

    public void setAlwaysSend(boolean alwaysSend) {
        this.alwaysSend = alwaysSend;
    }
}

