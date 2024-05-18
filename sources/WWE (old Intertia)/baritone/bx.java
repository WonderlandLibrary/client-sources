package baritone;

import baritone.api.pathing.goals.*;

public final class bx extends GoalBlock
{
    public bx(final et et) {
        super(et.a());
    }
    
    @Override
    public final double heuristic(final int n, final int n2, final int n3) {
        return this.y * 100 + super.heuristic(n, n2, n3);
    }
}
