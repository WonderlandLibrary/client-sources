package dev.luvbeeq.baritone.api.pathing.goals;

import java.util.Arrays;

/**
 * A composite of many goals, any one of which satisfies the composite.
 * For example, a GoalComposite of block goals for every oak log in loaded chunks
 * would result in it pathing to the easiest oak log to get to
 *
 * @author avecowa
 */
public class GoalComposite implements Goal {

    /**
     * An array of goals that any one of must be satisfied
     */
    private final Goal[] goals;

    public GoalComposite(Goal... goals) {
        this.goals = goals;
    }

    @Override
    public boolean isInGoal(int x, int y, int z) {
        for (Goal goal : goals) {
            if (goal.isInGoal(x, y, z)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public double heuristic(int x, int y, int z) {
        double min = Double.MAX_VALUE;
        for (Goal g : goals) {
            // TODO technically this isn't admissible...?
            min = Math.min(min, g.heuristic(x, y, z)); // whichever is closest
        }
        return min;
    }

    @Override
    public double heuristic() {
        double min = Double.MAX_VALUE;
        for (Goal g : goals) {
            // just take the highest value that is guaranteed to be inside the goal
            min = Math.min(min, g.heuristic());
        }
        return min;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GoalComposite goal = (GoalComposite) o;
        return Arrays.equals(goals, goal.goals);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(goals);
    }

    @Override
    public String toString() {
        return "GoalComposite" + Arrays.toString(goals);
    }

    public Goal[] goals() {
        return goals;
    }
}
