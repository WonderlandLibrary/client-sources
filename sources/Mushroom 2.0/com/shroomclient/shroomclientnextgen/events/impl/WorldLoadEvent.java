package com.shroomclient.shroomclientnextgen.events.impl;

import com.shroomclient.shroomclientnextgen.events.Event;
import net.minecraft.client.world.ClientWorld;

public class WorldLoadEvent extends Event {

    ClientWorld world;

    public WorldLoadEvent(ClientWorld world) {
        this.world = world;
    }
}
