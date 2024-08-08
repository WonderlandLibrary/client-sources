package net.futureclient.client.modules.render.waypoints;

import net.futureclient.client.events.Event;
import net.futureclient.client.Xa;
import net.futureclient.client.modules.render.Waypoints;
import net.futureclient.client.uF;
import net.futureclient.client.n;

public class Listener4 extends n<uF>
{
    public final Waypoints k;
    
    public Listener4(final Waypoints k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final uF uf) {
        final Xa e;
        if (Waypoints.b(this.k).M() && (e = this.k.e(new StringBuilder().insert(0, uf.M()).append("_logout_spot").toString())) != null && Waypoints.M(this.k, e)) {
            this.k.k.remove(e);
        }
    }
    
    public void M(final Event event) {
        this.M((uF)event);
    }
}
