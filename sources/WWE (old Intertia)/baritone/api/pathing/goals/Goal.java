package baritone.api.pathing.goals;

public interface Goal
{
    boolean isInGoal(final int p0, final int p1, final int p2);
    
    double heuristic(final int p0, final int p1, final int p2);
    
    default boolean isInGoal(final et et) {
        return this.isInGoal(et.p(), et.q(), et.r());
    }
    
    default double heuristic(final et et) {
        return this.heuristic(et.p(), et.q(), et.r());
    }
}
