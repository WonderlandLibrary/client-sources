package net.silentclient.client.event.impl;

import net.minecraft.client.multiplayer.WorldClient;
import net.silentclient.client.event.Event;

public class LoadWorldEvent extends Event {
	private final WorldClient world;

    public LoadWorldEvent(WorldClient world) {
        this.world = world;
    }

    public WorldClient getWorld() {
        return world;
    }
}
