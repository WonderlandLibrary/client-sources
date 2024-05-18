package baritone;

import baritone.api.utils.*;

enum be
{
    be(final String s) {
    }
    
    @Override
    public final ae a(final ad ad, final BetterBlockPos betterBlockPos) {
        return new bg(ad.a, betterBlockPos, new BetterBlockPos(betterBlockPos.a, betterBlockPos.b + 1, betterBlockPos.c + 1));
    }
    
    @Override
    public final double a(final ad ad, final int n, final int n2, final int n3) {
        return bg.a(ad, n, n2, n3, n, n3 + 1);
    }
}
