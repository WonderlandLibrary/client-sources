package baritone;

import baritone.api.*;
import baritone.api.utils.*;
import baritone.api.pathing.movement.*;
import baritone.api.utils.input.*;
import java.util.*;

public final class bi extends ae
{
    private static final double a;
    
    public bi(final IBaritone baritone, final BetterBlockPos betterBlockPos, final fa fa, final fa fa2, final int n) {
        this(baritone, betterBlockPos, betterBlockPos.offset(fa), betterBlockPos.offset(fa2), fa2, n);
    }
    
    private bi(final IBaritone baritone, final BetterBlockPos betterBlockPos, final BetterBlockPos betterBlockPos2, final BetterBlockPos betterBlockPos3, final fa fa, final int n) {
        this(baritone, betterBlockPos, betterBlockPos2.offset(fa).up(n), betterBlockPos2, betterBlockPos3);
    }
    
    private bi(final IBaritone baritone, final BetterBlockPos betterBlockPos, final BetterBlockPos betterBlockPos2, final BetterBlockPos betterBlockPos3, final BetterBlockPos betterBlockPos4) {
        super(baritone, betterBlockPos, betterBlockPos2, new BetterBlockPos[] { betterBlockPos3, betterBlockPos3.up(), betterBlockPos4, betterBlockPos4.up(), betterBlockPos2, betterBlockPos2.up() });
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
    
    public static void a(final ad ad, final int n, final int b, final int n2, final int a, final int c, final dh dh) {
        final awt a2 = ad.a(a, b, c);
        if (!af.b(ad.a, a, b, c, a2) || !af.a(ad.a, a, b + 1, c)) {
            return;
        }
        final awt a3 = ad.a(a, b - 1, c);
        boolean b2 = false;
        if (!af.c(ad.a, a, b - 1, c, a3)) {
            b2 = true;
            if (!ad.j || !af.b(ad.a, a, b - 2, c) || !af.b(ad.a, a, b - 1, c, a3)) {
                return;
            }
        }
        double b3 = 4.63284688441047;
        if (a3.u() == aox.aW) {
            b3 = 6.949270326615705;
        }
        else if (a3.u() == aox.j) {
            b3 = 4.63284688441047 + ad.f * bi.a;
        }
        final aow u;
        if ((u = ad.a(n, b - 1, n2).u()) == aox.au || u == aox.bn) {
            return;
        }
        if (u == aox.aW) {
            b3 += 2.316423442205235;
        }
        final aow u2;
        if ((u2 = ad.a(n, b - 1, c).u()) == aox.df || af.c(u2)) {
            return;
        }
        final aow u3;
        if ((u3 = ad.a(a, b - 1, n2).u()) == aox.df || af.c(u3)) {
            return;
        }
        final awt a4 = ad.a(n, b, c);
        final awt a5 = ad.a(a, b, n2);
        final double a6 = af.a(ad, n, b, c, a4, false);
        final double a7 = af.a(ad, a, b, n2, a5, false);
        if (a6 != 0.0 && a7 != 0.0) {
            return;
        }
        final awt a8 = ad.a(n, b + 1, c);
        final double n3;
        if ((n3 = a6 + af.a(ad, n, b + 1, c, a8, true)) != 0.0 && a7 != 0.0) {
            return;
        }
        final awt a9 = ad.a(a, b + 1, n2);
        if (n3 == 0.0 && ((af.a(a5.u()) && a5.u() != aox.j) || af.a(a9.u()))) {
            return;
        }
        final double n4 = a7 + af.a(ad, a, b + 1, n2, a9, true);
        if (n3 != 0.0 && n4 != 0.0) {
            return;
        }
        if (n4 == 0.0 && ((af.a(a4.u()) && a4.u() != aox.j) || af.a(a8.u()))) {
            return;
        }
        final aow a10;
        if (af.b(a10 = ad.a(n, b, n2)) || af.b(a2.u())) {
            b3 = ad.b;
        }
        if (n3 != 0.0 || n4 != 0.0) {
            b3 *= bi.a - 0.001;
            if (a10 == aox.au || a10 == aox.bn) {
                return;
            }
        }
        else if (ad.d) {}
        dh.a = b3 * bi.a;
        if (b2) {
            dh.a += Math.max(bi.FALL_N_BLOCKS_COST[1], 0.9265693768820937);
            dh.b = b - 1;
        }
        else {
            dh.b = b;
        }
        dh.a = a;
        dh.c = c;
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
        boolean b = false;
        Label_0123: {
            if (af.c(this.a, this.a.playerFeet()) && !baritone.a.a().sprintInWater.value) {
                b = false;
            }
            else {
                for (int i = 0; i < 4; ++i) {
                    if (!af.a(this.a, this.a[i])) {
                        b = false;
                        break Label_0123;
                    }
                }
                b = true;
            }
        }
        if (b) {
            ah.a(Input.SPRINT, true);
        }
        af.a(this.a, ah, this.b);
        return ah;
    }
    
    public final boolean a(final ah ah) {
        return true;
    }
    
    @Override
    public final List<et> a(final cn cn) {
        if (this.a != null) {
            return this.a;
        }
        final ArrayList<BetterBlockPos> a = new ArrayList<BetterBlockPos>();
        for (int i = 4; i < 6; ++i) {
            if (!af.a(cn, this.a[i].a, this.a[i].b, this.a[i].c)) {
                a.add(this.a[i]);
            }
        }
        return this.a = (List<et>)a;
    }
    
    @Override
    public final List<et> c(final cn cn) {
        if (this.b == null) {
            this.b = new ArrayList<et>();
        }
        final ArrayList<BetterBlockPos> b = new ArrayList<BetterBlockPos>();
        for (int i = 0; i < 4; ++i) {
            if (!af.a(cn, this.a[i].a, this.a[i].b, this.a[i].c)) {
                b.add(this.a[i]);
            }
        }
        return this.b = (List<et>)b;
    }
    
    static {
        a = Math.sqrt(2.0);
    }
}
