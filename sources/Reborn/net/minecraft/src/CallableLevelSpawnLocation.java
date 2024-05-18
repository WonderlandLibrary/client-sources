package net.minecraft.src;

import java.util.concurrent.*;

class CallableLevelSpawnLocation implements Callable
{
    final WorldInfo worldInfoInstance;
    
    CallableLevelSpawnLocation(final WorldInfo par1WorldInfo) {
        this.worldInfoInstance = par1WorldInfo;
    }
    
    public String callLevelSpawnLocation() {
        return CrashReportCategory.getLocationInfo(WorldInfo.getSpawnXCoordinate(this.worldInfoInstance), WorldInfo.getSpawnYCoordinate(this.worldInfoInstance), WorldInfo.getSpawnZCoordinate(this.worldInfoInstance));
    }
    
    @Override
    public Object call() {
        return this.callLevelSpawnLocation();
    }
}
