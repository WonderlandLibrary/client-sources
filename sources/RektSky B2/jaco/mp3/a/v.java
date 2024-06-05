package jaco.mp3.a;

final class v extends i
{
    private int n;
    private float o;
    private float p;
    private float q;
    
    public v(final int n) {
        super(n);
    }
    
    @Override
    public final void a(final z z, final D d, final o o) {
        super.a(z, d, o);
    }
    
    @Override
    public final void a(final z z, final o o) {
        if (this.b != 0) {
            this.c = z.d(2);
            this.n = z.d(2);
            if (o != null) {
                o.a(this.c, 2);
                o.a(this.n, 2);
            }
        }
    }
    
    @Override
    public final void a(final z z, final D d) {
        if (this.b != 0) {
            super.a(z, d);
            switch (this.n) {
                case 0: {
                    this.o = v.m[z.d(6)];
                    this.p = v.m[z.d(6)];
                    this.q = v.m[z.d(6)];
                }
                case 1: {
                    final float n = v.m[z.d(6)];
                    this.p = n;
                    this.o = n;
                    this.q = v.m[z.d(6)];
                }
                case 2: {
                    final float o = v.m[z.d(6)];
                    this.q = o;
                    this.p = o;
                    this.o = o;
                }
                case 3: {
                    this.o = v.m[z.d(6)];
                    final float n2 = v.m[z.d(6)];
                    this.q = n2;
                    this.p = n2;
                    break;
                }
            }
        }
    }
    
    @Override
    public final boolean a(final z z) {
        return super.a(z);
    }
    
    @Override
    public final boolean a(final int n, final y y, final y y2) {
        if (this.b != 0) {
            float n2 = this.j[this.i];
            if (this.g[0] == null) {
                n2 = (n2 + this.l[0]) * this.k[0];
            }
            if (n == 0) {
                final float n3 = n2;
                float n4;
                float n5;
                if (this.h <= 4) {
                    n4 = n2 * this.d;
                    n5 = n3 * this.o;
                }
                else if (this.h <= 8) {
                    n4 = n2 * this.e;
                    n5 = n3 * this.p;
                }
                else {
                    n4 = n2 * this.f;
                    n5 = n3 * this.q;
                }
                y.a(n4, this.a);
                y2.a(n5, this.a);
            }
            else if (n == 1) {
                float n6;
                if (this.h <= 4) {
                    n6 = n2 * this.d;
                }
                else if (this.h <= 8) {
                    n6 = n2 * this.e;
                }
                else {
                    n6 = n2 * this.f;
                }
                y.a(n6, this.a);
            }
            else {
                float n7;
                if (this.h <= 4) {
                    n7 = n2 * this.o;
                }
                else if (this.h <= 8) {
                    n7 = n2 * this.p;
                }
                else {
                    n7 = n2 * this.q;
                }
                y.a(n7, this.a);
            }
        }
        return ++this.i == 3;
    }
}
