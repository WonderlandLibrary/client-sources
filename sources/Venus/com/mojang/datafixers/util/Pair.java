/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.util;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.kinds.CartesianLike;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.kinds.Traversable;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Pair<F, S>
implements App<Mu<S>, F> {
    private final F first;
    private final S second;

    public static <F, S> Pair<F, S> unbox(App<Mu<S>, F> app) {
        return (Pair)app;
    }

    public Pair(F f, S s) {
        this.first = f;
        this.second = s;
    }

    public F getFirst() {
        return this.first;
    }

    public S getSecond() {
        return this.second;
    }

    public Pair<S, F> swap() {
        return Pair.of(this.second, this.first);
    }

    public String toString() {
        return "(" + this.first + ", " + this.second + ")";
    }

    public boolean equals(Object object) {
        if (!(object instanceof Pair)) {
            return true;
        }
        Pair pair = (Pair)object;
        return Objects.equals(this.first, pair.first) && Objects.equals(this.second, pair.second);
    }

    public int hashCode() {
        return com.google.common.base.Objects.hashCode(this.first, this.second);
    }

    public <F2> Pair<F2, S> mapFirst(Function<? super F, ? extends F2> function) {
        return Pair.of(function.apply(this.first), this.second);
    }

    public <S2> Pair<F, S2> mapSecond(Function<? super S, ? extends S2> function) {
        return Pair.of(this.first, function.apply(this.second));
    }

    public static <F, S> Pair<F, S> of(F f, S s) {
        return new Pair<F, S>(f, s);
    }

    public static <F, S> Collector<Pair<F, S>, ?, Map<F, S>> toMap() {
        return Collectors.toMap(Pair::getFirst, Pair::getSecond);
    }

    static Object access$000(Pair pair) {
        return pair.first;
    }

    static Object access$100(Pair pair) {
        return pair.second;
    }

    public static final class Instance<S2>
    implements Traversable<com.mojang.datafixers.util.Pair$Mu<S2>, Mu<S2>>,
    CartesianLike<com.mojang.datafixers.util.Pair$Mu<S2>, S2, Mu<S2>> {
        @Override
        public <T, R> App<com.mojang.datafixers.util.Pair$Mu<S2>, R> map(Function<? super T, ? extends R> function, App<com.mojang.datafixers.util.Pair$Mu<S2>, T> app) {
            return Pair.unbox(app).mapFirst(function);
        }

        @Override
        public <F extends K1, A, B> App<F, App<com.mojang.datafixers.util.Pair$Mu<S2>, B>> traverse(Applicative<F, ?> applicative, Function<A, App<F, B>> function, App<com.mojang.datafixers.util.Pair$Mu<S2>, A> app) {
            Pair<A, S2> pair = Pair.unbox(app);
            return applicative.ap(arg_0 -> Instance.lambda$traverse$0(pair, arg_0), function.apply(Pair.access$000(pair)));
        }

        @Override
        public <A> App<com.mojang.datafixers.util.Pair$Mu<S2>, A> to(App<com.mojang.datafixers.util.Pair$Mu<S2>, A> app) {
            return app;
        }

        @Override
        public <A> App<com.mojang.datafixers.util.Pair$Mu<S2>, A> from(App<com.mojang.datafixers.util.Pair$Mu<S2>, A> app) {
            return app;
        }

        private static App lambda$traverse$0(Pair pair, Object object) {
            return Pair.of(object, Pair.access$100(pair));
        }

        public static final class Mu<S2>
        implements Traversable.Mu,
        CartesianLike.Mu {
        }
    }

    public static final class Mu<S>
    implements K1 {
    }
}

