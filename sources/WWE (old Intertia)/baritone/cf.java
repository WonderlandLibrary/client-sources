package baritone;

import baritone.api.*;
import baritone.api.process.*;
import java.util.function.*;
import baritone.api.pathing.goals.*;
import baritone.api.utils.*;
import baritone.api.utils.input.*;
import java.util.*;

public final class cf extends ck implements IGetToBlockProcess
{
    private aow a;
    private List<et> a;
    private List<et> b;
    private et a;
    private int a;
    
    public cf(final a a) {
        super(a);
        this.a = 0;
    }
    
    @Override
    public final void getToBlock(final aow a) {
        this.onLostControl();
        this.a = a;
        this.a = this.a.playerFeet();
        this.b = new ArrayList<et>();
        this.a(new ArrayList<et>(), new ad(this.a));
    }
    
    @Override
    public final boolean isActive() {
        return this.a != null;
    }
    
    @Override
    public final synchronized PathingCommand onTick(final boolean b, final boolean b2) {
        if (this.a == null) {
            this.a(new ArrayList<et>(), new ad(this.a));
        }
        if (this.a.isEmpty()) {
            if (baritone.a.a().exploreForBlocks.value) {
                return new PathingCommand(new cg(this, new et[] { this.a }), PathingCommandType.FORCE_REVALIDATE_GOAL_AND_PATH);
            }
            this.b("No known locations of " + this.a + ", canceling GetToBlock");
            if (b2) {
                this.onLostControl();
            }
            return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
        }
        else {
            final GoalComposite goalComposite = new GoalComposite((Goal[])this.a.stream().map((Function<? super Object, ?>)this::a).toArray(cf::a));
            if (!b) {
                final int intValue;
                if ((intValue = baritone.a.a().mineGoalUpdateInterval.value) != 0 && this.a++ % intValue == 0) {
                    baritone.a.a().execute(this::b);
                }
                if (goalComposite.isInGoal(this.a.playerFeet()) && goalComposite.isInGoal(this.a.a.a()) && b2) {
                    if (!a(this.a)) {
                        this.onLostControl();
                        return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
                    }
                    if (this.a()) {
                        this.onLostControl();
                        return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
                    }
                }
                return new PathingCommand(goalComposite, PathingCommandType.REVALIDATE_GOAL_AND_PATH);
            }
            if (baritone.a.a().blacklistClosestOnFailure.value) {
                this.b("Unable to find any path to " + this.a + ", blacklisting presumably unreachable closest instances...");
                this.a();
                return this.onTick(false, b2);
            }
            this.b("Unable to find any path to " + this.a + ", canceling GetToBlock");
            if (b2) {
                this.onLostControl();
            }
            return new PathingCommand(goalComposite, PathingCommandType.CANCEL_AND_SET_GOAL);
        }
    }
    
    private synchronized void a() {
        final ArrayList<Object> list = new ArrayList<Object>();
        this.a.stream().min(Comparator.comparingDouble((ToDoubleFunction<? super Object>)this.a.player()::c)).ifPresent(list::add);
    Label_0058:
        while (true) {
            for (final et et : this.a) {
                for (final et et2 : list) {
                    final et et3 = et;
                    final et et4 = et2;
                    final et et5 = et3;
                    if (Math.abs(et3.p() - et4.p()) + Math.abs(et5.q() - et4.q()) + Math.abs(et5.r() - et4.r()) == 1) {
                        list.add(et);
                        this.a.remove(et);
                        continue Label_0058;
                    }
                }
            }
            break;
        }
        list.size();
        this.a("Blacklisting unreachable locations ".concat(String.valueOf(list)));
        this.b.addAll((Collection<? extends et>)list);
    }
    
    @Override
    public final synchronized void onLostControl() {
        this.a = null;
        this.a = null;
        this.a = null;
        this.b = null;
        this.a.a.clearAllKeys();
    }
    
    @Override
    public final String displayName0() {
        if (this.a.isEmpty()) {
            return "Exploring randomly to find " + this.a + ", no known locations";
        }
        return "Get To " + this.a + ", " + this.a.size() + " known locations";
    }
    
    private synchronized void a(final List<et> list, final ad ad) {
        final List<et> a;
        (a = ch.a(ad, Collections.singletonList(this.a), list, this.b)).removeIf(this.b::contains);
        this.a = a;
    }
    
    private Goal a(final et et) {
        final aow a = this.a;
        if (baritone.a.a().enterPortal.value && a == aox.aY) {
            return new GoalTwoBlocks(et);
        }
        final aow a2;
        if (a(a2 = this.a) && (a2 == aox.bQ || a2 == aox.ae || a2 == aox.cg) && this.a.a.a(et.a()).k()) {
            return new GoalBlock(et.a());
        }
        return new GoalGetToBlock(et);
    }
    
    private boolean a() {
        final Iterator<et> iterator = this.a.iterator();
        while (iterator.hasNext()) {
            final Optional<Rotation> reachable;
            if ((reachable = RotationUtils.reachable(this.a.player(), iterator.next(), this.a.playerController().getBlockReachDistance())).isPresent()) {
                this.a.a.updateTarget(reachable.get(), true);
                if (this.a.contains(this.a.getSelectedBlock().orElse(null))) {
                    this.a.a.setInputForceState(Input.CLICK_RIGHT, true);
                    System.out.println(this.a.player().by);
                    if (!(this.a.player().by instanceof agi)) {
                        return true;
                    }
                }
                return false;
            }
        }
        this.b("Arrived but failed to right click open");
        return true;
    }
    
    private static boolean a(final aow aow) {
        return baritone.a.a().rightClickContainerOnArrival.value && (aow == aox.ai || aow == aox.al || aow == aox.bQ || aow == aox.ae || aow == aox.cg);
    }
    
    private void b(final List list, final ad ad) {
        this.a(list, ad);
    }
    
    private static Goal[] a(final int n) {
        return new Goal[n];
    }
}
