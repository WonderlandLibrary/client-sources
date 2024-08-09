/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.kinds;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.kinds.K2;

public interface Kind2<F extends K2, Mu extends Mu>
extends App<Mu, F> {
    public static <F extends K2, Proof extends Mu> Kind2<F, Proof> unbox(App<Proof, F> app) {
        return (Kind2)app;
    }

    public static interface Mu
    extends K1 {
    }
}

