package baritone;

import it.unimi.dsi.fastutil.ints.*;
import java.util.*;

public final class l
{
    public static final Set<aow> a;
    public final int a;
    public final int b;
    final BitSet a;
    final Int2ObjectOpenHashMap<String> a;
    final awt[] a;
    final int[] a;
    final Map<String, List<et>> a;
    public final long a;
    
    l(final int a, final int b, final BitSet a2, final awt[] a3, final Map<String, List<et>> a4, final long a5) {
        super();
        if (a2.size() > 131072) {
            throw new IllegalArgumentException("BitSet of invalid length provided");
        }
        this.a = a;
        this.b = b;
        this.a = a2;
        this.a = a3;
        this.a = new int[256];
        this.a = a4;
        this.a = a5;
        if (a4.isEmpty()) {
            this.a = null;
        }
        else {
            this.a = (Int2ObjectOpenHashMap<String>)new Int2ObjectOpenHashMap();
            this.a();
        }
        this.b();
    }
    
    private final void a() {
        final Iterator<Map.Entry<String, List<et>>> iterator = this.a.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<et>> entry;
            for (final et et : (entry = iterator.next()).getValue()) {
                this.a.put(a(et.p(), et.q(), et.r()), (Object)entry.getKey());
            }
        }
    }
    
    private void b() {
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                final int n = i << 4 | j;
                this.a[n] = 0;
                for (int k = 256; k >= 0; --k) {
                    final int a = a(j, k, i);
                    if (this.a.get(a) || this.a.get(a + 1)) {
                        this.a[n] = k;
                        break;
                    }
                }
            }
        }
    }
    
    public final ArrayList<et> a(final String s) {
        if (this.a.get(s) == null) {
            return null;
        }
        final ArrayList<et> list = new ArrayList<et>();
        for (final et et : this.a.get(s)) {
            list.add(new et(et.p() + (this.a << 4), et.q(), et.r() + (this.b << 4)));
        }
        return list;
    }
    
    public static int a(final int n, final int n2, final int n3) {
        return n << 1 | n3 << 5 | n2 << 9;
    }
    
    static {
        final HashSet<aow> set;
        (set = new HashSet<aow>()).add(aox.ah);
        set.add(aox.cA);
        set.add(aox.S);
        set.add(aox.R);
        set.add(aox.bP);
        set.add(aox.bT);
        set.add(aox.bQ);
        set.add(aox.al);
        set.add((aow)aox.ae);
        set.add(aox.cg);
        set.add(aox.bF);
        set.add(aox.bG);
        set.add(aox.ac);
        set.add(aox.cv);
        set.add(aox.dk);
        set.add(aox.dl);
        set.add(aox.dm);
        set.add(aox.dn);
        set.add(aox.do);
        set.add(aox.dp);
        set.add(aox.dq);
        set.add(aox.dr);
        set.add(aox.ds);
        set.add(aox.dt);
        set.add(aox.du);
        set.add(aox.dv);
        set.add(aox.dw);
        set.add(aox.dx);
        set.add(aox.dy);
        set.add(aox.dz);
        set.add(aox.dA);
        set.add((aow)aox.aY);
        set.add((aow)aox.cp);
        set.add((aow)aox.bY);
        set.add(aox.bD);
        set.add((aow)aox.ce);
        set.add(aox.bC);
        set.add(aox.cf);
        set.add(aox.am);
        set.add(aox.C);
        set.add(aox.bI);
        set.add(aox.aN);
        set.add(aox.db);
        set.add(aox.G);
        set.add(aox.bB);
        set.add(aox.au);
        set.add(aox.bn);
        a = Collections.unmodifiableSet((Set<?>)set);
    }
}
