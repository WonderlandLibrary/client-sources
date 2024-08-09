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

interface ForgetE<R, A, B>
extends App2<Mu<R>, A, B> {
    public static <R, A, B> ForgetE<R, A, B> unbox(App2<Mu<R>, A, B> app2) {
        return (ForgetE)app2;
    }

    public Either<B, R> run(A var1);

    public static final class Instance<R>
    implements AffineP<com.mojang.datafixers.optics.ForgetE$Mu<R>, Mu<R>>,
    App<Mu<R>, com.mojang.datafixers.optics.ForgetE$Mu<R>> {
        @Override
        public <A, B, C, D> FunctionType<App2<com.mojang.datafixers.optics.ForgetE$Mu<R>, A, B>, App2<com.mojang.datafixers.optics.ForgetE$Mu<R>, C, D>> dimap(Function<C, A> function, Function<B, D> function2) {
            return arg_0 -> Instance.lambda$dimap$1(function, function2, arg_0);
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.ForgetE$Mu<R>, Pair<A, C>, Pair<B, C>> first(App2<com.mojang.datafixers.optics.ForgetE$Mu<R>, A, B> app2) {
            return Optics.forgetE(arg_0 -> Instance.lambda$first$3(app2, arg_0));
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.ForgetE$Mu<R>, Pair<C, A>, Pair<C, B>> second(App2<com.mojang.datafixers.optics.ForgetE$Mu<R>, A, B> app2) {
            return Optics.forgetE(arg_0 -> Instance.lambda$second$5(app2, arg_0));
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.ForgetE$Mu<R>, Either<A, C>, Either<B, C>> left(App2<com.mojang.datafixers.optics.ForgetE$Mu<R>, A, B> app2) {
            return Optics.forgetE(arg_0 -> Instance.lambda$left$8(app2, arg_0));
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.ForgetE$Mu<R>, Either<C, A>, Either<C, B>> right(App2<com.mojang.datafixers.optics.ForgetE$Mu<R>, A, B> app2) {
            return Optics.forgetE(arg_0 -> Instance.lambda$right$11(app2, arg_0));
        }

        private static Either lambda$right$11(App2 app2, Either either) {
            return either.map(Instance::lambda$null$9, arg_0 -> Instance.lambda$null$10(app2, arg_0));
        }

        private static Either lambda$null$10(App2 app2, Object object) {
            return ForgetE.unbox(app2).run(object).mapLeft(Either::right);
        }

        private static Either lambda$null$9(Object object) {
            return Either.left(Either.left(object));
        }

        private static Either lambda$left$8(App2 app2, Either either) {
            return either.map(arg_0 -> Instance.lambda$null$6(app2, arg_0), Instance::lambda$null$7);
        }

        private static Either lambda$null$7(Object object) {
            return Either.left(Either.right(object));
        }

        private static Either lambda$null$6(App2 app2, Object object) {
            return ForgetE.unbox(app2).run(object).mapLeft(Either::left);
        }

        private static Either lambda$second$5(App2 app2, Pair pair) {
            return ForgetE.unbox(app2).run(pair.getSecond()).mapLeft(arg_0 -> Instance.lambda$null$4(pair, arg_0));
        }

        private static Pair lambda$null$4(Pair pair, Object object) {
            return Pair.of(pair.getFirst(), object);
        }

        private static Either lambda$first$3(App2 app2, Pair pair) {
            return ForgetE.unbox(app2).run(pair.getFirst()).mapLeft(arg_0 -> Instance.lambda$null$2(pair, arg_0));
        }

        private static Pair lambda$null$2(Pair pair, Object object) {
            return Pair.of(object, pair.getSecond());
        }

        private static App2 lambda$dimap$1(Function function, Function function2, App2 app2) {
            return Optics.forgetE(arg_0 -> Instance.lambda$null$0(app2, function, function2, arg_0));
        }

        private static Either lambda$null$0(App2 app2, Function function, Function function2, Object object) {
            return ForgetE.unbox(app2).run(function.apply(object)).mapLeft(function2);
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

