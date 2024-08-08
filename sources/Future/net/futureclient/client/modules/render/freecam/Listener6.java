package net.futureclient.client.modules.render.freecam;

import net.futureclient.client.events.Event;
import net.futureclient.client.modules.render.Freecam;
import net.futureclient.client.eF;
import net.futureclient.client.n;

public class Listener6 extends n<eF>
{
    public final Freecam k;
    
    public Listener6(final Freecam k) {
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
