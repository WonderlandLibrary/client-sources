package net.minecraft.client.multiplayer;

import java.util.concurrent.*;

class WorldClient$3 implements Callable {
    @Override
    public String call() {
        return WorldClient.access$200(WorldClient.this).thePlayer.getClientBrand();
    }
}