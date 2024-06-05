package me.satisfactory.base.events;

import me.satisfactory.base.events.event.*;

public class Event3DRender implements Event
{
    public float partialTicks;
    
    public Event3DRender(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
}
