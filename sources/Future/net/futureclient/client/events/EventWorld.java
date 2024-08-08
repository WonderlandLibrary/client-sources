package net.futureclient.client.events;

import net.minecraft.client.multiplayer.WorldClient;

public class EventWorld extends Event
{
    private WorldClient k;
    
    public EventWorld(final WorldClient k) {
        super();
        this.k = k;
    }
    
    public WorldClient M() {
        return this.k;
    }
}
