package dev.luvbeeq.baritone.api.pathing.movement;

/**
 * @author Brady
 * @since 10/8/2018
 */
public enum MovementStatus {

    /**
     * We are preparing the movement to be executed. This is when any blocks obstructing the destination are broken.
     */
    PREPPING(false),

    /**
     * We are waiting for the movement to begin, after {@link MovementStatus#PREPPING}.
     */
    WAITING(false),

    /**
     * The movement is currently in progress, after {@link MovementStatus#WAITING}
     */
    RUNNING(false),

    /**
     * The movement has been completed and we are at our destination
     */
    SUCCESS(true),

    /**
     * There was a change in state between calculation and actual
     * movement execution, and the movement has now become impossible.
     */
    UNREACHABLE(true),

    /**
     * Unused
     */
    FAILED(true),

    /**
     * "Unused"
     */
    CANCELED(true);

    /**
     * Whether or not this status indicates a complete movement.
     */
    private final boolean complete;

    MovementStatus(boolean complete) {
        this.complete = complete;
    }

    public final boolean isComplete() {
        return this.complete;
    }
}
