/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.event.events;

import ru.govno.client.event.Event;

public class EventLordBog
extends Event {
    public static float yaw;
    public static float pitch;
    public double x;
    public double y;
    public double z;
    public boolean ground;
    public boolean onGround;

    public EventLordBog(float rotationYaw, float rotationPitch, double posX, double posY, double posZ, boolean onGround) {
        yaw = rotationYaw;
        pitch = rotationPitch;
        this.x = posX;
        this.y = posY;
        this.z = posZ;
        this.ground = onGround;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        EventLordBog.yaw = yaw;
    }

    public void setYaw1(float yaw) {
        EventLordBog.yaw = yaw;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getPosY() {
        return this.y;
    }

    public void setPosY(double posY) {
        this.y = posY;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        EventLordBog.pitch = pitch;
    }

    public boolean onGround() {
        return this.ground;
    }

    public boolean isGround() {
        return this.ground;
    }

    public void setGround(boolean isGround) {
        this.ground = isGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public double getZ() {
        return this.z;
    }
}

