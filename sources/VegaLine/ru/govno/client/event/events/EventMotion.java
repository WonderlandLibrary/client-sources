/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.event.events;

import net.minecraft.client.Minecraft;
import ru.govno.client.event.Event;

public class EventMotion
extends Event {
    private boolean onGround;
    private float yaw;
    private float pitch;
    private double y;
    private boolean pre;

    public EventMotion(float yaw, float pitch, double y, boolean onGround, boolean pre) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.y = y;
        this.onGround = onGround;
        this.pre = pre;
    }

    public EventMotion(float yaw, float pitch, double y, boolean onGround) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.y = y;
        this.onGround = onGround;
    }

    public void setRotation(float yaw, float pitch) {
        if (Float.isNaN(yaw) || Float.isNaN(pitch) || pitch > 90.0f || pitch < -90.0f) {
            return;
        }
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public void setRotations(float[] rotations) {
        this.setRotation(rotations[0], rotations[1]);
    }

    public float[] getRotations() {
        return new float[]{this.yaw, this.pitch};
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
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

    public boolean isOnGround() {
        return this.onGround;
    }

    public void setYaw(float yaw) {
        Minecraft.getMinecraft();
        Minecraft.player.renderYawOffset = yaw;
        Minecraft.getMinecraft();
        Minecraft.player.rotationYawHead = yaw;
        this.yaw = yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean isPre() {
        return this.pre;
    }
}

