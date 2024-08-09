/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.optics;

import com.mojang.datafixers.optics.Lens;
import com.mojang.datafixers.util.Pair;

public final class Proj2<F, G, G2>
implements Lens<Pair<F, G>, Pair<F, G2>, G, G2> {
    @Override
    public G view(Pair<F, G> pair) {
        return pair.getSecond();
    }

    @Override
    public Pair<F, G2> update(G2 G2, Pair<F, G> pair) {
        return Pair.of(pair.getFirst(), G2);
    }

    public String toString() {
        return "\u03c02";
    }

    public boolean equals(Object object) {
        return object instanceof Proj2;
    }

    @Override
    public Object update(Object object, Object object2) {
        return this.update(object, (Pair)object2);
    }

    @Override
    public Object view(Object object) {
        return this.view((Pair)object);
    }
}

