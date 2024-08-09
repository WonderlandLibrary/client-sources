/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.kinds;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.kinds.Kind1;
import java.util.function.Function;

public interface Functor<F extends K1, Mu extends Mu>
extends Kind1<F, Mu> {
    public static <F extends K1, Mu extends Mu> Functor<F, Mu> unbox(App<Mu, F> app) {
        return (Functor)app;
    }

    public <T, R> App<F, R> map(Function<? super T, ? extends R> var1, App<F, T> var2);

    public static interface Mu
    extends Kind1.Mu {
    }
}

