package pw.latematt.xiv.event.events;

import pw.latematt.xiv.event.Cancellable;
import pw.latematt.xiv.event.Event;

/**
 * @author Rederpz
 */
public class ResetDamageEvent extends Event implements Cancellable {
    private boolean cancelled;

    public ResetDamageEvent(boolean cancelled) {
        setCancelled(cancelled);
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
