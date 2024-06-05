package net.minecraft.src;

import java.util.concurrent.*;
import net.minecraft.server.*;

public class CallableServerProfiler implements Callable
{
    final MinecraftServer mcServer;
    
    public CallableServerProfiler(final MinecraftServer par1) {
        this.mcServer = par1;
    }
    
    public String callServerProfiler() {
        final int var1 = this.mcServer.worldServers[0].getWorldVec3Pool().getPoolSize();
        final int var2 = 56 * var1;
        final int var3 = var2 / 1024 / 1024;
        final int var4 = this.mcServer.worldServers[0].getWorldVec3Pool().func_82590_d();
        final int var5 = 56 * var4;
        final int var6 = var5 / 1024 / 1024;
        return String.valueOf(var1) + " (" + var2 + " bytes; " + var3 + " MB) allocated, " + var4 + " (" + var5 + " bytes; " + var6 + " MB) used";
    }
    
    @Override
    public Object call() {
        return this.callServerProfiler();
    }
}
