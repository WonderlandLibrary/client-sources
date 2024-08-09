/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.optics;

import com.mojang.datafixers.FunctionType;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.optics.Optics;
import com.mojang.datafixers.optics.profunctors.AffineP;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.function.Function;

interface ReForgetP<R, A, B>
extends App2<Mu<R>, A, B> {
    public static <R, A, B> ReForgetP<R, A, B> unbox(App2<Mu<R>, A, B> app2) {
        return (ReForgetP)app2;
    }

    public B run(A var1, R var2);

    public static final class Instance<R>
    implements AffineP<com.mojang.datafixers.optics.ReForgetP$Mu<R>, Mu<R>>,
    App<Mu<R>, com.mojang.datafixers.optics.ReForgetP$Mu<R>> {
        @Override
        public <A, B, C, D> FunctionType<App2<com.mojang.datafixers.optics.ReForgetP$Mu<R>, A, B>, App2<com.mojang.datafixers.optics.ReForgetP$Mu<R>, C, D>> dimap(Function<C, A> function, Function<B, D> function2) {
            return arg_0 -> Instance.lambda$dimap$1(function, function2, arg_0);
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.ReForgetP$Mu<R>, Either<A, C>, Either<B, C>> left(App2<com.mojang.datafixers.optics.ReForgetP$Mu<R>, A, B> app2) {
            return Optics.reForgetP("left", (arg_0, arg_1) -> Instance.lambda$left$3(app2, arg_0, arg_1));
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.ReForgetP$Mu<R>, Either<C, A>, Either<C, B>> right(App2<com.mojang.datafixers.optics.ReForgetP$Mu<R>, A, B> app2) {
            return Optics.reForgetP("right", (arg_0, arg_1) -> Instance.lambda$right$5(app2, arg_0, arg_1));
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.ReForgetP$Mu<R>, Pair<A, C>, Pair<B, C>> first(App2<com.mojang.datafixers.optics.ReForgetP$Mu<R>, A, B> app2) {
            return Optics.reForgetP("first", (arg_0, arg_1) -> Instance.lambda$first$6(app2, arg_0, arg_1));
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.ReForgetP$Mu<R>, Pair<C, A>, Pair<C, B>> second(App2<com.mojang.datafixers.optics.ReForgetP$Mu<R>, A, B> app2) {
            return Optics.reForgetP("second", (arg_0, arg_1) -> Instance.lambda$second$7(app2, arg_0, arg_1));
        }

        private static Pair lambda$second$7(App2 app2, Pair pair, Object object) {
            return Pair.of(pair.getFirst(), ReForgetP.unbox(app2).run(pair.getSecond(), object));
        }

        private static Pair lambda$first$6(App2 app2, Pair pair, Object object) {
            return Pair.of(ReForgetP.unbox(app2).run(pair.getFirst(), object), pair.getSecond());
        }

        private static Either lambda$right$5(App2 app2, Either either, Object object) {
            return either.mapRight(arg_0 -> Instance.lambda$null$4(app2, object, arg_0));
        }

        private static Object lambda$null$4(App2 app2, Object object, Object object2) {
            return ReForgetP.unbox(app2).run(object2, object);
        }

        private static Either lambda$left$3(App2 app2, Either either, Object object) {
            return either.mapLeft(arg_0 -> Instance.lambda$null$2(app2, object, arg_0));
        }

        private static Object lambda$null$2(App2 app2, Object object, Object object2) {
            return ReForgetP.unbox(app2).run(object2, object);
        }

        private static App2 lambda$dimap$1(Function function, Function function2, App2 app2) {
            return Optics.reForgetP("dimap", (arg_0, arg_1) -> Instance.lambda$null$0(function, app2, function2, arg_0, arg_1));
        }

        private static Object lambda$null$0(Function function, App2 app2, Function function2, Object object, Object object2) {
            Object r = function.apply(object);
            Object b = ReForgetP.unbox(app2).run(r, object2);
            Object r2 = function2.apply(b);
            return r2;
        }

        static final class Mu<R>
        implements AffineP.Mu {
            Mu() {
            }
        }
    }

    public static final class Mu<R>
    implements K2 {
    }
}

