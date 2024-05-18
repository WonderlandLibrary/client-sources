package baritone.api.pathing.goals;

import baritone.api.*;

public class GoalXZ implements Goal
{
    private static final double SQRT_2;
    private final int x;
    private final int z;
    
    public GoalXZ(final int x, final int z) {
        super();
        this.x = x;
        this.z = z;
    }
    
    @Override
    public boolean isInGoal(final int n, final int n2, final int n3) {
        return n == this.x && n3 == this.z;
    }
    
    @Override
    public double heuristic(int n, int n2, final int n3) {
        n -= this.x;
        n2 = n3 - this.z;
        return calculate(n, n2);
    }
    
    @Override
    public String toString() {
        return "GoalXZ{x=" + this.x + ",z=" + this.z + "}";
    }
    
    public static double calculate(final double n, final double n2) {
        final double abs = Math.abs(n);
        final double abs2 = Math.abs(n2);
        double n3;
        double n4;
        if (abs < abs2) {
            n3 = abs2 - abs;
            n4 = abs;
        }
        else {
            n3 = abs - abs2;
            n4 = abs2;
        }
        return (n4 * GoalXZ.SQRT_2 + n3) * BaritoneAPI.getSettings().costHeuristic.value;
    }
    
    public static GoalXZ fromDirection(final bhe bhe, float n, final double n2) {
        n = (float)Math.toRadians(n);
        return new GoalXZ((int)(bhe.b - rk.a(n) * n2), (int)(bhe.d + rk.b(n) * n2));
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getZ() {
        return this.z;
    }
    
    static {
        SQRT_2 = Math.sqrt(2.0);
    }
}
