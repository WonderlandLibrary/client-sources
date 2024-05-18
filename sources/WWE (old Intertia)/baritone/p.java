package baritone;

import java.util.function.*;
import java.util.*;

public final class p
{
    private static final Map<String, aow> a;
    
    public static l a(final axw axw) {
        final HashMap<String, Object> hashMap = new HashMap<String, Object>();
        final BitSet set = new BitSet(131072);
        try {
            final axx[] h = axw.h();
            for (int i = 0; i < 16; ++i) {
                final axx axx;
                if ((axx = h[i]) != null) {
                    final axp g = axx.g();
                    final int n = i << 4;
                    for (int j = 0; j < 16; ++j) {
                        final int n2 = j | n;
                        for (int k = 0; k < 16; ++k) {
                            for (int l = 0; l < 16; ++l) {
                                final int a = l.a(l, n2, k);
                                final awt a2;
                                final awt awt = a2 = g.a(l, j, k);
                                final int n3 = l;
                                final int n4 = n2;
                                final int n5 = k;
                                final int n6 = n4;
                                final int n7 = n3;
                                final awt awt2 = awt;
                                final aow u;
                                final boolean[] a3 = (((u = awt.u()) == aox.j || u == aox.i) ? (af.c(awt2) ? dj.c : ((n7 == 0 || n7 == 15 || n5 == 0 || n5 == 15) ? ((aru.a((amy)axw.q(), new et(n7 + axw.b << 4, n6, n5 + axw.c << 4), awt2.a(), awt2) == -1000.0f) ? dj.b : dj.c) : ((af.c(axw.a(n7 + 1, n6, n5)) || af.c(axw.a(n7 - 1, n6, n5)) || af.c(axw.a(n7, n6, n5 + 1)) || af.c(axw.a(n7, n6, n5 - 1))) ? dj.c : dj.b))) : ((af.a(u) || af.b(awt2)) ? dj.c : ((u == aox.a || u instanceof aun || u instanceof aqb || u instanceof aqr) ? dj.a : dj.d))).a;
                                set.set(a, a3[0]);
                                set.set(a + 1, a3[1]);
                                final aow u2 = a2.u();
                                if (l.a.contains(u2)) {
                                    hashMap.computeIfAbsent(a(u2), p::a).add(new et(l, n2, k));
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        final awt[] array = new awt[256];
        for (int n8 = 0; n8 < 16; ++n8) {
            int n9 = 0;
        Label_0552:
            while (n9 < 16) {
                while (true) {
                    for (int n10 = 255; n10 >= 0; --n10) {
                        final int a4 = l.a(n9, n10, n8);
                        if (set.get(a4) || set.get(a4 + 1)) {
                            array[n8 << 4 | n9] = axw.a(n9, n10, n8);
                            ++n9;
                            continue Label_0552;
                        }
                    }
                    array[n8 << 4 | n9] = aox.a.t();
                    continue;
                }
            }
        }
        return new l(axw.b, axw.c, set, array, (Map<String, List<et>>)hashMap, System.currentTimeMillis());
    }
    
    public static String a(final aow aow) {
        final nf nf;
        String s = (nf = (nf)aow.h.b((Object)aow)).a();
        if (!nf.b().equals("minecraft")) {
            s = nf.toString();
        }
        return s;
    }
    
    public static aow a(final String s) {
        final aow b;
        Objects.requireNonNull(b = b(s));
        return b;
    }
    
    public static aow b(final String s) {
        return p.a.computeIfAbsent(s, p::c);
    }
    
    public static awt a(final dj dj, final int n) {
        switch (q.a[dj.ordinal()]) {
            case 1: {
                return aox.a.t();
            }
            case 2: {
                return aox.j.t();
            }
            case 3: {
                return aox.l.t();
            }
            case 4: {
                switch (n) {
                    case -1: {
                        return aox.aV.t();
                    }
                    default: {
                        return aox.b.t();
                    }
                    case 1: {
                        return aox.bH.t();
                    }
                }
                break;
            }
            default: {
                return null;
            }
        }
    }
    
    private static aow c(final String s) {
        return aow.b(s.contains(":") ? s : "minecraft:".concat(String.valueOf(s)));
    }
    
    private static List a(final String s) {
        return new ArrayList();
    }
    
    static {
        a = new HashMap<String, aow>();
    }
}
