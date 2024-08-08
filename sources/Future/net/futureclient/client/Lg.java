package net.futureclient.client;

import net.futureclient.client.events.Event;

public class Lg extends Event
{
    private float k;
    
    public Lg() {
        super();
    }
    
    public void M(final float k) {
        this.k = k;
    }
    
    public float M() {
        return this.k;
    }
}
