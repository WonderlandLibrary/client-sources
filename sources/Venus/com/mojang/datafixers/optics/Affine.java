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
import com.mojang.datafixers.optics.profunctors.AffineP;
import com.mojang.datafixers.optics.profunctors.Cartesian;
import com.mojang.datafixers.optics.profunctors.Cocartesian;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.function.Function;

public interface Affine<S, T, A, B>
extends App2<Mu<A, B>, S, T>,
Optic<AffineP.Mu, S, T, A, B> {
    public static <S, T, A, B> Affine<S, T, A, B> unbox(App2<Mu<A, B>, S, T> app2) {
        return (Affine)app2;
    }

    public Either<T, A> preview(S var1);

    public T set(B var1, S var2);

    @Override
    default public <P extends K2> FunctionType<App2<P, A, B>, App2<P, S, T>> eval(App<? extends AffineP.Mu, P> app) {
        Cartesian<P, ? extends AffineP.Mu> cartesian = Cartesian.unbox(app);
        Cocartesian<P, ? extends AffineP.Mu> cocartesian = Cocartesian.unbox(app);
        return arg_0 -> this.lambda$eval$4(cartesian, cocartesian, arg_0);
    }

    @Override
    default public Function eval(App app) {
        return this.eval(app);
    }

    private App2 lambda$eval$4(Cartesian cartesian, Cocartesian cocartesian, App2 app2) {
        return cartesian.dimap(cocartesian.left(cartesian.rmap(cartesian.first(app2), this::lambda$null$0)), this::lambda$null$2, Affine::lambda$null$3);
    }

    private static Object lambda$null$3(Either either) {
        return either.map(Function.identity(), Function.identity());
    }

    private Either lambda$null$2(Object object) {
        return this.preview(object).map(Either::right, arg_0 -> Affine.lambda$null$1(object, arg_0));
    }

    private static Either lambda$null$1(Object object, Object object2) {
        return Either.left(Pair.of(object2, object));
    }

    private Object lambda$null$0(Pair pair) {
        return this.set(pair.getFirst(), pair.getSecond());
    }

    public static final class Instance<A2, B2>
    implements AffineP<Mu<A2, B2>, AffineP.Mu> {
        @Override
        public <A, B, C, D> FunctionType<App2<Mu<A2, B2>, A, B>, App2<Mu<A2, B2>, C, D>> dimap(Function<C, A> function, Function<B, D> function2) {
            return arg_0 -> Instance.lambda$dimap$2(function, function2, arg_0);
        }

        @Override
        public <A, B, C> App2<Mu<A2, B2>, Pair<A, C>, Pair<B, C>> first(App2<Mu<A2, B2>, A, B> app2) {
            Affine<A, B, A2, B2> affine = Affine.unbox(app2);
            return Optics.affine(arg_0 -> Instance.lambda$first$4(affine, arg_0), (arg_0, arg_1) -> Instance.lambda$first$5(affine, arg_0, arg_1));
        }

        @Override
        public <A, B, C> App2<Mu<A2, B2>, Pair<C, A>, Pair<C, B>> second(App2<Mu<A2, B2>, A, B> app2) {
            Affine<A, B, A2, B2> affine = Affine.unbox(app2);
            return Optics.affine(arg_0 -> Instance.lambda$second$7(affine, arg_0), (arg_0, arg_1) -> Instance.lambda$second$8(affine, arg_0, arg_1));
        }

        @Override
        public <A, B, C> App2<Mu<A2, B2>, Either<A, C>, Either<B, C>> left(App2<Mu<A2, B2>, A, B> app2) {
            Affine<A, B, A2, B2> affine = Affine.unbox(app2);
            return Optics.affine(arg_0 -> Instance.lambda$left$11(affine, arg_0), (arg_0, arg_1) -> Instance.lambda$left$13(affine, arg_0, arg_1));
        }

        @Override
        public <A, B, C> App2<Mu<A2, B2>, Either<C, A>, Either<C, B>> right(App2<Mu<A2, B2>, A, B> app2) {
            Affine<A, B, A2, B2> affine = Affine.unbox(app2);
            return Optics.affine(arg_0 -> Instance.lambda$right$16(affine, arg_0), (arg_0, arg_1) -> Instance.lambda$right$18(affine, arg_0, arg_1));
        }

        private static Either lambda$right$18(Affine affine, Object object, Either either) {
            return either.map(Either::left, arg_0 -> Instance.lambda$null$17(affine, object, arg_0));
        }

        private static Either lambda$null$17(Affine affine, Object object, Object object2) {
            return Either.right(affine.set(object, object2));
        }

        private static Either lambda$right$16(Affine affine, Either either) {
            return either.map(Instance::lambda$null$14, arg_0 -> Instance.lambda$null$15(affine, arg_0));
        }

        private static Either lambda$null$15(Affine affine, Object object) {
            return affine.preview(object).mapLeft(Either::right);
        }

        private static Either lambda$null$14(Object object) {
            return Either.left(Either.left(object));
        }

        private static Either lambda$left$13(Affine affine, Object object, Either either) {
            return either.map(arg_0 -> Instance.lambda$null$12(affine, object, arg_0), Either::right);
        }

        private static Either lambda$null$12(Affine affine, Object object, Object object2) {
            return Either.left(affine.set(object, object2));
        }

        private static Either lambda$left$11(Affine affine, Either either) {
            return either.map(arg_0 -> Instance.lambda$null$9(affine, arg_0), Instance::lambda$null$10);
        }

        private static Either lambda$null$10(Object object) {
            return Either.left(Either.right(object));
        }

        private static Either lambda$null$9(Affine affine, Object object) {
            return affine.preview(object).mapLeft(Either::left);
        }

        private static Pair lambda$second$8(Affine affine, Object object, Pair pair) {
            return Pair.of(pair.getFirst(), affine.set(object, pair.getSecond()));
        }

        private static Either lambda$second$7(Affine affine, Pair pair) {
            return affine.preview(pair.getSecond()).mapBoth(arg_0 -> Instance.lambda$null$6(pair, arg_0), Function.identity());
        }

        private static Pair lambda$null$6(Pair pair, Object object) {
            return Pair.of(pair.getFirst(), object);
        }

        private static Pair lambda$first$5(Affine affine, Object object, Pair pair) {
            return Pair.of(affine.set(object, pair.getFirst()), pair.getSecond());
        }

        private static Either lambda$first$4(Affine affine, Pair pair) {
            return affine.preview(pair.getFirst()).mapBoth(arg_0 -> Instance.lambda$null$3(pair, arg_0), Function.identity());
        }

        private static Pair lambda$null$3(Pair pair, Object object) {
            return Pair.of(object, pair.getSecond());
        }

        private static App2 lambda$dimap$2(Function function, Function function2, App2 app2) {
            return Optics.affine(arg_0 -> Instance.lambda$null$0(app2, function, function2, arg_0), (arg_0, arg_1) -> Instance.lambda$null$1(function2, app2, function, arg_0, arg_1));
        }

        private static Object lambda$null$1(Function function, App2 app2, Function function2, Object object, Object object2) {
            return function.apply(Affine.unbox(app2).set(object, function2.apply(object2)));
        }

        private static Either lambda$null$0(App2 app2, Function function, Function function2, Object object) {
            return Affine.unbox(app2).preview(function.apply(object)).mapLeft(function2);
        }
    }

    public static final class Mu<A, B>
    implements K2 {
    }
}

