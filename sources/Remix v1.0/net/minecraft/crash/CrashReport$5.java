package net.minecraft.crash;

import java.util.concurrent.*;

class CrashReport$5 implements Callable {
    @Override
    public String call() {
        final Runtime var1 = Runtime.getRuntime();
        final long var2 = var1.maxMemory();
        final long var3 = var1.totalMemory();
        final long var4 = var1.freeMemory();
        final long var5 = var2 / 1024L / 1024L;
        final long var6 = var3 / 1024L / 1024L;
        final long var7 = var4 / 1024L / 1024L;
        return var4 + " bytes (" + var7 + " MB) / " + var3 + " bytes (" + var6 + " MB) up to " + var2 + " bytes (" + var5 + " MB)";
    }
}