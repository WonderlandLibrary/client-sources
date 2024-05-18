package net.minecraft.src;

import java.util.concurrent.*;

class CallableOSInfo implements Callable
{
    final CrashReport theCrashReport;
    
    CallableOSInfo(final CrashReport par1CrashReport) {
        this.theCrashReport = par1CrashReport;
    }
    
    public String getOsAsString() {
        return String.valueOf(System.getProperty("os.name")) + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version");
    }
    
    @Override
    public Object call() {
        return this.getOsAsString();
    }
}
