package me.kaimson.melonclient.event.events;

import me.kaimson.melonclient.event.*;

public class TickEvent extends Event
{
    public final Phase phase;
    public final float renderTickTime;
    
    public TickEvent(final Phase phase, final float renderTickTime) {
        this.phase = phase;
        this.renderTickTime = renderTickTime;
    }
    
    public static class RenderTick extends TickEvent
    {
        public RenderTick(final Phase phase, final float renderTickTime) {
            super(phase, renderTickTime);
        }
        
        public static class Overlay extends RenderTick
        {
            public Overlay(final Phase phase, final float renderTickTime) {
                super(phase, renderTickTime);
            }
        }
    }
    
    public static class ClientTick extends TickEvent
    {
        public ClientTick(final Phase phase, final float renderTickTime) {
            super(phase, renderTickTime);
        }
    }
    
    public enum Phase
    {
        START, 
        END;
    }
}
