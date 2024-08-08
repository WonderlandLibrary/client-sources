package net.futureclient.client.modules.world.wallhack;

import net.futureclient.client.events.Event;
import net.futureclient.client.modules.world.Wallhack;
import net.futureclient.client.eF;
import net.futureclient.client.n;

public class Listener2 extends n<eF>
{
    public final Wallhack k;
    
    public Listener2(final Wallhack k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final eF ef) {
        ef.e(false);
    }
    
    public void M(final Event event) {
        this.M((eF)event);
    }
}
