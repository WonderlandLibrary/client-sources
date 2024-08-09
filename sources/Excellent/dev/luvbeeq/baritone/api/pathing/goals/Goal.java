package dev.luvbeeq.baritone.api.pathing.goals;

import net.minecraft.util.math.BlockPos;

/**
 * An abstract Goal for pathing, can be anything from a specific block to just a Y coordinate.
 *
 * @author leijurv
 */
public interface Goal {

    /**
     * Returns whether or not the specified position
     * meets the requirement for this goal based.
     *
     * @param x The goal X position
     * @param y The goal Y position
     * @param z The goal Z position
     * @return Whether or not it satisfies this goal
     */
    boolean isInGoal(int x, int y, int z);

    /**
     * Estimate the number of ticks it will take to get to the goal
     *
     * @param x The goal X position
     * @param y The goal Y position
     * @param z The goal Z position
     * @return The estimate number of ticks to satisfy the goal
     */
    double heuristic(int x, int y, int z);

    default boolean isInGoal(BlockPos pos) {
        return isInGoal(pos.getX(), pos.getY(), pos.getZ());
    }

    default double heuristic(BlockPos pos) {
        return heuristic(pos.getX(), pos.getY(), pos.getZ());
    }

    /**
     * Returns the heuristic at the goal.
     * i.e. {@code heuristic() == heuristic(x,y,z)}
     * when {@code isInGoal(x,y,z) == true}
     * This is needed by {@code PathingBehavior#estimatedTicksToGoal} because
     * some Goals actually do not have a heuristic of 0 when that condition is met
     *
     * @return The estimate number of ticks to satisfy the goal when the goal
     * is already satisfied
     */
    default double heuristic() {
        return 0;
    }
}
