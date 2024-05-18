package net.minecraft.src;

import java.util.concurrent.*;

class CallableLevelTime implements Callable
{
    final WorldInfo worldInfoInstance;
    
    CallableLevelTime(final WorldInfo par1WorldInfo) {
        this.worldInfoInstance = par1WorldInfo;
    }
    
    public String callLevelTime() {
        return String.format("%d game time, %d day time", WorldInfo.func_85126_g(this.worldInfoInstance), WorldInfo.getWorldTime(this.worldInfoInstance));
    }
    
    @Override
    public Object call() {
        return this.callLevelTime();
    }
}
