package net.futureclient.client.modules.world.autotunnel;

import net.futureclient.client.ZG;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.world.AutoTunnel;
import net.futureclient.client.xD;
import net.futureclient.client.n;

public class Listener2 extends n<xD>
{
    public final AutoTunnel k;
    
    public Listener2(final AutoTunnel k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((xD)event);
    }
    
    @Override
    public void M(final xD xd) {
        if (AutoTunnel.e(this.k)) {
            ZG.M(xd, AutoTunnel.M(this.k), AutoTunnel.e(this.k));
        }
    }
}
