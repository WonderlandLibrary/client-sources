package net.minecraft.src;

import java.util.concurrent.*;

class CallableMPL1 implements Callable
{
    final WorldClient theWorldClient;
    
    CallableMPL1(final WorldClient par1WorldClient) {
        this.theWorldClient = par1WorldClient;
    }
    
    public String getEntityCountAndList() {
        return String.valueOf(WorldClient.getEntityList(this.theWorldClient).size()) + " total; " + WorldClient.getEntityList(this.theWorldClient).toString();
    }
    
    @Override
    public Object call() {
        return this.getEntityCountAndList();
    }
}
