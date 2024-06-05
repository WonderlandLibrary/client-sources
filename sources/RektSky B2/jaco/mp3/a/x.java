package jaco.mp3.a;

final class x extends t
{
    private int j;
    private float k;
    private int l;
    private float n;
    private float o;
    private float p;
    
    public x(final int n) {
        super(n);
    }
    
    @Override
    public final void a(final z z, final D d, final o o) {
        this.d = z.d(4);
        this.j = z.d(4);
        if (o != null) {
            o.a(this.d, 4);
            o.a(this.j, 4);
        }
        if (this.d != 0) {
            this.f = this.d + 1;
            this.h = x.a[this.d];
            this.i = x.b[this.d];
        }
        if (this.j != 0) {
            this.l = this.j + 1;
            this.o = x.a[this.j];
            this.p = x.b[this.j];
        }
    }
    
    @Override
    public final void a(final z z, final D d) {
        if (this.d != 0) {
            this.e = x.m[z.d(6)];
        }
        if (this.j != 0) {
            this.k = x.m[z.d(6)];
        }
    }
    
    @Override
    public final boolean a(final z z) {
        final boolean a = super.a(z);
        if (this.j != 0) {
            this.n = (float)z.d(this.l);
        }
        return a;
    }
    
    @Override
    public final boolean a(final int n, final y y, final y y2) {
        super.a(n, y, y2);
        if (this.j != 0 && n != 1) {
            final float n2 = (this.n * this.o + this.p) * this.k;
            if (n == 0) {
                y2.a(n2, this.c);
            }
            else {
                y.a(n2, this.c);
            }
        }
        return true;
    }
}
