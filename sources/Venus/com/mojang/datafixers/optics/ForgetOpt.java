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
import java.util.Optional;
import java.util.function.Function;

public interface ForgetOpt<R, A, B>
extends App2<Mu<R>, A, B> {
    public static <R, A, B> ForgetOpt<R, A, B> unbox(App2<Mu<R>, A, B> app2) {
        return (ForgetOpt)app2;
    }

    public Optional<R> run(A var1);

    public static final class Instance<R>
    implements AffineP<com.mojang.datafixers.optics.ForgetOpt$Mu<R>, Mu<R>>,
    App<Mu<R>, com.mojang.datafixers.optics.ForgetOpt$Mu<R>> {
        @Override
        public <A, B, C, D> FunctionType<App2<com.mojang.datafixers.optics.ForgetOpt$Mu<R>, A, B>, App2<com.mojang.datafixers.optics.ForgetOpt$Mu<R>, C, D>> dimap(Function<C, A> function, Function<B, D> function2) {
            return arg_0 -> Instance.lambda$dimap$1(function, arg_0);
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.ForgetOpt$Mu<R>, Pair<A, C>, Pair<B, C>> first(App2<com.mojang.datafixers.optics.ForgetOpt$Mu<R>, A, B> app2) {
            return Optics.forgetOpt(arg_0 -> Instance.lambda$first$2(app2, arg_0));
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.ForgetOpt$Mu<R>, Pair<C, A>, Pair<C, B>> second(App2<com.mojang.datafixers.optics.ForgetOpt$Mu<R>, A, B> app2) {
            return Optics.forgetOpt(arg_0 -> Instance.lambda$second$3(app2, arg_0));
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.ForgetOpt$Mu<R>, Either<A, C>, Either<B, C>> left(App2<com.mojang.datafixers.optics.ForgetOpt$Mu<R>, A, B> app2) {
            return Optics.forgetOpt(arg_0 -> Instance.lambda$left$4(app2, arg_0));
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.ForgetOpt$Mu<R>, Either<C, A>, Either<C, B>> right(App2<com.mojang.datafixers.optics.ForgetOpt$Mu<R>, A, B> app2) {
            return Optics.forgetOpt(arg_0 -> Instance.lambda$right$5(app2, arg_0));
        }

        private static Optional lambda$right$5(App2 app2, Either either) {
            return either.right().flatMap(ForgetOpt.unbox(app2)::run);
        }

        private static Optional lambda$left$4(App2 app2, Either either) {
            return either.left().flatMap(ForgetOpt.unbox(app2)::run);
        }

        private static Optional lambda$second$3(App2 app2, Pair pair) {
            return ForgetOpt.unbox(app2).run(pair.getSecond());
        }

        private static Optional lambda$first$2(App2 app2, Pair pair) {
            return ForgetOpt.unbox(app2).run(pair.getFirst());
        }

        private static App2 lambda$dimap$1(Function function, App2 app2) {
            return Optics.forgetOpt(arg_0 -> Instance.lambda$null$0(app2, function, arg_0));
        }

        private static Optional lambda$null$0(App2 app2, Function function, Object object) {
            return ForgetOpt.unbox(app2).run(function.apply(object));
        }

        public static final class Mu<R>
        implements AffineP.Mu {
        }
    }

    public static final class Mu<R>
    implements K2 {
    }
}

