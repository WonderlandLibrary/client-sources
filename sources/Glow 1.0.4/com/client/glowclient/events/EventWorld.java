package com.client.glowclient.events;

import net.minecraftforge.event.world.*;
import net.minecraft.world.*;

public class EventWorld extends WorldEvent
{
    public boolean isWorldNull() {
        return this.getWorld() == null;
    }
    
    public EventWorld(final World world) {
        super(world);
    }
}
