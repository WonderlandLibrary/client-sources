// 
// Decompiled by Procyon v0.5.36
// 

package events.listeners;

import events.Event;

public class EventRender3D extends Event<EventRender3D>
{
    private final float partialTicks;
    public static EventRender3D INSTANCE;
    
    public EventRender3D(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
}
