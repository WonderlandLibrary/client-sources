// 
// Decompiled by Procyon v0.5.36
// 

package events.listeners;

import events.Event;

public class EventRenderNameTags extends Event<EventRenderNameTags>
{
    private final float partialTicks;
    public static EventRenderNameTags INSTANCE;
    
    public EventRenderNameTags(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
}
