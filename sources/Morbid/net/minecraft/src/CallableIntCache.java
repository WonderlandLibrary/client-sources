package net.minecraft.src;

import java.util.concurrent.*;

class CallableIntCache implements Callable
{
    final CrashReport theCrashReport;
    
    CallableIntCache(final CrashReport par1CrashReport) {
        this.theCrashReport = par1CrashReport;
    }
    
    public String func_85083_a() {
        return IntCache.func_85144_b();
    }
    
    @Override
    public Object call() {
        return this.func_85083_a();
    }
}
