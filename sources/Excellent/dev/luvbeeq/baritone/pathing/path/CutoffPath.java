package dev.luvbeeq.baritone.pathing.path;

import dev.luvbeeq.baritone.api.pathing.calc.IPath;
import dev.luvbeeq.baritone.api.pathing.goals.Goal;
import dev.luvbeeq.baritone.api.pathing.movement.IMovement;
import dev.luvbeeq.baritone.api.utils.BetterBlockPos;
import dev.luvbeeq.baritone.utils.pathing.PathBase;

import java.util.Collections;
import java.util.List;

public class CutoffPath extends PathBase {

    private final List<BetterBlockPos> path;

    private final List<IMovement> movements;

    private final int numNodes;

    private final Goal goal;

    public CutoffPath(IPath prev, int firstPositionToInclude, int lastPositionToInclude) {
        path = prev.positions().subList(firstPositionToInclude, lastPositionToInclude + 1);
        movements = prev.movements().subList(firstPositionToInclude, lastPositionToInclude);
        numNodes = prev.getNumNodesConsidered();
        goal = prev.getGoal();
        sanityCheck();
    }

    public CutoffPath(IPath prev, int lastPositionToInclude) {
        this(prev, 0, lastPositionToInclude);
    }

    @Override
    public Goal getGoal() {
        return goal;
    }

    @Override
    public List<IMovement> movements() {
        return Collections.unmodifiableList(movements);
    }

    @Override
    public List<BetterBlockPos> positions() {
        return Collections.unmodifiableList(path);
    }

    @Override
    public int getNumNodesConsidered() {
        return numNodes;
    }
}
