package com.client.glowclient;

public abstract class gf
{
    public qe M;
    private static gf G;
    public int d;
    public boolean L;
    public final int A;
    public int B;
    public final int b;
    
    public int e() {
        return this.d;
    }
    
    public boolean A() {
        return this.L;
    }
    
    public abstract String M();
    
    public void M() {
    }
    
    public void D(final int b) {
        this.B = b;
    }
    
    public boolean D() {
        return this.M.I.M();
    }
    
    public void M(final int d) {
        this.d = d;
    }
    
    public abstract int k();
    
    public int A() {
        return this.b;
    }
    
    public void D(final int n, final int n2) {
        final int d = this.D();
        final int e = this.e();
        final int n3 = d + this.A;
        final int n4 = e + this.b;
        this.L = (d <= n && n <= n3 && e <= n2 && n2 <= n4);
    }
    
    public qe M() {
        return this.M;
    }
    
    public void M(final char c, final int n) {
    }
    
    public boolean M() {
        return false;
    }
    
    public static gf M() {
        return gf.G;
    }
    
    public int D() {
        return this.B;
    }
    
    public void M(final boolean b) {
    }
    
    public gf(final int b, final int d, final int a, final int b2) {
        final boolean l = false;
        super();
        gf.G = this;
        this.L = l;
        this.B = b;
        this.d = d;
        this.A = a;
        this.b = b2;
    }
    
    public void D(final int n, final int n2, final int n3) {
        this.D(n, n2);
    }
    
    public void M(final int n, final int n2, final int n3) {
        this.D(n, n2);
    }
    
    public int M() {
        return this.A;
    }
    
    public abstract void M(final int p0, final int p1);
}
