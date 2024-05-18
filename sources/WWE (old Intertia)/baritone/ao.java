package baritone;

import baritone.api.utils.*;

enum ao
{
    ao(final String s) {
    }
    
    @Override
    public final ae a(final ad ad, final BetterBlockPos betterBlockPos) {
        final dh dh = new dh();
        this.a(ad, betterBlockPos.a, betterBlockPos.b, betterBlockPos.c, dh);
        if (dh.b == betterBlockPos.b - 1) {
            return new bh(ad.a, betterBlockPos, new BetterBlockPos(dh.a, dh.b, dh.c));
        }
        return new bk(ad.a, betterBlockPos, new BetterBlockPos(dh.a, dh.b, dh.c));
    }
    
    @Override
    public final void a(final ad ad, final int n, final int n2, final int n3, final dh dh) {
        bh.a(ad, n, n2, n3, n, n3 - 1, dh);
    }
}
