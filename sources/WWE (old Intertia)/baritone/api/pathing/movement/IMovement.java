package baritone.api.pathing.movement;

import baritone.api.utils.*;

public interface IMovement
{
    double getCost();
    
    MovementStatus update();
    
    void reset();
    
    void resetBlockCache();
    
    boolean safeToCancel();
    
    boolean calculatedWhileLoaded();
    
    BetterBlockPos getSrc();
    
    BetterBlockPos getDest();
    
    et getDirection();
}
