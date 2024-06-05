package net.minecraft.world.storage;

import java.util.concurrent.*;

class WorldInfo$5 implements Callable {
    @Override
    public String call() {
        return String.format("%d game time, %d day time", WorldInfo.access$600(WorldInfo.this), WorldInfo.access$700(WorldInfo.this));
    }
}