package net.futureclient.client.modules.movement.icespeed;

import net.futureclient.client.events.Event;
import net.minecraft.init.Blocks;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.modules.movement.IceSpeed;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener1 extends n<lF>
{
    public final IceSpeed k;
    
    public Listener1(final IceSpeed k) {
        this.k = k;
        super();
    }
    
    public void M(final EventUpdate eventUpdate) {
        Blocks.ICE.slipperiness = 0.4f;
        Blocks.PACKED_ICE.slipperiness = 0.4f;
        Blocks.FROSTED_ICE.slipperiness = 0.4f;
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
}
