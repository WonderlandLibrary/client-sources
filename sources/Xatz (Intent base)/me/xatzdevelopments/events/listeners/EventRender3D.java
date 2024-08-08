package me.xatzdevelopments.events.listeners;

import me.xatzdevelopments.events.Event;

public class EventRender3D extends Event<EventRender3D>
{
    public float partialTicks;
    
    public EventRender3D(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public float getpartialTicks() {
        return this.partialTicks;
    }
    
    public void setpartialTicks(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
}
