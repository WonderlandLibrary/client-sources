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
import com.mojang.datafixers.optics.profunctors.GetterP;
import java.util.function.Function;
import java.util.function.Supplier;

interface Getter<S, T, A, B>
extends App2<Mu<A, B>, S, T>,
Optic<GetterP.Mu, S, T, A, B> {
    public static <S, T, A, B> Getter<S, T, A, B> unbox(App2<Mu<A, B>, S, T> app2) {
        return (Getter)app2;
    }

    public A get(S var1);

    @Override
    default public <P extends K2> FunctionType<App2<P, A, B>, App2<P, S, T>> eval(App<? extends GetterP.Mu, P> app) {
        GetterP<P, ? extends GetterP.Mu> getterP = GetterP.unbox(app);
        return arg_0 -> this.lambda$eval$0(getterP, arg_0);
    }

    @Override
    default public Function eval(App app) {
        return this.eval(app);
    }

    private App2 lambda$eval$0(GetterP getterP, App2 app2) {
        return getterP.lmap(getterP.secondPhantom(app2), this::get);
    }

    public static final class Instance<A2, B2>
    implements GetterP<Mu<A2, B2>, GetterP.Mu> {
        @Override
        public <A, B, C, D> FunctionType<App2<Mu<A2, B2>, A, B>, App2<Mu<A2, B2>, C, D>> dimap(Function<C, A> function, Function<B, D> function2) {
            return arg_0 -> Instance.lambda$dimap$0(function, arg_0);
        }

        @Override
        public <A, B, C, D> FunctionType<Supplier<App2<Mu<A2, B2>, A, B>>, App2<Mu<A2, B2>, C, D>> cimap(Function<C, A> function, Function<D, B> function2) {
            return arg_0 -> Instance.lambda$cimap$1(function, arg_0);
        }

        private static App2 lambda$cimap$1(Function function, Supplier supplier) {
            return Optics.getter(function.andThen(Getter.unbox((App2)supplier.get())::get));
        }

        private static App2 lambda$dimap$0(Function function, App2 app2) {
            return Optics.getter(function.andThen(Getter.unbox(app2)::get));
        }
    }

    public static final class Mu<A, B>
    implements K2 {
    }
}

