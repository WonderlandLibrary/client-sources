// 
// Decompiled by Procyon v0.5.30
// 

package com.zCoreEvent.events;

import com.zCoreEvent.Event;

public class Render3DEvent extends Event
{
    private float partialTicks;
    
    public Render3DEvent(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
    
    public void setPartialTicks(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
}
