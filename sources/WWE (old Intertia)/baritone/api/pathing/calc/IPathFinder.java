package baritone.api.pathing.calc;

import baritone.api.pathing.goals.*;
import baritone.api.utils.*;
import java.util.*;

public interface IPathFinder
{
    Goal getGoal();
    
    PathCalculationResult calculate(final long p0, final long p1);
    
    boolean isFinished();
    
    Optional<IPath> pathToMostRecentNodeConsidered();
    
    Optional<IPath> bestPathSoFar();
}
