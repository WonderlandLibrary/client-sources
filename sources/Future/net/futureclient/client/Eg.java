package net.futureclient.client;

import net.futureclient.client.events.Event;

public class Eg extends n<JD>
{
    public final ne k;
    
    public Eg(final ne k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final JD jd) {
        jd.M(true);
    }
    
    public void M(final Event event) {
        this.M((JD)event);
    }
}
