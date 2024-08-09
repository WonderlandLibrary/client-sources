/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.optics;

import com.mojang.datafixers.FunctionType;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.optics.Optics;
import com.mojang.datafixers.optics.profunctors.Cocartesian;
import com.mojang.datafixers.optics.profunctors.ReCartesian;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.function.Function;

interface ReForget<R, A, B>
extends App2<Mu<R>, A, B> {
    public static <R, A, B> ReForget<R, A, B> unbox(App2<Mu<R>, A, B> app2) {
        return (ReForget)app2;
    }

    public B run(R var1);

    public static final class Instance<R>
    implements ReCartesian<com.mojang.datafixers.optics.ReForget$Mu<R>, Mu<R>>,
    Cocartesian<com.mojang.datafixers.optics.ReForget$Mu<R>, Mu<R>>,
    App<Mu<R>, com.mojang.datafixers.optics.ReForget$Mu<R>> {
        @Override
        public <A, B, C, D> FunctionType<App2<com.mojang.datafixers.optics.ReForget$Mu<R>, A, B>, App2<com.mojang.datafixers.optics.ReForget$Mu<R>, C, D>> dimap(Function<C, A> function, Function<B, D> function2) {
            return arg_0 -> Instance.lambda$dimap$1(function2, arg_0);
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.ReForget$Mu<R>, A, B> unfirst(App2<com.mojang.datafixers.optics.ReForget$Mu<R>, Pair<A, C>, Pair<B, C>> app2) {
            return Optics.reForget(arg_0 -> Instance.lambda$unfirst$2(app2, arg_0));
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.ReForget$Mu<R>, A, B> unsecond(App2<com.mojang.datafixers.optics.ReForget$Mu<R>, Pair<C, A>, Pair<C, B>> app2) {
            return Optics.reForget(arg_0 -> Instance.lambda$unsecond$3(app2, arg_0));
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.ReForget$Mu<R>, Either<A, C>, Either<B, C>> left(App2<com.mojang.datafixers.optics.ReForget$Mu<R>, A, B> app2) {
            return Optics.reForget(arg_0 -> Instance.lambda$left$4(app2, arg_0));
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.ReForget$Mu<R>, Either<C, A>, Either<C, B>> right(App2<com.mojang.datafixers.optics.ReForget$Mu<R>, A, B> app2) {
            return Optics.reForget(arg_0 -> Instance.lambda$right$5(app2, arg_0));
        }

        private static Either lambda$right$5(App2 app2, Object object) {
            return Either.right(ReForget.unbox(app2).run(object));
        }

        private static Either lambda$left$4(App2 app2, Object object) {
            return Either.left(ReForget.unbox(app2).run(object));
        }

        private static Object lambda$unsecond$3(App2 app2, Object object) {
            return ((Pair)ReForget.unbox(app2).run(object)).getSecond();
        }

        private static Object lambda$unfirst$2(App2 app2, Object object) {
            return ((Pair)ReForget.unbox(app2).run(object)).getFirst();
        }

        private static App2 lambda$dimap$1(Function function, App2 app2) {
            return Optics.reForget(arg_0 -> Instance.lambda$null$0(function, app2, arg_0));
        }

        private static Object lambda$null$0(Function function, App2 app2, Object object) {
            return function.apply(ReForget.unbox(app2).run(object));
        }

        static final class Mu<R>
        implements ReCartesian.Mu,
        Cocartesian.Mu {
            Mu() {
            }
        }
    }

    public static final class Mu<R>
    implements K2 {
    }
}

