package baritone;

import baritone.api.pathing.goals.*;
import baritone.api.pathing.calc.*;
import java.util.*;
import java.util.function.*;
import baritone.api.utils.*;

public final class dk
{
    private final BetterBlockPos a;
    private final Goal a;
    private final ad a;
    
    private dk(final BetterBlockPos a, final Goal a2, final ad a3) {
        super();
        this.a = a;
        this.a = a2;
        this.a = a3;
    }
    
    private void a(final int n, final int n2) {
        final BetterBlockPos betterBlockPos = new BetterBlockPos(n, 64, n2);
        final n n3 = (n)this.a.a.getPlayerContext().worldData().getCachedWorld();
        for (int i = 0; i < 4; ++i) {
            final BetterBlockPos offset = betterBlockPos.offset(fa.b(i), 16);
            n3.b(offset.a >> 9, offset.c >> 9);
        }
    }
    
    public static void a(final BetterBlockPos betterBlockPos, final Goal goal, final ad ad, final Consumer<IPath> consumer, final Runnable runnable) {
        a.a().execute(dk::b);
    }
    
    private static void b(final BetterBlockPos betterBlockPos, final Goal goal, final ad ad, final Consumer consumer, final Runnable runnable) {
        Optional<Object> empty;
        try {
            final dk dk = new dk(betterBlockPos, goal, ad);
            Object o = Optional.empty();
            Optional<Object> optional = null;
        Label_0259:
            while (true) {
                final dk dk2 = dk;
                final Object o2 = o;
                final dk dk3 = dk2;
                final BetterBlockPos betterBlockPos2 = ((Optional<IPath>)o2).map((Function<? super IPath, ? extends BetterBlockPos>)IPath::getDest).orElse(dk3.a);
                final PathCalculationResult calculate = new z(betterBlockPos2.a, betterBlockPos2.b, betterBlockPos2.c, dk3.a, new dg(((Optional<IPath>)o2).orElse(null), dk3.a), dk3.a).calculate(a.a().primaryTimeoutMS.value, a.a().failureTimeoutMS.value);
                switch (dl.a[calculate.getType().ordinal()]) {
                    case 1:
                    case 2: {
                        final IPath path = calculate.getPath().get();
                        final IPath path2 = ((Optional<Object>)o).map((Function<? super Object, ? extends IPath>)baritone.dk::a).orElse(path);
                        dk.a(path2.getDest().p(), path2.getDest().r());
                        o = Optional.of(path2);
                        if (calculate.getType() == PathCalculationResult$Type.SUCCESS_TO_GOAL) {
                            optional = (Optional<Object>)o;
                            break Label_0259;
                        }
                        continue;
                    }
                    case 3:
                    case 4: {
                        optional = (Optional<Object>)o;
                        break Label_0259;
                    }
                    default: {
                        throw new IllegalStateException();
                    }
                }
            }
            empty = optional;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            empty = Optional.empty();
        }
        if (empty.isPresent()) {
            consumer.accept(empty.get());
            return;
        }
        runnable.run();
    }
    
    private static IPath a(final IPath path, final IPath path2) {
        return br.a(path2, path, true).get();
    }
}
