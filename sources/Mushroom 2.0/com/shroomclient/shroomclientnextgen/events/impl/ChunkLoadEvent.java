package com.shroomclient.shroomclientnextgen.events.impl;

import com.shroomclient.shroomclientnextgen.events.Event;
import net.minecraft.util.math.ChunkPos;

public class ChunkLoadEvent extends Event {

    public ChunkPos chunk;

    public ChunkLoadEvent(ChunkPos chunk) {
        this.chunk = chunk;
    }
}
