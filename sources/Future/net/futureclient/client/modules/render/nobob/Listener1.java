package net.futureclient.client.modules.render.nobob;

import net.futureclient.client.events.EventMotion;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.render.NoBob;
import net.futureclient.client.KF;
import net.futureclient.client.n;

public class Listener1 extends n<KF>
{
    public final NoBob k;
    
    public Listener1(final NoBob k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventMotion)event);
    }
    
    public void M(final EventMotion eventMotion) {
        NoBob.getMinecraft().player.distanceWalkedModified = 4.0f;
    }
}
