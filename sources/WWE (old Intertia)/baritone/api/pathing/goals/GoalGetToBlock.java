package baritone.api.pathing.goals;

import baritone.api.utils.interfaces.*;

public class GoalGetToBlock implements Goal, IGoalRenderPos
{
    public final int x;
    public final int y;
    public final int z;
    
    public GoalGetToBlock(final et et) {
        super();
        this.x = et.p();
        this.y = et.q();
        this.z = et.r();
    }
    
    @Override
    public et getGoalPos() {
        return new et(this.x, this.y, this.z);
    }
    
    @Override
    public boolean isInGoal(int n, int n2, int n3) {
        n -= this.x;
        n2 -= this.y;
        n3 -= this.z;
        return Math.abs(n) + Math.abs((n2 < 0) ? (n2 + 1) : n2) + Math.abs(n3) <= 1;
    }
    
    @Override
    public double heuristic(int n, int n2, int n3) {
        n -= this.x;
        n2 -= this.y;
        n3 -= this.z;
        return GoalBlock.calculate(n, (n2 < 0) ? (n2 + 1) : n2, n3);
    }
    
    @Override
    public String toString() {
        return "GoalGetToBlock{x=" + this.x + ",y=" + this.y + ",z=" + this.z + "}";
    }
}
