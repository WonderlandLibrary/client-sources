/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.optics;

import com.mojang.datafixers.FunctionType;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.optics.Optics;
import com.mojang.datafixers.optics.profunctors.Cartesian;
import com.mojang.datafixers.optics.profunctors.ReCocartesian;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.function.Function;

public interface Forget<R, A, B>
extends App2<Mu<R>, A, B> {
    public static <R, A, B> Forget<R, A, B> unbox(App2<Mu<R>, A, B> app2) {
        return (Forget)app2;
    }

    public R run(A var1);

    public static final class Instance<R>
    implements Cartesian<com.mojang.datafixers.optics.Forget$Mu<R>, Mu<R>>,
    ReCocartesian<com.mojang.datafixers.optics.Forget$Mu<R>, Mu<R>>,
    App<Mu<R>, com.mojang.datafixers.optics.Forget$Mu<R>> {
        @Override
        public <A, B, C, D> FunctionType<App2<com.mojang.datafixers.optics.Forget$Mu<R>, A, B>, App2<com.mojang.datafixers.optics.Forget$Mu<R>, C, D>> dimap(Function<C, A> function, Function<B, D> function2) {
            return arg_0 -> Instance.lambda$dimap$1(function, arg_0);
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.Forget$Mu<R>, Pair<A, C>, Pair<B, C>> first(App2<com.mojang.datafixers.optics.Forget$Mu<R>, A, B> app2) {
            return Optics.forget(arg_0 -> Instance.lambda$first$2(app2, arg_0));
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.Forget$Mu<R>, Pair<C, A>, Pair<C, B>> second(App2<com.mojang.datafixers.optics.Forget$Mu<R>, A, B> app2) {
            return Optics.forget(arg_0 -> Instance.lambda$second$3(app2, arg_0));
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.Forget$Mu<R>, A, B> unleft(App2<com.mojang.datafixers.optics.Forget$Mu<R>, Either<A, C>, Either<B, C>> app2) {
            return Optics.forget(arg_0 -> Instance.lambda$unleft$4(app2, arg_0));
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.Forget$Mu<R>, A, B> unright(App2<com.mojang.datafixers.optics.Forget$Mu<R>, Either<C, A>, Either<C, B>> app2) {
            return Optics.forget(arg_0 -> Instance.lambda$unright$5(app2, arg_0));
        }

        private static Object lambda$unright$5(App2 app2, Object object) {
            return Forget.unbox(app2).run(Either.right(object));
        }

        private static Object lambda$unleft$4(App2 app2, Object object) {
            return Forget.unbox(app2).run(Either.left(object));
        }

        private static Object lambda$second$3(App2 app2, Pair pair) {
            return Forget.unbox(app2).run(pair.getSecond());
        }

        private static Object lambda$first$2(App2 app2, Pair pair) {
            return Forget.unbox(app2).run(pair.getFirst());
        }

        private static App2 lambda$dimap$1(Function function, App2 app2) {
            return Optics.forget(arg_0 -> Instance.lambda$null$0(app2, function, arg_0));
        }

        private static Object lambda$null$0(App2 app2, Function function, Object object) {
            return Forget.unbox(app2).run(function.apply(object));
        }

        public static final class Mu<R>
        implements Cartesian.Mu,
        ReCocartesian.Mu {
        }
    }

    public static final class Mu<R>
    implements K2 {
    }
}

