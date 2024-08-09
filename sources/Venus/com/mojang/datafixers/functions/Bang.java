/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.functions;

import com.mojang.datafixers.functions.PointFree;
import com.mojang.serialization.DynamicOps;
import java.util.function.Function;

final class Bang<A>
extends PointFree<Function<A, Void>> {
    Bang() {
    }

    @Override
    public String toString(int n) {
        return "!";
    }

    public boolean equals(Object object) {
        return object instanceof Bang;
    }

    public int hashCode() {
        return Bang.class.hashCode();
    }

    @Override
    public Function<DynamicOps<?>, Function<A, Void>> eval() {
        return Bang::lambda$eval$1;
    }

    private static Function lambda$eval$1(DynamicOps dynamicOps) {
        return Bang::lambda$null$0;
    }

    private static Void lambda$null$0(Object object) {
        return null;
    }
}

