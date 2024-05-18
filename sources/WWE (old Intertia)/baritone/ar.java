package baritone;

import baritone.api.utils.*;

enum ar
{
    ar(final String s) {
    }
    
    @Override
    public final ae a(final ad ad, final BetterBlockPos betterBlockPos) {
        final dh dh = new dh();
        this.a(ad, betterBlockPos.a, betterBlockPos.b, betterBlockPos.c, dh);
        return new bi(ad.a, betterBlockPos, fa.c, fa.e, dh.b - betterBlockPos.b);
    }
    
    @Override
    public final void a(final ad ad, final int n, final int n2, final int n3, final dh dh) {
        bi.a(ad, n, n2, n3, n - 1, n3 - 1, dh);
    }
}
