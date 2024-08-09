package dev.luvbeeq.baritone.api.pathing.goals;

import java.util.Objects;

/**
 * Invert any goal.
 * <p>
 * In the old chat control system, #invert just tried to pick a {@link GoalRunAway} that <i>effectively</i> inverted the
 * current goal. This goal just reverses the heuristic to act as a TRUE invert. Inverting a Y level? Baritone tries to
 * get away from that Y level. Inverting a GoalBlock? Baritone will try to make distance whether it's in the X, Y or Z
 * directions. And of course, you can always invert a GoalXZ.
 *
 * @author LoganDark
 */
public class GoalInverted implements Goal {

    public final Goal origin;

    public GoalInverted(Goal origin) {
        this.origin = origin;
    }

    @Override
    public boolean isInGoal(int x, int y, int z) {
        return false;
    }

    @Override
    public double heuristic(int x, int y, int z) {
        return -origin.heuristic(x, y, z);
    }

    @Override
    public double heuristic() {
        return Double.NEGATIVE_INFINITY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GoalInverted goal = (GoalInverted) o;
        return Objects.equals(origin, goal.origin);
    }

    @Override
    public int hashCode() {
        return origin.hashCode() * 495796690;
    }

    @Override
    public String toString() {
        return String.format("GoalInverted{%s}", origin.toString());
    }
}
