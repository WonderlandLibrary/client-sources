package net.futureclient.client;

import net.futureclient.client.events.Event;
import net.futureclient.client.events.EventWorld;

public class ce extends n<fF>
{
    public final ne k;
    
    public ce(final ne k) {
        this.k = k;
        super();
    }
    
    public void M(final EventWorld eventWorld) {
        ne.M(this.k);
    }
    
    public void M(final Event event) {
        this.M((EventWorld)event);
    }
}
