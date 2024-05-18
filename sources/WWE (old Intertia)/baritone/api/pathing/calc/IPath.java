package baritone.api.pathing.calc;

import baritone.api.pathing.movement.*;
import baritone.api.utils.*;
import baritone.api.pathing.goals.*;
import java.util.*;

public interface IPath
{
    List<IMovement> movements();
    
    List<BetterBlockPos> positions();
    
    default IPath postProcess() {
        throw new UnsupportedOperationException();
    }
    
    default int length() {
        return this.positions().size();
    }
    
    Goal getGoal();
    
    int getNumNodesConsidered();
    
    default BetterBlockPos getSrc() {
        return this.positions().get(0);
    }
    
    default BetterBlockPos getDest() {
        final List<BetterBlockPos> positions = this.positions();
        return positions.get(positions.size() - 1);
    }
    
    default double ticksRemainingFrom(int i) {
        double n = 0.0;
        for (List<IMovement> movements = this.movements(); i < movements.size(); ++i) {
            n += movements.get(i).getCost();
        }
        return n;
    }
    
    default IPath cutoffAtLoadedChunks(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    default IPath staticCutoff(final Goal goal) {
        throw new UnsupportedOperationException();
    }
    
    default void sanityCheck() {
        final List<BetterBlockPos> positions = this.positions();
        final List<IMovement> movements = this.movements();
        if (!this.getSrc().equals(positions.get(0))) {
            throw new IllegalStateException("Start node does not equal first path element");
        }
        final BetterBlockPos dest = this.getDest();
        final List<BetterBlockPos> list = positions;
        if (!dest.equals(list.get(list.size() - 1))) {
            throw new IllegalStateException("End node does not equal last path element");
        }
        if (positions.size() != movements.size() + 1) {
            throw new IllegalStateException("Size of path array is unexpected");
        }
        final HashSet<BetterBlockPos> set = new HashSet<BetterBlockPos>();
        for (int i = 0; i < positions.size() - 1; ++i) {
            final BetterBlockPos betterBlockPos = positions.get(i);
            final BetterBlockPos betterBlockPos2 = positions.get(i + 1);
            final IMovement movement = movements.get(i);
            if (!betterBlockPos.equals(movement.getSrc())) {
                throw new IllegalStateException("Path source is not equal to the movement source");
            }
            if (!betterBlockPos2.equals(movement.getDest())) {
                throw new IllegalStateException("Path destination is not equal to the movement destination");
            }
            if (set.contains(betterBlockPos)) {
                throw new IllegalStateException("Path doubles back on itself, making a loop");
            }
            set.add(betterBlockPos);
        }
    }
}
