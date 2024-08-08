package net.futureclient.client.events;

import net.futureclient.client.KD;

public enum EventType
{
    POST, 
    PRE;
    
    private static final EventType[] values;
    
    static {
        values = new EventType[] { EventType.PRE, EventType.POST };
    }
    
    public static EventType[] values() {
        return EventType.values.clone();
    }
    
    public static EventType valueOf(final String s) {
        return Enum.<EventType>valueOf((Class<EventType>)KD.TE.class, s);
    }
}
