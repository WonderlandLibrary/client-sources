package net.futureclient.client;

import net.futureclient.client.events.Event;
import net.minecraft.entity.Entity;
import net.futureclient.client.events.EventWorld;

private final class iD extends n<fF> {
    public final mf k;
    
    private iD(final mf k) {
        this.k = k;
        super();
    }
    
    public iD(final mf mf, final df df) {
        this(mf);
    }
    
    public void M(final EventWorld eventWorld) {
        mf.M(this.k, null);
    }
    
    public void M(final Event event) {
        this.M((EventWorld)event);
    }
}