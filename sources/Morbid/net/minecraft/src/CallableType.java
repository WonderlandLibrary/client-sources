package net.minecraft.src;

import java.util.concurrent.*;

class CallableType implements Callable
{
    final DedicatedServer theDecitatedServer;
    
    CallableType(final DedicatedServer par1DedicatedServer) {
        this.theDecitatedServer = par1DedicatedServer;
    }
    
    public String getType() {
        final String var1 = this.theDecitatedServer.getServerModName();
        return var1.equals("vanilla") ? "Unknown (can't tell)" : ("Definitely; Server brand changed to '" + var1 + "'");
    }
    
    @Override
    public Object call() {
        return this.getType();
    }
}
