package net.minecraft.world.storage;

import java.util.concurrent.*;

class WorldInfo$3 implements Callable {
    @Override
    public String call() {
        return WorldInfo.access$200(WorldInfo.this);
    }
}