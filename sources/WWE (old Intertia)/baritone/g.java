package baritone;

import baritone.api.pathing.goals.*;
import baritone.api.utils.*;

public final class g
{
    public final int a;
    public final int b;
    public final int c;
    public final double a;
    public double b;
    public double c;
    public g a;
    public int d;
    
    public g(final int a, final int b, final int c, final Goal goal) {
        super();
        this.a = null;
        this.b = 1000000.0;
        this.a = goal.heuristic(a, b, c);
        if (Double.isNaN(this.a)) {
            throw new IllegalStateException(goal + " calculated implausible heuristic");
        }
        this.d = -1;
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    @Override
    public final int hashCode() {
        return (int)BetterBlockPos.longHash(this.a, this.b, this.c);
    }
    
    @Override
    public final boolean equals(final Object o) {
        final g g = (g)o;
        return this.a == g.a && this.b == g.b && this.c == g.c;
    }
}
