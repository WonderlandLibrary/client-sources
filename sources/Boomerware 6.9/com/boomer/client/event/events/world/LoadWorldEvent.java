package com.boomer.client.event.events.world;

import com.boomer.client.event.Event;
import net.minecraft.client.multiplayer.WorldClient;

/**
 * made by Xen for BoomerWare
 *
 * @since 6/15/2019
 **/
public class LoadWorldEvent extends Event {
    private WorldClient worldClient;

    public LoadWorldEvent(WorldClient worldClient) {
        this.worldClient = worldClient;
    }

    public WorldClient getWorldClient() {
        return worldClient;
    }
}