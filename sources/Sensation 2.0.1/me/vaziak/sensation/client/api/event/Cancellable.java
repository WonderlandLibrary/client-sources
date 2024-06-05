package me.vaziak.sensation.client.api.event;

/**
 * @since 4/20/69
 */
public class Cancellable {

    private boolean cancelled;

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
