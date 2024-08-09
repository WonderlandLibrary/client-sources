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
import com.mojang.datafixers.optics.profunctors.Closed;
import java.util.function.Function;

interface Grate<S, T, A, B>
extends App2<Mu<A, B>, S, T>,
Optic<Closed.Mu, S, T, A, B> {
    public static <S, T, A, B> Grate<S, T, A, B> unbox(App2<Mu<A, B>, S, T> app2) {
        return (Grate)app2;
    }

    public T grate(FunctionType<FunctionType<S, A>, B> var1);

    @Override
    default public <P extends K2> FunctionType<App2<P, A, B>, App2<P, S, T>> eval(App<? extends Closed.Mu, P> app) {
        Closed<P, ? extends Closed.Mu> closed = Closed.unbox(app);
        return arg_0 -> this.lambda$eval$2(closed, arg_0);
    }

    @Override
    default public Function eval(App app) {
        return this.eval(app);
    }

    private App2 lambda$eval$2(Closed closed, App2 app2) {
        return closed.dimap(closed.closed(app2), Grate::lambda$null$1, this::grate);
    }

    private static FunctionType lambda$null$1(Object object) {
        return arg_0 -> Grate.lambda$null$0(object, arg_0);
    }

    private static Object lambda$null$0(Object object, FunctionType functionType) {
        return functionType.apply(object);
    }

    public static final class Instance<A2, B2>
    implements Closed<Mu<A2, B2>, Closed.Mu> {
        @Override
        public <A, B, C, D> FunctionType<App2<Mu<A2, B2>, A, B>, App2<Mu<A2, B2>, C, D>> dimap(Function<C, A> function, Function<B, D> function2) {
            return arg_0 -> Instance.lambda$dimap$2(function2, function, arg_0);
        }

        @Override
        public <A, B, X> App2<Mu<A2, B2>, FunctionType<X, A>, FunctionType<X, B>> closed(App2<Mu<A2, B2>, A, B> app2) {
            FunctionType<FunctionType, FunctionType> functionType = Instance::lambda$closed$5;
            return (App2)Optics.grate(functionType).eval((App)this).apply(Grate.unbox(app2));
        }

        private static FunctionType lambda$closed$5(FunctionType functionType) {
            return arg_0 -> Instance.lambda$null$4(functionType, arg_0);
        }

        private static Object lambda$null$4(FunctionType functionType, Object object) {
            return functionType.apply(arg_0 -> Instance.lambda$null$3(object, arg_0));
        }

        private static Object lambda$null$3(Object object, FunctionType functionType) {
            return functionType.apply(object);
        }

        private static App2 lambda$dimap$2(Function function, Function function2, App2 app2) {
            return Optics.grate(arg_0 -> Instance.lambda$null$1(function, app2, function2, arg_0));
        }

        private static Object lambda$null$1(Function function, App2 app2, Function function2, FunctionType functionType) {
            return function.apply(Grate.unbox(app2).grate(arg_0 -> Instance.lambda$null$0(functionType, function2, arg_0)));
        }

        private static Object lambda$null$0(FunctionType functionType, Function function, FunctionType functionType2) {
            return functionType.apply(FunctionType.create(functionType2.compose(function)));
        }
    }

    public static final class Mu<A, B>
    implements K2 {
    }
}

