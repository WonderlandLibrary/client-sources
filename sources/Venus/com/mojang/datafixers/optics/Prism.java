/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.optics;

import com.mojang.datafixers.FunctionType;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.optics.Optic;
import com.mojang.datafixers.optics.Optics;
import com.mojang.datafixers.optics.profunctors.Cocartesian;
import com.mojang.datafixers.util.Either;
import java.util.function.Function;

public interface Prism<S, T, A, B>
extends App2<Mu<A, B>, S, T>,
Optic<Cocartesian.Mu, S, T, A, B> {
    public static <S, T, A, B> Prism<S, T, A, B> unbox(App2<Mu<A, B>, S, T> app2) {
        return (Prism)app2;
    }

    public Either<T, A> match(S var1);

    public T build(B var1);

    @Override
    default public <P extends K2> FunctionType<App2<P, A, B>, App2<P, S, T>> eval(App<? extends Cocartesian.Mu, P> app) {
        Cocartesian<P, ? extends Cocartesian.Mu> cocartesian = Cocartesian.unbox(app);
        return arg_0 -> this.lambda$eval$1(cocartesian, arg_0);
    }

    @Override
    default public Function eval(App app) {
        return this.eval(app);
    }

    private App2 lambda$eval$1(Cocartesian cocartesian, App2 app2) {
        return cocartesian.dimap(cocartesian.right(app2), this::match, this::lambda$null$0);
    }

    private Object lambda$null$0(Either either) {
        return either.map(Function.identity(), this::build);
    }

    public static final class Instance<A2, B2>
    implements Cocartesian<Mu<A2, B2>, Cocartesian.Mu> {
        @Override
        public <A, B, C, D> FunctionType<App2<Mu<A2, B2>, A, B>, App2<Mu<A2, B2>, C, D>> dimap(Function<C, A> function, Function<B, D> function2) {
            return arg_0 -> Instance.lambda$dimap$2(function, function2, arg_0);
        }

        @Override
        public <A, B, C> App2<Mu<A2, B2>, Either<A, C>, Either<B, C>> left(App2<Mu<A2, B2>, A, B> app2) {
            Prism<A, B, A2, B2> prism = Prism.unbox(app2);
            return Optics.prism(arg_0 -> Instance.lambda$left$5(prism, arg_0), arg_0 -> Instance.lambda$left$6(prism, arg_0));
        }

        @Override
        public <A, B, C> App2<Mu<A2, B2>, Either<C, A>, Either<C, B>> right(App2<Mu<A2, B2>, A, B> app2) {
            Prism<A, B, A2, B2> prism = Prism.unbox(app2);
            return Optics.prism(arg_0 -> Instance.lambda$right$9(prism, arg_0), arg_0 -> Instance.lambda$right$10(prism, arg_0));
        }

        private static Either lambda$right$10(Prism prism, Object object) {
            return Either.right(prism.build(object));
        }

        private static Either lambda$right$9(Prism prism, Either either) {
            return either.map(Instance::lambda$null$7, arg_0 -> Instance.lambda$null$8(prism, arg_0));
        }

        private static Either lambda$null$8(Prism prism, Object object) {
            return prism.match(object).mapLeft(Either::right);
        }

        private static Either lambda$null$7(Object object) {
            return Either.left(Either.left(object));
        }

        private static Either lambda$left$6(Prism prism, Object object) {
            return Either.left(prism.build(object));
        }

        private static Either lambda$left$5(Prism prism, Either either) {
            return either.map(arg_0 -> Instance.lambda$null$3(prism, arg_0), Instance::lambda$null$4);
        }

        private static Either lambda$null$4(Object object) {
            return Either.left(Either.right(object));
        }

        private static Either lambda$null$3(Prism prism, Object object) {
            return prism.match(object).mapLeft(Either::left);
        }

        private static App2 lambda$dimap$2(Function function, Function function2, App2 app2) {
            return Optics.prism(arg_0 -> Instance.lambda$null$0(app2, function, function2, arg_0), arg_0 -> Instance.lambda$null$1(function2, app2, arg_0));
        }

        private static Object lambda$null$1(Function function, App2 app2, Object object) {
            return function.apply(Prism.unbox(app2).build(object));
        }

        private static Either lambda$null$0(App2 app2, Function function, Function function2, Object object) {
            return Prism.unbox(app2).match(function.apply(object)).mapLeft(function2);
        }
    }

    public static final class Mu<A, B>
    implements K2 {
    }
}

