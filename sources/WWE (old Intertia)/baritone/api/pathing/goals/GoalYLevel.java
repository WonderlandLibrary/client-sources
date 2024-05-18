package baritone.api.pathing.goals;

import baritone.api.pathing.movement.*;

public class GoalYLevel implements Goal, ActionCosts
{
    private final int level;
    
    public GoalYLevel(final int level) {
        super();
        this.level = level;
    }
    
    @Override
    public boolean isInGoal(final int n, final int n2, final int n3) {
        return n2 == this.level;
    }
    
    @Override
    public double heuristic(final int n, final int n2, final int n3) {
        return calculate(this.level, n2);
    }
    
    public static double calculate(final int n, final int n2) {
        if (n2 > n) {
            return GoalYLevel.FALL_N_BLOCKS_COST[2] / 2.0 * (n2 - n);
        }
        if (n2 < n) {
            return (n - n2) * GoalYLevel.JUMP_ONE_BLOCK_COST;
        }
        return 0.0;
    }
    
    @Override
    public String toString() {
        return "GoalYLevel{y=" + this.level + "}";
    }
}
