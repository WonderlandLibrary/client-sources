package baritone;

import baritone.api.*;
import baritone.api.pathing.movement.*;
import baritone.api.utils.input.*;
import baritone.api.utils.*;

public final class bg extends ae
{
    private int a;
    
    public bg(final IBaritone baritone, final BetterBlockPos betterBlockPos, final BetterBlockPos betterBlockPos2) {
        super(baritone, betterBlockPos, betterBlockPos2, new BetterBlockPos[] { betterBlockPos2, betterBlockPos.up(2), betterBlockPos2.up() }, betterBlockPos2.down());
        this.a = 0;
    }
    
    @Override
    public final void reset() {
        super.reset();
        this.a = 0;
    }
    
    @Override
    public final double b(final ad ad) {
        return a(ad, this.a.a, this.a.b, this.a.c, this.b.a, this.b.c);
    }
    
    public static double a(final ad ad, final int n, final int n2, final int n3, final int n4, final int n5) {
        final awt a = ad.a(n4, n2, n5);
        final double n6 = 0.0;
        if (!af.c(ad.a, n4, n2, n5, a)) {
            if (ad.a(n4, n2, n5) >= 1000000.0) {
                return 1000000.0;
            }
            if (!af.a(n4, n5, a, ad.a)) {
                return 1000000.0;
            }
            for (int i = 0; i < 5; ++i) {
                final int n7 = n4 + bg.a[i].g();
                final int n8 = n2 + bg.a[i].h();
                final int n9 = n5 + bg.a[i].i();
                if ((n7 != n || n9 != n3) && af.c(ad.a, n7, n8, n9)) {
                    break;
                }
            }
            return 1000000.0;
        }
        else {
            final awt a2 = ad.a(n, n2 + 2, n3);
            if (ad.a(n, n2 + 3, n3).u() instanceof aqm && (af.a(ad.a, n, n2 + 1, n3) || !(a2.u() instanceof aqm))) {
                return 1000000.0;
            }
            final awt a3;
            if ((a3 = ad.a(n, n2 - 1, n3)).u() == aox.au || a3.u() == aox.bn) {
                return 1000000.0;
            }
            final boolean b = af.b(a3);
            final boolean b2 = af.b(a);
            if (b) {
                return 1000000.0;
            }
            double n10;
            if (b2) {
                n10 = 4.63284688441047;
            }
            else {
                double max;
                if (a.u() == aox.aW) {
                    max = 9.26569376882094;
                }
                else {
                    max = Math.max(bg.JUMP_ONE_BLOCK_COST, 4.63284688441047);
                }
                n10 = max + ad.e;
            }
            final double n11;
            if ((n11 = n10 + n6 + af.a(ad, n, n2 + 2, n3, a2, false)) >= 1000000.0) {
                return 1000000.0;
            }
            final double n12;
            if ((n12 = n11 + af.a(ad, n4, n2 + 1, n5, false)) >= 1000000.0) {
                return 1000000.0;
            }
            return n12 + af.a(ad, n4, n2 + 2, n5, true);
        }
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
        if (this.a.playerFeet().b < this.a.b) {
            ah.a = MovementStatus.UNREACHABLE;
            return ah;
        }
        final awt a = cn.a(this.a, (et)this.c);
        final IPlayerContext a2 = this.a;
        final BetterBlockPos c = this.c;
        final awt awt = a;
        final BetterBlockPos betterBlockPos = c;
        if (!af.c(new cn(a2), betterBlockPos.a, betterBlockPos.b, betterBlockPos.c, awt)) {
            ++this.a;
            if (af.a(ah, this.a, this.b.down(), false) == ag.a) {
                ah.a(Input.SNEAK, true);
                if (this.a.player().aU()) {
                    ah.a(Input.CLICK_RIGHT, true);
                }
            }
            if (this.a > 10) {
                ah.a(Input.MOVE_BACK, true);
            }
            return ah;
        }
        af.a(this.a, ah, this.b);
        if (af.b(a) && !af.b(cn.a(this.a, (et)this.a.down()))) {
            return ah;
        }
        if (baritone.a.a().assumeStep.value || this.a.playerFeet().equals(this.a.up())) {
            return ah;
        }
        final int abs = Math.abs(this.a.p() - this.b.p());
        final int abs2 = Math.abs(this.a.r() - this.b.r());
        final double n = abs * Math.abs(this.b.p() + 0.5 - this.a.player().p) + abs2 * Math.abs(this.b.r() + 0.5 - this.a.player().r);
        final double n2 = abs2 * Math.abs(this.b.p() + 0.5 - this.a.player().p) + abs * Math.abs(this.b.r() + 0.5 - this.a.player().r);
        if (Math.abs(abs * this.a.player().u + abs2 * this.a.player().s) > 0.1) {
            return ah;
        }
        final BetterBlockPos up = this.a.up(2);
        int i = 0;
        while (true) {
            while (i < 4) {
                if (!af.a(this.a, up.offset(fa.b(i)))) {
                    final boolean b = false;
                    if (b) {
                        return ah.a(Input.JUMP, true);
                    }
                    if (n > 1.2 || n2 > 0.2) {
                        return ah;
                    }
                    return ah.a(Input.JUMP, true);
                }
                else {
                    ++i;
                }
            }
            final boolean b = true;
            continue;
        }
    }
    
    public final boolean b(final ah ah) {
        return ah.a != MovementStatus.RUNNING || this.a == 0;
    }
}
