package baritone;

import baritone.api.pathing.goals.*;

public final class bv extends GoalGetToBlock
{
    private boolean a;
    
    public bv(final et et, final boolean a) {
        super(et);
        this.a = a;
    }
    
    @Override
    public final boolean isInGoal(final int n, final int n2, final int n3) {
        return (n != this.x || n2 != this.y || n3 != this.z) && (this.a || n2 != this.y - 1) && n2 >= this.y - 1 && super.isInGoal(n, n2, n3);
    }
    
    @Override
    public final double heuristic(final int n, final int n2, final int n3) {
        return this.y * 100 + super.heuristic(n, n2, n3);
    }
}
