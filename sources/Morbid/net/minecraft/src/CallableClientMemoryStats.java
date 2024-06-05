package net.minecraft.src;

import java.util.concurrent.*;
import net.minecraft.client.*;

public class CallableClientMemoryStats implements Callable
{
    final Minecraft theMinecraft;
    
    public CallableClientMemoryStats(final Minecraft par1) {
        this.theMinecraft = par1;
    }
    
    public String callClientMemoryStats() {
        final int var1 = Minecraft.theWorld.getWorldVec3Pool().getPoolSize();
        final int var2 = 56 * var1;
        final int var3 = var2 / 1024 / 1024;
        final int var4 = Minecraft.theWorld.getWorldVec3Pool().func_82590_d();
        final int var5 = 56 * var4;
        final int var6 = var5 / 1024 / 1024;
        return String.valueOf(var1) + " (" + var2 + " bytes; " + var3 + " MB) allocated, " + var4 + " (" + var5 + " bytes; " + var6 + " MB) used";
    }
    
    @Override
    public Object call() {
        return this.callClientMemoryStats();
    }
}
