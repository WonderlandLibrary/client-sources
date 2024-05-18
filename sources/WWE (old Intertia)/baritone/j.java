package baritone;

import baritone.api.behavior.*;
import java.util.concurrent.*;
import baritone.api.pathing.calc.*;
import baritone.api.process.*;
import baritone.api.*;
import java.util.*;
import baritone.api.utils.interfaces.*;
import baritone.api.pathing.goals.*;
import baritone.api.event.events.*;
import baritone.api.pathing.path.*;
import java.util.function.*;
import baritone.api.utils.*;

public final class j extends b implements IPathingBehavior, cr
{
    public bq a;
    public bq b;
    private Goal a;
    public ad a;
    private boolean b;
    private boolean c;
    private boolean d;
    private boolean e;
    public boolean a;
    private aa a;
    private final Object b;
    public final Object a;
    private boolean f;
    private et a;
    private final LinkedBlockingQueue<PathEvent> a;
    
    public j(final a a) {
        super(a);
        this.b = new Object();
        this.a = new Object();
        this.a = new LinkedBlockingQueue<PathEvent>();
    }
    
    private void a(final PathEvent pathEvent) {
        this.a.add(pathEvent);
    }
    
    private void c() {
        final ArrayList<PathEvent> list = new ArrayList<PathEvent>();
        this.a.drainTo(list);
        this.a = list.contains(PathEvent.CALC_FAILED);
        final Iterator<PathEvent> iterator = list.iterator();
        while (iterator.hasNext()) {
            this.a.getGameEventHandler().onPathEvent(iterator.next());
        }
    }
    
    @Override
    public final void onTick(final TickEvent tickEvent) {
        this.c();
        if (tickEvent.getType() == TickEvent$Type.OUT) {
            this.d();
            this.a.a.a();
            return;
        }
        this.a = this.a();
        final cw a;
        final cw cw = a = this.a.a;
        cw.a = cw.b;
        a.b = null;
        final j a2 = a.a.a;
        final cw cw2 = a;
        cw2.a = cw2.a();
        if (a.a == null) {
            a2.b();
            a2.a = null;
        }
        else {
            if (a.b != a.a && a.a.commandType != PathingCommandType.REQUEST_PAUSE) {
                a2.b();
            }
            switch (cy.a[a.a.commandType.ordinal()]) {
                case 1: {
                    a2.c = true;
                    break;
                }
                case 2: {
                    a2.a = a.a.goal;
                    a2.b();
                    break;
                }
                case 3: {
                    if (!a2.isPathing() && !a2.getInProgress().isPresent()) {
                        a2.a(a.a);
                        break;
                    }
                    break;
                }
                case 4: {
                    if (!a2.isPathing() && !a2.getInProgress().isPresent()) {
                        a2.a(a.a);
                        break;
                    }
                    break;
                }
                case 5: {
                    if (a.a.goal != null) {
                        a.a.a.a(a.a);
                        break;
                    }
                    break;
                }
                default: {
                    throw new IllegalStateException();
                }
            }
        }
        Label_1314: {
            if (this.c && this.b) {
                this.c = false;
                if (this.d) {
                    this.a.a.clearAllKeys();
                    this.a.a.a.a();
                }
                this.d = false;
            }
            else {
                this.d = true;
                if (this.e) {
                    this.e = false;
                    this.a.a.clearAllKeys();
                }
                synchronized (this.a) {
                    synchronized (this.b) {
                        if (this.a != null) {
                            final BetterBlockPos a3 = this.a.a();
                            final Optional<IPath> bestPathSoFar = this.a.bestPathSoFar();
                            if ((this.a == null || !this.a.getPath().getDest().equals(a3)) && !a3.equals(this.a.playerFeet()) && !a3.equals(this.a) && (!bestPathSoFar.isPresent() || (!bestPathSoFar.get().positions().contains(this.a.playerFeet()) && !bestPathSoFar.get().positions().contains(this.a)))) {
                                this.a.a();
                                this.a = null;
                            }
                        }
                    }
                    final j j;
                    if (j.a != null) {
                        final j i = j;
                        i.b = i.a.a();
                        if (!j.a.a) {
                            final bq a4 = j.a;
                            if (a4.a < a4.a.length()) {
                                if (j.b && j.b != null) {
                                    final bq b;
                                    boolean b2;
                                    if (!(b = j.b).a.player().z && !(b.a.world().o((et)b.a.playerFeet()).u() instanceof aru)) {
                                        b2 = false;
                                    }
                                    else if (b.a.player().t < -0.1) {
                                        b2 = false;
                                    }
                                    else {
                                        final int index;
                                        if ((index = b.a.positions().indexOf(b.a.playerFeet())) == -1) {
                                            b2 = false;
                                        }
                                        else {
                                            b.a = index;
                                            b.a();
                                            b2 = true;
                                        }
                                    }
                                    if (b2) {
                                        j.a("Splicing into planned next path early...");
                                        j.a(PathEvent.SPLICING_ONTO_NEXT_EARLY);
                                        final j k = j;
                                        k.a = k.b;
                                        j.b = null;
                                        j.a.a();
                                        break Label_1314;
                                    }
                                }
                                final j l = j;
                                l.a = l.a.a(j.b);
                                if (j.b != null && j.a.getPath().getDest().equals(j.b.getPath().getDest())) {
                                    j.b = null;
                                }
                                synchronized (j.b) {
                                    if (j.a == null) {
                                        if (j.b == null) {
                                            if (j.a != null && !j.a.isInGoal(j.a.getPath().getDest())) {
                                                if (j.ticksRemainingInSegment(false).get() < baritone.a.a().planningTickLookahead.value) {
                                                    j.a("Path almost over. Planning ahead...");
                                                    j.a(PathEvent.NEXT_SEGMENT_CALC_STARTED);
                                                    final j m = j;
                                                    m.a(m.a.getPath().getDest(), false, j.a);
                                                }
                                            }
                                        }
                                    }
                                }
                                break Label_1314;
                            }
                        }
                        j.a = null;
                        if (j.a == null || j.a.isInGoal(j.a.playerFeet())) {
                            j.a("All done. At " + j.a);
                            j.a(PathEvent.AT_GOAL);
                            j.b = null;
                            if (baritone.a.a().disconnectOnArrival.value) {
                                j.a.world().O();
                            }
                        }
                        else {
                            if (j.b != null && !j.b.getPath().positions().contains(j.a.playerFeet()) && !j.b.getPath().positions().contains(j.a)) {
                                j.a("Discarding next path as it does not contain current position");
                                j.a(PathEvent.DISCARD_NEXT);
                                j.b = null;
                            }
                            if (j.b != null) {
                                j.a("Continuing on to planned next path");
                                j.a(PathEvent.CONTINUING_ONTO_PLANNED_NEXT);
                                final j j2 = j;
                                j2.a = j2.b;
                                j.b = null;
                                j.a.a();
                            }
                            else {
                                synchronized (j.b) {
                                    if (j.a != null) {
                                        j.a(PathEvent.PATH_FINISHED_NEXT_STILL_CALCULATING);
                                    }
                                    else {
                                        j.a(PathEvent.CALC_STARTED);
                                        final j j3 = j;
                                        j3.a(j3.a, true, j.a);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        this.c();
    }
    
    @Override
    public final void onPlayerSprintState(final SprintStateEvent sprintStateEvent) {
        if (this.a != null) {
            sprintStateEvent.setState(this.a.b);
        }
    }
    
    @Override
    public final void onPlayerUpdate(final PlayerUpdateEvent playerUpdateEvent) {
        if (this.a != null) {
            switch (k.a[playerUpdateEvent.getState().ordinal()]) {
                case 1: {
                    this.f = j.a.t.R;
                    j.a.t.R = false;
                }
                case 2: {
                    j.a.t.R = this.f;
                    break;
                }
            }
        }
    }
    
    public final boolean a(final PathingCommand pathingCommand) {
        this.a = pathingCommand.goal;
        if (pathingCommand instanceof cv) {
            this.a = ((cv)pathingCommand).a;
        }
        else {
            this.a = new ad(this.a, true);
        }
        if (this.a == null) {
            return false;
        }
        if (this.a.isInGoal(this.a.playerFeet()) || this.a.isInGoal(this.a)) {
            return false;
        }
        synchronized (this.a) {
            if (this.a != null) {
                return false;
            }
            synchronized (this.b) {
                if (this.a != null) {
                    return false;
                }
                this.a(PathEvent.CALC_STARTED);
                this.a(this.a, true, this.a);
                return true;
            }
        }
    }
    
    @Override
    public final Goal getGoal() {
        return this.a;
    }
    
    @Override
    public final Optional<aa> getInProgress() {
        return Optional.ofNullable(this.a);
    }
    
    public final boolean a() {
        return this.a == null || this.b;
    }
    
    private boolean b() {
        if (this.a()) {
            this.d();
            return true;
        }
        return false;
    }
    
    @Override
    public final boolean cancelEverything() {
        final boolean a;
        if (a = this.a()) {
            this.d();
        }
        this.a.a.a();
        return a;
    }
    
    public final void a() {
        synchronized (this.a) {
            this.getInProgress().ifPresent(aa::a);
            if (!this.a()) {
                return;
            }
            this.a = null;
            this.b = null;
        }
        this.e = true;
    }
    
    private void d() {
        this.a(PathEvent.CANCELED);
        synchronized (this.a) {
            this.getInProgress().ifPresent(aa::a);
            if (this.a != null) {
                this.a = null;
                this.b = null;
                this.a.a.clearAllKeys();
                this.a.a.a.a();
            }
        }
    }
    
    public final void b() {
        this.cancelEverything();
        this.d();
        synchronized (this.b) {
            this.a = null;
        }
    }
    
    public final BetterBlockPos a() {
        final BetterBlockPos playerFeet = this.a.playerFeet();
        if (!af.b(this.a, playerFeet.down())) {
            if (this.a.player().z) {
                final double p = this.a.player().p;
                final double r = this.a.player().r;
                final ArrayList<BetterBlockPos> list = new ArrayList<BetterBlockPos>();
                for (int i = -1; i <= 1; ++i) {
                    for (int j = -1; j <= 1; ++j) {
                        list.add(new BetterBlockPos(playerFeet.a + i, playerFeet.b, playerFeet.c + j));
                    }
                }
                list.sort(Comparator.comparingDouble(j::a));
                for (int k = 0; k < 4; ++k) {
                    final BetterBlockPos betterBlockPos;
                    final double abs = Math.abs((betterBlockPos = list.get(k)).a + 0.5 - p);
                    final double abs2 = Math.abs(betterBlockPos.c + 0.5 - r);
                    if ((abs <= 0.8 || abs2 <= 0.8) && af.b(this.a, betterBlockPos.down()) && af.a(this.a, betterBlockPos) && af.a(this.a, betterBlockPos.up())) {
                        return betterBlockPos;
                    }
                }
            }
            else if (af.b(this.a, playerFeet.down().down())) {
                return playerFeet.down();
            }
        }
        return playerFeet;
    }
    
    private void a(final et et, final boolean b, final ad ad) {
        if (!Thread.holdsLock(this.b)) {
            throw new IllegalStateException("Must be called with synchronization on pathCalcLock");
        }
        if (this.a != null) {
            throw new IllegalStateException("Already doing it");
        }
        if (!ad.a) {
            throw new IllegalStateException("Improper context thread safety level");
        }
        final Goal a;
        if ((a = this.a) == null) {
            this.a("no goal");
            return;
        }
        long n;
        long n2;
        if (this.a == null) {
            n = baritone.a.a().primaryTimeoutMS.value;
            n2 = baritone.a.a().failureTimeoutMS.value;
        }
        else {
            n = baritone.a.a().planAheadPrimaryTimeoutMS.value;
            n2 = baritone.a.a().planAheadFailureTimeoutMS.value;
        }
        final aa a2;
        if (!Objects.equals((a2 = a(et, a, (this.a == null) ? null : this.a.getPath(), ad)).getGoal(), a)) {
            this.a("Simplifying " + a.getClass() + " to GoalXZ due to distance");
        }
        this.a = a2;
        baritone.a.a().execute(this::a);
    }
    
    private static aa a(final et et, final Goal goal, final IPath path, final ad ad) {
        Goal goal2 = goal;
        if (baritone.a.a().simplifyUnloadedYCoord.value && goal instanceof IGoalRenderPos) {
            final et goalPos = ((IGoalRenderPos)goal).getGoalPos();
            if (!ad.a.a(goalPos.p(), goalPos.r())) {
                goal2 = new GoalXZ(goalPos.p(), goalPos.r());
            }
        }
        return new z(et.p(), et.q(), et.r(), goal2, new dg(ad.a.getPlayerContext(), path, ad), ad);
    }
    
    @Override
    public final void onRenderPass(final RenderEvent renderEvent) {
        cu.a(renderEvent, this);
    }
    
    @Override
    public final IPathExecutor getNext() {
        return this.b;
    }
    
    @Override
    public final IPathExecutor getCurrent() {
        return this.a;
    }
    
    private void a(final boolean b, final et et, final Goal goal, final aa aa, final long n, final long n2) {
        if (b) {
            this.a("Starting to search for path from " + et + " to " + goal);
        }
        final PathCalculationResult calculate = aa.calculate(n, n2);
        synchronized (this.a) {
            final Optional<Object> map = calculate.getPath().map((Function<? super IPath, ?>)this::a);
            if (this.a == null) {
                if (map.isPresent()) {
                    if (map.get().getPath().positions().contains(this.a)) {
                        this.a(PathEvent.CALC_FINISHED_NOW_EXECUTING);
                        this.a = map.get();
                    }
                    else {
                        this.a("Warning: discarding orphan path segment with incorrect start");
                    }
                }
                else if (calculate.getType() != PathCalculationResult$Type.CANCELLATION && calculate.getType() != PathCalculationResult$Type.EXCEPTION) {
                    this.a(PathEvent.CALC_FAILED);
                }
            }
            else if (this.b == null) {
                if (map.isPresent()) {
                    if (map.get().getPath().getSrc().equals(this.a.getPath().getDest())) {
                        this.a(PathEvent.NEXT_SEGMENT_CALC_FINISHED);
                        this.b = map.get();
                    }
                    else {
                        this.a("Warning: discarding orphan next segment with incorrect start");
                    }
                }
                else {
                    this.a(PathEvent.NEXT_CALC_FAILED);
                }
            }
            else {
                this.b("Warning: PathingBehaivor illegal state! Discarding invalid path!");
            }
            if (b && this.a != null && this.a.getPath() != null) {
                if (goal == null || goal.isInGoal(this.a.getPath().getDest())) {
                    this.a("Finished finding a path from " + et + " to " + goal + ". " + this.a.getPath().getNumNodesConsidered() + " nodes considered");
                }
                else {
                    this.a("Found path segment from " + et + " towards " + goal + ". " + this.a.getPath().getNumNodesConsidered() + " nodes considered");
                }
            }
            synchronized (this.b) {
                this.a = null;
            }
        }
    }
    
    private bq a(final IPath path) {
        return new bq(this, path);
    }
    
    private static double a(final double n, final double n2, final BetterBlockPos betterBlockPos) {
        return (betterBlockPos.a + 0.5 - n) * (betterBlockPos.a + 0.5 - n) + (betterBlockPos.c + 0.5 - n2) * (betterBlockPos.c + 0.5 - n2);
    }
}
