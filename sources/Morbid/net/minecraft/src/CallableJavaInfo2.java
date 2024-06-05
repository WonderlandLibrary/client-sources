package net.minecraft.src;

import java.util.concurrent.*;

class CallableJavaInfo2 implements Callable
{
    final CrashReport theCrashReport;
    
    CallableJavaInfo2(final CrashReport par1CrashReport) {
        this.theCrashReport = par1CrashReport;
    }
    
    public String getJavaVMInfoAsString() {
        return String.valueOf(System.getProperty("java.vm.name")) + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor");
    }
    
    @Override
    public Object call() {
        return this.getJavaVMInfoAsString();
    }
}
