// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.event.events.player;

import gg.childtrafficking.smokex.event.Event;

public final class EventUpdate extends Event
{
    private final float prevYaw;
    private final float prevPitch;
    private final double prevPosX;
    private final double prevPosY;
    private final double prevPosZ;
    private double posX;
    private double posY;
    private double posZ;
    private float yaw;
    private float pitch;
    private boolean ground;
    private boolean pre;
    private boolean rotating;
    
    public EventUpdate(final float prevYaw, final float prevPitch, final double posX, final double posY, final double posZ, final double prevPosX, final double prevPosY, final double prevPosZ, final float yaw, final float pitch, final boolean ground) {
        this.prevYaw = prevYaw;
        this.prevPitch = prevPitch;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.prevPosX = prevPosX;
        this.prevPosY = prevPosY;
        this.prevPosZ = prevPosZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.ground = ground;
        this.pre = true;
    }
    
    public double getPrevPosX() {
        return this.prevPosX;
    }
    
    public double getPrevPosY() {
        return this.prevPosY;
    }
    
    public double getPrevPosZ() {
        return this.prevPosZ;
    }
    
    public boolean isRotating() {
        return this.rotating;
    }
    
    public float getPrevYaw() {
        return this.prevYaw;
    }
    
    public float getPrevPitch() {
        return this.prevPitch;
    }
    
    public boolean isPre() {
        return this.pre;
    }
    
    public void setPost() {
        this.pre = false;
    }
    
    public double getPosX() {
        return this.posX;
    }
    
    public void setPosX(final double posX) {
        this.posX = posX;
    }
    
    public double getPosY() {
        return this.posY;
    }
    
    public void setPosY(final double posY) {
        this.posY = posY;
    }
    
    public double getPosZ() {
        return this.posZ;
    }
    
    public void setPosZ(final double posZ) {
        this.posZ = posZ;
    }
    
    public void setPosition(final double x, final double y, final double z) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public void setYaw(final float yaw) {
        if (this.yaw - yaw != 0.0f) {
            this.rotating = true;
        }
        this.yaw = yaw;
    }
    
    public boolean hasMoved() {
        final double xDif = this.prevPosX - this.posX;
        final double yDif = this.prevPosY - this.posY;
        final double zDif = this.prevPosZ - this.posZ;
        return Math.sqrt(xDif * xDif + yDif * yDif + zDif * zDif) > 1.0E-5;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public void setPitch(final float pitch) {
        if (this.pitch - pitch != 0.0f) {
            this.rotating = true;
        }
        this.pitch = pitch;
    }
    
    public boolean isOnGround() {
        return this.ground;
    }
    
    public void setOnGround(final boolean ground) {
        this.ground = ground;
    }
}
