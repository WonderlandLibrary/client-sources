package baritone.api.pathing.goals;

import java.util.*;

public class GoalComposite implements Goal
{
    private final Goal[] goals;
    
    public GoalComposite(final Goal... goals) {
        super();
        this.goals = goals;
    }
    
    @Override
    public boolean isInGoal(final int n, final int n2, final int n3) {
        Goal[] goals;
        for (int length = (goals = this.goals).length, i = 0; i < length; ++i) {
            if (goals[i].isInGoal(n, n2, n3)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public double heuristic(final int n, final int n2, final int n3) {
        double min = Double.MAX_VALUE;
        Goal[] goals;
        for (int length = (goals = this.goals).length, i = 0; i < length; ++i) {
            min = Math.min(min, goals[i].heuristic(n, n2, n3));
        }
        return min;
    }
    
    @Override
    public String toString() {
        return "GoalComposite" + Arrays.toString(this.goals);
    }
    
    public Goal[] goals() {
        return this.goals;
    }
}
