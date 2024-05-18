package baritone.api.pathing.goals;

import baritone.api.*;

public class GoalAxis implements Goal
{
    private static final double SQRT_2_OVER_2;
    
    public GoalAxis() {
        super();
    }
    
    @Override
    public boolean isInGoal(final int n, final int n2, final int n3) {
        return n2 == BaritoneAPI.getSettings().axisHeight.value && (n == 0 || n3 == 0 || Math.abs(n) == Math.abs(n3));
    }
    
    @Override
    public double heuristic(int abs, final int n, int abs2) {
        abs = Math.abs(abs);
        abs2 = Math.abs(abs2);
        return Math.min(abs, Math.min(abs2, (Math.max(abs, abs2) - Math.min(abs, abs2)) * GoalAxis.SQRT_2_OVER_2)) * BaritoneAPI.getSettings().costHeuristic.value + GoalYLevel.calculate(BaritoneAPI.getSettings().axisHeight.value, n);
    }
    
    @Override
    public String toString() {
        return "GoalAxis";
    }
    
    static {
        SQRT_2_OVER_2 = Math.sqrt(2.0) / 2.0;
    }
}
