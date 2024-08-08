package net.futureclient.client.events;

public class EventMotion extends Event
{
    private float H;
    private double l;
    private double L;
    private double E;
    private EventType2 A;
    private boolean j;
    private double K;
    private float M;
    private float d;
    private float a;
    private double D;
    private double k;
    
    public EventMotion(final EventType2 a, final float n, final float n2, final double n3, final double n4, final double n5, final boolean j) {
        super();
        this.A = a;
        this.d = n;
        this.H = n;
        this.a = n2;
        this.M = n2;
        this.k = n3;
        this.l = n3;
        this.K = n4;
        this.E = n4;
        this.L = n5;
        this.D = n5;
        this.j = j;
    }
    
    public EventMotion(final EventType2 a) {
        super();
        this.A = a;
    }
    
    public double B() {
        return this.E;
    }
    
    public float B() {
        return this.d;
    }
    
    public double C() {
        return this.K;
    }
    
    public double b() {
        return this.l;
    }
    
    public float b() {
        return this.M;
    }
    
    public double e() {
        return this.D;
    }
    
    public boolean e() {
        return this.j;
    }
    
    public void e(final boolean j) {
        this.j = j;
    }
    
    public float e() {
        return this.H;
    }
    
    public void e(final float m) {
        this.M = m;
    }
    
    public double i() {
        return this.k;
    }
    
    public void M(final double e) {
        this.E = e;
    }
    
    public double M() {
        return this.L;
    }
    
    public float M() {
        return this.a;
    }
    
    public EventType2 M() {
        return this.A;
    }
    
    public void M(final float h) {
        this.H = h;
    }
}
