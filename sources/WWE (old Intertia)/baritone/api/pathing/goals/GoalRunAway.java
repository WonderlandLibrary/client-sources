package baritone.api.pathing.goals;

import java.util.*;

public class GoalRunAway implements Goal
{
    private final et[] from;
    private final double distanceSq;
    private final Integer maintainY;
    
    public GoalRunAway(final double n, final et... array) {
        this(n, (Integer)null, array);
    }
    
    public GoalRunAway(final double n, final Integer maintainY, final et... from) {
        super();
        if (from.length == 0) {
            throw new IllegalArgumentException();
        }
        this.from = from;
        this.distanceSq = n * n;
        this.maintainY = maintainY;
    }
    
    @Override
    public boolean isInGoal(final int n, final int n2, final int n3) {
        if (this.maintainY != null && this.maintainY != n2) {
            return false;
        }
        et[] from;
        for (int length = (from = this.from).length, i = 0; i < length; ++i) {
            final et et = from[i];
            final int n4 = n - et.p();
            final int n5 = n3 - et.r();
            final int n6 = n4;
            final int n7 = n6 * n6;
            final int n8 = n5;
            if (n7 + n8 * n8 < this.distanceSq) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public double heuristic(final int n, final int n2, final int n3) {
        double n4 = Double.MAX_VALUE;
        et[] from;
        for (int length = (from = this.from).length, i = 0; i < length; ++i) {
            final et et = from[i];
            final double calculate;
            if ((calculate = GoalXZ.calculate(et.p() - n, et.r() - n3)) < n4) {
                n4 = calculate;
            }
        }
        double n5 = -n4;
        if (this.maintainY != null) {
            n5 = n5 * 0.6 + GoalYLevel.calculate(this.maintainY, n2) * 1.5;
        }
        return n5;
    }
    
    @Override
    public String toString() {
        if (this.maintainY != null) {
            return "GoalRunAwayFromMaintainY y=" + this.maintainY + ", " + Arrays.asList(this.from);
        }
        return "GoalRunAwayFrom" + Arrays.asList(this.from);
    }
}
