package net.minecraft.src;

import java.util.concurrent.*;

class CallableLevelGeneratorOptions implements Callable
{
    final WorldInfo worldInfoInstance;
    
    CallableLevelGeneratorOptions(final WorldInfo par1WorldInfo) {
        this.worldInfoInstance = par1WorldInfo;
    }
    
    public String callLevelGeneratorOptions() {
        return WorldInfo.getWorldGeneratorOptions(this.worldInfoInstance);
    }
    
    @Override
    public Object call() {
        return this.callLevelGeneratorOptions();
    }
}
