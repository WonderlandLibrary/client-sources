// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.event.events.render;

import gg.childtrafficking.smokex.utils.render.LockedResolution;
import gg.childtrafficking.smokex.event.Event;

public class EventRender2D extends Event
{
    private final LockedResolution resolution;
    private final float partialTicks;
    
    public EventRender2D(final LockedResolution resolution, final float partialTicks) {
        this.resolution = resolution;
        this.partialTicks = partialTicks;
    }
    
    public EventRender2D(final float partialTicks) {
        this.partialTicks = partialTicks;
        this.resolution = null;
    }
    
    public LockedResolution getResolution() {
        return this.resolution;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
}
