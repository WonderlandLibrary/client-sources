package baritone;

import baritone.api.process.*;
import java.util.*;
import java.util.function.*;
import baritone.api.cache.*;
import baritone.api.pathing.goals.*;

public final class cd extends ck
{
    public et a;
    
    public cd(final a a) {
        super(a);
    }
    
    @Override
    public final boolean isActive() {
        return this.a != null;
    }
    
    @Override
    public final PathingCommand onTick(final boolean b, final boolean b2) {
        if (b) {
            this.b("Failed");
            this.onLostControl();
            return null;
        }
        final Goal[] a;
        if ((a = this.a(this.a)) == null) {
            this.a("awaiting region load from disk");
            return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
        }
        System.out.println("Closest uncached: ".concat(String.valueOf(a)));
        return new PathingCommand(new GoalComposite(a), PathingCommandType.FORCE_REVALIDATE_GOAL_AND_PATH);
    }
    
    private Goal[] a(final et et) {
        final int n = et.p() >> 4;
        final int n2 = et.r() >> 4;
        final ICachedWorld cachedWorld = this.a.a.a.getCachedWorld();
        int n3 = 0;
        while (true) {
            final ArrayList<Object> list = new ArrayList<Object>();
            for (int i = -n3; i <= n3; ++i) {
                for (int j = -n3; j <= n3; ++j) {
                    int n7;
                    if (baritone.a.a().exploreUsePythagorean.value) {
                        final int n4 = i;
                        final int n5 = n4 * n4;
                        final int n6 = j;
                        n7 = n5 + n6 * n6;
                    }
                    else {
                        n7 = Math.abs(i) + Math.abs(j);
                    }
                    if (n7 == n3) {
                        final int n8 = (n + i << 4) + 8;
                        final int n9 = (n2 + j) * 18 + 8;
                        if (!cachedWorld.isCached(n8, n9)) {
                            if (!((n)cachedWorld).a(n8, n9)) {
                                baritone.a.a().execute(cd::a);
                                return null;
                            }
                            list.add(new et(n8, 0, n9));
                        }
                    }
                }
            }
            if (!list.isEmpty()) {
                return list.stream().map((Function<? super Object, ?>)cd::a).toArray(cd::a);
            }
            ++n3;
        }
    }
    
    @Override
    public final void onLostControl() {
        this.a = null;
    }
    
    @Override
    public final String displayName0() {
        return "Exploring around " + this.a + ", currently going to " + new GoalComposite(this.a(this.a));
    }
    
    private static Goal[] a(final int n) {
        return new Goal[n];
    }
    
    private static GoalXZ a(final et et) {
        return new GoalXZ(et.p(), et.r());
    }
    
    private static void a(final ICachedWorld cachedWorld, final int n, final int n2) {
        ((n)cachedWorld).b(n >> 9, n2 >> 9);
    }
}
