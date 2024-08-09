/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.optics.profunctors;

import com.mojang.datafixers.FunctionType;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.kinds.Kind2;
import java.util.function.Function;
import java.util.function.Supplier;

interface Bicontravariant<P extends K2, Mu extends Mu>
extends Kind2<P, Mu> {
    public static <P extends K2, Proof extends Mu> Bicontravariant<P, Proof> unbox(App<Proof, P> app) {
        return (Bicontravariant)app;
    }

    public <A, B, C, D> FunctionType<Supplier<App2<P, A, B>>, App2<P, C, D>> cimap(Function<C, A> var1, Function<D, B> var2);

    default public <A, B, C, D> App2<P, C, D> cimap(Supplier<App2<P, A, B>> supplier, Function<C, A> function, Function<D, B> function2) {
        return this.cimap(function, function2).apply(supplier);
    }

    public static interface Mu
    extends Kind2.Mu {
    }
}

