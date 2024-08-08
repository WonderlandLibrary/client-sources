package net.futureclient.client.modules.world.fucker;

import net.futureclient.client.events.Event;
import net.futureclient.client.ZG;
import net.futureclient.client.modules.world.Fucker;
import net.futureclient.client.xD;
import net.futureclient.client.n;

public class Listener2 extends n<xD>
{
    public final Fucker k;
    
    public Listener2(final Fucker k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final xD xd) {
        if (Fucker.M(this.k) != null && Fucker.b(this.k).M()) {
            ZG.M(xd, Fucker.e(this.k), Fucker.M(this.k));
        }
    }
    
    public void M(final Event event) {
        this.M((xD)event);
    }
}
