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
import com.mojang.datafixers.util.Either;
import java.util.function.Function;

interface ReForgetE<R, A, B>
extends App2<Mu<R>, A, B> {
    public static <R, A, B> ReForgetE<R, A, B> unbox(App2<Mu<R>, A, B> app2) {
        return (ReForgetE)app2;
    }

    public B run(Either<A, R> var1);

    public static final class Instance<R>
    implements Cocartesian<com.mojang.datafixers.optics.ReForgetE$Mu<R>, Mu<R>>,
    App<Mu<R>, com.mojang.datafixers.optics.ReForgetE$Mu<R>> {
        @Override
        public <A, B, C, D> FunctionType<App2<com.mojang.datafixers.optics.ReForgetE$Mu<R>, A, B>, App2<com.mojang.datafixers.optics.ReForgetE$Mu<R>, C, D>> dimap(Function<C, A> function, Function<B, D> function2) {
            return arg_0 -> Instance.lambda$dimap$1(function, function2, arg_0);
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.ReForgetE$Mu<R>, Either<A, C>, Either<B, C>> left(App2<com.mojang.datafixers.optics.ReForgetE$Mu<R>, A, B> app2) {
            ReForgetE<R, A, B> reForgetE = ReForgetE.unbox(app2);
            return Optics.reForgetE("left", arg_0 -> Instance.lambda$left$5(reForgetE, arg_0));
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.ReForgetE$Mu<R>, Either<C, A>, Either<C, B>> right(App2<com.mojang.datafixers.optics.ReForgetE$Mu<R>, A, B> app2) {
            ReForgetE<R, A, B> reForgetE = ReForgetE.unbox(app2);
            return Optics.reForgetE("right", arg_0 -> Instance.lambda$right$9(reForgetE, arg_0));
        }

        private static Either lambda$right$9(ReForgetE reForgetE, Either either) {
            return either.map(arg_0 -> Instance.lambda$null$7(reForgetE, arg_0), arg_0 -> Instance.lambda$null$8(reForgetE, arg_0));
        }

        private static Either lambda$null$8(ReForgetE reForgetE, Object object) {
            return Either.right(reForgetE.run(Either.right(object)));
        }

        private static Either lambda$null$7(ReForgetE reForgetE, Either either) {
            return either.map(Either::left, arg_0 -> Instance.lambda$null$6(reForgetE, arg_0));
        }

        private static Either lambda$null$6(ReForgetE reForgetE, Object object) {
            return Either.right(reForgetE.run(Either.left(object)));
        }

        private static Either lambda$left$5(ReForgetE reForgetE, Either either) {
            return either.map(arg_0 -> Instance.lambda$null$3(reForgetE, arg_0), arg_0 -> Instance.lambda$null$4(reForgetE, arg_0));
        }

        private static Either lambda$null$4(ReForgetE reForgetE, Object object) {
            return Either.left(reForgetE.run(Either.right(object)));
        }

        private static Either lambda$null$3(ReForgetE reForgetE, Either either) {
            return either.map(arg_0 -> Instance.lambda$null$2(reForgetE, arg_0), Either::right);
        }

        private static Either lambda$null$2(ReForgetE reForgetE, Object object) {
            return Either.left(reForgetE.run(Either.left(object)));
        }

        private static App2 lambda$dimap$1(Function function, Function function2, App2 app2) {
            return Optics.reForgetE("dimap", arg_0 -> Instance.lambda$null$0(function, app2, function2, arg_0));
        }

        private static Object lambda$null$0(Function function, App2 app2, Function function2, Either either) {
            Either either2 = either.mapLeft(function);
            Object b = ReForgetE.unbox(app2).run(either2);
            Object r = function2.apply(b);
            return r;
        }

        static final class Mu<R>
        implements Cocartesian.Mu {
            Mu() {
            }
        }
    }

    public static final class Mu<R>
    implements K2 {
    }
}

