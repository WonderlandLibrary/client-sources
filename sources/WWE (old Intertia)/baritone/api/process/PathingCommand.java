package baritone.api.process;

import baritone.api.pathing.goals.*;
import java.util.*;

public class PathingCommand
{
    public final Goal goal;
    public final PathingCommandType commandType;
    
    public PathingCommand(final Goal goal, final PathingCommandType commandType) {
        super();
        Objects.requireNonNull(commandType);
        this.goal = goal;
        this.commandType = commandType;
    }
    
    @Override
    public String toString() {
        return this.commandType + " " + this.goal;
    }
}
