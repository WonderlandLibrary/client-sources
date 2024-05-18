package baritone.api.cache;

import java.util.*;

public interface IWaypointCollection
{
    void addWaypoint(final IWaypoint p0);
    
    void removeWaypoint(final IWaypoint p0);
    
    IWaypoint getMostRecentByTag(final IWaypoint$Tag p0);
    
    Set<IWaypoint> getByTag(final IWaypoint$Tag p0);
    
    Set<IWaypoint> getAllWaypoints();
}
