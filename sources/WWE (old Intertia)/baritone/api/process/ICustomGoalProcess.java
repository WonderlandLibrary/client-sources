package baritone.api.process;

import baritone.api.pathing.goals.*;

public interface ICustomGoalProcess extends IBaritoneProcess
{
    void setGoal(final Goal p0);
    
    void path();
    
    Goal getGoal();
    
    default void setGoalAndPath(final Goal goal) {
        this.setGoal(goal);
        this.path();
    }
}
