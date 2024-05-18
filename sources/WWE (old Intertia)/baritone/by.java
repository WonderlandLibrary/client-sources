package baritone;

import baritone.api.pathing.goals.*;

public final class by implements Goal
{
    private final Goal a;
    private final Goal b;
    
    public by(final Goal a, final Goal b) {
        super();
        this.a = a;
        this.b = b;
    }
    
    @Override
    public final boolean isInGoal(final int n, final int n2, final int n3) {
        return this.a.isInGoal(n, n2, n3) || this.b.isInGoal(n, n2, n3);
    }
    
    @Override
    public final double heuristic(final int n, final int n2, final int n3) {
        return this.a.heuristic(n, n2, n3);
    }
    
    @Override
    public final String toString() {
        return "JankyComposite Primary: " + this.a + " Fallback: " + this.b;
    }
}
