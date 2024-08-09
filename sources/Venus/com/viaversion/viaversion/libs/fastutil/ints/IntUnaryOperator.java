/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.function.UnaryOperator;

@FunctionalInterface
public interface IntUnaryOperator
extends UnaryOperator<Integer>,
java.util.function.IntUnaryOperator {
    @Override
    public int apply(int var1);

    public static IntUnaryOperator identity() {
        return IntUnaryOperator::lambda$identity$0;
    }

    public static IntUnaryOperator negation() {
        return IntUnaryOperator::lambda$negation$1;
    }

    @Override
    @Deprecated
    default public int applyAsInt(int n) {
        return this.apply(n);
    }

    @Override
    @Deprecated
    default public Integer apply(Integer n) {
        return this.apply((int)n);
    }

    @Override
    @Deprecated
    default public Object apply(Object object) {
        return this.apply((Integer)object);
    }

    private static int lambda$negation$1(int n) {
        return -n;
    }

    private static int lambda$identity$0(int n) {
        return n;
    }
}

