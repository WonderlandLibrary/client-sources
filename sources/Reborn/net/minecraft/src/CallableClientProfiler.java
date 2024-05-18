package net.minecraft.src;

import java.util.concurrent.*;
import net.minecraft.client.*;

public class CallableClientProfiler implements Callable
{
    final Minecraft theMinecraft;
    
    public CallableClientProfiler(final Minecraft par1Minecraft) {
        this.theMinecraft = par1Minecraft;
    }
    
    public String callClientProfilerInfo() {
        return this.theMinecraft.mcProfiler.profilingEnabled ? this.theMinecraft.mcProfiler.getNameOfLastSection() : "N/A (disabled)";
    }
    
    @Override
    public Object call() {
        return this.callClientProfilerInfo();
    }
}
