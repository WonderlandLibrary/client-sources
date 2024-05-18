// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Events;

import net.minecraft.client.network.badlion.memes.events.Event;

public class StepEvent implements Event
{
    private double stepHeight;
    private boolean active;
    private EventState state;
    
    public StepEvent(final double stepHeight, final EventState state) {
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
    
    public EventState getState() {
        return this.state;
    }
    
    public void setState(final EventState state) {
        this.state = state;
    }
}
