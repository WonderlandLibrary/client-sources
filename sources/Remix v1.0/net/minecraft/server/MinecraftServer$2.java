package net.minecraft.server;

import java.util.concurrent.*;

class MinecraftServer$2 implements Callable {
    public String func_179879_a() {
        return MinecraftServer.this.theProfiler.profilingEnabled ? MinecraftServer.this.theProfiler.getNameOfLastSection() : "N/A (disabled)";
    }
    
    @Override
    public Object call() {
        return this.func_179879_a();
    }
}