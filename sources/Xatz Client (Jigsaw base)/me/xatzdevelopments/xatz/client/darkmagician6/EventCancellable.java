package me.xatzdevelopments.xatz.client.darkmagician6;


/**
 * Abstract example implementation of the Cancellable interface.
 *
 * @author DarkMagician6
 * @since August 27, 2013
 */
public abstract class EventCancellable implements Event, Cancellable {

    protected boolean cancelled;

    protected EventCancellable() {
    }

    /**
     * @see darkmagician6.darkmagician6.eventapi.events.Cancellable.isCancelled
     */
    public boolean isCancelled() {
        return this.cancelled;
    }
     

    /**
     * @see darkmagician6.darkmagician6.eventapi.events.Cancellable.setCancelled
     */
    public void setCancelled(boolean state) {
        this.cancelled = state;
     }
  }

