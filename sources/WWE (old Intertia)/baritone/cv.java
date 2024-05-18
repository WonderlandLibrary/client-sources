package baritone;

import baritone.api.pathing.goals.*;
import baritone.api.process.*;

public final class cv extends PathingCommand
{
    public final ad a;
    
    public cv(final Goal goal, final PathingCommandType pathingCommandType, final ad a) {
        super(goal, pathingCommandType);
        this.a = a;
    }
}
