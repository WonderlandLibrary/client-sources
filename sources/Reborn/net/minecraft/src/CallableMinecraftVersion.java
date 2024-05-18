package net.minecraft.src;

import java.util.concurrent.*;

class CallableMinecraftVersion implements Callable
{
    final CrashReport theCrashReport;
    
    CallableMinecraftVersion(final CrashReport par1CrashReport) {
        this.theCrashReport = par1CrashReport;
    }
    
    public String minecraftVersion() {
        return "1.5.2";
    }
    
    @Override
    public Object call() {
        return this.minecraftVersion();
    }
}
