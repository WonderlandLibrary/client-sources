package baritone.api.cache;

public interface IWorldData
{
    ICachedWorld getCachedWorld();
    
    IWaypointCollection getWaypoints();
    
    IContainerMemory getContainerMemory();
}
