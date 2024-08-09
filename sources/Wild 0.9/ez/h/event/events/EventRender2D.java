package ez.h.event.events;

import ez.h.event.*;

public class EventRender2D extends Event
{
    public float mouseY;
    public float partialTicks;
    public float mouseX;
    public float width;
    public float height;
    
    public EventRender2D(final float width, final float height, final float partialTicks) {
        this.width = width;
        this.height = height;
        this.partialTicks = partialTicks;
    }
}
