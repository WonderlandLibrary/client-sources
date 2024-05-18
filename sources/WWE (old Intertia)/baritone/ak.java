package baritone;

import baritone.api.utils.*;

enum ak
{
    ak(final String s) {
    }
    
    @Override
    public final ae a(final ad ad, final BetterBlockPos betterBlockPos) {
        return new bj(ad.a, betterBlockPos, betterBlockPos.down());
    }
    
    @Override
    public final double a(final ad ad, final int n, final int n2, final int n3) {
        return bj.a(ad, n, n2, n3);
    }
}
