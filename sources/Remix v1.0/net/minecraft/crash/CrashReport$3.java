package net.minecraft.crash;

import java.util.concurrent.*;

class CrashReport$3 implements Callable {
    @Override
    public String call() {
        return System.getProperty("java.version") + ", " + System.getProperty("java.vendor");
    }
}