package baritone.api.behavior;

import java.util.*;
import baritone.api.pathing.path.*;
import baritone.api.pathing.goals.*;
import java.util.function.*;
import baritone.api.pathing.calc.*;

public interface IPathingBehavior extends IBehavior
{
    default Optional<Double> ticksRemainingInSegment() {
        return this.ticksRemainingInSegment(true);
    }
    
    default Optional<Double> ticksRemainingInSegment(final boolean b) {
        final IPathExecutor current;
        if ((current = this.getCurrent()) == null) {
            return Optional.empty();
        }
        return Optional.of(current.getPath().ticksRemainingFrom(b ? current.getPosition() : (current.getPosition() + 1)));
    }
    
    Goal getGoal();
    
    default boolean isPathing() {
        return this.getCurrent() != null;
    }
    
    boolean cancelEverything();
    
    default Optional<IPath> getPath() {
        return Optional.ofNullable(this.getCurrent()).map((Function<? super IPathExecutor, ? extends IPath>)IPathExecutor::getPath);
    }
    
    Optional<? extends IPathFinder> getInProgress();
    
    IPathExecutor getCurrent();
    
    IPathExecutor getNext();
}
