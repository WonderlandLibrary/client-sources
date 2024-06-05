package net.minecraft.crash;

import java.util.concurrent.*;

class CrashReport$4 implements Callable {
    @Override
    public String call() {
        return System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor");
    }
}