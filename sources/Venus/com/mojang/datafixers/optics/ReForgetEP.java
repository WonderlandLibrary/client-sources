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

interface ReForgetEP<R, A, B>
extends App2<Mu<R>, A, B> {
    public static <R, A, B> ReForgetEP<R, A, B> unbox(App2<Mu<R>, A, B> app2) {
        return (ReForgetEP)app2;
    }

    public B run(Either<A, Pair<A, R>> var1);

    public static final class Instance<R>
    implements AffineP<com.mojang.datafixers.optics.ReForgetEP$Mu<R>, Mu<R>>,
    App<Mu<R>, com.mojang.datafixers.optics.ReForgetEP$Mu<R>> {
        @Override
        public <A, B, C, D> FunctionType<App2<com.mojang.datafixers.optics.ReForgetEP$Mu<R>, A, B>, App2<com.mojang.datafixers.optics.ReForgetEP$Mu<R>, C, D>> dimap(Function<C, A> function, Function<B, D> function2) {
            return arg_0 -> Instance.lambda$dimap$2(function, function2, arg_0);
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.ReForgetEP$Mu<R>, Either<A, C>, Either<B, C>> left(App2<com.mojang.datafixers.optics.ReForgetEP$Mu<R>, A, B> app2) {
            ReForgetEP<R, A, B> reForgetEP = ReForgetEP.unbox(app2);
            return Optics.reForgetEP("left", arg_0 -> Instance.lambda$left$7(reForgetEP, arg_0));
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.ReForgetEP$Mu<R>, Either<C, A>, Either<C, B>> right(App2<com.mojang.datafixers.optics.ReForgetEP$Mu<R>, A, B> app2) {
            ReForgetEP<R, A, B> reForgetEP = ReForgetEP.unbox(app2);
            return Optics.reForgetEP("right", arg_0 -> Instance.lambda$right$12(reForgetEP, arg_0));
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.ReForgetEP$Mu<R>, Pair<A, C>, Pair<B, C>> first(App2<com.mojang.datafixers.optics.ReForgetEP$Mu<R>, A, B> app2) {
            ReForgetEP<R, A, B> reForgetEP = ReForgetEP.unbox(app2);
            return Optics.reForgetEP("first", arg_0 -> Instance.lambda$first$15(reForgetEP, arg_0));
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.ReForgetEP$Mu<R>, Pair<C, A>, Pair<C, B>> second(App2<com.mojang.datafixers.optics.ReForgetEP$Mu<R>, A, B> app2) {
            ReForgetEP<R, A, B> reForgetEP = ReForgetEP.unbox(app2);
            return Optics.reForgetEP("second", arg_0 -> Instance.lambda$second$18(reForgetEP, arg_0));
        }

        private static Pair lambda$second$18(ReForgetEP reForgetEP, Either either) {
            return either.map(arg_0 -> Instance.lambda$null$16(reForgetEP, arg_0), arg_0 -> Instance.lambda$null$17(reForgetEP, arg_0));
        }

        private static Pair lambda$null$17(ReForgetEP reForgetEP, Pair pair) {
            return Pair.of(((Pair)pair.getFirst()).getFirst(), reForgetEP.run(Either.right(Pair.of(((Pair)pair.getFirst()).getSecond(), pair.getSecond()))));
        }

        private static Pair lambda$null$16(ReForgetEP reForgetEP, Pair pair) {
            return Pair.of(pair.getFirst(), reForgetEP.run(Either.left(pair.getSecond())));
        }

        private static Pair lambda$first$15(ReForgetEP reForgetEP, Either either) {
            return either.map(arg_0 -> Instance.lambda$null$13(reForgetEP, arg_0), arg_0 -> Instance.lambda$null$14(reForgetEP, arg_0));
        }

        private static Pair lambda$null$14(ReForgetEP reForgetEP, Pair pair) {
            return Pair.of(reForgetEP.run(Either.right(Pair.of(((Pair)pair.getFirst()).getFirst(), pair.getSecond()))), ((Pair)pair.getFirst()).getSecond());
        }

        private static Pair lambda$null$13(ReForgetEP reForgetEP, Pair pair) {
            return Pair.of(reForgetEP.run(Either.left(pair.getFirst())), pair.getSecond());
        }

        private static Either lambda$right$12(ReForgetEP reForgetEP, Either either) {
            return either.map(arg_0 -> Instance.lambda$null$9(reForgetEP, arg_0), arg_0 -> Instance.lambda$null$11(reForgetEP, arg_0));
        }

        private static Either lambda$null$11(ReForgetEP reForgetEP, Pair pair) {
            return ((Either)pair.getFirst()).mapRight(arg_0 -> Instance.lambda$null$10(reForgetEP, pair, arg_0));
        }

        private static Object lambda$null$10(ReForgetEP reForgetEP, Pair pair, Object object) {
            return reForgetEP.run(Either.right(Pair.of(object, pair.getSecond())));
        }

        private static Either lambda$null$9(ReForgetEP reForgetEP, Either either) {
            return either.mapRight(arg_0 -> Instance.lambda$null$8(reForgetEP, arg_0));
        }

        private static Object lambda$null$8(ReForgetEP reForgetEP, Object object) {
            return reForgetEP.run(Either.left(object));
        }

        private static Either lambda$left$7(ReForgetEP reForgetEP, Either either) {
            return either.map(arg_0 -> Instance.lambda$null$4(reForgetEP, arg_0), arg_0 -> Instance.lambda$null$6(reForgetEP, arg_0));
        }

        private static Either lambda$null$6(ReForgetEP reForgetEP, Pair pair) {
            return ((Either)pair.getFirst()).mapLeft(arg_0 -> Instance.lambda$null$5(reForgetEP, pair, arg_0));
        }

        private static Object lambda$null$5(ReForgetEP reForgetEP, Pair pair, Object object) {
            return reForgetEP.run(Either.right(Pair.of(object, pair.getSecond())));
        }

        private static Either lambda$null$4(ReForgetEP reForgetEP, Either either) {
            return either.mapLeft(arg_0 -> Instance.lambda$null$3(reForgetEP, arg_0));
        }

        private static Object lambda$null$3(ReForgetEP reForgetEP, Object object) {
            return reForgetEP.run(Either.left(object));
        }

        private static App2 lambda$dimap$2(Function function, Function function2, App2 app2) {
            return Optics.reForgetEP("dimap", arg_0 -> Instance.lambda$null$1(function, app2, function2, arg_0));
        }

        private static Object lambda$null$1(Function function, App2 app2, Function function2, Either either) {
            Either either2 = either.mapBoth(function, arg_0 -> Instance.lambda$null$0(function, arg_0));
            Object b = ReForgetEP.unbox(app2).run(either2);
            Object r = function2.apply(b);
            return r;
        }

        private static Pair lambda$null$0(Function function, Pair pair) {
            return Pair.of(function.apply(pair.getFirst()), pair.getSecond());
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

