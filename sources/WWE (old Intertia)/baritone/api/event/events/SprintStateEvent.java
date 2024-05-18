package baritone.api.event.events;

public final class SprintStateEvent
{
    private Boolean state;
    
    public SprintStateEvent() {
        super();
    }
    
    public final void setState(final boolean b) {
        this.state = b;
    }
    
    public final Boolean getState() {
        return this.state;
    }
}
