package net.futureclient.client;

import net.futureclient.client.events.Event;

public class OE extends n<DE>
{
    public final te k;
    
    public OE(final te k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final DE de) {
        de.M(true);
    }
    
    public void M(final Event event) {
        this.M((DE)event);
    }
}
