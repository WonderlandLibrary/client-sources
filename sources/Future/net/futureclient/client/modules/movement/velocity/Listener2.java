package net.futureclient.client.modules.movement.velocity;

import net.futureclient.client.events.Event;
import net.futureclient.client.modules.movement.Velocity;
import net.futureclient.client.XD;
import net.futureclient.client.n;

public class Listener2 extends n<XD>
{
    public final Velocity k;
    
    public Listener2(final Velocity k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((XD)event);
    }
    
    @Override
    public void M(final XD xd) {
        if (this.k.water.M()) {
            xd.M(true);
        }
    }
}
