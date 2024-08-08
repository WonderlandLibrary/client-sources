package net.futureclient.client.modules.world.scaffold;

import net.futureclient.client.ZC;
import net.futureclient.client.pg;
import net.futureclient.client.modules.movement.Flight;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.world.Scaffold;
import net.futureclient.client.fg;
import net.futureclient.client.n;

public class Listener2 extends n<fg>
{
    public final Scaffold k;
    
    public Listener2(final Scaffold k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((fg)event);
    }
    
    @Override
    public void M(final fg fg) {
        final Flight flight;
        if ((flight = (Flight)pg.M().M().M((Class)ZC.class)) != null && !flight.M() && Scaffold.getMinecraft5().player.onGround) {
            fg.e(true);
        }
    }
}
