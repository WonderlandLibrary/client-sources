package net.minecraft.crash;

import java.util.concurrent.*;
import net.minecraft.world.gen.layer.*;

class CrashReport$7 implements Callable {
    @Override
    public String call() {
        return IntCache.getCacheSizes();
    }
}