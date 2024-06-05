package net.minecraft.world.storage;

import java.util.concurrent.*;

class WorldInfo$1 implements Callable {
    @Override
    public String call() {
        return String.valueOf(WorldInfo.this.getSeed());
    }
}