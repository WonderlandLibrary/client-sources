// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Events;

import net.minecraft.client.network.badlion.memes.events.callables.MemeMeable;

public class EventUpdate extends MemeMeable
{
    public double y;
    public float yaw;
    public float pitch;
    public boolean onGround;
    public EventState state;
    public boolean alwaysSend;
    private double stepHeight;
    private boolean active;
    
    public EventUpdate(final double y, final float yaw, final float pitch, final boolean onGround) {
        this.y = y;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
        this.state = EventState.PRE;
    }
    
    public EventUpdate() {
        this.state = EventState.POST;
    }
    
    public double getStepHeight() {
        return this.stepHeight;
    }
    
    public boolean shouldAlwaysSend() {
        return this.alwaysSend;
    }
    
    public boolean isActive() {
        return this.active;
    }
    
    public void setStepHeight(final double stepHeight) {
        this.stepHeight = stepHeight;
    }
    
    public void setActive(final boolean bypass) {
        this.active = bypass;
    }
    
    public void setAlwaysSend(final boolean alwaysSend) {
        this.alwaysSend = alwaysSend;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
}
