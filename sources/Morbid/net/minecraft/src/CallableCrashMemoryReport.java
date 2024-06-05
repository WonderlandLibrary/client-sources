package net.minecraft.src;

import java.util.concurrent.*;

class CallableCrashMemoryReport implements Callable
{
    final CrashReport theCrashReport;
    
    CallableCrashMemoryReport(final CrashReport par1CrashReport) {
        this.theCrashReport = par1CrashReport;
    }
    
    public String getMemoryReport() {
        final int var1 = AxisAlignedBB.getAABBPool().getlistAABBsize();
        final int var2 = 56 * var1;
        final int var3 = var2 / 1024 / 1024;
        final int var4 = AxisAlignedBB.getAABBPool().getnextPoolIndex();
        final int var5 = 56 * var4;
        final int var6 = var5 / 1024 / 1024;
        return String.valueOf(var1) + " (" + var2 + " bytes; " + var3 + " MB) allocated, " + var4 + " (" + var5 + " bytes; " + var6 + " MB) used";
    }
    
    @Override
    public Object call() {
        return this.getMemoryReport();
    }
}
