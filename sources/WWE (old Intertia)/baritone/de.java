package baritone;

import baritone.api.utils.*;
import java.util.*;
import java.util.function.*;

public final class de
{
    final int a;
    final int b;
    final int c;
    final double a;
    final int d;
    private final int e;
    
    private de(final et et, final double n, final int n2) {
        this(et.p(), et.q(), et.r(), n, n2);
    }
    
    private de(final int a, final int b, final int c, final double a2, final int d) {
        super();
        this.a = a;
        this.b = b;
        this.c = c;
        this.a = a2;
        this.d = d;
        this.e = d * d;
    }
    
    public static List<de> a(final IPlayerContext playerContext) {
        if (!a.a().avoidance.value) {
            return Collections.emptyList();
        }
        final ArrayList<de> list = new ArrayList<de>();
        final double doubleValue = a.a().mobSpawnerAvoidanceCoefficient.value;
        final double doubleValue2 = a.a().mobAvoidanceCoefficient.value;
        if (doubleValue != 1.0) {
            playerContext.worldData().getCachedWorld().getLocationsOf("mob_spawner", 1, playerContext.playerFeet().a, playerContext.playerFeet().c, 2).forEach((Consumer<? super et>)de::a);
        }
        if (doubleValue2 != 1.0) {
            playerContext.world().e.stream().filter(de::a).forEach(de::a);
        }
        return list;
    }
    
    private static void a(final List list, final double n, final vg vg) {
        list.add(new de(new et(vg), n, a.a().mobAvoidanceRadius.value));
    }
    
    private static boolean a(final vg vg) {
        return vg instanceof ade;
    }
    
    private static void a(final List list, final double n, final et et) {
        list.add(new de(et, n, a.a().mobSpawnerAvoidanceRadius.value));
    }
}
