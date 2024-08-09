/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.optics;

import com.mojang.datafixers.optics.Prism;
import com.mojang.datafixers.util.Either;

public final class Inj2<F, G, G2>
implements Prism<Either<F, G>, Either<F, G2>, G, G2> {
    @Override
    public Either<Either<F, G2>, G> match(Either<F, G> either) {
        return either.map(Inj2::lambda$match$0, Either::right);
    }

    @Override
    public Either<F, G2> build(G2 G2) {
        return Either.right(G2);
    }

    public String toString() {
        return "inj2";
    }

    public boolean equals(Object object) {
        return object instanceof Inj2;
    }

    @Override
    public Object build(Object object) {
        return this.build(object);
    }

    @Override
    public Either match(Object object) {
        return this.match((Either)object);
    }

    private static Either lambda$match$0(Object object) {
        return Either.left(Either.left(object));
    }
}

