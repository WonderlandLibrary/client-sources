// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.management.event.events;

import net.minecraft.client.triton.management.event.Event;

public class StepEvent extends Event
{
    private double stepHeight;
    private boolean active;
    private State state;
    
    public StepEvent(final double stepHeight, final State state) {
        this.stepHeight = stepHeight;
        this.state = state;
    }
    
    public double getStepHeight() {
        return this.stepHeight;
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
    
    public State getState() {
        return this.state;
    }
    
    public void setState(final State state) {
        this.state = state;
    }
}
