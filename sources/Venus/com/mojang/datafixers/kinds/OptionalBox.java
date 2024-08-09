/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.kinds;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.kinds.Traversable;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class OptionalBox<T>
implements App<Mu, T> {
    private final Optional<T> value;

    public static <T> Optional<T> unbox(App<Mu, T> app) {
        return ((OptionalBox)app).value;
    }

    public static <T> OptionalBox<T> create(Optional<T> optional) {
        return new OptionalBox<T>(optional);
    }

    private OptionalBox(Optional<T> optional) {
        this.value = optional;
    }

    public static enum Instance implements Applicative<com.mojang.datafixers.kinds.OptionalBox$Mu, Mu>,
    Traversable<com.mojang.datafixers.kinds.OptionalBox$Mu, Mu>
    {
        INSTANCE;


        @Override
        public <T, R> App<com.mojang.datafixers.kinds.OptionalBox$Mu, R> map(Function<? super T, ? extends R> function, App<com.mojang.datafixers.kinds.OptionalBox$Mu, T> app) {
            return OptionalBox.create(OptionalBox.unbox(app).map(function));
        }

        @Override
        public <A> App<com.mojang.datafixers.kinds.OptionalBox$Mu, A> point(A a) {
            return OptionalBox.create(Optional.of(a));
        }

        @Override
        public <A, R> Function<App<com.mojang.datafixers.kinds.OptionalBox$Mu, A>, App<com.mojang.datafixers.kinds.OptionalBox$Mu, R>> lift1(App<com.mojang.datafixers.kinds.OptionalBox$Mu, Function<A, R>> app) {
            return arg_0 -> Instance.lambda$lift1$1(app, arg_0);
        }

        @Override
        public <A, B, R> BiFunction<App<com.mojang.datafixers.kinds.OptionalBox$Mu, A>, App<com.mojang.datafixers.kinds.OptionalBox$Mu, B>, App<com.mojang.datafixers.kinds.OptionalBox$Mu, R>> lift2(App<com.mojang.datafixers.kinds.OptionalBox$Mu, BiFunction<A, B, R>> app) {
            return (arg_0, arg_1) -> Instance.lambda$lift2$5(app, arg_0, arg_1);
        }

        @Override
        public <F extends K1, A, B> App<F, App<com.mojang.datafixers.kinds.OptionalBox$Mu, B>> traverse(Applicative<F, ?> applicative, Function<A, App<F, B>> function, App<com.mojang.datafixers.kinds.OptionalBox$Mu, A> app) {
            Optional<App<F, B>> optional = OptionalBox.unbox(app).map(function);
            if (optional.isPresent()) {
                return applicative.map(Instance::lambda$traverse$6, optional.get());
            }
            return applicative.point(OptionalBox.create(Optional.empty()));
        }

        private static App lambda$traverse$6(Object object) {
            return OptionalBox.create(Optional.of(object));
        }

        private static App lambda$lift2$5(App app, App app2, App app3) {
            return OptionalBox.create(OptionalBox.unbox(app).flatMap(arg_0 -> Instance.lambda$null$4(app2, app3, arg_0)));
        }

        private static Optional lambda$null$4(App app, App app2, BiFunction biFunction) {
            return OptionalBox.unbox(app).flatMap(arg_0 -> Instance.lambda$null$3(app2, biFunction, arg_0));
        }

        private static Optional lambda$null$3(App app, BiFunction biFunction, Object object) {
            return OptionalBox.unbox(app).map(arg_0 -> Instance.lambda$null$2(biFunction, object, arg_0));
        }

        private static Object lambda$null$2(BiFunction biFunction, Object object, Object object2) {
            return biFunction.apply(object, object2);
        }

        private static App lambda$lift1$1(App app, App app2) {
            return OptionalBox.create(OptionalBox.unbox(app).flatMap(arg_0 -> Instance.lambda$null$0(app2, arg_0)));
        }

        private static Optional lambda$null$0(App app, Function function) {
            return OptionalBox.unbox(app).map(function);
        }

        public static final class Mu
        implements Applicative.Mu,
        Traversable.Mu {
        }
    }

    public static final class Mu
    implements K1 {
    }
}

