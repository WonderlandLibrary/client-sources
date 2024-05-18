package baritone;

import baritone.api.*;
import java.util.function.*;
import baritone.api.process.*;
import java.util.stream.*;
import java.util.*;
import baritone.api.pathing.goals.*;
import baritone.api.utils.*;

public final class ch extends ck implements IMineProcess
{
    private List<aow> a;
    private List<et> b;
    private List<et> c;
    private et a;
    private GoalRunAway a;
    private int a;
    private int b;
    
    public ch(final a a) {
        super(a);
    }
    
    @Override
    public final boolean isActive() {
        return this.a != null;
    }
    
    @Override
    public final PathingCommand onTick(boolean b, final boolean b2) {
        if (this.a > 0) {
            final ain a = this.a.get(0).a(this.a.get(0).t(), new Random(), 0);
            final int sum = this.a.player().bv.a.stream().filter(ch::a).mapToInt(aip::E).sum();
            System.out.println("Currently have " + sum + " " + a);
            if (sum >= this.a) {
                this.b("Have " + sum + " " + a.b(new aip(a, 1)));
                this.cancel();
                return null;
            }
        }
        if (b && !this.b.isEmpty() && baritone.a.a().blacklistClosestOnFailure.value) {
            this.b("Unable to find any path to " + this.a + ", blacklisting presumably unreachable closest instance...");
            this.b.stream().sorted(Comparator.comparingDouble((ToDoubleFunction<? super Object>)this.a.player()::c)).findFirst().ifPresent(this.c::add);
            this.b.removeIf(this.c::contains);
            b = false;
        }
        if (b) {
            this.b("Unable to find any path to " + this.a + ", canceling Mine");
            this.cancel();
            return null;
        }
        final int intValue;
        if ((intValue = baritone.a.a().mineGoalUpdateInterval.value) != 0 && this.b++ % intValue == 0) {
            baritone.a.a().execute(this::b);
        }
        if (baritone.a.a().legitMine.value) {
            this.a();
        }
        final boolean booleanValue = baritone.a.a().legitMine.value;
        final List<et> b3;
        PathingCommand pathingCommand2;
        PathingCommand pathingCommand;
        if (!(b3 = this.b).isEmpty()) {
            final List<et> b4 = b(new ad(this.a), new ArrayList<et>(b3), this.a, this.c);
            final GoalComposite goalComposite = new GoalComposite((Goal[])b4.stream().map((Function<? super Object, ?>)this::a).toArray(ch::a));
            this.b = b4;
            pathingCommand = (pathingCommand2 = new PathingCommand(goalComposite, booleanValue ? PathingCommandType.FORCE_REVALIDATE_GOAL_AND_PATH : PathingCommandType.REVALIDATE_GOAL_AND_PATH));
        }
        else {
            pathingCommand = (pathingCommand2 = null);
        }
        final PathingCommand pathingCommand3 = pathingCommand2;
        if (pathingCommand == null) {
            this.cancel();
            return null;
        }
        return pathingCommand3;
    }
    
    @Override
    public final void onLostControl() {
        this.mine(0, (aow[])null);
    }
    
    @Override
    public final String displayName0() {
        return "Mine " + this.a;
    }
    
    private void a(final List<et> list, final ad ad) {
        if (this.a == null) {
            return;
        }
        if (baritone.a.a().legitMine.value) {
            return;
        }
        final List<et> a;
        (a = a(ad, this.a, list, this.c)).addAll(a(this.a, this.a.world()));
        if (a.isEmpty()) {
            this.b("No locations for " + this.a + " known, cancelling");
            this.cancel();
            return;
        }
        this.b = a;
    }
    
    private static List<et> a(final List<aow> list, final amu amu) {
        if (!baritone.a.a().mineScanDroppedItems.value) {
            return new ArrayList<et>();
        }
        final HashSet<ain> set = new HashSet<ain>();
        for (final aow aow2 : list) {
            final aow aow = aow2;
            final ain a = aow.a(aow.t(), new Random(), 0);
            final ain a2 = ain.a(aow2);
            set.add(a);
            set.add(a2);
        }
        final ArrayList<et> list2 = new ArrayList<et>();
        final Iterator<vg> iterator2 = (Iterator<vg>)amu.e.iterator();
        while (iterator2.hasNext()) {
            final vg vg;
            if ((vg = iterator2.next()) instanceof acl && set.contains(((acl)vg).k().c())) {
                list2.add(new et(vg));
            }
        }
        return list2;
    }
    
    public static List<et> a(final ad ad, final List<aow> list, final List<et> list2, final List<et> list3) {
        final ArrayList<Object> list4 = new ArrayList<Object>();
        ArrayList<aow> list5 = new ArrayList<aow>();
        for (final aow aow : list) {
            if (l.a.contains(aow)) {
                list4.addAll(ad.a.getCachedWorld().getLocationsOf(p.a(aow), baritone.a.a().maxCachedWorldScanCount.value, ad.a.getPlayerContext().playerFeet().p(), ad.a.getPlayerContext().playerFeet().r(), 2));
            }
            else {
                list5.add(aow);
            }
        }
        final List<et> b;
        if ((b = b(ad, (List<et>)list4, list, list3)).isEmpty()) {
            list5 = (ArrayList<aow>)list;
        }
        if (!list5.isEmpty()) {
            b.addAll(x.a.scanChunkRadius(ad.a.getPlayerContext(), list5, 64, 10, 32));
        }
        b.addAll(list2);
        return b(ad, b, list, list3);
    }
    
    private void a() {
        this.b.addAll(a(this.a, this.a.world()));
        final BetterBlockPos playerFeet = this.a.playerFeet();
        final cn cn = new cn(this.a);
        for (int i = playerFeet.p() - 10; i <= playerFeet.p() + 10; ++i) {
            for (int j = playerFeet.q() - 10; j <= playerFeet.q() + 10; ++j) {
                for (int k = playerFeet.r() - 10; k <= playerFeet.r() + 10; ++k) {
                    if (this.a.contains(cn.a(i, j, k).u())) {
                        final et et = new et(i, j, k);
                        if ((baritone.a.a().legitMineIncludeDiagonals.value && this.b.stream().anyMatch((Predicate<? super Object>)ch::a)) || RotationUtils.reachable(this.a.player(), et, 20.0).isPresent()) {
                            this.b.add(et);
                        }
                    }
                }
            }
        }
        this.b = b(new ad(this.a), this.b, this.a, this.c);
    }
    
    private static List<et> b(final ad ad, final List<et> list, final List<aow> list2, final List<et> list3) {
        final List<et> a;
        (a = a(list2, ad.a)).removeIf(ch::a);
        final List<? super Object> list4;
        if ((list4 = list.stream().distinct().filter(ch::a).filter(ch::a).filter(ch::a).sorted(Comparator.comparingDouble((ToDoubleFunction<? super Object>)ad.a.getPlayerContext().playerFeet()::n)).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList())).size() > 64) {
            return (List<et>)list4.subList(0, 64);
        }
        return (List<et>)list4;
    }
    
    private static boolean a(final cn cn, final et et) {
        return !af.a(cn, et.p(), et.q(), et.r(), cn.a(et)) && (cn.a(et.a()).u() != aox.h || cn.a(et.b()).u() != aox.h);
    }
    
    @Override
    public final void mineByName(final int n, final String... array) {
        this.mine(n, (aow[])((array == null || array.length == 0) ? null : ((aow[])Arrays.stream(array).map((Function<? super String, ?>)p::a).toArray(ch::a))));
    }
    
    @Override
    public final void mine(final int a, final aow... array) {
        this.a = ((array == null || array.length == 0) ? null : Arrays.asList(array));
        this.a = a;
        this.b = new ArrayList<et>();
        this.c = new ArrayList<et>();
        this.a = null;
        this.a = null;
        if (this.a != null) {
            this.a(new ArrayList<et>(), new ad(this.a));
        }
    }
    
    private static aow[] a(final int n) {
        return new aow[n];
    }
    
    private static boolean a(final List list, final et et) {
        return !list.contains(et);
    }
    
    private static boolean a(final ad ad, final et et) {
        return a(ad.a, et);
    }
    
    private static boolean a(final ad ad, final List list, final List list2, final et et) {
        return !ad.a.a(et.p(), et.r()) || list.contains(ad.a(et.p(), et.q(), et.r())) || list2.contains(et);
    }
    
    private static boolean a(final List list, final List list2, final ad ad, final et et) {
        final Iterator<et> iterator = list.iterator();
        while (iterator.hasNext()) {
            final et et2;
            if ((et2 = iterator.next()).n((fq)et) <= 9.0 && list2.contains(ad.a(et2.p(), et2.q(), et2.r())) && a(ad.a, et2)) {
                return true;
            }
        }
        return false;
    }
    
    private static boolean a(final et et, final et et2) {
        return et2.n((fq)et) <= 2.0;
    }
    
    private static Goal[] a(final int n) {
        return new Goal[n];
    }
    
    private Goal a(final List list, et et) {
        final IPlayerContext a = this.a;
        et = et;
        final IPlayerContext playerContext = a;
        if (!baritone.a.a().forceInternalMining.value) {
            return new GoalTwoBlocks(et);
        }
        final boolean b = list.contains(et.a()) || (baritone.a.a().internalMiningAirException.value && cn.a(playerContext, et.a()) == aox.a);
        if (b == (list.contains(et.b()) || (baritone.a.a().internalMiningAirException.value && cn.a(playerContext, et.b()) == aox.a))) {
            return new GoalTwoBlocks(et);
        }
        if (b) {
            return new GoalBlock(et);
        }
        return new GoalBlock(et.b());
    }
    
    private void b(final List list, final ad ad) {
        this.a(list, ad);
    }
    
    private static boolean a(final ain ain, final aip aip) {
        return ain.equals(aip.c());
    }
}
