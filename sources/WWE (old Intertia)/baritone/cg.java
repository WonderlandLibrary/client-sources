package baritone;

import baritone.api.pathing.goals.*;

final class cg extends GoalRunAway
{
    private cf a;
    
    cg(final cf a, final et... array) {
        this.a = a;
        super(1.0, array);
    }
    
    @Override
    public final boolean isInGoal(final int n, final int n2, final int n3) {
        return false;
    }
}
