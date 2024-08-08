package net.futureclient.client;

import net.minecraft.entity.Entity;
import net.futureclient.client.events.Event;

public class Bf extends Event
{
    private Entity D;
    private int k;
    
    public Bf(final Entity d) {
        final int k = -1;
        super();
        this.k = k;
        this.D = d;
    }
    
    public Entity M() {
        return this.D;
    }
    
    public int M() {
        return this.k;
    }
    
    public void M(final int k) {
        this.k = k;
    }
}
