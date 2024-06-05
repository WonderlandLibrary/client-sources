package net.minecraft.world.storage;

import java.util.concurrent.*;

class WorldInfo$8 implements Callable {
    @Override
    public String call() {
        return String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", WorldInfo.access$1000(WorldInfo.this), WorldInfo.access$1100(WorldInfo.this), WorldInfo.access$1200(WorldInfo.this), WorldInfo.access$1300(WorldInfo.this));
    }
}