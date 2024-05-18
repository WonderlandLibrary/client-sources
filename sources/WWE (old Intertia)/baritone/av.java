package baritone;

import baritone.api.utils.*;

enum av
{
    av(final String s) {
    }
    
    @Override
    public final ae a(final ad ad, final BetterBlockPos betterBlockPos) {
        return new bm(ad.a, betterBlockPos, betterBlockPos.up());
    }
    
    @Override
    public final double a(final ad ad, final int n, final int n2, final int n3) {
        return bm.a(ad, n, n2, n3);
    }
}
