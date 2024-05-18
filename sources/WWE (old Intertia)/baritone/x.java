package baritone;

import baritone.api.cache.*;
import baritone.api.utils.*;
import java.util.*;

public enum x implements IWorldScanner
{
    a;
    
    private static final x[] a;
    
    public static x[] values() {
        return x.a.clone();
    }
    
    public static x valueOf(final String s) {
        return Enum.valueOf(x.class, s);
    }
    
    @Override
    public final List<et> scanChunkRadius(final IPlayerContext playerContext, final List<aow> list, final int n, final int n2, int n3) {
        if (list.contains(null)) {
            throw new IllegalStateException("Invalid block name should have been caught earlier: " + list.toString());
        }
        final ArrayList<et> list2 = new ArrayList<et>();
        if (list.isEmpty()) {
            return list2;
        }
        final brx brx = (brx)playerContext.world().B();
        final int n4 = n3;
        n3 = n4 * n4;
        final int n5 = playerContext.playerFeet().p() >> 4;
        final int n6 = playerContext.playerFeet().r() >> 4;
        final int q = playerContext.playerFeet().q();
        int n7 = 0;
        while (true) {
            for (int i = -n7; i <= n7; ++i) {
                for (int j = -n7; j <= n7; ++j) {
                    final int n8 = i;
                    final int n9 = n8 * n8;
                    final int n10 = j;
                    if (n9 + n10 * n10 == n7) {
                        final int n11 = i + n5;
                        final int n12 = j + n6;
                        final axw a;
                        if ((a = brx.a(n11, n12)) != null) {
                            a(n11 << 4, n12 << 4, a, list, list2, n, n2, q);
                        }
                    }
                }
            }
            if (list2.size() >= n) {
                if (n7 > n3) {
                    break;
                }
                if (n7 > 1) {}
            }
            ++n7;
        }
        return list2;
    }
    
    @Override
    public final List<et> scanChunk(final IPlayerContext playerContext, final List<aow> list, final amn amn, final int n, final int n2) {
        if (list.isEmpty()) {
            return Collections.emptyList();
        }
        final axw a = ((brx)playerContext.world().B()).a(amn.a, amn.b);
        final int q = playerContext.playerFeet().q();
        if (a == null || a.f()) {
            return Collections.emptyList();
        }
        final ArrayList<et> list2 = new ArrayList<et>();
        a(amn.a << 4, amn.b << 4, a, list, list2, n, n2, q);
        return list2;
    }
    
    private static void a(final int n, final int n2, final axw axw, final List<aow> list, final Collection<et> collection, final int n3, final int n4, final int n5) {
        final axx[] h = axw.h();
        for (int i = 0; i < 16; ++i) {
            final axx axx;
            if ((axx = h[i]) != null) {
                final int n6 = i << 4;
                final axp g = axx.g();
                for (int j = 0; j < 16; ++j) {
                    for (int k = 0; k < 16; ++k) {
                        for (int l = 0; l < 16; ++l) {
                            if (list.contains(g.a(l, j, k).u())) {
                                final int n7 = n6 | j;
                                collection.add(new et(n | l, n7, n2 | k));
                                if (collection.size() >= n3 && Math.abs(n7 - n5) < n4) {
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    static {
        a = new x[] { x.a };
    }
}
