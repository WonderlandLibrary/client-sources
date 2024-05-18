package baritone;

import baritone.api.utils.*;

enum az
{
    az(final String s) {
    }
    
    @Override
    public final ae a(final ad ad, final BetterBlockPos betterBlockPos) {
        return new bn(ad.a, betterBlockPos, betterBlockPos.north());
    }
    
    @Override
    public final double a(final ad ad, final int n, final int n2, final int n3) {
        return bn.a(ad, n, n2, n3, n, n3 - 1);
    }
}
