package me.valk.event.events.screen;

import me.valk.event.Event;
import net.minecraft.client.gui.ScaledResolution;

public class EventRenderer2D extends Event
{
    public ScaledResolution res;
    
    public EventRenderer2D(final ScaledResolution res) {
        this.res = res;
    }
}
