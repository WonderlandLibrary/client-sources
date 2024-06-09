package com.client.glowclient.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.world.chunk.*;

public class EventChunk extends Event
{
    private final Chunk b;
    
    public EventChunk(final Chunk b) {
        super();
        this.b = b;
    }
    
    public Chunk getChunk() {
        return this.b;
    }
}
