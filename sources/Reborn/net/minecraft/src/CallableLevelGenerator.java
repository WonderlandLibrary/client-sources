package net.minecraft.src;

import java.util.concurrent.*;

class CallableLevelGenerator implements Callable
{
    final WorldInfo worldInfoInstance;
    
    CallableLevelGenerator(final WorldInfo par1WorldInfo) {
        this.worldInfoInstance = par1WorldInfo;
    }
    
    public String callLevelGeneratorInfo() {
        return String.format("ID %02d - %s, ver %d. Features enabled: %b", WorldInfo.getTerrainTypeOfWorld(this.worldInfoInstance).getWorldTypeID(), WorldInfo.getTerrainTypeOfWorld(this.worldInfoInstance).getWorldTypeName(), WorldInfo.getTerrainTypeOfWorld(this.worldInfoInstance).getGeneratorVersion(), WorldInfo.getMapFeaturesEnabled(this.worldInfoInstance));
    }
    
    @Override
    public Object call() {
        return this.callLevelGeneratorInfo();
    }
}
