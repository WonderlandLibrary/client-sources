package baritone.api.event.events;

import baritone.api.event.events.type.*;

public final class PlayerUpdateEvent
{
    private final EventState state;
    
    public PlayerUpdateEvent(final EventState state) {
        super();
        this.state = state;
    }
    
    public final EventState getState() {
        return this.state;
    }
}
