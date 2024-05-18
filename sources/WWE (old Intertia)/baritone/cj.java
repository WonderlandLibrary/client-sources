package baritone;

import baritone.api.event.listener.*;
import baritone.api.*;
import baritone.api.event.events.*;
import baritone.api.utils.*;
import baritone.api.pathing.goals.*;

public final class cj implements AbstractGameEventListener, cr
{
    public static final cj a;
    private static final et a;
    private static final Goal a;
    
    public static void a() {
        if (!cj.a) {
            return;
        }
        System.out.println("Optimizing Game Settings");
        final bid t;
        (t = cj.a.t).i = 20;
        t.K = 0;
        t.aH = 2;
        t.D = 128;
        t.E = 128;
        t.F = false;
        t.M = false;
        t.G = 0.0f;
        t.l = 0;
        t.j = 0;
        t.k = false;
        t.S = cib.f;
        t.av = true;
        t.aD = 30.0f;
    }
    
    @Override
    public final void onTick(final TickEvent tickEvent) {
        final IPlayerContext playerContext = BaritoneAPI.getProvider().getPrimaryBaritone().getPlayerContext();
        if (cj.a.m instanceof blr) {
            System.out.println("Beginning Baritone automatic test routine");
            cj.a.a((blk)null);
            cj.a.a("BaritoneAutoTest", "BaritoneAutoTest", new amx(-928872506371745L, ams.a("survival"), true, false, amz.b));
        }
        if (cj.a.F() != null && cj.a.F().d[0] != null) {
            cj.a.F().d[0].A(cj.a);
            cj.a.F().d[0].W().a("spawnRadius", "0");
        }
        if (tickEvent.getType() == TickEvent$Type.IN) {
            if (cj.a.E() && !cj.a.F().a()) {
                cj.a.F().a(ams.a("survival"), false);
            }
            if (tickEvent.getCount() < 200) {
                System.out.println("Waiting for world to generate... " + tickEvent.getCount());
                return;
            }
            if (tickEvent.getCount() % 100 == 0) {
                System.out.println(playerContext.playerFeet() + " " + tickEvent.getCount());
            }
            if (!BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().isActive()) {
                BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath(cj.a);
            }
            if (cj.a.isInGoal(playerContext.playerFeet())) {
                System.out.println("Successfully pathed to " + playerContext.playerFeet() + " in " + tickEvent.getCount() + " ticks");
                cj.a.n();
            }
            if (tickEvent.getCount() > 3300) {
                throw new IllegalStateException("took too long");
            }
        }
    }
    
    private cj() {
        super();
    }
    
    static {
        a = new cj();
        cj.a = "true".equals(System.getenv("BARITONE_AUTO_TEST"));
        a = new et(0, 65, 0);
        a = new GoalBlock(69, 69, 420);
    }
}
