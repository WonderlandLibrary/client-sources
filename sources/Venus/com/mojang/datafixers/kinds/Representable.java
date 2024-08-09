/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.kinds;

import com.mojang.datafixers.FunctionType;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Functor;
import com.mojang.datafixers.kinds.K1;

public interface Representable<T extends K1, C, Mu extends Mu>
extends Functor<T, Mu> {
    public static <F extends K1, C, Mu extends Mu> Representable<F, C, Mu> unbox(App<Mu, F> app) {
        return (Representable)app;
    }

    public <A> App<FunctionType.ReaderMu<C>, A> to(App<T, A> var1);

    public <A> App<T, A> from(App<FunctionType.ReaderMu<C>, A> var1);

    public static interface Mu
    extends Functor.Mu {
    }
}

