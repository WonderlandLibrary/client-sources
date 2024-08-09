/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.optics.profunctors;

import com.google.common.reflect.TypeToken;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.optics.profunctors.Cartesian;
import com.mojang.datafixers.optics.profunctors.Cocartesian;

public interface AffineP<P extends K2, Mu extends Mu>
extends Cartesian<P, Mu>,
Cocartesian<P, Mu> {

    public static interface Mu
    extends Cartesian.Mu,
    Cocartesian.Mu {
        public static final TypeToken<Mu> TYPE_TOKEN = new TypeToken<Mu>(){};
    }
}

