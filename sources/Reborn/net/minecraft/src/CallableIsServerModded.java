package net.minecraft.src;

import java.util.concurrent.*;
import net.minecraft.server.*;

public class CallableIsServerModded implements Callable
{
    final MinecraftServer mcServer;
    
    public CallableIsServerModded(final MinecraftServer par1) {
        this.mcServer = par1;
    }
    
    public String func_96558_a() {
        return this.mcServer.theProfiler.profilingEnabled ? this.mcServer.theProfiler.getNameOfLastSection() : "N/A (disabled)";
    }
    
    @Override
    public Object call() {
        return this.func_96558_a();
    }
}
