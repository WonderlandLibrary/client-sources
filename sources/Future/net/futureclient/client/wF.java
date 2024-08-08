package net.futureclient.client;

import net.futureclient.client.events.Event;

public final class wF extends Event
{
    private boolean k;
    
    public wF() {
        super();
    }
    
    public boolean e() {
        return this.k;
    }
    
    public void e(final boolean k) {
        this.k = k;
    }
}
