/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.optics;

import com.mojang.datafixers.FunctionType;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.optics.profunctors.Profunctor;
import java.util.function.Function;
import java.util.function.Supplier;

public final class Procompose<F extends K2, G extends K2, A, B, C>
implements App2<Mu<F, G>, A, B> {
    private final Supplier<App2<F, A, C>> first;
    private final App2<G, C, B> second;

    public Procompose(Supplier<App2<F, A, C>> supplier, App2<G, C, B> app2) {
        this.first = supplier;
        this.second = app2;
    }

    public static <F extends K2, G extends K2, A, B> Procompose<F, G, A, B, ?> unbox(App2<Mu<F, G>, A, B> app2) {
        return (Procompose)app2;
    }

    public Supplier<App2<F, A, C>> first() {
        return this.first;
    }

    public App2<G, C, B> second() {
        return this.second;
    }

    static App2 access$000(Procompose procompose) {
        return procompose.second;
    }

    static Supplier access$100(Procompose procompose) {
        return procompose.first;
    }

    static final class ProfunctorInstance<F extends K2, G extends K2>
    implements Profunctor<Mu<F, G>, Profunctor.Mu> {
        private final Profunctor<F, Profunctor.Mu> p1;
        private final Profunctor<G, Profunctor.Mu> p2;

        ProfunctorInstance(Profunctor<F, Profunctor.Mu> profunctor, Profunctor<G, Profunctor.Mu> profunctor2) {
            this.p1 = profunctor;
            this.p2 = profunctor2;
        }

        @Override
        public <A, B, C, D> FunctionType<App2<Mu<F, G>, A, B>, App2<Mu<F, G>, C, D>> dimap(Function<C, A> function, Function<B, D> function2) {
            return arg_0 -> this.lambda$dimap$0(function, function2, arg_0);
        }

        private <A, B, C, D, E> App2<Mu<F, G>, C, D> cap(Procompose<F, G, A, B, E> procompose, Function<C, A> function, Function<B, D> function2) {
            return new Procompose(() -> this.lambda$cap$1(function, procompose), this.p2.dimap(Function.identity(), function2).apply(Procompose.access$000(procompose)));
        }

        private App2 lambda$cap$1(Function function, Procompose procompose) {
            return this.p1.dimap(function, Function.identity()).apply(Procompose.access$100(procompose).get());
        }

        private App2 lambda$dimap$0(Function function, Function function2, App2 app2) {
            return this.cap(Procompose.unbox(app2), function, function2);
        }
    }

    public static final class Mu<F extends K2, G extends K2>
    implements K2 {
    }
}

