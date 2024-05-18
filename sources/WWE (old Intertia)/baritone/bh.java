package baritone;

import baritone.api.*;
import baritone.api.pathing.movement.*;
import baritone.api.utils.*;
import baritone.api.utils.input.*;

public final class bh extends ae
{
    private int a;
    
    public bh(final IBaritone baritone, final BetterBlockPos betterBlockPos, final BetterBlockPos betterBlockPos2) {
        super(baritone, betterBlockPos, betterBlockPos2, new BetterBlockPos[] { betterBlockPos2.up(2), betterBlockPos2.up(), betterBlockPos2 }, betterBlockPos2.down());
        this.a = 0;
    }
    
    @Override
    public final void reset() {
        super.reset();
        this.a = 0;
    }
    
    @Override
    public final double b(final ad ad) {
        final dh dh = new dh();
        a(ad, this.a.a, this.a.b, this.a.c, this.b.a, this.b.c, dh);
        if (dh.b != this.b.b) {
            return 1000000.0;
        }
        return dh.a;
    }
    
    public static void a(final ad ad, final int n, final int n2, final int n3, final int a, final int c, final dh dh) {
        final awt a2 = ad.a(a, n2 - 1, c);
        final double n4;
        if ((n4 = 0.0 + af.a(ad, a, n2 - 1, c, a2, false)) >= 1000000.0) {
            return;
        }
        final double n5;
        if ((n5 = n4 + af.a(ad, a, n2, c, false)) >= 1000000.0) {
            return;
        }
        final double n6;
        if ((n6 = n5 + af.a(ad, a, n2 + 1, c, true)) >= 1000000.0) {
            return;
        }
        final aow u;
        if ((u = ad.a(n, n2 - 1, n3).u()) == aox.au || u == aox.bn) {
            return;
        }
        final awt a3 = ad.a(a, n2 - 2, c);
        if (!af.c(ad.a, a, n2 - 2, c, a3)) {
            a(ad, n2, a, c, n6, a3, dh);
            return;
        }
        if (a2.u() == aox.au || a2.u() == aox.bn) {
            return;
        }
        double n7 = 3.7062775075283763;
        if (u == aox.aW) {
            n7 = 7.4125550150567525;
        }
        final double a4 = n6 + (n7 + Math.max(bh.FALL_N_BLOCKS_COST[1], 0.9265693768820937));
        dh.a = a;
        dh.b = n2 - 1;
        dh.c = c;
        dh.a = a4;
    }
    
    public static boolean a(final ad ad, final int n, final int a, final int c, final double n2, final awt awt, final dh dh) {
        if (n2 != 0.0 && ad.a(a, n + 2, c).u() instanceof aqm) {
            return false;
        }
        if (!af.b(ad.a, a, n - 2, c, awt)) {
            return false;
        }
        double n3 = 0.0;
        int n4 = n;
        int n5 = 3;
        int b;
        while ((b = n - n5) >= 0) {
            final awt a2 = ad.a(a, b, c);
            final int n6 = n5 - (n - n4);
            final double n7 = 3.7062775075283763 + bh.FALL_N_BLOCKS_COST[n6] + n2 + n3;
            if (af.b(a2.u())) {
                if (!af.b(ad.a, a, b, c, a2)) {
                    return false;
                }
                if (ad.i) {
                    return false;
                }
                if (af.a(a, b, c, a2, ad.a)) {
                    return false;
                }
                if (!af.b(ad.a, a, b - 1, c)) {
                    return false;
                }
                dh.a = a;
                dh.b = b;
                dh.c = c;
                dh.a = n7;
                return false;
            }
            else {
                if (n6 <= 11 && (a2.u() == aox.bn || a2.u() == aox.au)) {
                    n3 = n3 + bh.FALL_N_BLOCKS_COST[n6 - 1] + 6.666666666666667;
                    n4 = b;
                }
                else if (!af.b(ad.a, a, b, c, a2)) {
                    if (!af.c(ad.a, a, b, c, a2)) {
                        return false;
                    }
                    if (af.b(a2)) {
                        return false;
                    }
                    if (n6 <= ad.a + 1) {
                        dh.a = a;
                        dh.b = b + 1;
                        dh.c = c;
                        dh.a = n7;
                        return false;
                    }
                    if (ad.b && n6 <= ad.b + 1) {
                        dh.a = a;
                        dh.b = b + 1;
                        dh.c = c;
                        dh.a = n7 + ad.a();
                        return true;
                    }
                    return false;
                }
                ++n5;
            }
        }
        return false;
    }
    
    @Override
    public final ah a(final ah ah) {
        super.a(ah);
        if (ah.a != MovementStatus.RUNNING) {
            return ah;
        }
        final BetterBlockPos playerFeet = this.a.playerFeet();
        final et et = new et((this.b.p() << 1) - this.a.p(), this.b.q(), (this.b.r() << 1) - this.a.r());
        if ((playerFeet.equals((Object)this.b) || playerFeet.equals((Object)et)) && (af.c(this.a, this.b) || this.a.player().q - this.b.q() < 0.5)) {
            ah.a = MovementStatus.SUCCESS;
            return ah;
        }
        if (this.a()) {
            final double n = (this.a.p() + 0.5) * 0.17 + (this.b.p() + 0.5) * 0.83;
            final double n2 = (this.a.r() + 0.5) * 0.17 + (this.b.r() + 0.5) * 0.83;
            final bud player = this.a.player();
            ah.a = new ai(new Rotation(RotationUtils.calcRotationFromVec3d(player.f(1.0f), new bhe(n, (double)this.b.q(), n2), new Rotation(player.v, player.w)).getYaw(), player.w), false);
            ah.a(Input.MOVE_FORWARD, true);
            return ah;
        }
        final double n3 = this.a.player().p - (this.b.p() + 0.5);
        final double n4 = this.a.player().r - (this.b.r() + 0.5);
        final double n5 = n3;
        final double n6 = n5 * n5;
        final double n7 = n4;
        final double sqrt = Math.sqrt(n6 + n7 * n7);
        final double n8 = this.a.player().p - (this.a.p() + 0.5);
        final double n9 = this.a.player().r - (this.a.r() + 0.5);
        final double n10 = n8;
        final double n11 = n10 * n10;
        final double n12 = n9;
        final double sqrt2 = Math.sqrt(n11 + n12 * n12);
        if (!playerFeet.equals((Object)this.b) || sqrt > 0.25) {
            if (this.a++ < 20 && sqrt2 < 1.25) {
                af.a(this.a, ah, et);
            }
            else {
                af.a(this.a, ah, this.b);
            }
        }
        return ah;
    }
    
    public final boolean a() {
        final et a = this.b.b((fq)this.a.down()).a((fq)this.b);
        if (this.b()) {
            return true;
        }
        for (int i = 0; i <= 2; ++i) {
            if (af.a(cn.a(this.a, a.b(i)))) {
                return true;
            }
        }
        return false;
    }
    
    public final boolean b() {
        final et a = this.b.b((fq)this.a.down()).a((fq)this.b);
        return !af.a(this.a, new BetterBlockPos(a)) && af.a(this.a, new BetterBlockPos(a).up()) && af.a(this.a, new BetterBlockPos(a).up(2));
    }
}
