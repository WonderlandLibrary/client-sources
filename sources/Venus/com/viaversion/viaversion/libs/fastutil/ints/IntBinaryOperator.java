/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.function.BinaryOperator;

@FunctionalInterface
public interface IntBinaryOperator
extends BinaryOperator<Integer>,
java.util.function.IntBinaryOperator {
    @Override
    public int apply(int var1, int var2);

    @Override
    @Deprecated
    default public int applyAsInt(int n, int n2) {
        return this.apply(n, n2);
    }

    @Override
    @Deprecated
    default public Integer apply(Integer n, Integer n2) {
        return this.apply((int)n, (int)n2);
    }

    @Override
    @Deprecated
    default public Object apply(Object object, Object object2) {
        return this.apply((Integer)object, (Integer)object2);
    }
}

