package net.futureclient.client.modules.render.freecam;

import net.futureclient.client.events.Event;
import net.futureclient.client.dd;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.modules.render.Freecam;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener1 extends n<lF>
{
    public final Freecam k;
    
    public Listener1(final Freecam k) {
        this.k = k;
        super();
    }
    
    public void M(final EventUpdate eventUpdate) {
        if (!Freecam.getMinecraft4().player.capabilities.isFlying && Freecam.M(this.k).M() != dd.FB.D) {
            Freecam.getMinecraft2().player.capabilities.isFlying = true;
        }
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
}
