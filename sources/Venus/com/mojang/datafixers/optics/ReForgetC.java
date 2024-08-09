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
import java.util.function.BiFunction;
import java.util.function.Function;

public interface ReForgetC<R, A, B>
extends App2<Mu<R>, A, B> {
    public static <R, A, B> ReForgetC<R, A, B> unbox(App2<Mu<R>, A, B> app2) {
        return (ReForgetC)app2;
    }

    public Either<Function<R, B>, BiFunction<A, R, B>> impl();

    default public B run(A a, R r) {
        return (B)this.impl().map(arg_0 -> ReForgetC.lambda$run$0(r, arg_0), arg_0 -> ReForgetC.lambda$run$1(a, r, arg_0));
    }

    private static Object lambda$run$1(Object object, Object object2, BiFunction biFunction) {
        return biFunction.apply(object, object2);
    }

    private static Object lambda$run$0(Object object, Function function) {
        return function.apply(object);
    }

    public static final class Instance<R>
    implements AffineP<com.mojang.datafixers.optics.ReForgetC$Mu<R>, Mu<R>>,
    App<Mu<R>, com.mojang.datafixers.optics.ReForgetC$Mu<R>> {
        @Override
        public <A, B, C, D> FunctionType<App2<com.mojang.datafixers.optics.ReForgetC$Mu<R>, A, B>, App2<com.mojang.datafixers.optics.ReForgetC$Mu<R>, C, D>> dimap(Function<C, A> function, Function<B, D> function2) {
            return arg_0 -> Instance.lambda$dimap$4(function2, function, arg_0);
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.ReForgetC$Mu<R>, Pair<A, C>, Pair<B, C>> first(App2<com.mojang.datafixers.optics.ReForgetC$Mu<R>, A, B> app2) {
            return Optics.reForgetC("first", ReForgetC.unbox(app2).impl().map(Instance::lambda$first$6, Instance::lambda$first$8));
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.ReForgetC$Mu<R>, Pair<C, A>, Pair<C, B>> second(App2<com.mojang.datafixers.optics.ReForgetC$Mu<R>, A, B> app2) {
            return Optics.reForgetC("second", ReForgetC.unbox(app2).impl().map(Instance::lambda$second$10, Instance::lambda$second$12));
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.ReForgetC$Mu<R>, Either<A, C>, Either<B, C>> left(App2<com.mojang.datafixers.optics.ReForgetC$Mu<R>, A, B> app2) {
            return Optics.reForgetC("left", ReForgetC.unbox(app2).impl().map(Instance::lambda$left$14, Instance::lambda$left$17));
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.optics.ReForgetC$Mu<R>, Either<C, A>, Either<C, B>> right(App2<com.mojang.datafixers.optics.ReForgetC$Mu<R>, A, B> app2) {
            return Optics.reForgetC("right", ReForgetC.unbox(app2).impl().map(Instance::lambda$right$19, Instance::lambda$right$22));
        }

        private static Either lambda$right$22(BiFunction biFunction) {
            return Either.right((arg_0, arg_1) -> Instance.lambda$null$21(biFunction, arg_0, arg_1));
        }

        private static Either lambda$null$21(BiFunction biFunction, Either either, Object object) {
            return either.mapRight(arg_0 -> Instance.lambda$null$20(biFunction, object, arg_0));
        }

        private static Object lambda$null$20(BiFunction biFunction, Object object, Object object2) {
            return biFunction.apply(object2, object);
        }

        private static Either lambda$right$19(Function function) {
            return Either.left(arg_0 -> Instance.lambda$null$18(function, arg_0));
        }

        private static Either lambda$null$18(Function function, Object object) {
            return Either.right(function.apply(object));
        }

        private static Either lambda$left$17(BiFunction biFunction) {
            return Either.right((arg_0, arg_1) -> Instance.lambda$null$16(biFunction, arg_0, arg_1));
        }

        private static Either lambda$null$16(BiFunction biFunction, Either either, Object object) {
            return either.mapLeft(arg_0 -> Instance.lambda$null$15(biFunction, object, arg_0));
        }

        private static Object lambda$null$15(BiFunction biFunction, Object object, Object object2) {
            return biFunction.apply(object2, object);
        }

        private static Either lambda$left$14(Function function) {
            return Either.left(arg_0 -> Instance.lambda$null$13(function, arg_0));
        }

        private static Either lambda$null$13(Function function, Object object) {
            return Either.left(function.apply(object));
        }

        private static Either lambda$second$12(BiFunction biFunction) {
            return Either.right((arg_0, arg_1) -> Instance.lambda$null$11(biFunction, arg_0, arg_1));
        }

        private static Pair lambda$null$11(BiFunction biFunction, Pair pair, Object object) {
            return Pair.of(pair.getFirst(), biFunction.apply(pair.getSecond(), object));
        }

        private static Either lambda$second$10(Function function) {
            return Either.right((arg_0, arg_1) -> Instance.lambda$null$9(function, arg_0, arg_1));
        }

        private static Pair lambda$null$9(Function function, Pair pair, Object object) {
            return Pair.of(pair.getFirst(), function.apply(object));
        }

        private static Either lambda$first$8(BiFunction biFunction) {
            return Either.right((arg_0, arg_1) -> Instance.lambda$null$7(biFunction, arg_0, arg_1));
        }

        private static Pair lambda$null$7(BiFunction biFunction, Pair pair, Object object) {
            return Pair.of(biFunction.apply(pair.getFirst(), object), pair.getSecond());
        }

        private static Either lambda$first$6(Function function) {
            return Either.right((arg_0, arg_1) -> Instance.lambda$null$5(function, arg_0, arg_1));
        }

        private static Pair lambda$null$5(Function function, Pair pair, Object object) {
            return Pair.of(function.apply(object), pair.getSecond());
        }

        private static App2 lambda$dimap$4(Function function, Function function2, App2 app2) {
            return Optics.reForgetC("dimap", ReForgetC.unbox(app2).impl().map(arg_0 -> Instance.lambda$null$1(function, arg_0), arg_0 -> Instance.lambda$null$3(function, function2, arg_0)));
        }

        private static Either lambda$null$3(Function function, Function function2, BiFunction biFunction) {
            return Either.right((arg_0, arg_1) -> Instance.lambda$null$2(function, biFunction, function2, arg_0, arg_1));
        }

        private static Object lambda$null$2(Function function, BiFunction biFunction, Function function2, Object object, Object object2) {
            return function.apply(biFunction.apply(function2.apply(object), object2));
        }

        private static Either lambda$null$1(Function function, Function function2) {
            return Either.left(arg_0 -> Instance.lambda$null$0(function, function2, arg_0));
        }

        private static Object lambda$null$0(Function function, Function function2, Object object) {
            return function.apply(function2.apply(object));
        }

        public static final class Mu<R>
        implements AffineP.Mu {
        }
    }

    public static final class Mu<R>
    implements K2 {
    }
}

