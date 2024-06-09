package com.client.glowclient;

public class FC
{
    public static final float A = 1.0E-5f;
    public float B;
    public float b;
    
    public FC A(final float b, final float b2) {
        this.b = b;
        this.B = b2;
        return this;
    }
    
    public Qc M() {
        return new Qc((int)Math.floor(this.b), (int)Math.floor(this.B));
    }
    
    public final void D(final float b) {
        this.B = b;
    }
    
    public FC() {
        final float n = 0.0f;
        this(n, n);
    }
    
    public FC(final FC fc) {
        this(fc.b, fc.B);
    }
    
    public FC D(final float n, final float n2) {
        this.b += n;
        this.B += n2;
        return this;
    }
    
    public boolean M(final FC fc) {
        return this.M(fc, 1.0E-5f);
    }
    
    public Yb M() {
        return new Yb(this.b, this.B);
    }
    
    public final void M(final float b) {
        this.b = b;
    }
    
    public float D(final FC fc) {
        return this.M(this.b - fc.b) + this.M(this.B - fc.B);
    }
    
    public final double M() {
        return Math.sqrt(this.A());
    }
    
    public float M(final FC fc) {
        return this.b * fc.b + this.B * fc.B;
    }
    
    public FC A() {
        return new FC(this);
    }
    
    public FC A(final FC fc) {
        this.b += fc.b;
        this.B += fc.B;
        return this;
    }
    
    public FC M(final float n, final float n2) {
        this.b -= n;
        this.B -= n2;
        return this;
    }
    
    public float A() {
        return this.b * this.b + this.B * this.B;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof FC && this.M((FC)o);
    }
    
    public FC(final float n) {
        this(n, n);
    }
    
    public Yb M(final Yb yb) {
        return yb.M(this.b, this.B);
    }
    
    @Override
    public String toString() {
        return String.format("[%s, %s]", this.b, this.B);
    }
    
    public final FC D() {
        final double m;
        if ((m = this.M()) != 0.0) {
            return this.M(1.0 / m);
        }
        return this;
    }
    
    public FC D(final FC fc) {
        return this.A(fc.b, fc.B);
    }
    
    public FC M(final double n) {
        this.b *= (float)n;
        this.B *= (float)n;
        return this;
    }
    
    public final float M(final float n) {
        return n * n;
    }
    
    public Object clone() throws CloneNotSupportedException {
        return this.A();
    }
    
    public final double M(final FC fc) {
        return Math.sqrt(this.D(fc));
    }
    
    public Qc M(final Qc qc) {
        return qc.M((int)Math.floor(this.b), (int)Math.floor(this.B));
    }
    
    public boolean M(final FC fc, final float n) {
        return Math.abs(this.b - fc.b) < n && Math.abs(this.B - fc.B) < n;
    }
    
    public FC M() {
        this.b = -this.b;
        this.B = -this.B;
        return this;
    }
    
    public FC(final float b, final float b2) {
        super();
        this.b = b;
        this.B = b2;
    }
    
    public final float D() {
        return this.b;
    }
    
    public FC M(final FC fc) {
        this.b -= fc.b;
        this.B -= fc.B;
        return this;
    }
    
    public final float M() {
        return this.B;
    }
}
