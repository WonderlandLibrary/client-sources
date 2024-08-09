package dev.luvbeeq.baritone.api.event.events.type;

/**
 * @author Brady
 * @since 10/11/2018
 */
public interface ICancellable {

    /**
     * Cancels this event
     */
    void cancel();

    /**
     * @return Whether or not this event has been cancelled
     */
    boolean isCancelled();
}
