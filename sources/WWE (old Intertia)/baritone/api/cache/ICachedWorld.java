package baritone.api.cache;

import java.util.*;

public interface ICachedWorld
{
    ICachedRegion getRegion(final int p0, final int p1);
    
    void queueForPacking(final axw p0);
    
    boolean isCached(final int p0, final int p1);
    
    ArrayList<et> getLocationsOf(final String p0, final int p1, final int p2, final int p3, final int p4);
    
    void reloadAllFromDisk();
    
    void save();
}
