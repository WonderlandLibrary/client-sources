package baritone;

import baritone.api.utils.*;
import baritone.api.*;
import baritone.api.pathing.movement.*;
import baritone.api.utils.input.*;

public final class bl extends ae
{
    private static final BetterBlockPos[] b;
    private final fa a;
    private final int a;
    
    private bl(final IBaritone baritone, final BetterBlockPos betterBlockPos, final int a, final fa a2) {
        super(baritone, betterBlockPos, betterBlockPos.offset(a2, a), bl.b, betterBlockPos.offset(a2, a).down());
        this.a = a2;
        this.a = a;
    }
    
    public static bl a(final ad ad, final BetterBlockPos betterBlockPos, final fa fa) {
        final dh dh = new dh();
        a(ad, betterBlockPos.a, betterBlockPos.b, betterBlockPos.c, fa, dh);
        return new bl(ad.a, betterBlockPos, Math.abs(dh.a - betterBlockPos.a) + Math.abs(dh.c - betterBlockPos.c), fa);
    }
    
    public static void a(final ad ad, final int n, final int n2, final int n3, final fa fa, final dh dh) {
        if (!ad.f) {
            return;
        }
        if (n2 == 256 && !ad.h) {
            return;
        }
        final int g = fa.g();
        final int i = fa.i();
        if (!af.a(ad, n + g, n2, n3 + i)) {
            return;
        }
        final awt a = ad.a(n + g, n2 - 1, n3 + i);
        if (af.c(ad.a, n + g, n2 - 1, n3 + i, a)) {
            return;
        }
        if (af.a(a.u()) && a.u() != aox.j && a.u() != aox.i) {
            return;
        }
        if (!af.a(ad, n + g, n2 + 1, n3 + i)) {
            return;
        }
        if (!af.a(ad, n + g, n2 + 2, n3 + i)) {
            return;
        }
        if (!af.a(ad, n, n2 + 2, n3)) {
            return;
        }
        final awt a2;
        if ((a2 = ad.a(n, n2 - 1, n3)).u() == aox.bn || a2.u() == aox.au || a2.u() instanceof aud || af.b(a2) || a2.u() instanceof aru) {
            return;
        }
        int n4;
        if (a2.u() == aox.aW) {
            n4 = 2;
        }
        else if (ad.d) {
            n4 = 4;
        }
        else {
            n4 = 3;
        }
        for (int j = 2; j <= n4; ++j) {
            for (int k = 0; k < 4; ++k) {
                if (!af.a(ad, n + g * j, n2 + k, n3 + i * j)) {
                    return;
                }
            }
            if (af.b(ad.a, n + g * j, n2 - 1, n3 + i * j)) {
                dh.a = n + g * j;
                dh.b = n2;
                dh.c = n3 + i * j;
                dh.a = a(j) + ad.e;
                return;
            }
        }
        if (n4 != 4) {
            return;
        }
        if (!ad.g) {
            return;
        }
        final int a3 = n + 4 * g;
        final int c = n3 + 4 * i;
        final double a4;
        if ((a4 = ad.a(a3, n2 - 1, c)) >= 1000000.0) {
            return;
        }
        if (!af.a(a3, c, ad.a(a3, n2 - 1, c), ad.a)) {
            return;
        }
        for (int l = 0; l < 5; ++l) {
            final int n5 = a3 + bl.a[l].g();
            final int n6 = n2 - 1 + bl.a[l].h();
            final int n7 = c + bl.a[l].i();
            if ((n5 != n + g * 3 || n7 != n3 + i * 3) && af.c(ad.a, n5, n6, n7)) {
                dh.a = a3;
                dh.b = n2;
                dh.c = c;
                dh.a = a(4) + a4 + ad.e;
                return;
            }
        }
    }
    
    private static double a(final int n) {
        switch (n) {
            case 2: {
                return 9.26569376882094;
            }
            case 3: {
                return 13.89854065323141;
            }
            case 4: {
                return 14.255167498218103;
            }
            default: {
                throw new IllegalStateException("LOL ".concat(String.valueOf(n)));
            }
        }
    }
    
    @Override
    public final double b(final ad ad) {
        final dh dh = new dh();
        a(ad, this.a.a, this.a.b, this.a.c, this.a, dh);
        if (dh.a != this.b.a || dh.c != this.b.c) {
            return 1000000.0;
        }
        return dh.a;
    }
    
    public final boolean b(final ah ah) {
        return ah.a != MovementStatus.RUNNING;
    }
    
    @Override
    public final ah a(final ah ah) {
        super.a(ah);
        if (ah.a != MovementStatus.RUNNING) {
            return ah;
        }
        if (this.a.player().cG()) {
            this.a("Pausing parkour since hand is active");
            return ah;
        }
        if (this.a.playerFeet().b < this.a.b) {
            this.a("sorry");
            ah.a = MovementStatus.UNREACHABLE;
            return ah;
        }
        if (this.a >= 4) {
            ah.a(Input.SPRINT, true);
        }
        af.a(this.a, ah, this.b);
        if (this.a.playerFeet().equals(this.b)) {
            final aow a;
            if ((a = cn.a(this.a, (et)this.b)) == aox.bn || a == aox.au) {
                ah.a = MovementStatus.SUCCESS;
                return ah;
            }
            if (this.a.player().q - this.a.playerFeet().q() < 0.094) {
                ah.a = MovementStatus.SUCCESS;
            }
        }
        else if (!this.a.playerFeet().equals(this.a)) {
            if (this.a.playerFeet().equals(this.a.offset(this.a)) || this.a.player().q - this.a.playerFeet().q() > 1.0E-4) {
                if (!af.b(this.a, this.b.down()) && !this.a.player().z && af.a(ah, this.a, this.b.down(), true) == ag.a) {
                    ah.a(Input.CLICK_RIGHT, true);
                }
                if (this.a == 3 && Math.max(Math.abs(this.a.a + 0.5 - this.a.player().p), Math.abs(this.a.c + 0.5 - this.a.player().r)) < 0.7) {
                    return ah;
                }
                ah.a(Input.JUMP, true);
            }
            else if (!this.a.playerFeet().equals(this.b.offset(this.a, -1))) {
                ah.a(Input.SPRINT, false);
                if (this.a.playerFeet().equals(this.a.offset(this.a, -1))) {
                    af.a(this.a, ah, this.a);
                }
                else {
                    af.a(this.a, ah, this.a.offset(this.a, -1));
                }
            }
        }
        return ah;
    }
    
    static {
        b = new BetterBlockPos[0];
    }
}
