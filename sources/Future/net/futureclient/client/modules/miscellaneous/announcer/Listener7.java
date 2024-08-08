package net.futureclient.client.modules.miscellaneous.announcer;

import net.futureclient.client.events.Event;
import net.futureclient.client.events.EventWorld;
import net.futureclient.client.modules.miscellaneous.Announcer;
import net.futureclient.client.fF;
import net.futureclient.client.n;

public class Listener7 extends n<fF>
{
    public final Announcer k;
    
    public Listener7(final Announcer k) {
        this.k = k;
        super();
    }
    
    public void M(final EventWorld eventWorld) {
        Announcer.e(this.k).clear();
        Announcer.M(this.k).clear();
        Announcer.b(this.k).clear();
        Announcer.M(this.k).e();
        Announcer.e(this.k).e();
        Announcer.b(this.k).e();
    }
    
    public void M(final Event event) {
        this.M((EventWorld)event);
    }
}
