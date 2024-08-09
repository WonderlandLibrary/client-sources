/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.kinds;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.kinds.Functor;
import com.mojang.datafixers.kinds.K1;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class IdF<A>
implements App<Mu, A> {
    protected final A value;

    IdF(A a) {
        this.value = a;
    }

    public A value() {
        return this.value;
    }

    public static <A> A get(App<Mu, A> app) {
        return ((IdF)app).value;
    }

    public static <A> IdF<A> create(A a) {
        return new IdF<A>(a);
    }

    public static enum Instance implements Functor<com.mojang.datafixers.kinds.IdF$Mu, Mu>,
    Applicative<com.mojang.datafixers.kinds.IdF$Mu, Mu>
    {
        INSTANCE;


        @Override
        public <T, R> App<com.mojang.datafixers.kinds.IdF$Mu, R> map(Function<? super T, ? extends R> function, App<com.mojang.datafixers.kinds.IdF$Mu, T> app) {
            IdF idF = (IdF)app;
            return new IdF<R>(function.apply(idF.value));
        }

        @Override
        public <A> App<com.mojang.datafixers.kinds.IdF$Mu, A> point(A a) {
            return IdF.create(a);
        }

        @Override
        public <A, R> Function<App<com.mojang.datafixers.kinds.IdF$Mu, A>, App<com.mojang.datafixers.kinds.IdF$Mu, R>> lift1(App<com.mojang.datafixers.kinds.IdF$Mu, Function<A, R>> app) {
            return arg_0 -> Instance.lambda$lift1$0(app, arg_0);
        }

        @Override
        public <A, B, R> BiFunction<App<com.mojang.datafixers.kinds.IdF$Mu, A>, App<com.mojang.datafixers.kinds.IdF$Mu, B>, App<com.mojang.datafixers.kinds.IdF$Mu, R>> lift2(App<com.mojang.datafixers.kinds.IdF$Mu, BiFunction<A, B, R>> app) {
            return (arg_0, arg_1) -> Instance.lambda$lift2$1(app, arg_0, arg_1);
        }

        private static App lambda$lift2$1(App app, App app2, App app3) {
            return IdF.create(((BiFunction)IdF.get(app)).apply(IdF.get(app2), IdF.get(app3)));
        }

        private static App lambda$lift1$0(App app, App app2) {
            return IdF.create(((Function)IdF.get(app)).apply(IdF.get(app2)));
        }

        public static final class Mu
        implements Functor.Mu,
        Applicative.Mu {
        }
    }

    public static final class Mu
    implements K1 {
    }
}

