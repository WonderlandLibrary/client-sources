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
import com.mojang.datafixers.optics.profunctors.Profunctor;
import java.util.function.Function;

public interface Adapter<S, T, A, B>
extends App2<Mu<A, B>, S, T>,
Optic<Profunctor.Mu, S, T, A, B> {
    public static <S, T, A, B> Adapter<S, T, A, B> unbox(App2<Mu<A, B>, S, T> app2) {
        return (Adapter)app2;
    }

    public A from(S var1);

    public T to(B var1);

    @Override
    default public <P extends K2> FunctionType<App2<P, A, B>, App2<P, S, T>> eval(App<? extends Profunctor.Mu, P> app) {
        Profunctor<P, ? extends Profunctor.Mu> profunctor = Profunctor.unbox(app);
        return arg_0 -> this.lambda$eval$0(profunctor, arg_0);
    }

    @Override
    default public Function eval(App app) {
        return this.eval(app);
    }

    private App2 lambda$eval$0(Profunctor profunctor, App2 app2) {
        return profunctor.dimap(app2, this::from, this::to);
    }

    public static final class Instance<A2, B2>
    implements Profunctor<Mu<A2, B2>, Profunctor.Mu> {
        @Override
        public <A, B, C, D> FunctionType<App2<Mu<A2, B2>, A, B>, App2<Mu<A2, B2>, C, D>> dimap(Function<C, A> function, Function<B, D> function2) {
            return arg_0 -> Instance.lambda$dimap$2(function, function2, arg_0);
        }

        private static App2 lambda$dimap$2(Function function, Function function2, App2 app2) {
            return Optics.adapter(arg_0 -> Instance.lambda$null$0(app2, function, arg_0), arg_0 -> Instance.lambda$null$1(function2, app2, arg_0));
        }

        private static Object lambda$null$1(Function function, App2 app2, Object object) {
            return function.apply(Adapter.unbox(app2).to(object));
        }

        private static Object lambda$null$0(App2 app2, Function function, Object object) {
            return Adapter.unbox(app2).from(function.apply(object));
        }
    }

    public static final class Mu<A, B>
    implements K2 {
    }
}

