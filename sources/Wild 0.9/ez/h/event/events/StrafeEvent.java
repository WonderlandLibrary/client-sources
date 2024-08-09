package ez.h.event.events;

import ez.h.event.*;

public class StrafeEvent extends Event
{
    public float forward;
    public float strafe;
    public float friction;
    
    public float getFriction() {
        return this.friction;
    }
    
    public StrafeEvent(final float strafe, final float forward, final float friction) {
        this.strafe = strafe;
        this.forward = forward;
        this.friction = friction;
    }
    
    public void setForward(final float forward) {
        this.forward = forward;
    }
    
    public float getStrafe() {
        return this.strafe;
    }
    
    public void setFriction(final float friction) {
        this.friction = friction;
    }
    
    public float getForward() {
        return this.forward;
    }
    
    public void setStrafe(final float strafe) {
        this.strafe = strafe;
    }
}
