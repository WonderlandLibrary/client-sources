package net.futureclient.client.modules.render.freecam;

import net.futureclient.client.events.Event;
import net.futureclient.client.modules.render.Freecam;
import net.futureclient.client.lE;
import net.futureclient.client.n;

public class Listener5 extends n<lE>
{
    public final Freecam k;
    
    public Listener5(final Freecam k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((lE)event);
    }
    
    @Override
    public void M(final lE le) {
        le.M(true);
    }
}
