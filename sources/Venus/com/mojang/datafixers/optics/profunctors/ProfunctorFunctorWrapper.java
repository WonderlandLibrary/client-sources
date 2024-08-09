/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.optics.profunctors;

import com.mojang.datafixers.FunctionType;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.Functor;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.optics.profunctors.Profunctor;
import java.util.function.Function;

public class ProfunctorFunctorWrapper<P extends K2, F extends K1, G extends K1, A, B>
implements App2<Mu<P, F, G>, A, B> {
    private final App2<P, App<F, A>, App<G, B>> value;

    public static <P extends K2, F extends K1, G extends K1, A, B> ProfunctorFunctorWrapper<P, F, G, A, B> unbox(App2<Mu<P, F, G>, A, B> app2) {
        return (ProfunctorFunctorWrapper)app2;
    }

    public ProfunctorFunctorWrapper(App2<P, App<F, A>, App<G, B>> app2) {
        this.value = app2;
    }

    public App2<P, App<F, A>, App<G, B>> value() {
        return this.value;
    }

    public static final class Instance<P extends K2, F extends K1, G extends K1>
    implements Profunctor<com.mojang.datafixers.optics.profunctors.ProfunctorFunctorWrapper$Mu<P, F, G>, Mu>,
    App<Mu, com.mojang.datafixers.optics.profunctors.ProfunctorFunctorWrapper$Mu<P, F, G>> {
        private final Profunctor<P, ? extends Profunctor.Mu> profunctor;
        private final Functor<F, ?> fFunctor;
        private final Functor<G, ?> gFunctor;

        public Instance(App<? extends Profunctor.Mu, P> app, Functor<F, ?> functor, Functor<G, ?> functor2) {
            this.profunctor = Profunctor.unbox(app);
            this.fFunctor = functor;
            this.gFunctor = functor2;
        }

        @Override
        public <A, B, C, D> FunctionType<App2<com.mojang.datafixers.optics.profunctors.ProfunctorFunctorWrapper$Mu<P, F, G>, A, B>, App2<com.mojang.datafixers.optics.profunctors.ProfunctorFunctorWrapper$Mu<P, F, G>, C, D>> dimap(Function<C, A> function, Function<B, D> function2) {
            return arg_0 -> this.lambda$dimap$2(function, function2, arg_0);
        }

        private App2 lambda$dimap$2(Function function, Function function2, App2 app2) {
            App2 app22 = ProfunctorFunctorWrapper.unbox(app2).value();
            App2<P, App, App> app23 = this.profunctor.dimap(app22, arg_0 -> this.lambda$null$0(function, arg_0), arg_0 -> this.lambda$null$1(function2, arg_0));
            return new ProfunctorFunctorWrapper(app23);
        }

        private App lambda$null$1(Function function, App app) {
            return this.gFunctor.map(function, app);
        }

        private App lambda$null$0(Function function, App app) {
            return this.fFunctor.map(function, app);
        }

        public static final class Mu
        implements Profunctor.Mu {
        }
    }

    public static final class Mu<P extends K2, F extends K1, G extends K1>
    implements K2 {
    }
}

