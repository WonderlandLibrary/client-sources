package zamorozka.event.types;

/**
 * Represents the priority of an {@link Subscribe}.
 *
 * <p>
 * {@link Subscribe}s are invoked in the ascending {@link #ordinal()} order, that is:
 * starting from {@link #HIGHEST} and ending with {@link #MONITOR}.
 * </p>
 */
public enum EventPriority {
    /**
     * {@link Subscribe}s with this {@link EventPriority} have the FIRST (1) priority to be invoked.
     */
    HIGHEST,

    /**
     * {@link Subscribe}s with this {@link EventPriority} have the SECOND (2) priority to be invoked.
     */
    HIGHER,

    /**
     * {@link Subscribe}s with this {@link EventPriority} have the THIRD (3) priority to be invoked.
     */
    HIGH,

    /**
     * {@link Subscribe}s with this {@link EventPriority} have the FOURTH (4) priority to be invoked.
     * <p>
     * This is the default {@link EventPriority} for {@link Subscribe}s
     * </p>
     */
    MEDIUM,

    /**
     * {@link Subscribe}s with this {@link EventPriority} have the FIFTH (5) priority to be invoked.
     */
    LOW,

    /**
     * {@link Subscribe}s with this {@link EventPriority} have the SIXTH (6) priority to be invoked.
     */
    LOWER,

    /**
     *{@link Subscribe}s with this {@link EventPriority} have the SEVENTH (7) priority to be invoked.
     */
    LOWEST,

    /**
     * {@link Subscribe}s with this {@link EventPriority} have the EIGHTH (8), and LAST priority to be invoked.
     *
     * <p>
     * By convention, all {@link Subscribe}s with this {@link EventPriority} shall not modify events in any way.
     * </p>
     */
    MONITOR
}