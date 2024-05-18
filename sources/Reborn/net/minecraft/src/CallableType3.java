package net.minecraft.src;

import java.util.concurrent.*;

class CallableType3 implements Callable
{
    final IntegratedServer theIntegratedServer;
    
    CallableType3(final IntegratedServer par1IntegratedServer) {
        this.theIntegratedServer = par1IntegratedServer;
    }
    
    public String getType() {
        return "Integrated Server (map_client.txt)";
    }
    
    @Override
    public Object call() {
        return this.getType();
    }
}
