/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.optics.profunctors;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.optics.profunctors.Profunctor;
import com.mojang.datafixers.util.Either;

public interface ReCocartesian<P extends K2, Mu extends Mu>
extends Profunctor<P, Mu> {
    public static <P extends K2, Proof extends Mu> ReCocartesian<P, Proof> unbox(App<Proof, P> app) {
        return (ReCocartesian)app;
    }

    public <A, B, C> App2<P, A, B> unleft(App2<P, Either<A, C>, Either<B, C>> var1);

    public <A, B, C> App2<P, A, B> unright(App2<P, Either<C, A>, Either<C, B>> var1);

    public static interface Mu
    extends Profunctor.Mu {
    }
}

