/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.optics;

import com.mojang.datafixers.optics.Lens;
import com.mojang.datafixers.util.Pair;

public final class Proj1<F, G, F2>
implements Lens<Pair<F, G>, Pair<F2, G>, F, F2> {
    @Override
    public F view(Pair<F, G> pair) {
        return pair.getFirst();
    }

    @Override
    public Pair<F2, G> update(F2 F2, Pair<F, G> pair) {
        return Pair.of(F2, pair.getSecond());
    }

    public String toString() {
        return "\u03c01";
    }

    public boolean equals(Object object) {
        return object instanceof Proj1;
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

