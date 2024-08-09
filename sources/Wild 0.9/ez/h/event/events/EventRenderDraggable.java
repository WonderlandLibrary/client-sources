package ez.h.event.events;

import ez.h.event.*;

public class EventRenderDraggable extends Event
{
    public float mouseY;
    public float ticks;
    public float mouseX;
    
    public EventRenderDraggable(final float mouseX, final float mouseY, final float ticks) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.ticks = ticks;
    }
}
