package baritone;

import baritone.api.*;
import baritone.api.pathing.movement.*;
import baritone.api.utils.*;
import baritone.api.utils.input.*;
import java.util.*;

public final class bn extends ae
{
    private boolean a;
    
    public bn(final IBaritone baritone, final BetterBlockPos betterBlockPos, final BetterBlockPos betterBlockPos2) {
        super(baritone, betterBlockPos, betterBlockPos2, new BetterBlockPos[] { betterBlockPos2.up(), betterBlockPos2 }, betterBlockPos2.down());
        this.a = true;
    }
    
    @Override
    public final void reset() {
        super.reset();
        this.a = true;
    }
    
    @Override
    public final double b(final ad ad) {
        return a(ad, this.a.a, this.a.b, this.a.c, this.b.a, this.b.c);
    }
    
    public static double a(final ad ad, final int n, final int n2, final int n3, final int n4, final int n5) {
        final awt a = ad.a(n4, n2 + 1, n5);
        final awt a2 = ad.a(n4, n2, n5);
        final awt a3 = ad.a(n4, n2 - 1, n5);
        final aow a4 = ad.a(n, n2 - 1, n3);
        if (af.c(ad.a, n4, n2 - 1, n5, a3)) {
            double b = 4.63284688441047;
            if (af.b(a.u()) || af.b(a2.u())) {
                b = ad.b;
            }
            else {
                if (a3.u() == aox.aW) {
                    b = 6.949270326615705;
                }
                else if (a3.u() == aox.j) {
                    b = 4.63284688441047 + ad.f;
                }
                if (a4 == aox.aW) {
                    b += 2.316423442205235;
                }
            }
            double a5;
            if ((a5 = af.a(ad, n4, n2, n5, a2, false)) >= 1000000.0) {
                return 1000000.0;
            }
            double a6 = af.a(ad, n4, n2 + 1, n5, a, true);
            if (a5 == 0.0 && a6 == 0.0) {
                return b;
            }
            if (a4 == aox.au || a4 == aox.bn) {
                a5 *= 5.0;
                a6 *= 5.0;
            }
            return b + a5 + a6;
        }
        else {
            if (a4 == aox.au || a4 == aox.bn) {
                return 1000000.0;
            }
            if (!af.a(n4, n5, a3, ad.a)) {
                return 1000000.0;
            }
            final boolean b2 = af.b(a.u()) || af.b(a2.u());
            if (af.b(a3.u()) && b2) {
                return 1000000.0;
            }
            final double a7;
            if ((a7 = ad.a(n4, n2 - 1, n5)) >= 1000000.0) {
                return 1000000.0;
            }
            final double a8;
            if ((a8 = af.a(ad, n4, n2, n5, a2, false)) >= 1000000.0) {
                return 1000000.0;
            }
            final double a9 = af.a(ad, n4, n2 + 1, n5, a, true);
            final double n6 = b2 ? ad.b : 4.63284688441047;
            for (int i = 0; i < 5; ++i) {
                final int n7 = n4 + bn.a[i].g();
                final int n8 = n2 - 1 + bn.a[i].h();
                final int n9 = n5 + bn.a[i].i();
                if ((n7 != n || n9 != n3) && af.c(ad.a, n7, n8, n9)) {
                    return n6 + a7 + a8 + a9;
                }
            }
            if (a4 == aox.aW || (a4 instanceof arf && !((arf)a4).e())) {
                return 1000000.0;
            }
            if (a4 == aox.i || a4 == aox.j) {
                return 1000000.0;
            }
            return n6 * 3.3207692307692307 + a7 + a8 + a9;
        }
    }
    
    @Override
    public final ah a(final ah ah) {
        super.a(ah);
        if (ah.a != MovementStatus.RUNNING) {
            if (!baritone.a.a().walkWhileBreaking.value) {
                return ah;
            }
            if (ah.a != MovementStatus.PREPPING) {
                return ah;
            }
            if (af.a(cn.a(this.a, (et)this.a[0]).u())) {
                return ah;
            }
            if (af.a(cn.a(this.a, (et)this.a[1]).u())) {
                return ah;
            }
            if (Math.max(Math.abs(this.a.player().p - (this.b.p() + 0.5)), Math.abs(this.a.player().r - (this.b.r() + 0.5))) < 0.83) {
                return ah;
            }
            ah.a = new ai(new Rotation(RotationUtils.calcRotationFromVec3d(this.a.playerHead(), VecUtils.calculateBlockCenter(this.a.world(), this.b), this.a.playerRotations()).getYaw(), Optional.ofNullable(ah.a.a).get().getPitch()), true);
            return ah.a(Input.MOVE_FORWARD, true).a(Input.SPRINT, true);
        }
        else {
            ah.a(Input.SNEAK, false);
            final aow u;
            final boolean b = (u = cn.a(this.a, (et)this.a.down()).u()) == aox.au || u == aox.bn;
            final awt a = cn.a(this.a, (et)this.a[0]);
            final awt a2 = cn.a(this.a, (et)this.a[1]);
            if (a.u() instanceof aqa || a2.u() instanceof aqa) {
                boolean b2 = false;
                if (a.u() instanceof aqa && !af.a(this.a, this.a, this.b)) {
                    b2 = true;
                }
                else if (a2.u() instanceof aqa && !af.a(this.a, this.b, this.a)) {
                    b2 = true;
                }
                if (b2 && !aox.aA.equals(a.u()) && !aox.aA.equals(a2.u())) {
                    ah.a = new ai(RotationUtils.calcRotationFromVec3d(this.a.playerHead(), VecUtils.calculateBlockCenter(this.a.world(), this.a[0]), this.a.playerRotations()), true);
                    return ah.a(Input.CLICK_RIGHT, true);
                }
            }
            if (a.u() instanceof aqp || a2.u() instanceof aqp) {
                et et = null;
                if (!af.b(this.a, this.a[0], this.a.up())) {
                    et = this.a[0];
                }
                else if (!af.b(this.a, this.a[1], this.a)) {
                    et = this.a[1];
                }
                if (et != null) {
                    ah.a = new ai(RotationUtils.calcRotationFromVec3d(this.a.playerHead(), VecUtils.calculateBlockCenter(this.a.world(), et), this.a.playerRotations()), true);
                    return ah.a(Input.CLICK_RIGHT, true);
                }
            }
            final boolean b3 = af.b(this.a, this.c) || b;
            final BetterBlockPos playerFeet;
            if ((playerFeet = this.a.playerFeet()).q() != this.b.q()) {
                this.a("Wrong Y coordinate");
                if (playerFeet.q() < this.b.q()) {
                    ah.a(Input.JUMP, true);
                }
                return ah;
            }
            if (b3) {
                if (this.a.playerFeet().equals(this.b)) {
                    ah.a = MovementStatus.SUCCESS;
                    return ah;
                }
                final aow u2 = cn.a(this.a, (et)this.a).u();
                final aow u3 = cn.a(this.a, (et)this.a.up()).u();
                if (this.a.player().q > this.a.b + 0.1 && !this.a.player().z && (u2 == aox.bn || u2 == aox.au || u3 == aox.bn || u3 == aox.au)) {
                    return ah;
                }
                final et a3 = this.b.b((fq)this.a).a((fq)this.b);
                final aow u4 = cn.a(this.a, a3).u();
                final aow u5 = cn.a(this.a, a3.a()).u();
                if (this.a && (!af.c(this.a, this.a.playerFeet()) || baritone.a.a().sprintInWater.value) && (!af.a(u4) || af.b(u4)) && !af.a(u5)) {
                    ah.a(Input.SPRINT, true);
                }
                final aow u6 = cn.a(this.a, (et)this.b.down()).u();
                if (playerFeet.q() != this.b.q() && b && (u6 == aox.bn || u6 == aox.au)) {
                    new bm(this.a, this.b.down(), this.b).a(ah);
                    return ah;
                }
                af.a(this.a, ah, this.a[0]);
                return ah;
            }
            else {
                this.a = false;
                final aow u7;
                if (((u7 = cn.a(this.a, (et)this.a.playerFeet().down()).u()).equals(aox.aW) || u7 instanceof arf) && Math.max(Math.abs(this.b.p() + 0.5 - this.a.player().p), Math.abs(this.b.r() + 0.5 - this.a.player().r)) < 0.85) {
                    af.a(this.a, ah, this.b);
                    return ah.a(Input.MOVE_FORWARD, false).a(Input.MOVE_BACK, true);
                }
                final double max = Math.max(Math.abs(this.a.player().p - (this.b.p() + 0.5)), Math.abs(this.a.player().r - (this.b.r() + 0.5)));
                final int a4;
                if (((a4 = af.a(ah, this.a, this.b.down(), false)) == ag.a || max < 0.6) && !baritone.a.a().assumeSafeWalk.value) {
                    ah.a(Input.SNEAK, true);
                }
                switch (bo.a[a4 - 1]) {
                    case 1: {
                        if (this.a.player().aU() || baritone.a.a().assumeSafeWalk.value) {
                            ah.a(Input.CLICK_RIGHT, true);
                        }
                        return ah;
                    }
                    case 2: {
                        if (max > 0.83) {
                            if (Math.abs(ah.a.a.getYaw() - RotationUtils.calcRotationFromVec3d(this.a.playerHead(), VecUtils.getBlockPosCenter(this.b), this.a.playerRotations()).getYaw()) < 0.1) {
                                return ah.a(Input.MOVE_FORWARD, true);
                            }
                        }
                        else if (this.a.playerRotations().isReallyCloseTo(ah.a.a)) {
                            return ah.a(Input.CLICK_LEFT, true);
                        }
                        return ah;
                    }
                    default: {
                        if (!playerFeet.equals((Object)this.b)) {
                            af.a(this.a, ah, this.a[0]);
                            return ah;
                        }
                        final double n = (this.b.p() + this.a.p() + 1.0) * 0.5;
                        final double n2 = (this.b.q() + this.a.q() - 1.0) * 0.5;
                        final double n3 = (this.b.r() + this.a.r() + 1.0) * 0.5;
                        final BetterBlockPos down = this.a.down();
                        final Rotation calcRotationFromVec3d;
                        final float pitch = (calcRotationFromVec3d = RotationUtils.calcRotationFromVec3d(this.a.playerHead(), new bhe(n, n2, n3), this.a.playerRotations())).getPitch();
                        if (Math.max(Math.abs(this.a.player().p - n), Math.abs(this.a.player().r - n3)) < 0.29) {
                            ah.a = new ai(new Rotation(RotationUtils.calcRotationFromVec3d(VecUtils.getBlockPosCenter(this.b), this.a.playerHead(), this.a.playerRotations()).getYaw(), pitch), true);
                            ah.a(Input.MOVE_BACK, true);
                        }
                        else {
                            ah.a = new ai(calcRotationFromVec3d, true);
                        }
                        if (Objects.equals(this.a.getSelectedBlock().orElse(null), down)) {
                            return ah.a(Input.CLICK_RIGHT, true);
                        }
                        if (this.a.playerRotations().isReallyCloseTo(ah.a.a)) {
                            ah.a(Input.CLICK_LEFT, true);
                        }
                        return ah;
                    }
                }
            }
        }
    }
    
    public final boolean b(final ah ah) {
        return ah.a != MovementStatus.RUNNING || af.b(this.a, this.b.down());
    }
    
    public final boolean a(final ah ah) {
        final aow a;
        if ((this.a.playerFeet().equals(this.a) || this.a.playerFeet().equals(this.a.down())) && ((a = cn.a(this.a, (et)this.a.down())) == aox.au || a == aox.bn)) {
            ah.a(Input.SNEAK, true);
        }
        return super.a(ah);
    }
}
