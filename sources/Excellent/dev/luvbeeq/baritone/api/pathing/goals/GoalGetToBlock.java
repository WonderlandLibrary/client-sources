package dev.luvbeeq.baritone.api.pathing.goals;

import dev.luvbeeq.baritone.api.utils.BetterBlockPos;
import dev.luvbeeq.baritone.api.utils.SettingsUtil;
import dev.luvbeeq.baritone.api.utils.interfaces.IGoalRenderPos;
import net.minecraft.util.math.BlockPos;


/**
 * Don't get into the block, but get directly adjacent to it. Useful for chests.
 *
 * @author avecowa
 */
public class GoalGetToBlock implements Goal, IGoalRenderPos {

    public final int x;
    public final int y;
    public final int z;

    public GoalGetToBlock(BlockPos pos) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    @Override
    public BlockPos getGoalPos() {
        return new BlockPos(x, y, z);
    }

    @Override
    public boolean isInGoal(int x, int y, int z) {
        int xDiff = x - this.x;
        int yDiff = y - this.y;
        int zDiff = z - this.z;
        return Math.abs(xDiff) + Math.abs(yDiff < 0 ? yDiff + 1 : yDiff) + Math.abs(zDiff) <= 1;
    }

    @Override
    public double heuristic(int x, int y, int z) {
        int xDiff = x - this.x;
        int yDiff = y - this.y;
        int zDiff = z - this.z;
        return GoalBlock.calculate(xDiff, yDiff < 0 ? yDiff + 1 : yDiff, zDiff);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GoalGetToBlock goal = (GoalGetToBlock) o;
        return x == goal.x
                && y == goal.y
                && z == goal.z;
    }

    @Override
    public int hashCode() {
        return (int) BetterBlockPos.longHash(x, y, z) * -49639096;
    }

    @Override
    public String toString() {
        return String.format(
                "GoalGetToBlock{x=%s,y=%s,z=%s}",
                SettingsUtil.maybeCensor(x),
                SettingsUtil.maybeCensor(y),
                SettingsUtil.maybeCensor(z)
        );
    }
}
