package net.minecraft.world.storage;

import java.util.concurrent.*;

class WorldInfo$7 implements Callable {
    @Override
    public String call() {
        String var1 = "Unknown?";
        try {
            switch (WorldInfo.access$900(WorldInfo.this)) {
                case 19132: {
                    var1 = "McRegion";
                    break;
                }
                case 19133: {
                    var1 = "Anvil";
                    break;
                }
            }
        }
        catch (Throwable t) {}
        return String.format("0x%05X - %s", WorldInfo.access$900(WorldInfo.this), var1);
    }
}