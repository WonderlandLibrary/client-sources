package net.minecraft.world.storage;

import java.util.concurrent.*;

class WorldInfo$9 implements Callable {
    @Override
    public String call() {
        return String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", WorldInfo.access$1400(WorldInfo.this).getName(), WorldInfo.access$1400(WorldInfo.this).getID(), WorldInfo.access$1500(WorldInfo.this), WorldInfo.access$1600(WorldInfo.this));
    }
}