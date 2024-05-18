package baritone;

import baritone.api.utils.input.*;
import baritone.api.*;
import baritone.api.pathing.movement.*;
import baritone.api.utils.*;
import java.util.*;

public interface af extends ActionCosts, cr
{
    default boolean a(final cn cn, final int n, final int n2, final int n3, final awt awt) {
        final aow u;
        return (u = awt.u()) == aox.aI || u instanceof asa || cn.a(n, n2 + 1, n3).u() instanceof aru || cn.a(n + 1, n2, n3).u() instanceof aru || cn.a(n - 1, n2, n3).u() instanceof aru || cn.a(n, n2, n3 + 1).u() instanceof aru || cn.a(n, n2, n3 - 1).u() instanceof aru;
    }
    
    default boolean a(final IPlayerContext playerContext, final BetterBlockPos betterBlockPos) {
        return a(new cn(playerContext), betterBlockPos.a, betterBlockPos.b, betterBlockPos.c);
    }
    
    default boolean a(final cn cn, final int n, final int n2, final int n3) {
        return b(cn, n, n2, n3, cn.a(n, n2, n3));
    }
    
    default boolean b(final cn cn, final int n, final int n2, final int n3, final awt awt) {
        final aow u;
        if ((u = awt.u()) == aox.a) {
            return true;
        }
        if (u == aox.ab || u == aox.bS || u == aox.G || u == aox.bF || u == aox.bN || u instanceof att || u instanceof aur) {
            return false;
        }
        if (u instanceof aqa || u instanceof aqp) {
            return u != aox.aA;
        }
        if (u == aox.cy) {
            return b(cn, n, n2 - 1, n3);
        }
        if (u instanceof atw) {
            return !cn.a(n, n3) || ((int)awt.c((axj)atw.a) < 3 && b(cn, n, n2 - 1, n3));
        }
        if (a(n, n2, n3, awt, cn)) {
            return false;
        }
        if (u instanceof aru) {
            final awt a;
            return !baritone.a.a().assumeWalkOnWater.value && !((a = cn.a(n, n2 + 1, n3)).u() instanceof aru) && !(a.u() instanceof auy) && (u == aox.j || u == aox.i);
        }
        return u.b((amy)null, (et)null);
    }
    
    default boolean a(final ad ad, final int n, final int n2, final int n3) {
        return a(ad.a(n, n2, n3));
    }
    
    default boolean a(final awt awt) {
        final aow u;
        return (u = awt.u()) == aox.a || (u != aox.ab && u != aox.bS && u != aox.G && u != aox.bn && u != aox.au && u != aox.bN && !(u instanceof aqa) && !(u instanceof aqp) && !(u instanceof atw) && !(u instanceof aru) && !(u instanceof aur) && !(u instanceof aqh) && !(u instanceof att) && u.b((amy)null, (et)null));
    }
    
    default boolean a(final int n, final int n2, final awt awt, final cn cn) {
        final aow u;
        if ((u = awt.u()) == aox.a || b(u)) {
            return true;
        }
        if (u instanceof atw) {
            return !cn.a(n, n2) || (int)awt.c((axj)atw.a) == 1;
        }
        if (u instanceof aqb) {
            final aqb$b aqb$b;
            return (aqb$b = (aqb$b)awt.c((axj)aqb.a)) == aqb$b.d || aqb$b == aqb$b.c;
        }
        return awt.a().j();
    }
    
    default boolean a(final IPlayerContext playerContext, final et et, et et2) {
        if (et2.equals((Object)et)) {
            return false;
        }
        final awt a;
        if (!((a = cn.a(playerContext, et)).u() instanceof aqa)) {
            return true;
        }
        final awt awt = a;
        final et et3 = et2;
        final axf b = aqa.b;
        et2 = et3;
        final awt awt2 = awt;
        if (!et2.equals((Object)et)) {
            final fa$a k = ((fa)awt2.c((axj)arm.D)).k();
            final boolean booleanValue = (boolean)awt2.c((axj)b);
            fa$a fa$a;
            if (et2.c().equals((Object)et) || et2.d().equals((Object)et)) {
                fa$a = fa$a.c;
            }
            else {
                if (!et2.f().equals((Object)et) && !et2.e().equals((Object)et)) {
                    return true;
                }
                fa$a = fa$a.a;
            }
            if (k == fa$a == booleanValue) {
                return true;
            }
        }
        return false;
    }
    
    default boolean b(final IPlayerContext playerContext, final et et, final et et2) {
        final awt a;
        return !et2.equals((Object)et) && (!((a = cn.a(playerContext, et)).u() instanceof aqp) || (boolean)a.c((axj)aqp.a));
    }
    
    default boolean a(final aow aow) {
        return aow instanceof aru || aow instanceof aqe || aow == aox.df || aow == aox.aK || aow == aox.ab || aow == aox.bF || aow == aox.G;
    }
    
    default boolean c(final cn cn, final int n, final int n2, final int n3, final awt awt) {
        final aow u;
        if ((u = awt.u()) == aox.a || u == aox.df) {
            return false;
        }
        if (awt.k()) {
            return true;
        }
        if (u == aox.au || (u == aox.bn && baritone.a.a().allowVines.value)) {
            return true;
        }
        if (u == aox.ak || u == aox.da) {
            return true;
        }
        if (u == aox.bQ || u == aox.ae) {
            return true;
        }
        if (b(u)) {
            final aow u2;
            if ((u2 = cn.a(n, n2 + 1, n3).u()) == aox.bx || u2 == aox.cy) {
                return true;
            }
            if (a(n, n2, n3, awt, cn) || u == aox.i) {
                return b(u2) && !baritone.a.a().assumeWalkOnWater.value;
            }
            return b(u2) ^ baritone.a.a().assumeWalkOnWater.value;
        }
        else {
            if (u == aox.w || u == aox.cG) {
                return true;
            }
            if (u instanceof arf) {
                return baritone.a.a().allowWalkOnBottomSlab.value || ((arf)u).e() || awt.c((axj)arf.a) != arf$a.b;
            }
            return u instanceof aud;
        }
    }
    
    default boolean b(final IPlayerContext playerContext, final BetterBlockPos betterBlockPos) {
        return b(new cn(playerContext), betterBlockPos.a, betterBlockPos.b, betterBlockPos.c);
    }
    
    default boolean b(final cn cn, final int n, final int n2, final int n3) {
        return c(cn, n, n2, n3, cn.a(n, n2, n3));
    }
    
    default boolean c(final cn cn, final int n, final int n2, final int n3) {
        final awt a;
        return (a = cn.a(n, n2, n3)).k() || a.b() || a.u() == aox.w || a.u() == aox.cG;
    }
    
    default boolean a(final IPlayerContext playerContext, final et et) {
        return c(new cn(playerContext), et.p(), et.q(), et.r());
    }
    
    default double a(final ad ad, final int n, final int n2, final int n3, final boolean b) {
        return a(ad, n, n2, n3, ad.a(n, n2, n3), b);
    }
    
    default double a(final ad ad, final int n, final int n2, final int n3, awt a, final boolean b) {
        final aow u = a.u();
        if (b(ad.a, n, n2, n3, a)) {
            return 0.0;
        }
        final double b2;
        if ((b2 = ad.b(n, n2, n3)) >= 1000000.0) {
            return 1000000.0;
        }
        if (a(ad.a, n, n2, n3, a)) {
            return 1000000.0;
        }
        if (u instanceof aru) {
            return 1000000.0;
        }
        final double n4 = aox.ai.equals(u) ? 10.0 : 1.0;
        final double a2;
        if ((a2 = ad.a.a(a)) <= 0.0) {
            return 1000000.0;
        }
        double n5 = (n4 / a2 + ad.c) * b2;
        if (b && (a = ad.a(n, n2 + 1, n3)).u() instanceof aqm) {
            n5 += a(ad, n, n2 + 1, n3, a, true);
        }
        return n5;
    }
    
    default boolean b(final awt awt) {
        return awt.u() instanceof arf && !((arf)awt.u()).e() && awt.c((axj)arf.a) == arf$a.b;
    }
    
    default void a(final IPlayerContext playerContext, final awt awt) {
        a(playerContext, awt, new da(playerContext.player()));
    }
    
    default void a(final IPlayerContext playerContext, final awt awt, final da da) {
        playerContext.player().bv.d = da.a(awt.u());
    }
    
    default void a(final IPlayerContext playerContext, final ah ah, final et et) {
        ah.a = new ai(new Rotation(RotationUtils.calcRotationFromVec3d(playerContext.playerHead(), VecUtils.getBlockPosCenter(et), playerContext.playerRotations()).getYaw(), playerContext.player().w), false);
        ah.a(Input.MOVE_FORWARD, true);
    }
    
    default boolean b(final aow aow) {
        return aow == aox.i || aow == aox.j;
    }
    
    default boolean b(final IPlayerContext playerContext, final et et) {
        return b(cn.a(playerContext, et));
    }
    
    default boolean c(final aow aow) {
        return aow == aox.k || aow == aox.l;
    }
    
    default boolean c(final IPlayerContext playerContext, final et et) {
        return cn.a(playerContext, et) instanceof aru;
    }
    
    default boolean c(final awt awt) {
        return awt.u() instanceof aru && (int)awt.c((axj)aru.b) != 0;
    }
    
    default boolean a(final int n, final int n2, final int n3, final awt awt, final cn cn) {
        return awt.u() instanceof aru && ((int)awt.c((axj)aru.b) != 0 || (c(cn.a(n + 1, n2, n3)) || c(cn.a(n - 1, n2, n3)) || c(cn.a(n, n2, n3 + 1)) || c(cn.a(n, n2, n3 - 1))));
    }
    
    default int a(final ah ah, final IBaritone baritone, final et et, final boolean b) {
        final IPlayerContext playerContext;
        final Optional<Rotation> reachable = RotationUtils.reachable(playerContext = baritone.getPlayerContext(), et);
        boolean b2 = false;
        if (reachable.isPresent()) {
            ah.a = new ai(reachable.get(), true);
            b2 = true;
        }
        for (int i = 0; i < 5; ++i) {
            final et a = et.a(ae.a[i]);
            if (a(playerContext, a)) {
                if (!((a)baritone).a.a(et.p(), et.q(), et.r())) {
                    cr.a.a("bb pls get me some blocks. dirt or cobble");
                    ah.a = MovementStatus.UNREACHABLE;
                    return ag.c;
                }
                final Rotation calcRotationFromVec3d = RotationUtils.calcRotationFromVec3d(playerContext.playerHead(), new bhe((et.p() + a.p() + 1.0) * 0.5, (et.q() + a.q() + 1.0) * 0.5, (et.r() + a.r() + 1.0) * 0.5), playerContext.playerRotations());
                final bhc rayTraceTowards;
                if ((rayTraceTowards = RayTraceUtils.rayTraceTowards((vg)playerContext.player(), calcRotationFromVec3d, playerContext.playerController().getBlockReachDistance())) != null && rayTraceTowards.a == bhc$a.b && rayTraceTowards.a().equals((Object)a) && rayTraceTowards.a().a(rayTraceTowards.b).equals((Object)et)) {
                    ah.a = new ai(calcRotationFromVec3d, true);
                    b2 = true;
                    if (!b) {
                        break;
                    }
                }
            }
        }
        if (playerContext.getSelectedBlock().isPresent()) {
            final et et2 = playerContext.getSelectedBlock().get();
            final fa b3 = playerContext.objectMouseOver().b;
            if (et2.equals((Object)et) || (a(playerContext, et2) && et2.a(b3).equals((Object)et))) {
                return ag.a;
            }
        }
        if (b2) {
            return ag.b;
        }
        return ag.c;
    }
}
