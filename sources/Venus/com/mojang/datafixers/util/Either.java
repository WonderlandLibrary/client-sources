/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.util;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.kinds.CocartesianLike;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.kinds.Traversable;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Either<L, R>
implements App<Mu<R>, L> {
    public static <L, R> Either<L, R> unbox(App<Mu<R>, L> app) {
        return (Either)app;
    }

    private Either() {
    }

    public abstract <C, D> Either<C, D> mapBoth(Function<? super L, ? extends C> var1, Function<? super R, ? extends D> var2);

    public abstract <T> T map(Function<? super L, ? extends T> var1, Function<? super R, ? extends T> var2);

    public abstract Either<L, R> ifLeft(Consumer<? super L> var1);

    public abstract Either<L, R> ifRight(Consumer<? super R> var1);

    public abstract Optional<L> left();

    public abstract Optional<R> right();

    public <T> Either<T, R> mapLeft(Function<? super L, ? extends T> function) {
        return this.map(arg_0 -> Either.lambda$mapLeft$0(function, arg_0), Either::right);
    }

    public <T> Either<L, T> mapRight(Function<? super R, ? extends T> function) {
        return this.map(Either::left, arg_0 -> Either.lambda$mapRight$1(function, arg_0));
    }

    public static <L, R> Either<L, R> left(L l) {
        return new Left(l);
    }

    public static <L, R> Either<L, R> right(R r) {
        return new Right(r);
    }

    public L orThrow() {
        return (L)this.map(Either::lambda$orThrow$2, Either::lambda$orThrow$3);
    }

    public Either<R, L> swap() {
        return this.map(Either::right, Either::left);
    }

    public <L2> Either<L2, R> flatMap(Function<L, Either<L2, R>> function) {
        return this.map(function, Either::right);
    }

    private static Object lambda$orThrow$3(Object object) {
        if (object instanceof Throwable) {
            throw new RuntimeException((Throwable)object);
        }
        throw new RuntimeException(object.toString());
    }

    private static Object lambda$orThrow$2(Object object) {
        return object;
    }

    private static Either lambda$mapRight$1(Function function, Object object) {
        return Either.right(function.apply(object));
    }

    private static Either lambda$mapLeft$0(Function function, Object object) {
        return Either.left(function.apply(object));
    }

    Either(1 var1_1) {
        this();
    }

    public static final class Instance<R2>
    implements Applicative<com.mojang.datafixers.util.Either$Mu<R2>, Mu<R2>>,
    Traversable<com.mojang.datafixers.util.Either$Mu<R2>, Mu<R2>>,
    CocartesianLike<com.mojang.datafixers.util.Either$Mu<R2>, R2, Mu<R2>> {
        @Override
        public <T, R> App<com.mojang.datafixers.util.Either$Mu<R2>, R> map(Function<? super T, ? extends R> function, App<com.mojang.datafixers.util.Either$Mu<R2>, T> app) {
            return Either.unbox(app).mapLeft(function);
        }

        @Override
        public <A> App<com.mojang.datafixers.util.Either$Mu<R2>, A> point(A a) {
            return Either.left(a);
        }

        @Override
        public <A, R> Function<App<com.mojang.datafixers.util.Either$Mu<R2>, A>, App<com.mojang.datafixers.util.Either$Mu<R2>, R>> lift1(App<com.mojang.datafixers.util.Either$Mu<R2>, Function<A, R>> app) {
            return arg_0 -> Instance.lambda$lift1$1(app, arg_0);
        }

        @Override
        public <A, B, R> BiFunction<App<com.mojang.datafixers.util.Either$Mu<R2>, A>, App<com.mojang.datafixers.util.Either$Mu<R2>, B>, App<com.mojang.datafixers.util.Either$Mu<R2>, R>> lift2(App<com.mojang.datafixers.util.Either$Mu<R2>, BiFunction<A, B, R>> app) {
            return (arg_0, arg_1) -> Instance.lambda$lift2$5(app, arg_0, arg_1);
        }

        @Override
        public <F extends K1, A, B> App<F, App<com.mojang.datafixers.util.Either$Mu<R2>, B>> traverse(Applicative<F, ?> applicative, Function<A, App<F, B>> function, App<com.mojang.datafixers.util.Either$Mu<R2>, A> app) {
            return Either.unbox(app).map(arg_0 -> Instance.lambda$traverse$6(function, applicative, arg_0), arg_0 -> Instance.lambda$traverse$7(applicative, arg_0));
        }

        @Override
        public <A> App<com.mojang.datafixers.util.Either$Mu<R2>, A> to(App<com.mojang.datafixers.util.Either$Mu<R2>, A> app) {
            return app;
        }

        @Override
        public <A> App<com.mojang.datafixers.util.Either$Mu<R2>, A> from(App<com.mojang.datafixers.util.Either$Mu<R2>, A> app) {
            return app;
        }

        private static App lambda$traverse$7(Applicative applicative, Object object) {
            return applicative.point(Either.right(object));
        }

        private static App lambda$traverse$6(Function function, Applicative applicative, Object object) {
            App app = (App)function.apply(object);
            return applicative.ap(Either::left, app);
        }

        private static App lambda$lift2$5(App app, App app2, App app3) {
            return Either.unbox(app).flatMap(arg_0 -> Instance.lambda$null$4(app2, app3, arg_0));
        }

        private static Either lambda$null$4(App app, App app2, BiFunction biFunction) {
            return Either.unbox(app).flatMap(arg_0 -> Instance.lambda$null$3(app2, biFunction, arg_0));
        }

        private static Either lambda$null$3(App app, BiFunction biFunction, Object object) {
            return Either.unbox(app).mapLeft(arg_0 -> Instance.lambda$null$2(biFunction, object, arg_0));
        }

        private static Object lambda$null$2(BiFunction biFunction, Object object, Object object2) {
            return biFunction.apply(object, object2);
        }

        private static App lambda$lift1$1(App app, App app2) {
            return Either.unbox(app).flatMap(arg_0 -> Instance.lambda$null$0(app2, arg_0));
        }

        private static Either lambda$null$0(App app, Function function) {
            return Either.unbox(app).mapLeft(function);
        }

        public static final class Mu<R2>
        implements Applicative.Mu,
        Traversable.Mu,
        CocartesianLike.Mu {
        }
    }

    private static final class Right<L, R>
    extends Either<L, R> {
        private final R value;

        public Right(R r) {
            super(null);
            this.value = r;
        }

        @Override
        public <C, D> Either<C, D> mapBoth(Function<? super L, ? extends C> function, Function<? super R, ? extends D> function2) {
            return new Right<L, D>(function2.apply(this.value));
        }

        @Override
        public <T> T map(Function<? super L, ? extends T> function, Function<? super R, ? extends T> function2) {
            return function2.apply(this.value);
        }

        @Override
        public Either<L, R> ifLeft(Consumer<? super L> consumer) {
            return this;
        }

        @Override
        public Either<L, R> ifRight(Consumer<? super R> consumer) {
            consumer.accept(this.value);
            return this;
        }

        @Override
        public Optional<L> left() {
            return Optional.empty();
        }

        @Override
        public Optional<R> right() {
            return Optional.of(this.value);
        }

        public String toString() {
            return "Right[" + this.value + "]";
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return true;
            }
            Right right = (Right)object;
            return Objects.equals(this.value, right.value);
        }

        public int hashCode() {
            return Objects.hash(this.value);
        }
    }

    private static final class Left<L, R>
    extends Either<L, R> {
        private final L value;

        public Left(L l) {
            super(null);
            this.value = l;
        }

        @Override
        public <C, D> Either<C, D> mapBoth(Function<? super L, ? extends C> function, Function<? super R, ? extends D> function2) {
            return new Left<C, R>(function.apply(this.value));
        }

        @Override
        public <T> T map(Function<? super L, ? extends T> function, Function<? super R, ? extends T> function2) {
            return function.apply(this.value);
        }

        @Override
        public Either<L, R> ifLeft(Consumer<? super L> consumer) {
            consumer.accept(this.value);
            return this;
        }

        @Override
        public Either<L, R> ifRight(Consumer<? super R> consumer) {
            return this;
        }

        @Override
        public Optional<L> left() {
            return Optional.of(this.value);
        }

        @Override
        public Optional<R> right() {
            return Optional.empty();
        }

        public String toString() {
            return "Left[" + this.value + "]";
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return true;
            }
            Left left = (Left)object;
            return Objects.equals(this.value, left.value);
        }

        public int hashCode() {
            return Objects.hash(this.value);
        }
    }

    public static final class Mu<R>
    implements K1 {
    }
}

