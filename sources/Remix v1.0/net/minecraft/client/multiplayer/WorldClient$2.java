package net.minecraft.client.multiplayer;

import java.util.concurrent.*;

class WorldClient$2 implements Callable {
    @Override
    public String call() {
        return WorldClient.access$100(WorldClient.this).size() + " total; " + WorldClient.access$100(WorldClient.this).toString();
    }
}