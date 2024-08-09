package dev.luvbeeq.baritone.api.pathing.goals;

import dev.luvbeeq.baritone.api.utils.BetterBlockPos;
import dev.luvbeeq.baritone.api.utils.SettingsUtil;
import dev.luvbeeq.baritone.api.utils.interfaces.IGoalRenderPos;
import net.minecraft.util.math.BlockPos;

/**
 * Useful if the goal is just to mine a block. This goal will be satisfied if the specified
 * {@link BlockPos} is at to or above the specified position for this goal.
 *
 * @author leijurv
 */
public class GoalTwoBlocks implements Goal, IGoalRenderPos {

    /**
     * The X block position of this goal
     */
    protected final int x;

    /**
     * The Y block position of this goal
     */
    protected final int y;

    /**
     * The Z block position of this goal
     */
    protected final int z;

    public GoalTwoBlocks(BlockPos pos) {
        this(pos.getX(), pos.getY(), pos.getZ());
    }

    public GoalTwoBlocks(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean isInGoal(int x, int y, int z) {
        return x == this.x && (y == this.y || y == this.y - 1) && z == this.z;
    }

    @Override
    public double heuristic(int x, int y, int z) {
        int xDiff = x - this.x;
        int yDiff = y - this.y;
        int zDiff = z - this.z;
        return GoalBlock.calculate(xDiff, yDiff < 0 ? yDiff + 1 : yDiff, zDiff);
    }

    @Override
    public BlockPos getGoalPos() {
        return new BlockPos(x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GoalTwoBlocks goal = (GoalTwoBlocks) o;
        return x == goal.x
                && y == goal.y
                && z == goal.z;
    }

    @Override
    public int hashCode() {
        return (int) BetterBlockPos.longHash(x, y, z) * 516508351;
    }

    @Override
    public String toString() {
        return String.format(
                "GoalTwoBlocks{x=%s,y=%s,z=%s}",
                SettingsUtil.maybeCensor(x),
                SettingsUtil.maybeCensor(y),
                SettingsUtil.maybeCensor(z)
        );
    }
}
