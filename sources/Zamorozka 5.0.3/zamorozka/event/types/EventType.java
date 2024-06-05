package zamorozka.event.types;

/**
 * Represents the timing of an {@link Subscribe}.
 *
 * <p>
 * {@link Subscribe}s can be either invoked BEFORE ({@link #PRE}), or AFTER ({@link #POST}) an event occurs.
 * </p>
 */
public enum EventType {
    /**
     * {@link Subscribe}s with this {@link EventType} are invoked before the event occurs.
     *
     * <p>
     * This is the default {@link EventType} for {@link Subscribe}s.
     * </p>
     */
    PRE,

    /**
     * {@link Subscribe}s with this {@link EventType} are invoked after the event occurs.
     *
     * <p>
     * Typically, {@link Subscribe}s can not cancel an event with this timing, as cancellation requests
     * are either ignored, or in many cases, illegal.
     * </p>
     */
    POST
}