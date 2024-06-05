package jaco.mp3.a;

public final class b
{
    private static final g a;
    private C b;
    private y c;
    private y d;
    private c e;
    private B f;
    private w g;
    private int h;
    private int i;
    private f j;
    private g k;
    private boolean l;
    
    static {
        a = new g();
    }
    
    public b() {
        this(null);
    }
    
    private b(g a) {
        this.j = new f();
        a = jaco.mp3.a.b.a;
        this.k = a;
        final f a2;
        if ((a2 = this.k.a()) != null) {
            this.j.a(a2);
        }
    }
    
    public final C a(final D d, final z z) {
        if (!this.l) {
            final float n = 32700.0f;
            final int i = (d.f() == 3) ? 1 : 2;
            if (this.b == null) {
                this.b = new A(d.e(), i);
            }
            this.j.a();
            this.c = new y(0, n);
            if (i == 2) {
                this.d = new y(1, n);
            }
            this.i = i;
            this.h = d.e();
            this.l = true;
        }
        final int b = d.b();
        this.b.c();
        final int n2 = b;
        k k = null;
        switch (n2) {
            case 3: {
                if (this.e == null) {
                    this.e = new c(z, d, this.c, this.d, this.b, 0);
                }
                k = this.e;
                break;
            }
            case 2: {
                if (this.f == null) {
                    (this.f = new B()).a(z, d, this.c, this.d, this.b, 0);
                }
                k = this.f;
                break;
            }
            case 1: {
                if (this.g == null) {
                    (this.g = new w()).a(z, d, this.c, this.d, this.b, 0);
                }
                k = this.g;
                break;
            }
        }
        if (k == null) {
            throw new u(513, null);
        }
        k.a();
        return this.b;
    }
    
    public final int a() {
        return this.h;
    }
    
    public final int b() {
        return this.i;
    }
}
