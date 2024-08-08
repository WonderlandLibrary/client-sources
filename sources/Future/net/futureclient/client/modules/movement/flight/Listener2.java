package net.futureclient.client.modules.movement.flight;

import net.futureclient.client.events.Event;
import net.futureclient.client.ZC;
import net.futureclient.client.modules.movement.Flight;
import net.futureclient.client.rE;
import net.futureclient.client.n;

public class Listener2 extends n<rE>
{
    public final Flight k;
    
    public Listener2(final Flight k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final rE re) {
        if (Flight.B(this.k).M() && Flight.M(this.k).M() == ZC.SB.a) {
            re.M(true);
        }
    }
    
    public void M(final Event event) {
        this.M((rE)event);
    }
}
