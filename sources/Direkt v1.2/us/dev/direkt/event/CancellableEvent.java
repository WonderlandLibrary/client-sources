package us.dev.direkt.event;

/**
 * @author Foundry
 */
public abstract class CancellableEvent implements Event, Cancellable {
    private boolean cancelled;

    @Override
    public void setCancelled(boolean state) {
        cancelled = state;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }
}
