package pw.stamina.causam.event;

public abstract class AbstractCancellable implements Cancellable
{
    private boolean cancelled;
    
    @Override
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    @Override
    public final boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public final void cancel() {
        this.setCancelled(true);
    }
}
