// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.event.events;

import net.andrewsnetwork.icarus.event.Cancellable;
import net.andrewsnetwork.icarus.event.Event;

public class MotionFlying extends Event implements Cancellable
{
    private float strafe;
    private float forward;
    private float friction;
    private boolean cancel;
    
    public MotionFlying(final float strafe, final float forward, final float friction) {
        this.strafe = strafe;
        this.forward = forward;
        this.friction = friction;
    }
    
    public float getStrafe() {
        return this.strafe;
    }
    
    public void setStrafe(final float strafe) {
        this.strafe = strafe;
    }
    
    public float getForward() {
        return this.forward;
    }
    
    public void setForward(final float forward) {
        this.forward = forward;
    }
    
    public float getFriction() {
        return this.friction;
    }
    
    public void setFriction(final float friction) {
        this.friction = friction;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancel;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
}
