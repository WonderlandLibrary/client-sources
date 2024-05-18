package baritone;

import baritone.api.*;
import baritone.api.pathing.movement.*;
import baritone.api.utils.input.*;
import baritone.api.utils.*;
import java.util.*;

public final class bm extends ae
{
    public bm(final IBaritone baritone, final BetterBlockPos betterBlockPos, final BetterBlockPos betterBlockPos2) {
        super(baritone, betterBlockPos, betterBlockPos2, new BetterBlockPos[] { betterBlockPos.up(2) }, betterBlockPos);
    }
    
    @Override
    public final double b(final ad ad) {
        return a(ad, this.a.a, this.a.b, this.a.c);
    }
    
    public static double a(final ad ad, final int n, final int n2, final int n3) {
        final aow u;
        final boolean b = (u = ad.a(n, n2, n3).u()) == aox.au || u == aox.bn;
        final awt a = ad.a(n, n2 - 1, n3);
        if (a.u() == aox.au || a.u() == aox.bn) {
            return 1000000.0;
        }
        if (a.u() instanceof arf && !((arf)a.u()).e() && a.c((axj)arf.a) == arf$a.b) {
            return 1000000.0;
        }
        if (u == aox.bn && !b(ad, n, n2, n3)) {
            return 1000000.0;
        }
        final awt a2;
        final aow u2;
        if ((u2 = (a2 = ad.a(n, n2 + 2, n3)).u()) instanceof aqp) {
            return 1000000.0;
        }
        aow aow = null;
        if (af.b(u2) && af.b(u) && af.b(aow = ad.a(n, n2 + 1, n3).u())) {
            return 8.51063829787234;
        }
        double a3;
        if ((a3 = ad.a(n, n2, n3)) >= 1000000.0) {
            return 1000000.0;
        }
        if (a.u() == aox.a) {
            a3 += 0.1;
        }
        if (u instanceof aru || (a.u() instanceof aru && ad.i)) {
            return 1000000.0;
        }
        double a4;
        if ((a4 = af.a(ad, n, n2 + 2, n3, a2, true)) >= 1000000.0) {
            return 1000000.0;
        }
        if (a4 != 0.0) {
            if (u2 == aox.au || u2 == aox.bn) {
                a4 = 0.0;
            }
            else if (ad.a(n, n2 + 3, n3).u() instanceof aqm) {
                if (aow == null) {
                    aow = ad.a(n, n2 + 1, n3).u();
                }
                if (!(u2 instanceof aqm) || !(aow instanceof aqm)) {
                    return 1000000.0;
                }
            }
        }
        if (b) {
            return 8.51063829787234 + a4 * 5.0;
        }
        return bm.JUMP_ONE_BLOCK_COST + a3 + ad.e + a4;
    }
    
    private static boolean b(final ad ad, final int n, final int n2, final int n3) {
        return ad.a(n + 1, n2, n3).k() || ad.a(n - 1, n2, n3).k() || ad.a(n, n2, n3 + 1).k() || ad.a(n, n2, n3 - 1).k();
    }
    
    @Override
    public final ah a(final ah ah) {
        super.a(ah);
        if (ah.a != MovementStatus.RUNNING) {
            return ah;
        }
        if (this.a.playerFeet().b < this.a.b) {
            ah.a = MovementStatus.UNREACHABLE;
            return ah;
        }
        final awt a;
        if (af.b((a = cn.a(this.a, (et)this.a)).u()) && af.b(this.a, (et)this.b)) {
            ah.a = new ai(RotationUtils.calcRotationFromVec3d(this.a.playerHead(), VecUtils.getBlockPosCenter(this.b), this.a.playerRotations()), false);
            final bhe blockPosCenter = VecUtils.getBlockPosCenter(this.b);
            if (Math.abs(this.a.player().p - blockPosCenter.b) > 0.2 || Math.abs(this.a.player().r - blockPosCenter.d) > 0.2) {
                ah.a(Input.MOVE_FORWARD, true);
            }
            if (this.a.playerFeet().equals(this.b)) {
                ah.a = MovementStatus.SUCCESS;
                return ah;
            }
            return ah;
        }
        else {
            final boolean b = a.u() == aox.au || a.u() == aox.bn;
            final boolean b2 = a.u() == aox.bn;
            final Rotation calcRotationFromVec3d = RotationUtils.calcRotationFromVec3d(this.a.player().f(1.0f), VecUtils.getBlockPosCenter(this.c), new Rotation(this.a.player().v, this.a.player().w));
            ah.a = new ai(new Rotation(this.a.player().v, calcRotationFromVec3d.getPitch()), true);
            boolean b3 = af.b(this.a, this.a) || b;
            if (b) {
                final BetterBlockPos offset;
                if ((offset = this.a.offset(((fa)a.c((axj)arq.a)).d())) == null) {
                    this.a("Unable to climb vines");
                    ah.a = MovementStatus.UNREACHABLE;
                    return ah;
                }
                if (this.a.playerFeet().equals(offset.a()) || this.a.playerFeet().equals(this.b)) {
                    ah.a = MovementStatus.SUCCESS;
                    return ah;
                }
                if (af.b(cn.a(this.a, (et)this.a.down()))) {
                    ah.a(Input.JUMP, true);
                }
                af.a(this.a, ah, offset);
                return ah;
            }
            else {
                if (!((a)this.a).a.a(this.a.a, this.a.b, this.a.c)) {
                    ah.a = MovementStatus.UNREACHABLE;
                    return ah;
                }
                ah.a(Input.SNEAK, this.a.player().q > this.b.q() || this.a.player().q < this.a.q() + 0.2);
                final double n = this.a.player().p - (this.b.p() + 0.5);
                final double n2 = this.a.player().r - (this.b.r() + 0.5);
                final double n3 = n;
                final double n4 = n3 * n3;
                final double n5 = n2;
                final double sqrt = Math.sqrt(n4 + n5 * n5);
                final double sqrt2 = Math.sqrt(this.a.player().s * this.a.player().s + this.a.player().u * this.a.player().u);
                if (sqrt > 0.17) {
                    ah.a(Input.MOVE_FORWARD, true);
                    ah.a = new ai(calcRotationFromVec3d, true);
                }
                else if (sqrt2 < 0.05) {
                    ah.a(Input.JUMP, this.a.player().q < this.b.q());
                }
                final awt a2;
                if (!((a2 = cn.a(this.a, (et)this.a)).u() instanceof aom) && !a2.a().j()) {
                    final Optional<Rotation> reachable;
                    if ((reachable = RotationUtils.reachable(this.a.player(), this.a, this.a.playerController().getBlockReachDistance())).isPresent()) {
                        ah.a = new ai(reachable.get(), true);
                    }
                    ah.a(Input.JUMP, false);
                    ah.a(Input.CLICK_LEFT, true);
                    b3 = false;
                }
                else if (this.a.player().aU() && (Objects.equals(this.a.down(), this.a.objectMouseOver().a()) || Objects.equals(this.a, this.a.objectMouseOver().a())) && this.a.player().q > this.b.q() + 0.1) {
                    ah.a(Input.CLICK_RIGHT, true);
                }
                if (this.a.playerFeet().equals(this.b) && b3) {
                    ah.a = MovementStatus.SUCCESS;
                    return ah;
                }
                return ah;
            }
        }
    }
    
    public final boolean a(final ah ah) {
        final aow a;
        if ((this.a.playerFeet().equals(this.a) || this.a.playerFeet().equals(this.a.down())) && ((a = cn.a(this.a, (et)this.a.down())) == aox.au || a == aox.bn)) {
            ah.a(Input.SNEAK, true);
        }
        return af.b(this.a, (et)this.b.up()) || super.a(ah);
    }
}
