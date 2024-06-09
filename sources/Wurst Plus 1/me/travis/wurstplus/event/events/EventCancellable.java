package me.travis.wurstplus.event.events;

public class EventCancellable extends EventStageable
{
    private boolean canceled;
    
    public EventCancellable() {
    }
    
    public EventCancellable(final EventStage stage) {
        super(stage);
    }
    
    public EventCancellable(final EventStage stage, final boolean canceled) {
        super(stage);
        this.canceled = canceled;
    }
    
    public boolean isCanceled() {
        return this.canceled;
    }
    
    public void setCanceled(final boolean canceled) {
        this.canceled = canceled;
    }
}
