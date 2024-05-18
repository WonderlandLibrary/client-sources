package baritone;

import baritone.api.utils.*;

enum bf
{
    bf(final String s) {
    }
    
    @Override
    public final ae a(final ad ad, final BetterBlockPos betterBlockPos) {
        return new bg(ad.a, betterBlockPos, new BetterBlockPos(betterBlockPos.a + 1, betterBlockPos.b + 1, betterBlockPos.c));
    }
    
    @Override
    public final double a(final ad ad, final int n, final int n2, final int n3) {
        return bg.a(ad, n, n2, n3, n + 1, n3);
    }
}
