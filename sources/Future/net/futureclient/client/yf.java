package net.futureclient.client;

import net.futureclient.client.events.Event;

public class yf extends Event
{
    private String k;
    
    public yf(final String k) {
        super();
        this.k = k;
    }
    
    public void M(final String k) {
        this.k = k;
    }
    
    public String M() {
        return this.k;
    }
}
