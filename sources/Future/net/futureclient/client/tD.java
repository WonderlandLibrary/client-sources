package net.futureclient.client;

import net.minecraft.world.EnumSkyBlock;
import net.futureclient.client.events.Event;

public class tD extends Event
{
    private EnumSkyBlock k;
    
    public tD(final EnumSkyBlock k) {
        super();
        this.k = k;
    }
    
    public EnumSkyBlock M() {
        return this.k;
    }
}
