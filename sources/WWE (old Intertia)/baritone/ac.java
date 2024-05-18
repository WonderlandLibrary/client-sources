package baritone;

import java.util.*;

public final class ac
{
    private g[] a;
    public int a;
    
    public ac() {
        this((byte)0);
    }
    
    private ac(final byte b) {
        super();
        this.a = 0;
        this.a = new g[1024];
    }
    
    public final void a(final g g) {
        if (this.a >= this.a.length - 1) {
            this.a = Arrays.copyOf(this.a, this.a.length << 1);
        }
        ++this.a;
        g.d = this.a;
        this.b(this.a[this.a] = g);
    }
    
    public final void b(final g g) {
        int d2;
        int d = (d2 = g.d) >>> 1;
        final double c = g.c;
        for (g g2 = this.a[d]; d2 > 1 && g2.c > c; d = (d2 = d) >>> 1, g2 = this.a[d]) {
            this.a[d2] = g2;
            this.a[d] = g;
            g.d = d;
            g2.d = d2;
        }
    }
    
    public final g a() {
        if (this.a == 0) {
            throw new IllegalStateException();
        }
        final g g = this.a[1];
        final g g2 = this.a[this.a];
        this.a[1] = g2;
        g2.d = 1;
        this.a[this.a] = null;
        --this.a;
        g.d = -1;
        if (this.a < 2) {
            return g;
        }
        int d = 1;
        int d2 = 2;
        final double c = g2.c;
        do {
            g g3;
            double c2 = (g3 = this.a[d2]).c;
            if (d2 < this.a) {
                final g g4;
                final double c3 = (g4 = this.a[d2 + 1]).c;
                if (c2 > c3) {
                    ++d2;
                    c2 = c3;
                    g3 = g4;
                }
            }
            if (c <= c2) {
                break;
            }
            this.a[d] = g3;
            this.a[d2] = g2;
            g2.d = d2;
            g3.d = d;
            d = d2;
        } while ((d2 <<= 1) <= this.a);
        return g;
    }
}
