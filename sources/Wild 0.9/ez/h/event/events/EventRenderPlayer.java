package ez.h.event.events;

import ez.h.event.*;

public class EventRenderPlayer extends Event
{
    public Type type;
    
    public EventRenderPlayer(final Type type) {
        this.type = type;
    }
    
    public enum Type
    {
        POST, 
        PRE;
    }
}
