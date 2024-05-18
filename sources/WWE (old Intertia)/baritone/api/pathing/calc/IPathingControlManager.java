package baritone.api.pathing.calc;

import java.util.*;
import baritone.api.process.*;

public interface IPathingControlManager
{
    void registerProcess(final IBaritoneProcess p0);
    
    Optional<IBaritoneProcess> mostRecentInControl();
    
    Optional<PathingCommand> mostRecentCommand();
}
