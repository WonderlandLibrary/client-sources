package net.minecraft.crash;

import java.util.concurrent.*;

class CrashReport$2 implements Callable {
    @Override
    public String call() {
        return System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version");
    }
}