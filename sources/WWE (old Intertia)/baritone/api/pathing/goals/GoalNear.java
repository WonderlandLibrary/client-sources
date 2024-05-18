package baritone.api.pathing.goals;

import baritone.api.utils.interfaces.*;

public class GoalNear implements Goal, IGoalRenderPos
{
    private final int x;
    private final int y;
    private final int z;
    private final int rangeSq;
    
    public GoalNear(final et et, final int n) {
        super();
        this.x = et.p();
        this.y = et.q();
        this.z = et.r();
        this.rangeSq = n * n;
    }
    
    @Override
    public boolean isInGoal(int n, int n2, int n3) {
        n -= this.x;
        n2 -= this.y;
        n3 -= this.z;
        final int n4 = n;
        final int n5 = n4 * n4;
        final int n6 = n2;
        final int n7 = n5 + n6 * n6;
        final int n8 = n3;
        return n7 + n8 * n8 <= this.rangeSq;
    }
    
    @Override
    public double heuristic(int n, int n2, int n3) {
        n -= this.x;
        n2 -= this.y;
        n3 -= this.z;
        return GoalBlock.calculate(n, n2, n3);
    }
    
    @Override
    public et getGoalPos() {
        return new et(this.x, this.y, this.z);
    }
    
    @Override
    public String toString() {
        return "GoalNear{x=" + this.x + ", y=" + this.y + ", z=" + this.z + ", rangeSq=" + this.rangeSq + '}';
    }
}
