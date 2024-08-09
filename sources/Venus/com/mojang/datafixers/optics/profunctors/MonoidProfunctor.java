/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.optics.profunctors;

import com.mojang.datafixers.FunctionType;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.optics.Procompose;
import com.mojang.datafixers.optics.profunctors.Profunctor;
import java.util.function.Supplier;

public interface MonoidProfunctor<P extends K2, Mu extends Mu>
extends Profunctor<P, Mu> {
    public <A, B> App2<P, A, B> zero(App2<FunctionType.Mu, A, B> var1);

    public <A, B> App2<P, A, B> plus(App2<Procompose.Mu<P, P>, A, B> var1);

    default public <A, B, C> App2<P, A, C> compose(App2<P, B, C> app2, Supplier<App2<P, A, B>> supplier) {
        return this.plus(new Procompose(supplier, app2));
    }

    public static interface Mu
    extends Profunctor.Mu {
    }
}

