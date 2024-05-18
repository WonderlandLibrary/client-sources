package baritone.api.event.events;

import baritone.api.event.events.type.*;

public final class WorldEvent
{
    private final bsb world;
    private final EventState state;
    
    public WorldEvent(final bsb world, final EventState state) {
        super();
        this.world = world;
        this.state = state;
    }
    
    public final bsb getWorld() {
        return this.world;
    }
    
    public final EventState getState() {
        return this.state;
    }
}
