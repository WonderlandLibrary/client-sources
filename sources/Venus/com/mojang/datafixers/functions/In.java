/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.functions;

import com.mojang.datafixers.functions.PointFree;
import com.mojang.datafixers.types.templates.RecursivePoint;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import java.util.function.Function;

final class In<A>
extends PointFree<Function<A, A>> {
    protected final RecursivePoint.RecursivePointType<A> type;

    public In(RecursivePoint.RecursivePointType<A> recursivePointType) {
        this.type = recursivePointType;
    }

    @Override
    public String toString(int n) {
        return "In[" + this.type + "]";
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        return object instanceof In && Objects.equals(this.type, ((In)object).type);
    }

    public int hashCode() {
        return Objects.hash(this.type);
    }

    @Override
    public Function<DynamicOps<?>, Function<A, A>> eval() {
        return In::lambda$eval$0;
    }

    private static Function lambda$eval$0(DynamicOps dynamicOps) {
        return Function.identity();
    }
}

