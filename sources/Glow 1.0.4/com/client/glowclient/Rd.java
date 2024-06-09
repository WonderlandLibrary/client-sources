package com.client.glowclient;

public class rd extends FC
{
    public float L;
    
    public float D(final rd rd) {
        return this.b * rd.b + this.B * rd.B + this.L * rd.L;
    }
    
    @Override
    public FC M(final double n) {
        return this.M(n);
    }
    
    public rd D() {
        return new rd(this);
    }
    
    @Override
    public float A() {
        return this.b * this.b + this.B * this.B + this.L * this.L;
    }
    
    @Override
    public Ic M() {
        return new Ic((int)Math.floor(this.b), (int)Math.floor(this.B), (int)Math.floor(this.L));
    }
    
    public rd A(final rd rd) {
        this.b -= rd.b;
        this.B -= rd.B;
        this.L -= rd.L;
        return this;
    }
    
    public boolean M(final rd rd, final float n) {
        return Math.abs(this.b - rd.b) < n && Math.abs(this.B - rd.B) < n && Math.abs(this.L - rd.L) < n;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return this.D();
    }
    
    public final float k() {
        return this.L;
    }
    
    @Override
    public WB M() {
        return new WB(this.b, this.B, this.L);
    }
    
    @Override
    public FC A() {
        return this.D();
    }
    
    public rd(final float n) {
        this(n, n, n);
    }
    
    @Override
    public rd M() {
        this.b = -this.b;
        this.B = -this.B;
        this.L = -this.L;
        return this;
    }
    
    public rd A(final float b, final float b2, final float l) {
        this.b = b;
        this.B = b2;
        this.L = l;
        return this;
    }
    
    public rd(final rd rd) {
        this(rd.b, rd.B, rd.L);
    }
    
    public rd D(final rd rd) {
        return this.A(rd.b, rd.B, rd.L);
    }
    
    @Override
    public String toString() {
        return String.format("[%s, %s, %s]", this.b, this.B, this.L);
    }
    
    public rd(final float n, final float n2, final float l) {
        super(n, n2);
        this.L = l;
    }
    
    public final void A(final float l) {
        this.L = l;
    }
    
    @Override
    public rd M(final double n) {
        this.b *= (float)n;
        this.B *= (float)n;
        this.L *= (float)n;
        return this;
    }
    
    public rd D(final float n, final float n2, final float n3) {
        this.b -= n;
        this.B -= n2;
        this.L -= n3;
        return this;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof rd && this.M((rd)o);
    }
    
    public boolean M(final rd rd) {
        return this.M(rd, 1.0E-5f);
    }
    
    public rd M(final float n, final float n2, final float n3) {
        this.b += n;
        this.B += n2;
        this.L += n3;
        return this;
    }
    
    public final double M(final rd rd) {
        return Math.sqrt(this.M(rd));
    }
    
    public rd() {
        final float n = 0.0f;
        this(n, n, n);
    }
    
    public float M(final rd rd) {
        return this.M(this.b - rd.b) + this.M(this.B - rd.B) + this.M(this.L - rd.L);
    }
    
    public Ic M(final Ic ic) {
        return ic.A((int)Math.floor(this.b), (int)Math.floor(this.B), (int)Math.floor(this.L));
    }
    
    @Override
    public FC M() {
        return this.M();
    }
    
    public rd M(final rd rd) {
        this.b += rd.b;
        this.B += rd.B;
        this.L += rd.L;
        return this;
    }
    
    public WB M(final WB wb) {
        return wb.M(this.b, this.B, this.L);
    }
}
