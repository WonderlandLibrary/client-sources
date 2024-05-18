package baritone;

import baritone.api.pathing.goals.*;

public final class bw extends GoalGetToBlock
{
    public bw(final et et) {
        super(et);
    }
    
    @Override
    public final boolean isInGoal(final int n, final int n2, final int n3) {
        return n2 <= this.y && super.isInGoal(n, n2, n3);
    }
}
