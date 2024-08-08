package net.futureclient.client.modules.world.nuker;

import net.futureclient.client.ZG;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.world.Nuker;
import net.futureclient.client.xD;
import net.futureclient.client.n;

public class Listener3 extends n<xD>
{
    public final Nuker k;
    
    public Listener3(final Nuker k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((xD)event);
    }
    
    @Override
    public void M(final xD xd) {
        if (Nuker.M(this.k)) {
            ZG.M(xd, Nuker.M(this.k), Nuker.e(this.k));
        }
    }
}
