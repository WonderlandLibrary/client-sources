package ez.h.event.events;

import ez.h.event.*;

public class EventRender3D extends Event
{
    final float partialTicks;
    
    public EventRender3D(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
}
