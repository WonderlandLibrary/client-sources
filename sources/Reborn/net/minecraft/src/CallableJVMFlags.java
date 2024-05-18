package net.minecraft.src;

import java.util.concurrent.*;
import java.lang.management.*;
import java.util.*;

class CallableJVMFlags implements Callable
{
    final CrashReport theCrashReport;
    
    CallableJVMFlags(final CrashReport par1CrashReport) {
        this.theCrashReport = par1CrashReport;
    }
    
    public String getJVMFlagsAsString() {
        final RuntimeMXBean var1 = ManagementFactory.getRuntimeMXBean();
        final List var2 = var1.getInputArguments();
        int var3 = 0;
        final StringBuilder var4 = new StringBuilder();
        for (final String var6 : var2) {
            if (var6.startsWith("-X")) {
                if (var3++ > 0) {
                    var4.append(" ");
                }
                var4.append(var6);
            }
        }
        return String.format("%d total; %s", var3, var4.toString());
    }
    
    @Override
    public Object call() {
        return this.getJVMFlagsAsString();
    }
}
