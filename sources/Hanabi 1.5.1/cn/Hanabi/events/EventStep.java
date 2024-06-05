package cn.Hanabi.events;

import com.darkmagician6.eventapi.events.callables.*;

public class EventStep extends EventCancellable
{
    private double stepHeight;
    private double realHeight;
    private boolean active;
    private boolean pre;
    
    public EventStep(final boolean state, final double stepHeight, final double realHeight) {
        this.pre = state;
        this.stepHeight = stepHeight;
        this.realHeight = realHeight;
    }
    
    public EventStep(final boolean state, final double stepHeight) {
        this.pre = state;
        this.realHeight = stepHeight;
        this.stepHeight = stepHeight;
    }
    
    public boolean isPre() {
        return this.pre;
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
    
    public double getRealHeight() {
        return this.realHeight;
    }
    
    public void setRealHeight(final double realHeight) {
        this.realHeight = realHeight;
    }
}
