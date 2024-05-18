package baritone;

import it.unimi.dsi.fastutil.longs.*;
import baritone.api.pathing.calc.*;
import baritone.api.utils.*;
import java.util.*;
import java.util.function.*;

public final class dg
{
    public final Long2DoubleOpenHashMap a;
    
    public dg(final IPlayerContext playerContext, final IPath path, final ad ad) {
        this(path, ad);
        for (final de de : de.a(playerContext)) {
            final Long2DoubleOpenHashMap a = this.a;
            final de de2 = de;
            for (int i = -de.d; i <= de2.d; ++i) {
                for (int j = -de2.d; j <= de2.d; ++j) {
                    for (int k = -de2.d; k <= de2.d; ++k) {
                        final int n = i;
                        final int n2 = n * n;
                        final int n3 = j;
                        final int n4 = n2 + n3 * n3;
                        final int n5 = k;
                        if (n4 + n5 * n5 <= de2.d * de2.d) {
                            final long longHash = BetterBlockPos.longHash(de2.a + i, de2.b + j, de2.c + k);
                            a.put(longHash, a.get(longHash) * de2.a);
                        }
                    }
                }
            }
        }
        System.out.println("Favoring size: " + this.a.size());
    }
    
    public dg(final IPath path, final ad ad) {
        super();
        (this.a = new Long2DoubleOpenHashMap()).defaultReturnValue(1.0);
        final double d;
        if ((d = ad.d) != 1.0 && path != null) {
            path.positions().forEach((Consumer<? super Object>)this::a);
        }
    }
    
    private void a(final double n, final BetterBlockPos betterBlockPos) {
        this.a.put(BetterBlockPos.longHash(betterBlockPos), n);
    }
}
