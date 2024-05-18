package net.minecraft.src;

import java.util.concurrent.*;

class CallableMPL2 implements Callable
{
    final WorldClient theWorldClient;
    
    CallableMPL2(final WorldClient par1WorldClient) {
        this.theWorldClient = par1WorldClient;
    }
    
    public String getEntitySpawnQueueCountAndList() {
        return String.valueOf(WorldClient.getEntitySpawnQueue(this.theWorldClient).size()) + " total; " + WorldClient.getEntitySpawnQueue(this.theWorldClient).toString();
    }
    
    @Override
    public Object call() {
        return this.getEntitySpawnQueueCountAndList();
    }
}
