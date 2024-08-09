package dev.luvbeeq.baritone.utils.pathing;

import dev.luvbeeq.baritone.api.pathing.movement.ActionCosts;

/**
 * The result of a calculated movement, with destination x, y, z, and the cost of performing the movement
 *
 * @author leijurv
 */
public final class MutableMoveResult {

    public int x;
    public int y;
    public int z;
    public double cost;

    public MutableMoveResult() {
        reset();
    }

    public final void reset() {
        x = 0;
        y = 0;
        z = 0;
        cost = ActionCosts.COST_INF;
    }
}
