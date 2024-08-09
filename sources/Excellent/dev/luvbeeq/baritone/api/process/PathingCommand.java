package dev.luvbeeq.baritone.api.process;

import dev.luvbeeq.baritone.api.pathing.goals.Goal;

import java.util.Objects;

/**
 * @author leijurv
 */
public class PathingCommand {

    /**
     * The target goal, may be {@code null}.
     */
    public final Goal goal;

    /**
     * The command type.
     *
     * @see PathingCommandType
     */
    public final PathingCommandType commandType;

    /**
     * Create a new {@link PathingCommand}.
     *
     * @param goal        The target goal, may be {@code null}.
     * @param commandType The command type, cannot be {@code null}.
     * @throws NullPointerException if {@code commandType} is {@code null}.
     * @see Goal
     * @see PathingCommandType
     */
    public PathingCommand(Goal goal, PathingCommandType commandType) {
        Objects.requireNonNull(commandType);

        this.goal = goal;
        this.commandType = commandType;
    }

    @Override
    public String toString() {
        return commandType + " " + goal;
    }
}
