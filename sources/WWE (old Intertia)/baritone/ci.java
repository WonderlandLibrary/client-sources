package baritone;

import baritone.api.pathing.goals.*;

final class ci extends GoalRunAway
{
    private ch a;
    
    ci(final ch a, final Integer n, final et... array) {
        this.a = a;
        super(1.0, n, array);
    }
    
    @Override
    public final boolean isInGoal(final int n, final int n2, final int n3) {
        return false;
    }
}
