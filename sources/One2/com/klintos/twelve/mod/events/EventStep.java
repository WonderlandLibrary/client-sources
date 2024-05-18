// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.events;

import com.darkmagician6.eventapi.events.Event;

public class EventStep implements Event
{
    private double offset;
    
    public EventStep(final double offset) {
        this.offset = offset;
    }
    
    public double getOffset() {
        return this.offset;
    }
    
    public void setOffset(final double offset) {
        this.offset = offset;
    }
}
