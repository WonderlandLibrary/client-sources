package net.futureclient.client;

import net.minecraft.entity.MoverType;
import net.futureclient.client.events.Event;

public class fg extends Event
{
    public double M;
    private boolean d;
    private MoverType a;
    public double D;
    public double k;
    
    public fg(final MoverType a, final double k, final double d, final double m, final boolean d2) {
        super();
        this.a = a;
        this.k = k;
        this.D = d;
        this.M = m;
        this.d = d2;
    }
    
    public void b(final double d) {
        this.D = d;
    }
    
    public double b() {
        return this.M;
    }
    
    public boolean e() {
        return this.d;
    }
    
    public void e(final double k) {
        this.k = k;
    }
    
    public double e() {
        return this.D;
    }
    
    public void e(final boolean d) {
        this.d = d;
    }
    
    public void M(final double m) {
        this.M = m;
    }
    
    public double M() {
        return this.k;
    }
    
    public MoverType M() {
        return this.a;
    }
}
