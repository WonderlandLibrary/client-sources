package baritone.api.cache;

public interface IWaypoint
{
    String getName();
    
    IWaypoint$Tag getTag();
    
    long getCreationTimestamp();
    
    et getLocation();
}
