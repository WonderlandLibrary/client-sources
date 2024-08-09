/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.kinds;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.kinds.Functor;
import com.mojang.datafixers.kinds.K1;
import java.util.function.Function;

public interface Traversable<T extends K1, Mu extends Mu>
extends Functor<T, Mu> {
    public static <F extends K1, Mu extends Mu> Traversable<F, Mu> unbox(App<Mu, F> app) {
        return (Traversable)app;
    }

    public <F extends K1, A, B> App<F, App<T, B>> traverse(Applicative<F, ?> var1, Function<A, App<F, B>> var2, App<T, A> var3);

    default public <F extends K1, A> App<F, App<T, A>> flip(Applicative<F, ?> applicative, App<T, App<F, A>> app) {
        return this.traverse(applicative, Function.identity(), app);
    }

    public static interface Mu
    extends Functor.Mu {
    }
}

