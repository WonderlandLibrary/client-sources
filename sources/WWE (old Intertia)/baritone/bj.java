package baritone;

import baritone.api.*;
import baritone.api.utils.*;
import baritone.api.pathing.movement.*;

public final class bj extends ae
{
    private int a;
    
    public bj(final IBaritone baritone, final BetterBlockPos betterBlockPos, final BetterBlockPos betterBlockPos2) {
        super(baritone, betterBlockPos, betterBlockPos2, new BetterBlockPos[] { betterBlockPos2 });
        this.a = 0;
    }
    
    @Override
    public final void reset() {
        super.reset();
        this.a = 0;
    }
    
    @Override
    public final double b(final ad ad) {
        return a(ad, this.a.a, this.a.b, this.a.c);
    }
    
    public static double a(final ad ad, final int n, final int n2, final int n3) {
        if (!af.b(ad.a, n, n2 - 2, n3)) {
            return 1000000.0;
        }
        final awt a;
        final aow u;
        if ((u = (a = ad.a(n, n2 - 1, n3)).u()) == aox.au || u == aox.bn) {
            return 6.666666666666667;
        }
        return bj.FALL_N_BLOCKS_COST[1] + af.a(ad, n, n2 - 1, n3, a, false);
    }
    
    @Override
    public final ah a(final ah ah) {
        super.a(ah);
        if (ah.a != MovementStatus.RUNNING) {
            return ah;
        }
        if (this.a.playerFeet().equals(this.b)) {
            ah.a = MovementStatus.SUCCESS;
            return ah;
        }
        final double n = this.a.player().p - (this.b.p() + 0.5);
        final double n2 = this.a.player().r - (this.b.r() + 0.5);
        final double n3 = n;
        final double n4 = n3 * n3;
        final double n5 = n2;
        final double sqrt = Math.sqrt(n4 + n5 * n5);
        if (this.a++ < 10 && sqrt < 0.2) {
            return ah;
        }
        af.a(this.a, ah, this.a[0]);
        return ah;
    }
}
