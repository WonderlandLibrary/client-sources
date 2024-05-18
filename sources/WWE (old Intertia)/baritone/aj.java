package baritone;

import baritone.api.utils.*;

public enum aj
{
    a("DOWNWARD"), 
    b("PILLAR"), 
    c("TRAVERSE_NORTH"), 
    d("TRAVERSE_SOUTH"), 
    e("TRAVERSE_EAST"), 
    f("TRAVERSE_WEST"), 
    g("ASCEND_NORTH"), 
    h("ASCEND_SOUTH"), 
    i("ASCEND_EAST"), 
    j("ASCEND_WEST"), 
    k("DESCEND_EAST"), 
    l("DESCEND_WEST"), 
    m("DESCEND_NORTH"), 
    n("DESCEND_SOUTH"), 
    o("DIAGONAL_NORTHEAST"), 
    p("DIAGONAL_NORTHWEST"), 
    q("DIAGONAL_SOUTHEAST"), 
    r("DIAGONAL_SOUTHWEST"), 
    s("PARKOUR_NORTH"), 
    t("PARKOUR_SOUTH"), 
    u("PARKOUR_EAST"), 
    v("PARKOUR_WEST");
    
    public final boolean a;
    public final boolean b;
    public final int a;
    public final int b;
    public final int c;
    private static final aj[] a;
    
    public static aj[] values() {
        return aj.a.clone();
    }
    
    public static aj valueOf(final String s) {
        return Enum.valueOf(aj.class, s);
    }
    
    private aj(final int a, final int b, final int c, final boolean a2, final boolean b2) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.a = a2;
        this.b = b2;
    }
    
    private aj(final int n2, final int n3, final int n4) {
        this(n2, n3, n4, false, false);
    }
    
    public abstract ae a(final ad p0, final BetterBlockPos p1);
    
    public void a(final ad ad, final int n, final int n2, final int n3, final dh dh) {
        if (this.a || this.b) {
            throw new UnsupportedOperationException();
        }
        dh.a = n + this.a;
        dh.b = n2 + this.b;
        dh.c = n3 + this.c;
        dh.a = this.a(ad, n, n2, n3);
    }
    
    public double a(final ad ad, final int n, final int n2, final int n3) {
        throw new UnsupportedOperationException();
    }
    
    aj(final String s, final int n, final int n2, final int n3, final int n4, final byte b) {
        this(n2, n3, n4);
    }
    
    aj(final String s, final int n, final int n2, final int n3, final int n4, final boolean b, final boolean b2, final byte b3) {
        this(n2, n3, n4, b, b2);
    }
    
    static {
        a = new aj[] { aj.a, aj.b, aj.c, aj.d, aj.e, aj.f, aj.g, aj.h, aj.i, aj.j, aj.k, aj.l, aj.m, aj.n, aj.o, aj.p, aj.q, aj.r, aj.s, aj.t, aj.u, aj.v };
    }
}
