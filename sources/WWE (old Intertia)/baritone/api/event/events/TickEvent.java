package baritone.api.event.events;

import baritone.api.event.events.type.*;

public final class TickEvent
{
    private final EventState state;
    private final TickEvent$Type type;
    private final int count;
    private static int overallTickCount;
    
    public TickEvent(final EventState state, final TickEvent$Type type) {
        super();
        this.state = state;
        this.type = type;
        this.count = incrementCount();
    }
    
    private static synchronized int incrementCount() {
        return TickEvent.overallTickCount++;
    }
    
    public final int getCount() {
        return this.count;
    }
    
    public final TickEvent$Type getType() {
        return this.type;
    }
    
    public final EventState getState() {
        return this.state;
    }
}
