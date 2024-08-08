package net.futureclient.client.modules.miscellaneous.xcarry;

import net.futureclient.client.events.Event;
import net.futureclient.client.modules.miscellaneous.XCarry;
import net.futureclient.client.Kf;
import net.futureclient.client.n;

public class Listener2 extends n<Kf>
{
    public final XCarry k;
    
    public Listener2(final XCarry k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final Kf kf) {
        kf.M(true);
    }
    
    public void M(final Event event) {
        this.M((Kf)event);
    }
}
