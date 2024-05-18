package baritone.api.event.events;

public final class RenderEvent
{
    private final float partialTicks;
    
    public RenderEvent(final float partialTicks) {
        super();
        this.partialTicks = partialTicks;
    }
    
    public final float getPartialTicks() {
        return this.partialTicks;
    }
}
