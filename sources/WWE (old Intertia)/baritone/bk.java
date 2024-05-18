package baritone;

import baritone.api.*;
import baritone.api.pathing.movement.*;
import baritone.api.utils.*;
import baritone.api.utils.input.*;
import java.util.*;
import java.util.function.*;

public final class bk extends ae
{
    private static final aip a;
    private static final aip b;
    
    public bk(final IBaritone baritone, final BetterBlockPos betterBlockPos, final BetterBlockPos betterBlockPos2) {
        super(baritone, betterBlockPos, betterBlockPos2, a(betterBlockPos, betterBlockPos2));
    }
    
    @Override
    public final double b(final ad ad) {
        final dh dh = new dh();
        bh.a(ad, this.a.a, this.a.b, this.a.c, this.b.a, this.b.c, dh);
        if (dh.b != this.b.b) {
            return 1000000.0;
        }
        return dh.a;
    }
    
    @Override
    public final ah a(final ah ah) {
        super.a(ah);
        if (ah.a != MovementStatus.RUNNING) {
            return ah;
        }
        final BetterBlockPos playerFeet = this.a.playerFeet();
        final Rotation calcRotationFromVec3d = RotationUtils.calcRotationFromVec3d(this.a.playerHead(), VecUtils.getBlockPosCenter(this.b), this.a.playerRotations());
        Rotation rotation = null;
        final aow u;
        final boolean b;
        if (!(b = ((u = this.a.world().o((et)this.b).u()) == aox.j || u == aox.i))) {
            final ad ad = new ad(this.a);
            if (bh.a(ad, this.a.b, this.b.a, this.b.c, 0.0, ad.a(this.b.a, this.a.b - 2, this.b.c), new dh()) && !playerFeet.equals((Object)this.b)) {
                if (!aec.e(this.a.player().bv.b(bk.a)) || this.a.world().s.n()) {
                    ah.a = MovementStatus.UNREACHABLE;
                    return ah;
                }
                if (this.a.player().q - this.b.q() < this.a.playerController().getBlockReachDistance() && !this.a.player().z) {
                    this.a.player().bv.d = this.a.player().bv.b(bk.a);
                    rotation = new Rotation(calcRotationFromVec3d.getYaw(), 90.0f);
                    final bhc objectMouseOver;
                    if ((objectMouseOver = this.a.objectMouseOver()) != null && objectMouseOver.a == bhc$a.b && (objectMouseOver.a().equals((Object)this.b) || objectMouseOver.a().equals((Object)this.b.down()))) {
                        ah.a(Input.CLICK_RIGHT, true);
                    }
                }
            }
        }
        if (rotation != null) {
            ah.a = new ai(rotation, true);
        }
        else {
            ah.a = new ai(calcRotationFromVec3d, false);
        }
        if (playerFeet.equals((Object)this.b) && (this.a.player().q - playerFeet.q() < 0.094 || b)) {
            if (!b) {
                ah.a = MovementStatus.SUCCESS;
                return ah;
            }
            if (aec.e(this.a.player().bv.b(bk.b))) {
                this.a.player().bv.d = this.a.player().bv.b(bk.b);
                if (this.a.player().t >= 0.0) {
                    return ah.a(Input.CLICK_RIGHT, true);
                }
                return ah;
            }
            else if (this.a.player().t >= 0.0) {
                ah.a = MovementStatus.SUCCESS;
                return ah;
            }
        }
        final bhe blockPosCenter = VecUtils.getBlockPosCenter(this.b);
        if (Math.abs(this.a.player().p + this.a.player().s - blockPosCenter.b) > 0.1 || Math.abs(this.a.player().r + this.a.player().u - blockPosCenter.d) > 0.1) {
            if (!this.a.player().z && Math.abs(this.a.player().t) > 0.4) {
                ah.a(Input.SNEAK, true);
            }
            ah.a(Input.MOVE_FORWARD, true);
        }
        while (true) {
            for (int i = 0; i < 15; ++i) {
                final awt o;
                if ((o = this.a.world().o((et)this.a.playerFeet().down(i))).u() == aox.au) {
                    final fa fa = (fa)o.c((axj)arq.a);
                    Object b2;
                    if ((b2 = Optional.ofNullable(fa).map((Function<? super fa, ? extends fq>)fa::n).orElse(null)) == null) {
                        b2 = this.a.b((fq)this.b);
                    }
                    else if (Math.abs(((fq)b2).p() * (blockPosCenter.b - ((fq)b2).p() / 2.0 - this.a.player().p)) + Math.abs(((fq)b2).r() * (blockPosCenter.d - ((fq)b2).r() / 2.0 - this.a.player().r)) < 0.6) {
                        ah.a(Input.MOVE_FORWARD, true);
                    }
                    else if (!this.a.player().z) {
                        ah.a(Input.SNEAK, false);
                    }
                    if (rotation == null) {
                        ah.a = new ai(RotationUtils.calcRotationFromVec3d(this.a.playerHead(), new bhe(blockPosCenter.b + 0.125 * ((fq)b2).p(), blockPosCenter.c, blockPosCenter.d + 0.125 * ((fq)b2).r()), this.a.playerRotations()), false);
                    }
                    return ah;
                }
            }
            final fa fa = null;
            continue;
        }
    }
    
    public final boolean b(final ah ah) {
        return this.a.playerFeet().equals(this.a) || ah.a != MovementStatus.RUNNING;
    }
    
    private static BetterBlockPos[] a(final BetterBlockPos betterBlockPos, final BetterBlockPos betterBlockPos2) {
        final int n = betterBlockPos.p() - betterBlockPos2.p();
        final int n2 = betterBlockPos.r() - betterBlockPos2.r();
        final BetterBlockPos[] array = new BetterBlockPos[betterBlockPos.q() - betterBlockPos2.q() + 2];
        for (int i = 0; i < array.length; ++i) {
            array[i] = new BetterBlockPos(betterBlockPos.p() - n, betterBlockPos.q() + 1 - i, betterBlockPos.r() - n2);
        }
        return array;
    }
    
    public final boolean a(final ah ah) {
        if (ah.a == MovementStatus.WAITING) {
            return true;
        }
        for (int n = 0; n < 4 && n < this.a.length; ++n) {
            if (!af.a(this.a, this.a[n])) {
                return super.a(ah);
            }
        }
        return true;
    }
    
    static {
        a = new aip(air.aA);
        b = new aip(air.az);
    }
}
