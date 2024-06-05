package cn.Hanabi.events;

import com.darkmagician6.eventapi.events.*;

public class EventRender2D implements Event
{
    public float partialTicks;
    
    public EventRender2D(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
}
