package net.minecraft.src;

import java.util.concurrent.*;

class CallableLevelSeed implements Callable
{
    final WorldInfo worldInfoInstance;
    
    CallableLevelSeed(final WorldInfo par1WorldInfo) {
        this.worldInfoInstance = par1WorldInfo;
    }
    
    public String callLevelSeed() {
        return String.valueOf(this.worldInfoInstance.getSeed());
    }
    
    @Override
    public Object call() {
        return this.callLevelSeed();
    }
}
