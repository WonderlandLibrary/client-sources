package net.futureclient.client.modules.movement.entitycontrol;

import net.futureclient.client.events.Event;
import net.futureclient.client.modules.movement.EntityControl;
import net.futureclient.client.pe;
import net.futureclient.client.n;

public class Listener4 extends n<pe>
{
    public final EntityControl k;
    
    public Listener4(final EntityControl k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final pe pe) {
        if (EntityControl.M(this.k).M()) {
            pe.M(true);
        }
    }
    
    public void M(final Event event) {
        this.M((pe)event);
    }
}
