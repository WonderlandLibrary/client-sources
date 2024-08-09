/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.kinds;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.kinds.Functor;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.kinds.Traversable;
import com.mojang.datafixers.util.Either;
import java.util.function.Function;

public interface CocartesianLike<T extends K1, C, Mu extends Mu>
extends Functor<T, Mu>,
Traversable<T, Mu> {
    public static <F extends K1, C, Mu extends Mu> CocartesianLike<F, C, Mu> unbox(App<Mu, F> app) {
        return (CocartesianLike)app;
    }

    public <A> App<Either.Mu<C>, A> to(App<T, A> var1);

    public <A> App<T, A> from(App<Either.Mu<C>, A> var1);

    @Override
    default public <F extends K1, A, B> App<F, App<T, B>> traverse(Applicative<F, ?> applicative, Function<A, App<F, B>> function, App<T, A> app) {
        return applicative.map(this::from, new Either.Instance<C>().traverse(applicative, function, this.to(app)));
    }

    public static interface Mu
    extends Functor.Mu,
    Traversable.Mu {
    }
}

