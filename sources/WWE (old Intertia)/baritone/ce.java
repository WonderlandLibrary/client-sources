package baritone;

import baritone.api.process.*;
import baritone.api.pathing.goals.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public final class ce extends ck implements IFollowProcess
{
    private Predicate<vg> a;
    private List<vg> a;
    
    public ce(final a a) {
        super(a);
    }
    
    @Override
    public final PathingCommand onTick(final boolean b, final boolean b2) {
        this.a();
        return new PathingCommand(new GoalComposite((Goal[])this.a.stream().map((Function<? super Object, ?>)this::a).toArray(ce::a)), PathingCommandType.REVALIDATE_GOAL_AND_PATH);
    }
    
    private Goal a(final vg vg) {
        et et;
        if (baritone.a.a().followOffsetDistance.value == 0.0) {
            et = new et(vg);
        }
        else {
            final GoalXZ fromDirection = GoalXZ.fromDirection(vg.d(), baritone.a.a().followOffsetDirection.value, baritone.a.a().followOffsetDistance.value);
            et = new et((double)fromDirection.getX(), vg.q, (double)fromDirection.getZ());
        }
        return new GoalNear(et, baritone.a.a().followRadius.value);
    }
    
    private boolean a(final vg vg) {
        return vg != null && !vg.F && !vg.equals((Object)this.a.player()) && this.a.world().e.contains(vg);
    }
    
    private void a() {
        this.a = Stream.of((List[])new List[] { this.a.world().e, this.a.world().i }).flatMap((Function<? super List, ? extends Stream<?>>)Collection::stream).filter((Predicate<? super Object>)this::a).filter((Predicate<? super Object>)this.a).distinct().collect((Collector<? super Object, ?, List<vg>>)Collectors.toCollection((Supplier<R>)ArrayList::new));
    }
    
    @Override
    public final boolean isActive() {
        if (this.a == null) {
            return false;
        }
        this.a();
        return !this.a.isEmpty();
    }
    
    @Override
    public final void onLostControl() {
        this.a = null;
        this.a = null;
    }
    
    @Override
    public final String displayName0() {
        return "Following " + this.a;
    }
    
    @Override
    public final void follow(final Predicate<vg> a) {
        this.a = a;
    }
    
    @Override
    public final List<vg> following() {
        return this.a;
    }
    
    @Override
    public final Predicate<vg> currentFilter() {
        return this.a;
    }
    
    private static Goal[] a(final int n) {
        return new Goal[n];
    }
}
