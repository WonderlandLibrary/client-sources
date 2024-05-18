package baritone.api.event.events;

import baritone.api.event.events.type.*;

public final class ChunkEvent
{
    private final EventState state;
    private final ChunkEvent$Type type;
    private final int x;
    private final int z;
    
    public ChunkEvent(final EventState state, final ChunkEvent$Type type, final int x, final int z) {
        super();
        this.state = state;
        this.type = type;
        this.x = x;
        this.z = z;
    }
    
    public final EventState getState() {
        return this.state;
    }
    
    public final ChunkEvent$Type getType() {
        return this.type;
    }
    
    public final int getX() {
        return this.x;
    }
    
    public final int getZ() {
        return this.z;
    }
}
