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
import com.mojang.datafixers.optics.profunctors.Cartesian;
import com.mojang.datafixers.util.Pair;
import java.util.function.Function;

public interface Lens<S, T, A, B>
extends App2<Mu<A, B>, S, T>,
Optic<Cartesian.Mu, S, T, A, B> {
    public static <S, T, A, B> Lens<S, T, A, B> unbox(App2<Mu<A, B>, S, T> app2) {
        return (Lens)app2;
    }

    public static <S, T, A, B> Lens<S, T, A, B> unbox2(App2<Mu2<S, T>, B, A> app2) {
        return Box.access$000((Box)app2);
    }

    public static <S, T, A, B> App2<Mu2<S, T>, B, A> box(Lens<S, T, A, B> lens) {
        return new Box<S, T, A, B>(lens);
    }

    public A view(S var1);

    public T update(B var1, S var2);

    @Override
    default public <P extends K2> FunctionType<App2<P, A, B>, App2<P, S, T>> eval(App<? extends Cartesian.Mu, P> app) {
        Cartesian<P, ? extends Cartesian.Mu> cartesian = Cartesian.unbox(app);
        return arg_0 -> this.lambda$eval$2(cartesian, arg_0);
    }

    @Override
    default public Function eval(App app) {
        return this.eval(app);
    }

    private App2 lambda$eval$2(Cartesian cartesian, App2 app2) {
        return cartesian.dimap(cartesian.first(app2), this::lambda$null$0, this::lambda$null$1);
    }

    private Object lambda$null$1(Pair pair) {
        return this.update(pair.getFirst(), pair.getSecond());
    }

    private Pair lambda$null$0(Object object) {
        return Pair.of(this.view(object), object);
    }

    public static final class Instance<A2, B2>
    implements Cartesian<Mu<A2, B2>, Cartesian.Mu> {
        @Override
        public <A, B, C, D> FunctionType<App2<Mu<A2, B2>, A, B>, App2<Mu<A2, B2>, C, D>> dimap(Function<C, A> function, Function<B, D> function2) {
            return arg_0 -> Instance.lambda$dimap$2(function, function2, arg_0);
        }

        @Override
        public <A, B, C> App2<Mu<A2, B2>, Pair<A, C>, Pair<B, C>> first(App2<Mu<A2, B2>, A, B> app2) {
            return Optics.lens(arg_0 -> Instance.lambda$first$3(app2, arg_0), (arg_0, arg_1) -> Instance.lambda$first$4(app2, arg_0, arg_1));
        }

        @Override
        public <A, B, C> App2<Mu<A2, B2>, Pair<C, A>, Pair<C, B>> second(App2<Mu<A2, B2>, A, B> app2) {
            return Optics.lens(arg_0 -> Instance.lambda$second$5(app2, arg_0), (arg_0, arg_1) -> Instance.lambda$second$6(app2, arg_0, arg_1));
        }

        private static Pair lambda$second$6(App2 app2, Object object, Pair pair) {
            return Pair.of(pair.getFirst(), Lens.unbox(app2).update(object, pair.getSecond()));
        }

        private static Object lambda$second$5(App2 app2, Pair pair) {
            return Lens.unbox(app2).view(pair.getSecond());
        }

        private static Pair lambda$first$4(App2 app2, Object object, Pair pair) {
            return Pair.of(Lens.unbox(app2).update(object, pair.getFirst()), pair.getSecond());
        }

        private static Object lambda$first$3(App2 app2, Pair pair) {
            return Lens.unbox(app2).view(pair.getFirst());
        }

        private static App2 lambda$dimap$2(Function function, Function function2, App2 app2) {
            return Optics.lens(arg_0 -> Instance.lambda$null$0(app2, function, arg_0), (arg_0, arg_1) -> Instance.lambda$null$1(function2, app2, function, arg_0, arg_1));
        }

        private static Object lambda$null$1(Function function, App2 app2, Function function2, Object object, Object object2) {
            return function.apply(Lens.unbox(app2).update(object, function2.apply(object2)));
        }

        private static Object lambda$null$0(App2 app2, Function function, Object object) {
            return Lens.unbox(app2).view(function.apply(object));
        }
    }

    public static final class Box<S, T, A, B>
    implements App2<Mu2<S, T>, B, A> {
        private final Lens<S, T, A, B> lens;

        public Box(Lens<S, T, A, B> lens) {
            this.lens = lens;
        }

        static Lens access$000(Box box) {
            return box.lens;
        }
    }

    public static final class Mu2<S, T>
    implements K2 {
    }

    public static final class Mu<A, B>
    implements K2 {
    }
}

