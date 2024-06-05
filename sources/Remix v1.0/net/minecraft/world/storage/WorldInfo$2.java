package net.minecraft.world.storage;

import java.util.concurrent.*;

class WorldInfo$2 implements Callable {
    @Override
    public String call() {
        return String.format("ID %02d - %s, ver %d. Features enabled: %b", WorldInfo.access$000(WorldInfo.this).getWorldTypeID(), WorldInfo.access$000(WorldInfo.this).getWorldTypeName(), WorldInfo.access$000(WorldInfo.this).getGeneratorVersion(), WorldInfo.access$100(WorldInfo.this));
    }
}