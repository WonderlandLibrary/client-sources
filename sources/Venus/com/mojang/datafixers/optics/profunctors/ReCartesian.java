/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.optics.profunctors;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.optics.profunctors.Profunctor;
import com.mojang.datafixers.util.Pair;

public interface ReCartesian<P extends K2, Mu extends Mu>
extends Profunctor<P, Mu> {
    public static <P extends K2, Proof extends Mu> ReCartesian<P, Proof> unbox(App<Proof, P> app) {
        return (ReCartesian)app;
    }

    public <A, B, C> App2<P, A, B> unfirst(App2<P, Pair<A, C>, Pair<B, C>> var1);

    public <A, B, C> App2<P, A, B> unsecond(App2<P, Pair<C, A>, Pair<C, B>> var1);

    public static interface Mu
    extends Profunctor.Mu {
    }
}

