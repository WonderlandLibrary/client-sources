package net.minecraft.src;

import java.util.concurrent.*;

class CallableServerType implements Callable
{
    final DedicatedServer theDedicatedServer;
    
    CallableServerType(final DedicatedServer par1DedicatedServer) {
        this.theDedicatedServer = par1DedicatedServer;
    }
    
    public String callServerType() {
        return "Dedicated Server (map_server.txt)";
    }
    
    @Override
    public Object call() {
        return this.callServerType();
    }
}
