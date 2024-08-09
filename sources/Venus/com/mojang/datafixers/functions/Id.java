/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.functions;

import com.mojang.datafixers.functions.PointFree;
import com.mojang.serialization.DynamicOps;
import java.util.function.Function;

final class Id<A>
extends PointFree<Function<A, A>> {
    public boolean equals(Object object) {
        return object instanceof Id;
    }

    public int hashCode() {
        return Id.class.hashCode();
    }

    @Override
    public String toString(int n) {
        return "id";
    }

    @Override
    public Function<DynamicOps<?>, Function<A, A>> eval() {
        return Id::lambda$eval$0;
    }

    private static Function lambda$eval$0(DynamicOps dynamicOps) {
        return Function.identity();
    }
}

