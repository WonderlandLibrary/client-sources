package net.futureclient.client.modules.movement.entitycontrol;

import net.futureclient.client.events.Event;
import net.futureclient.client.modules.movement.EntityControl;
import net.futureclient.client.pe;
import net.futureclient.client.n;

public class Listener2 extends n<pe.Xf>
{
    public final EntityControl k;
    
    public Listener2(final EntityControl k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((pe.Xf)event);
    }
    
    @Override
    public void M(final pe.Xf pe) {
        pe.M(EntityControl.M(this.k).B().doubleValue());
    }
}
