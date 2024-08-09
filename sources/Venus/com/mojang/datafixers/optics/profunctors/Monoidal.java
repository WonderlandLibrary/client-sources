/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.optics.profunctors;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.optics.profunctors.Profunctor;
import com.mojang.datafixers.util.Pair;
import java.util.function.Supplier;

public interface Monoidal<P extends K2, Mu extends Mu>
extends Profunctor<P, Mu> {
    public static <P extends K2, Proof extends Mu> Monoidal<P, Proof> unbox(App<Proof, P> app) {
        return (Monoidal)app;
    }

    public <A, B, C, D> App2<P, Pair<A, C>, Pair<B, D>> par(App2<P, A, B> var1, Supplier<App2<P, C, D>> var2);

    public App2<P, Void, Void> empty();

    public static interface Mu
    extends Profunctor.Mu {
    }
}

