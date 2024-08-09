package dev.luvbeeq.baritone.pathing.path;

import dev.luvbeeq.baritone.api.pathing.calc.IPath;
import dev.luvbeeq.baritone.api.pathing.goals.Goal;
import dev.luvbeeq.baritone.api.pathing.movement.IMovement;
import dev.luvbeeq.baritone.api.utils.BetterBlockPos;
import dev.luvbeeq.baritone.utils.pathing.PathBase;

import java.util.*;

public class SplicedPath extends PathBase {

    private final List<BetterBlockPos> path;

    private final List<IMovement> movements;

    private final int numNodes;

    private final Goal goal;

    private SplicedPath(List<BetterBlockPos> path, List<IMovement> movements, int numNodesConsidered, Goal goal) {
        this.path = path;
        this.movements = movements;
        this.numNodes = numNodesConsidered;
        this.goal = goal;
        sanityCheck();
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

    @Override
    public int length() {
        return path.size();
    }

    public static Optional<SplicedPath> trySplice(IPath first, IPath second, boolean allowOverlapCutoff) {
        if (second == null || first == null) {
            return Optional.empty();
        }
        if (!first.getDest().equals(second.getSrc())) {
            return Optional.empty();
        }
        HashSet<BetterBlockPos> secondPos = new HashSet<>(second.positions());
        int firstPositionInSecond = -1;
        for (int i = 0; i < first.length() - 1; i++) { // overlap in the very last element is fine (and required) so only go up to first.length() - 1
            if (secondPos.contains(first.positions().get(i))) {
                firstPositionInSecond = i;
                break;
            }
        }
        if (firstPositionInSecond != -1) {
            if (!allowOverlapCutoff) {
                return Optional.empty();
            }
        } else {
            firstPositionInSecond = first.length() - 1;
        }
        int positionInSecond = second.positions().indexOf(first.positions().get(firstPositionInSecond));
        if (!allowOverlapCutoff && positionInSecond != 0) {
            throw new IllegalStateException();
        }
        List<BetterBlockPos> positions = new ArrayList<>();
        List<IMovement> movements = new ArrayList<>();
        positions.addAll(first.positions().subList(0, firstPositionInSecond + 1));
        movements.addAll(first.movements().subList(0, firstPositionInSecond));

        positions.addAll(second.positions().subList(positionInSecond + 1, second.length()));
        movements.addAll(second.movements().subList(positionInSecond, second.length() - 1));
        return Optional.of(new SplicedPath(positions, movements, first.getNumNodesConsidered() + second.getNumNodesConsidered(), first.getGoal()));
    }
}
