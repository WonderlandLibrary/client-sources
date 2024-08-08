package net.futureclient.client;

import net.futureclient.client.events.Event;

public class xD extends Event
{
    private float H;
    private float l;
    private float L;
    private float E;
    private float A;
    private float j;
    private float K;
    private float M;
    private float d;
    private float a;
    private float D;
    private float k;
    
    public xD(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        super();
        this.H = n;
        this.d = n;
        this.L = n2;
        this.l = n2;
        this.K = n3;
        this.D = n3;
        this.a = n4;
        this.A = n4;
        this.j = n5;
        this.E = n5;
        this.M = n6;
        this.k = n6;
    }
    
    public void B(final float d) {
        this.D = d;
    }
    
    public float B() {
        return this.A;
    }
    
    public float C() {
        return this.j;
    }
    
    public void C(final float d) {
        this.d = d;
    }
    
    public float J() {
        return this.D;
    }
    
    public float c() {
        return this.H;
    }
    
    public float b() {
        return this.M;
    }
    
    public void b(final float k) {
        this.k = k;
    }
    
    public float H() {
        return this.d;
    }
    
    public float e() {
        return this.L;
    }
    
    public void e(final float e) {
        this.E = e;
    }
    
    public float i() {
        return this.a;
    }
    
    public void i(final float l) {
        this.l = l;
    }
    
    public float g() {
        return this.l;
    }
    
    public float K() {
        return this.E;
    }
    
    public float M() {
        return this.k;
    }
    
    public void M(final float a) {
        this.A = a;
    }
    
    public float h() {
        return this.K;
    }
}
