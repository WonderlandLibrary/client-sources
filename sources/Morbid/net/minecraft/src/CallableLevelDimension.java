package net.minecraft.src;

import java.util.concurrent.*;

class CallableLevelDimension implements Callable
{
    final WorldInfo worldInfoInstance;
    
    CallableLevelDimension(final WorldInfo par1WorldInfo) {
        this.worldInfoInstance = par1WorldInfo;
    }
    
    public String callLevelDimension() {
        return String.valueOf(WorldInfo.func_85122_i(this.worldInfoInstance));
    }
    
    @Override
    public Object call() {
        return this.callLevelDimension();
    }
}
