/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.optics;

import com.mojang.datafixers.optics.Prism;
import com.mojang.datafixers.util.Either;

public final class Inj1<F, G, F2>
implements Prism<Either<F, G>, Either<F2, G>, F, F2> {
    @Override
    public Either<Either<F2, G>, F> match(Either<F, G> either) {
        return either.map(Either::right, Inj1::lambda$match$0);
    }

    @Override
    public Either<F2, G> build(F2 F2) {
        return Either.left(F2);
    }

    public String toString() {
        return "inj1";
    }

    public boolean equals(Object object) {
        return object instanceof Inj1;
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
        return Either.left(Either.right(object));
    }
}

