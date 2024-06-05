/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.event.impl;

import cc.diablo.event.Event;
import net.minecraft.client.Minecraft;

public class UpdateEvent
extends Event {
    private double posX;
    private double posY;
    private double posZ;
    private float rotationYaw;
    private float rotationPitch;
    private boolean onGround;
    private final boolean isPre;

    public UpdateEvent(double posX, double posY, double posZ, float rotationYaw, float rotationPitch, boolean onGround) {
        this(true);
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.rotationYaw = rotationYaw;
        this.rotationPitch = rotationPitch;
        this.onGround = onGround;
    }

    public UpdateEvent(boolean isPre) {
        this.isPre = isPre;
    }

    public double getPosX() {
        return this.posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return this.posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getPosZ() {
        return this.posZ;
    }

    public void setPosZ(double posZ) {
        this.posZ = posZ;
    }

    public float getRotationYaw() {
        return this.rotationYaw;
    }

    public void setRotationYaw(float rotationYaw) {
        Minecraft.getMinecraft().thePlayer.renderYawOffset = rotationYaw;
        Minecraft.getMinecraft().thePlayer.rotationYawHead = rotationYaw;
        this.rotationYaw = rotationYaw;
    }

    public float getRotationPitch() {
        return this.rotationPitch;
    }

    public void setRotationPitch(float rotationPitch) {
        Minecraft.getMinecraft().thePlayer.renderPitchHead = rotationPitch;
        this.rotationPitch = rotationPitch;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    @Override
    public boolean isPre() {
        return this.isPre;
    }
}

