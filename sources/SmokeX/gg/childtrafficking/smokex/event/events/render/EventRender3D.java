// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.event.events.render;

import gg.childtrafficking.smokex.event.Event;

public class EventRender3D extends Event
{
    private final float partialTicks;
    
    public EventRender3D(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
}
