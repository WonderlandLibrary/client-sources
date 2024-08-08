package net.futureclient.client.modules.movement.entitycontrol;

import net.futureclient.client.events.Event;
import net.futureclient.client.modules.movement.EntityControl;
import net.futureclient.client.pe;
import net.futureclient.client.n;

public class Listener3 extends n<pe.Se>
{
    public final EntityControl k;
    
    public Listener3(final EntityControl k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final pe.Se pe) {
        if (EntityControl.e(this.k).M()) {
            pe.M(true);
        }
    }
    
    public void M(final Event event) {
        this.M((pe.Se)event);
    }
}
