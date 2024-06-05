package net.minecraft.world.storage;

import java.util.concurrent.*;

class WorldInfo$6 implements Callable {
    @Override
    public String call() {
        return String.valueOf(WorldInfo.access$800(WorldInfo.this));
    }
}