package baritone.api.event.events.type;

public enum EventState
{
    PRE, 
    POST;
    
    private static final EventState[] $VALUES;
    
    public static EventState[] values() {
        return EventState.$VALUES.clone();
    }
    
    public static EventState valueOf(final String s) {
        return Enum.valueOf(EventState.class, s);
    }
    
    static {
        $VALUES = new EventState[] { EventState.PRE, EventState.POST };
    }
}
