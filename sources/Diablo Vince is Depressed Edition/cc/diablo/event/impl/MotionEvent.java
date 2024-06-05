/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.event.impl;

import cc.diablo.event.Event;
import cc.diablo.event.EventType;

public class MotionEvent
extends Event {
    public EventType type;
    public float yaw;
    public float pitch;
    public double y;
    public double x;
    public double z;
    public boolean onground;
    public boolean alwaysSend;
    public boolean cancelled;

    public MotionEvent(double y, double x, double z, float yaw, float pitch, boolean ground, EventType type) {
        this.type = type;
        this.cancelled = false;
        this.yaw = yaw;
        this.pitch = pitch;
        this.y = y;
        this.z = z;
        this.x = x;
        this.onground = ground;
    }

    public MotionEvent() {
        this.type = EventType.Post;
    }

    @Override
    public EventType getType() {
        return this.type;
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

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = this.y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double z) {
        this.y = z;
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

    public void setGround(boolean ground) {
        this.onground = ground;
    }

    public void setAlwaysSend(boolean alwaysSend) {
        this.alwaysSend = alwaysSend;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean state) {
        this.cancelled = state;
    }

    public boolean isAlwaysSend() {
        return this.alwaysSend;
    }

    @Override
    public void setType(EventType type) {
        this.type = type;
    }

    public void setOnground(boolean onground) {
        this.onground = onground;
    }
}

