package baritone.api.event.events;

public final class BlockInteractEvent
{
    private final et pos;
    private final BlockInteractEvent$Type type;
    
    public BlockInteractEvent(final et pos, final BlockInteractEvent$Type type) {
        super();
        this.pos = pos;
        this.type = type;
    }
    
    public final et getPos() {
        return this.pos;
    }
    
    public final BlockInteractEvent$Type getType() {
        return this.type;
    }
}
