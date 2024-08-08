package net.futureclient.client;

import net.futureclient.client.events.Event;

public class sf extends Event
{
    private float K;
    private float M;
    private sf.gE d;
    private float a;
    private float D;
    private boolean k;
    
    public sf(final sf.gE d) {
        super();
        this.d = d;
    }
    
    public sf(final sf.gE d, final float n, final float n2, final boolean k) {
        super();
        this.d = d;
        this.K = n;
        this.a = n;
        this.D = n2;
        this.M = n2;
        this.k = k;
    }
    
    public float B() {
        return this.a;
    }
    
    public float b() {
        return this.D;
    }
    
    public boolean e() {
        return this.k;
    }
    
    public float e() {
        return this.K;
    }
    
    public void e(final float a) {
        this.a = a;
    }
    
    public void e(final boolean k) {
        this.k = k;
    }
    
    public float M() {
        return this.M;
    }
    
    public void M(final float m) {
        this.M = m;
    }
    
    public sf.gE M() {
        return this.d;
    }
}
