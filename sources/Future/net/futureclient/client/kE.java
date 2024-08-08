package net.futureclient.client;

import net.futureclient.client.events.Event;

public class kE extends Event
{
    private String k;
    
    public kE(final String s) {
        final String s2 = "\u00e5\u0019";
        super();
        this.k = s.replaceAll(aF.M(s2), "");
    }
    
    public String M() {
        return this.k;
    }
}
