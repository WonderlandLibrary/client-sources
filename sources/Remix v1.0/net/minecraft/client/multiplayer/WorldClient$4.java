package net.minecraft.client.multiplayer;

import java.util.concurrent.*;

class WorldClient$4 implements Callable {
    @Override
    public String call() {
        return (WorldClient.access$200(WorldClient.this).getIntegratedServer() == null) ? "Non-integrated multiplayer server" : "Integrated singleplayer server";
    }
}