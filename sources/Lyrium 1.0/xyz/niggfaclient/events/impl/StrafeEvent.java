// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.events.impl;

import xyz.niggfaclient.events.CancellableEvent;

public class StrafeEvent extends CancellableEvent
{
    public float strafe;
    public float forward;
    public float friction;
    
    public StrafeEvent(final float strafe, final float forward, final float friction) {
        this.strafe = strafe;
        this.forward = forward;
        this.friction = friction;
    }
    
    public void setStrafe(final float strafe) {
        this.strafe = strafe;
    }
    
    public float getStrafe() {
        return this.strafe;
    }
    
    public void setForward(final float forward) {
        this.forward = forward;
    }
    
    public float getForward() {
        return this.forward;
    }
    
    public void setFriction(final float friction) {
        this.friction = friction;
    }
    
    public float getFriction() {
        return this.friction;
    }
}
