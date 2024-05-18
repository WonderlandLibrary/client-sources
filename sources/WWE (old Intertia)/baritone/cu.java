package baritone;

import baritone.api.event.events.*;
import baritone.api.*;
import java.awt.*;
import java.util.function.*;
import baritone.api.pathing.calc.*;
import baritone.api.utils.*;
import java.util.*;
import baritone.api.utils.interfaces.*;
import org.lwjgl.opengl.*;
import baritone.api.pathing.goals.*;

public final class cu implements cr
{
    private static final bve a;
    private static final buk a;
    
    public static void a(final RenderEvent renderEvent, final j j) {
        final float partialTicks = renderEvent.getPartialTicks();
        final Goal goal = j.getGoal();
        if (cu.a.m instanceof cq) {
            ((cq)cu.a.m).a();
        }
        if (j.a.getPlayerContext().world().s.q().a() != BaritoneAPI.getProvider().getPrimaryBaritone().getPlayerContext().world().s.q().a()) {
            return;
        }
        final vg aa;
        if ((aa = cu.a.aa()).l != BaritoneAPI.getProvider().getPrimaryBaritone().getPlayerContext().world()) {
            System.out.println("I have no idea what's going on");
            System.out.println("The primary baritone is in a different world than the render view entity");
            System.out.println("Not rendering the path");
            return;
        }
        if (goal != null && baritone.a.a().renderGoal.value) {
            a(aa, goal, partialTicks, baritone.a.a().colorGoalBox.value);
        }
        if (!baritone.a.a().renderPath.value) {
            return;
        }
        final bq a = j.a;
        final bq b = j.b;
        if (a != null && a.getPath() != null) {
            a(a.getPath(), Math.max(a.getPosition() - 3, 0), baritone.a.a().colorCurrentPath.value, baritone.a.a().fadePath.value);
        }
        if (b != null && b.getPath() != null) {
            a(b.getPath(), 0, baritone.a.a().colorNextPath.value, baritone.a.a().fadePath.value);
        }
        if (a != null) {
            a(aa, (Collection<et>)Collections.unmodifiableSet((Set<?>)a.a), baritone.a.a().colorBlocksToBreak.value);
            a(aa, (Collection<et>)Collections.unmodifiableSet((Set<?>)a.b), baritone.a.a().colorBlocksToPlace.value);
            a(aa, (Collection<et>)Collections.unmodifiableSet((Set<?>)a.c), baritone.a.a().colorBlocksToWalkInto.value);
        }
        j.getInProgress().ifPresent((Consumer<? super aa>)cu::a);
    }
    
    private static void a(final IPath path, int n, final Color color, final boolean b) {
        bus.m();
        bus.a(770, 771, 1, 0);
        bus.c(color.getColorComponents(null)[0], color.getColorComponents(null)[1], color.getColorComponents(null)[2], 0.4f);
        bus.d((float)baritone.a.a().pathRenderLineWidthPixels.value);
        bus.z();
        bus.a(false);
        if (baritone.a.a().renderPathIgnoreDepth.value) {
            bus.j();
        }
        final List<BetterBlockPos> positions = path.positions();
        final bve a = bve.a();
        final int n2 = n + 10;
        final int n3 = n + 20;
        for (int i = n; i < positions.size() - 1; i = n) {
            final BetterBlockPos betterBlockPos = positions.get(i);
            n = i + 1;
            BetterBlockPos betterBlockPos2;
            for (int n4 = (betterBlockPos2 = positions.get(n)).a - betterBlockPos.a, n5 = betterBlockPos2.b - betterBlockPos.b, n6 = betterBlockPos2.c - betterBlockPos.c; n + 1 < positions.size() && (!b || n + 1 < n2) && n4 == positions.get(n + 1).a - betterBlockPos2.a && n5 == positions.get(n + 1).b - betterBlockPos2.b && n6 == positions.get(n + 1).c - betterBlockPos2.c; ++n, betterBlockPos2 = positions.get(n)) {}
            final double n7 = betterBlockPos.a;
            final double n8 = betterBlockPos.b;
            final double n9 = betterBlockPos.c;
            final double n10 = betterBlockPos2.a;
            final double n11 = betterBlockPos2.b;
            final double n12 = betterBlockPos2.c;
            if (b) {
                float n13;
                if (i <= n2) {
                    n13 = 0.4f;
                }
                else {
                    if (i > n3) {
                        break;
                    }
                    n13 = 0.4f * (1.0f - (i - n2) / (float)(n3 - n2));
                }
                bus.c(color.getColorComponents(null)[0], color.getColorComponents(null)[1], color.getColorComponents(null)[2], n13);
            }
            a(n7, n8, n9, n10, n11, n12);
            a.b();
        }
        if (baritone.a.a().renderPathIgnoreDepth.value) {
            bus.k();
        }
        bus.a(true);
        bus.y();
        bus.l();
    }
    
    private static void a(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        final double h = cu.a.ac().h;
        final double i = cu.a.ac().i;
        final double j = cu.a.ac().j;
        cu.a.a(3, cdy.e);
        cu.a.b(n + 0.5 - h, n2 + 0.5 - i, n3 + 0.5 - j).d();
        cu.a.b(n4 + 0.5 - h, n5 + 0.5 - i, n6 + 0.5 - j).d();
        cu.a.b(n4 + 0.5 - h, n5 + 0.53 - i, n6 + 0.5 - j).d();
        cu.a.b(n + 0.5 - h, n2 + 0.53 - i, n3 + 0.5 - j).d();
        cu.a.b(n + 0.5 - h, n2 + 0.5 - i, n3 + 0.5 - j).d();
    }
    
    public static void a(final vg vg, final Collection<et> collection, final Color color) {
        bus.m();
        bus.a(770, 771, 1, 0);
        bus.c(color.getColorComponents(null)[0], color.getColorComponents(null)[1], color.getColorComponents(null)[2], 0.4f);
        bus.d((float)baritone.a.a().pathRenderLineWidthPixels.value);
        bus.z();
        bus.a(false);
        if (baritone.a.a().renderSelectionBoxesIgnoreDepth.value) {
            bus.j();
        }
        collection.forEach(cu::a);
        if (baritone.a.a().renderSelectionBoxesIgnoreDepth.value) {
            bus.k();
        }
        bus.a(true);
        bus.y();
        bus.l();
    }
    
    public static void a(bhb d) {
        d = d.b(0.0020000000949949026, 0.0020000000949949026, 0.0020000000949949026).d(-cu.a.ac().h, -cu.a.ac().i, -cu.a.ac().j);
        cu.a.a(3, cdy.e);
        cu.a.b(d.a, d.b, d.c).d();
        cu.a.b(d.d, d.b, d.c).d();
        cu.a.b(d.d, d.b, d.f).d();
        cu.a.b(d.a, d.b, d.f).d();
        cu.a.b(d.a, d.b, d.c).d();
        cu.a.b();
        cu.a.a(3, cdy.e);
        cu.a.b(d.a, d.e, d.c).d();
        cu.a.b(d.d, d.e, d.c).d();
        cu.a.b(d.d, d.e, d.f).d();
        cu.a.b(d.a, d.e, d.f).d();
        cu.a.b(d.a, d.e, d.c).d();
        cu.a.b();
        cu.a.a(1, cdy.e);
        cu.a.b(d.a, d.b, d.c).d();
        cu.a.b(d.a, d.e, d.c).d();
        cu.a.b(d.d, d.b, d.c).d();
        cu.a.b(d.d, d.e, d.c).d();
        cu.a.b(d.d, d.b, d.f).d();
        cu.a.b(d.d, d.e, d.f).d();
        cu.a.b(d.a, d.b, d.f).d();
        cu.a.b(d.a, d.e, d.f).d();
        cu.a.b();
    }
    
    private static void a(final vg vg, Goal goal, final float n, final Color color) {
        final double h = cu.a.ac().h;
        final double i = cu.a.ac().i;
        final double j = cu.a.ac().j;
        double n2;
        double n3;
        double n4;
        double n5;
        double n7;
        double n8;
        double n10;
        double n9;
        if (goal instanceof IGoalRenderPos) {
            final et goalPos;
            n2 = (goalPos = ((IGoalRenderPos)goal).getGoalPos()).p() + 0.002 - h;
            n3 = goalPos.p() + 1 - 0.002 - h;
            n4 = goalPos.r() + 0.002 - j;
            n5 = goalPos.r() + 1 - 0.002 - j;
            double n6 = rk.b((float)(System.nanoTime() / 100000L % 20000L / 20000.0f * 3.141592653589793 * 2.0));
            if (goal instanceof GoalGetToBlock || goal instanceof GoalTwoBlocks) {
                n6 /= 2.0;
            }
            n7 = n6 + 1.0 + goalPos.q() - i;
            n8 = 1.0 - n6 + goalPos.q() - i;
            n9 = (n10 = goalPos.q() - i) + 2.0;
            if (goal instanceof GoalGetToBlock || goal instanceof GoalTwoBlocks) {
                n7 -= 0.5;
                n8 -= 0.5;
                --n9;
            }
        }
        else if (goal instanceof GoalXZ) {
            final GoalXZ goalXZ = (GoalXZ)goal;
            if (baritone.a.a().renderGoalXZBeacon.value) {
                GL11.glPushAttrib(64);
                cu.a.N().a(bwv.a);
                if (baritone.a.a().renderGoalIgnoreDepth.value) {
                    bus.j();
                }
                bwv.a(goalXZ.getX() - h, -i, goalXZ.getZ() - j, (double)n, 1.0, (double)vg.l.R(), 0, 256, color.getColorComponents(null));
                if (baritone.a.a().renderGoalIgnoreDepth.value) {
                    bus.k();
                }
                GL11.glPopAttrib();
                return;
            }
            n2 = goalXZ.getX() + 0.002 - h;
            n3 = goalXZ.getX() + 1 - 0.002 - h;
            n4 = goalXZ.getZ() + 0.002 - j;
            n5 = goalXZ.getZ() + 1 - 0.002 - j;
            n7 = 0.0;
            n8 = 0.0;
            n10 = 0.0 - i;
            n9 = 256.0 - i;
        }
        else {
            if (goal instanceof GoalComposite) {
                Goal[] goals;
                for (int length = (goals = ((GoalComposite)goal).goals()).length, k = 0; k < length; ++k) {
                    goal = goals[k];
                    a(vg, goal, n, color);
                }
            }
            return;
        }
        bus.m();
        bus.a(770, 771, 1, 0);
        bus.c(color.getColorComponents(null)[0], color.getColorComponents(null)[1], color.getColorComponents(null)[2], 0.6f);
        bus.d((float)baritone.a.a().goalRenderLineWidthPixels.value);
        bus.z();
        bus.a(false);
        if (baritone.a.a().renderGoalIgnoreDepth.value) {
            bus.j();
        }
        a(n2, n3, n4, n5, n7);
        a(n2, n3, n4, n5, n8);
        cu.a.a(1, cdy.e);
        cu.a.b(n2, n10, n4).d();
        cu.a.b(n2, n9, n4).d();
        cu.a.b(n3, n10, n4).d();
        cu.a.b(n3, n9, n4).d();
        cu.a.b(n3, n10, n5).d();
        cu.a.b(n3, n9, n5).d();
        cu.a.b(n2, n10, n5).d();
        cu.a.b(n2, n9, n5).d();
        cu.a.b();
        if (baritone.a.a().renderGoalIgnoreDepth.value) {
            bus.k();
        }
        bus.a(true);
        bus.y();
        bus.l();
    }
    
    private static void a(final double n, final double n2, final double n3, final double n4, final double n5) {
        if (n5 != 0.0) {
            cu.a.a(2, cdy.e);
            cu.a.b(n, n5, n3).d();
            cu.a.b(n2, n5, n3).d();
            cu.a.b(n2, n5, n4).d();
            cu.a.b(n, n5, n4).d();
            cu.a.b();
        }
    }
    
    private static void a(final cn cn, final vg vg, final et et) {
        final awt a;
        bhb bhb;
        if ((a = cn.a(et)).u().equals(aox.a)) {
            bhb = aox.d.t().c(vg.l, et);
        }
        else {
            bhb = a.c(vg.l, et);
        }
        a(bhb);
    }
    
    private static void a(final vg vg, final float n, final aa aa) {
        aa.bestPathSoFar().ifPresent(cu::b);
        aa.pathToMostRecentNodeConsidered().ifPresent(cu::a);
    }
    
    private static void a(final vg vg, final float n, final IPath path) {
        a(path, 0, baritone.a.a().colorMostRecentConsidered.value, baritone.a.a().fadePath.value);
        a(vg, (Collection<et>)Collections.singletonList(path.getDest()), baritone.a.a().colorMostRecentConsidered.value);
    }
    
    private static void b(final vg vg, final float n, final IPath path) {
        a(path, 0, baritone.a.a().colorBestPathSoFar.value, baritone.a.a().fadePath.value);
    }
    
    static {
        a = (a = bve.a()).c();
    }
}
