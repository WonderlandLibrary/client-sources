package net.futureclient.client.modules.movement.jesus;

import net.futureclient.client.events.EventWorld;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.movement.Jesus;
import net.futureclient.client.fF;
import net.futureclient.client.n;

public class Listener6 extends n<fF>
{
    public final Jesus k;
    
    public Listener6(final Jesus k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventWorld)event);
    }
    
    public void M(final EventWorld eventWorld) {
        Jesus.M(this.k).e();
    }
}
