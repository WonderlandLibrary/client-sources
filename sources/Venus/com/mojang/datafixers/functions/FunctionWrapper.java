/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.functions;

import com.mojang.datafixers.functions.PointFree;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import java.util.function.Function;

final class FunctionWrapper<A, B>
extends PointFree<Function<A, B>> {
    private final String name;
    protected final Function<DynamicOps<?>, Function<A, B>> fun;

    FunctionWrapper(String string, Function<DynamicOps<?>, Function<A, B>> function) {
        this.name = string;
        this.fun = function;
    }

    @Override
    public String toString(int n) {
        return "fun[" + this.name + "]";
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        FunctionWrapper functionWrapper = (FunctionWrapper)object;
        return Objects.equals(this.fun, functionWrapper.fun);
    }

    public int hashCode() {
        return Objects.hash(this.fun);
    }

    @Override
    public Function<DynamicOps<?>, Function<A, B>> eval() {
        return this.fun;
    }
}

