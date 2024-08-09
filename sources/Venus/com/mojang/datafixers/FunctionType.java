/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers;

import com.google.common.reflect.TypeToken;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.Functor;
import com.mojang.datafixers.kinds.IdF;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.kinds.Representable;
import com.mojang.datafixers.optics.Optics;
import com.mojang.datafixers.optics.Procompose;
import com.mojang.datafixers.optics.Wander;
import com.mojang.datafixers.optics.profunctors.Mapping;
import com.mojang.datafixers.optics.profunctors.MonoidProfunctor;
import com.mojang.datafixers.optics.profunctors.Monoidal;
import com.mojang.datafixers.optics.profunctors.TraversalP;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nonnull;

public interface FunctionType<A, B>
extends Function<A, B>,
App2<Mu, A, B>,
App<ReaderMu<A>, B> {
    public static <A, B> FunctionType<A, B> create(Function<? super A, ? extends B> function) {
        return function::apply;
    }

    public static <A, B> Function<A, B> unbox(App2<Mu, A, B> app2) {
        return (FunctionType)app2;
    }

    public static <A, B> Function<A, B> unbox(App<ReaderMu<A>, B> app) {
        return (FunctionType)app;
    }

    @Override
    @Nonnull
    public B apply(@Nonnull A var1);

    public static enum Instance implements TraversalP<com.mojang.datafixers.FunctionType$Mu, Mu>,
    MonoidProfunctor<com.mojang.datafixers.FunctionType$Mu, Mu>,
    Mapping<com.mojang.datafixers.FunctionType$Mu, Mu>,
    Monoidal<com.mojang.datafixers.FunctionType$Mu, Mu>,
    App<Mu, com.mojang.datafixers.FunctionType$Mu>
    {
        INSTANCE;


        @Override
        public <A, B, C, D> FunctionType<App2<com.mojang.datafixers.FunctionType$Mu, A, B>, App2<com.mojang.datafixers.FunctionType$Mu, C, D>> dimap(Function<C, A> function, Function<B, D> function2) {
            return arg_0 -> Instance.lambda$dimap$0(function2, function, arg_0);
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.FunctionType$Mu, Pair<A, C>, Pair<B, C>> first(App2<com.mojang.datafixers.FunctionType$Mu, A, B> app2) {
            return FunctionType.create(arg_0 -> Instance.lambda$first$1(app2, arg_0));
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.FunctionType$Mu, Pair<C, A>, Pair<C, B>> second(App2<com.mojang.datafixers.FunctionType$Mu, A, B> app2) {
            return FunctionType.create(arg_0 -> Instance.lambda$second$2(app2, arg_0));
        }

        @Override
        public <S, T, A, B> App2<com.mojang.datafixers.FunctionType$Mu, S, T> wander(Wander<S, T, A, B> wander, App2<com.mojang.datafixers.FunctionType$Mu, A, B> app2) {
            return FunctionType.create(arg_0 -> Instance.lambda$wander$4(wander, app2, arg_0));
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.FunctionType$Mu, Either<A, C>, Either<B, C>> left(App2<com.mojang.datafixers.FunctionType$Mu, A, B> app2) {
            return FunctionType.create(arg_0 -> Instance.lambda$left$5(app2, arg_0));
        }

        @Override
        public <A, B, C> App2<com.mojang.datafixers.FunctionType$Mu, Either<C, A>, Either<C, B>> right(App2<com.mojang.datafixers.FunctionType$Mu, A, B> app2) {
            return FunctionType.create(arg_0 -> Instance.lambda$right$6(app2, arg_0));
        }

        @Override
        public <A, B, C, D> App2<com.mojang.datafixers.FunctionType$Mu, Pair<A, C>, Pair<B, D>> par(App2<com.mojang.datafixers.FunctionType$Mu, A, B> app2, Supplier<App2<com.mojang.datafixers.FunctionType$Mu, C, D>> supplier) {
            return FunctionType.create(arg_0 -> Instance.lambda$par$7(app2, supplier, arg_0));
        }

        @Override
        public App2<com.mojang.datafixers.FunctionType$Mu, Void, Void> empty() {
            return FunctionType.create(Function.identity());
        }

        @Override
        public <A, B> App2<com.mojang.datafixers.FunctionType$Mu, A, B> zero(App2<com.mojang.datafixers.FunctionType$Mu, A, B> app2) {
            return app2;
        }

        @Override
        public <A, B> App2<com.mojang.datafixers.FunctionType$Mu, A, B> plus(App2<Procompose.Mu<com.mojang.datafixers.FunctionType$Mu, com.mojang.datafixers.FunctionType$Mu>, A, B> app2) {
            Procompose<com.mojang.datafixers.FunctionType$Mu, com.mojang.datafixers.FunctionType$Mu, A, B, ?> procompose = Procompose.unbox(app2);
            return this.cap(procompose);
        }

        private <A, B, C> App2<com.mojang.datafixers.FunctionType$Mu, A, B> cap(Procompose<com.mojang.datafixers.FunctionType$Mu, com.mojang.datafixers.FunctionType$Mu, A, B, C> procompose) {
            return FunctionType.create(Optics.getFunc(procompose.second()).compose(Optics.getFunc(procompose.first().get())));
        }

        @Override
        public <A, B, F extends K1> App2<com.mojang.datafixers.FunctionType$Mu, App<F, A>, App<F, B>> mapping(Functor<F, ?> functor, App2<com.mojang.datafixers.FunctionType$Mu, A, B> app2) {
            return FunctionType.create(arg_0 -> Instance.lambda$mapping$8(functor, app2, arg_0));
        }

        private static App lambda$mapping$8(Functor functor, App2 app2, App app) {
            return functor.map(Optics.getFunc(app2), app);
        }

        private static Pair lambda$par$7(App2 app2, Supplier supplier, Pair pair) {
            return Pair.of(Optics.getFunc(app2).apply(pair.getFirst()), Optics.getFunc((App2)supplier.get()).apply(pair.getSecond()));
        }

        private static Either lambda$right$6(App2 app2, Either either) {
            return either.mapRight(Optics.getFunc(app2));
        }

        private static Either lambda$left$5(App2 app2, Either either) {
            return either.mapLeft(Optics.getFunc(app2));
        }

        private static Object lambda$wander$4(Wander wander, App2 app2, Object object) {
            return IdF.get(wander.wander(IdF.Instance.INSTANCE, arg_0 -> Instance.lambda$null$3(app2, arg_0)).apply(object));
        }

        private static App lambda$null$3(App2 app2, Object object) {
            return IdF.create(Optics.getFunc(app2).apply(object));
        }

        private static Pair lambda$second$2(App2 app2, Pair pair) {
            return Pair.of(pair.getFirst(), Optics.getFunc(app2).apply(pair.getSecond()));
        }

        private static Pair lambda$first$1(App2 app2, Pair pair) {
            return Pair.of(Optics.getFunc(app2).apply(pair.getFirst()), pair.getSecond());
        }

        private static App2 lambda$dimap$0(Function function, Function function2, App2 app2) {
            return FunctionType.create(function.compose(Optics.getFunc(app2)).compose(function2));
        }

        public static final class Mu
        implements TraversalP.Mu,
        MonoidProfunctor.Mu,
        Mapping.Mu,
        Monoidal.Mu {
            public static final TypeToken<Mu> TYPE_TOKEN = new TypeToken<Mu>(){};
        }
    }

    public static final class ReaderInstance<R>
    implements Representable<ReaderMu<R>, R, Mu<R>> {
        @Override
        public <T, R2> App<ReaderMu<R>, R2> map(Function<? super T, ? extends R2> function, App<ReaderMu<R>, T> app) {
            return FunctionType.create(function.compose(FunctionType.unbox(app)));
        }

        @Override
        public <B> App<ReaderMu<R>, B> to(App<ReaderMu<R>, B> app) {
            return app;
        }

        @Override
        public <B> App<ReaderMu<R>, B> from(App<ReaderMu<R>, B> app) {
            return app;
        }

        public static final class Mu<A>
        implements Representable.Mu {
        }
    }

    public static final class ReaderMu<A>
    implements K1 {
    }

    public static final class Mu
    implements K2 {
    }
}

