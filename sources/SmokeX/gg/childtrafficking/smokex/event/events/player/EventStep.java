// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.event.events.player;

import gg.childtrafficking.smokex.event.Event;

public final class EventStep extends Event
{
    private float stepHeight;
    private double heightStepped;
    private boolean pre;
    
    public EventStep(final float stepHeight) {
        this.stepHeight = stepHeight;
        this.pre = true;
    }
    
    public double getHeightStepped() {
        return this.heightStepped;
    }
    
    public void setHeightStepped(final double heightStepped) {
        this.heightStepped = heightStepped;
    }
    
    public boolean isPre() {
        return this.pre;
    }
    
    public void setPost() {
        this.pre = false;
    }
    
    public float getStepHeight() {
        return this.stepHeight;
    }
    
    public void setStepHeight(final float stepHeight) {
        this.stepHeight = stepHeight;
    }
}
