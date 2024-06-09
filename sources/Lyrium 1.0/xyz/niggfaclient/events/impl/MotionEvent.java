// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.events.impl;

import xyz.niggfaclient.events.CancellableEvent;

public class MotionEvent extends CancellableEvent
{
    private float prevYaw;
    private float prevPitch;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private boolean onGround;
    private boolean rotating;
    private boolean pre;
    
    public MotionEvent(final double x, final double y, final double z, final float yaw, final float pitch, final float prevYaw, final float prevPitch, final boolean onGround) {
        this.pre = true;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.prevYaw = prevYaw;
        this.prevPitch = prevPitch;
        this.onGround = onGround;
    }
    
    public boolean isPre() {
        return this.pre;
    }
    
    public boolean isPost() {
        return !this.pre;
    }
    
    public void setPost() {
        this.pre = false;
    }
    
    public float getPrevYaw() {
        return this.prevYaw;
    }
    
    public float getPrevPitch() {
        return this.prevPitch;
    }
    
    public double getX() {
        return this.x;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public void setZ(final double z) {
        this.z = z;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }
    
    public boolean isOnGround() {
        return this.onGround;
    }
    
    public void setOnGround(final boolean onGround) {
        this.onGround = onGround;
    }
    
    public boolean isRotating() {
        return this.rotating;
    }
    
    public void setRotating(final boolean rotating) {
        this.rotating = rotating;
    }
}
