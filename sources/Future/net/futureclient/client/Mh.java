package net.futureclient.client;

public class Mh implements A<Mh>
{
    public final double M;
    public static final Mh d;
    public final double a;
    private double D;
    public final double k;
    
    public Mh(final double k, final double m, final double a) {
        final double d = 0.0;
        super();
        this.D = d;
        this.k = k;
        this.M = m;
        this.a = a;
    }
    
    static {
        final double n = 0.0;
        d = new Mh(n, n, n);
    }
    
    @Override
    public Object e(final Object o) {
        return this.e((Mh)o);
    }
    
    @Override
    public Mh e(final Mh mh) {
        return new Mh(this.k - mh.k, this.M - mh.M, this.a - mh.a);
    }
    
    public Mh M(final double n) {
        return new Mh(this.k / n, this.M / n, this.a / n);
    }
    
    @Override
    public Object M(final Object o) {
        return this.M((Mh)o);
    }
    
    @Override
    public double M() {
        this.M();
        return this.D;
    }
    
    @Override
    public Mh M(final Mh mh) {
        return new Mh(this.k + mh.k, this.M + mh.M, this.a + mh.a);
    }
    
    private void M() {
        if (this.D == 0.0) {
            this.D = Math.sqrt(this.k * this.k + this.M * this.M + this.a * this.a);
        }
    }
}
