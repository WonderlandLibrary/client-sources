package dev.luvbeeq.baritone.api.process;

import dev.luvbeeq.baritone.api.Settings;

public enum PathingCommandType {

    /**
     * Set the goal and path.
     * <p>
     * If you use this alongside a {@code null} goal, it will continue along its current path and current goal.
     */
    SET_GOAL_AND_PATH,

    /**
     * Has no effect on the current goal or path, just requests a pause
     */
    REQUEST_PAUSE,

    /**
     * Set the goal (regardless of {@code null}), and request a cancel of the current path (when safe)
     */
    CANCEL_AND_SET_GOAL,

    /**
     * Set the goal and path.
     * <p>
     * If {@link Settings#cancelOnGoalInvalidation} is {@code true}, revalidate the
     * current goal, and cancel if it's no longer valid, or if the new goal is {@code null}.
     */
    REVALIDATE_GOAL_AND_PATH,

    /**
     * Set the goal and path.
     * <p>
     * Cancel the current path if the goals are not equal
     */
    FORCE_REVALIDATE_GOAL_AND_PATH,

    /**
     * Go and ask the next process what to do
     */
    DEFER
}
