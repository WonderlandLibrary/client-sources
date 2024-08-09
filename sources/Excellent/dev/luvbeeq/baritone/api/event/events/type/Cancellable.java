package dev.luvbeeq.baritone.api.event.events.type;

/**
 * @author Brady
 * @since 8/1/2018
 */
public class Cancellable implements ICancellable {

    /**
     * Whether or not this event has been cancelled
     */
    private boolean cancelled;

    @Override
    public final void cancel() {
        this.cancelled = true;
    }

    @Override
    public final boolean isCancelled() {
        return this.cancelled;
    }
}
