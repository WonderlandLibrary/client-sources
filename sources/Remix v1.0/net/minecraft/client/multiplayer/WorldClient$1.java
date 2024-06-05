package net.minecraft.client.multiplayer;

import java.util.concurrent.*;

class WorldClient$1 implements Callable {
    @Override
    public String call() {
        return WorldClient.access$000(WorldClient.this).size() + " total; " + WorldClient.access$000(WorldClient.this).toString();
    }
}