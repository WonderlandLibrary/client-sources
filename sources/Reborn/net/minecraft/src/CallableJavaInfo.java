package net.minecraft.src;

import java.util.concurrent.*;

class CallableJavaInfo implements Callable
{
    final CrashReport theCrashReport;
    
    CallableJavaInfo(final CrashReport par1CrashReport) {
        this.theCrashReport = par1CrashReport;
    }
    
    public String getJavaInfoAsString() {
        return String.valueOf(System.getProperty("java.version")) + ", " + System.getProperty("java.vendor");
    }
    
    @Override
    public Object call() {
        return this.getJavaInfoAsString();
    }
}
