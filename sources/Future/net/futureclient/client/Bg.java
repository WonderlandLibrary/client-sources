package net.futureclient.client;

import net.minecraft.entity.item.EntityItem;
import net.futureclient.client.events.Event;

public class Bg extends Event
{
    private EntityItem k;
    
    public Bg(final EntityItem k) {
        super();
        this.k = k;
    }
    
    public EntityItem M() {
        return this.k;
    }
}
