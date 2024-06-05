/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.API.events.world;

import drunkclient.beta.API.Event;
import net.minecraft.client.Minecraft;

public class EventPreUpdate
extends Event {
    private float yaw;
    public static float pitch;
    public double y;
    private boolean ground;

    public EventPreUpdate(float yaw, float pitch, double y, boolean ground) {
        this.yaw = yaw;
        EventPreUpdate.pitch = pitch;
        this.y = y;
        this.ground = ground;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
        Minecraft.getMinecraft();
        Minecraft.thePlayer.rotationYawHead = yaw;
        Minecraft.getMinecraft();
        Minecraft.thePlayer.renderYawOffset = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        EventPreUpdate.pitch = pitch;
        Minecraft.getMinecraft();
        Minecraft.thePlayer.rotationPitchHead = pitch;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean isOnground() {
        return this.ground;
    }

    public void setOnground(boolean ground) {
        this.ground = ground;
    }
}

