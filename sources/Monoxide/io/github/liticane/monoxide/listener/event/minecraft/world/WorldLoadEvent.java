package io.github.liticane.monoxide.listener.event.minecraft.world;

import net.minecraft.client.multiplayer.WorldClient;
import io.github.liticane.monoxide.listener.event.Event;

public class WorldLoadEvent extends Event {

    private final WorldClient worldClient;

    public WorldLoadEvent(WorldClient worldClient) {
        this.worldClient = worldClient;
    }

    public WorldClient getWorldClient() {
        return worldClient;
    }
}
