package jaco.mp3.a;

class w implements k
{
    protected z a;
    protected D b;
    private y g;
    private y h;
    private C i;
    private int j;
    protected int c;
    protected int d;
    protected p[] e;
    protected o f;
    
    public w() {
        this.f = null;
        this.f = new o();
    }
    
    public final void a(final z a, final D b, final y g, final y h, final C i, final int n) {
        this.a = a;
        this.b = b;
        this.g = g;
        this.h = h;
        this.i = i;
        this.j = 0;
    }
    
    @Override
    public final void a() {
        this.d = this.b.j();
        this.e = new p[32];
        this.c = this.b.f();
        this.b();
        for (int i = 0; i < this.d; ++i) {
            this.e[i].a(this.a, this.b, this.f);
        }
        this.c();
        if (this.f != null || this.b.g()) {
            for (int j = 0; j < this.d; ++j) {
                this.e[j].a(this.a, this.b);
            }
            boolean a = false;
            boolean a2 = false;
            final int f = this.b.f();
            do {
                for (int k = 0; k < this.d; ++k) {
                    a = this.e[k].a(this.a);
                }
                do {
                    for (int l = 0; l < this.d; ++l) {
                        a2 = this.e[l].a(this.j, this.g, this.h);
                    }
                    this.g.a(this.i);
                    if (this.j == 0 && f != 3) {
                        this.h.a(this.i);
                    }
                } while (!a2);
            } while (!a);
        }
    }
    
    protected void b() {
        if (this.c == 3) {
            for (int i = 0; i < this.d; ++i) {
                this.e[i] = new t(i);
            }
            return;
        }
        if (this.c == 1) {
            int j;
            for (j = 0; j < this.b.k(); ++j) {
                this.e[j] = new x(j);
            }
            while (j < this.d) {
                this.e[j] = new a(j);
                ++j;
            }
            return;
        }
        for (int k = 0; k < this.d; ++k) {
            this.e[k] = new x(k);
        }
    }
    
    protected void c() {
    }
}
