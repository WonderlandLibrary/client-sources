package net.futureclient.client.events;

import net.futureclient.client.KF;

public enum EventType2
{
    POST, 
    PRE;
    
    private static final EventType2[] values;
    
    static {
        values = new EventType2[] { EventType2.POST, EventType2.PRE };
    }
    
    public static EventType2[] values() {
        return EventType2.values.clone();
    }
    
    public static EventType2 valueOf(final String s) {
        return Enum.<EventType2>valueOf((Class<EventType2>)KF.ZD.class, s);
    }
}
