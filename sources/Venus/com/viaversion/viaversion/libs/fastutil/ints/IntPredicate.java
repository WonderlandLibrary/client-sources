/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.Objects;
import java.util.function.Predicate;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface IntPredicate
extends Predicate<Integer>,
java.util.function.IntPredicate {
    @Override
    @Deprecated
    default public boolean test(Integer n) {
        return this.test(n.intValue());
    }

    @Override
    default public IntPredicate and(java.util.function.IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        return arg_0 -> this.lambda$and$0(intPredicate, arg_0);
    }

    default public IntPredicate and(IntPredicate intPredicate) {
        return this.and((java.util.function.IntPredicate)intPredicate);
    }

    @Override
    @Deprecated
    default public Predicate<Integer> and(Predicate<? super Integer> predicate) {
        return Predicate.super.and(predicate);
    }

    @Override
    default public IntPredicate negate() {
        return this::lambda$negate$1;
    }

    @Override
    default public IntPredicate or(java.util.function.IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        return arg_0 -> this.lambda$or$2(intPredicate, arg_0);
    }

    default public IntPredicate or(IntPredicate intPredicate) {
        return this.or((java.util.function.IntPredicate)intPredicate);
    }

    @Override
    @Deprecated
    default public Predicate<Integer> or(Predicate<? super Integer> predicate) {
        return Predicate.super.or(predicate);
    }

    @Override
    default public Predicate negate() {
        return this.negate();
    }

    @Override
    @Deprecated
    default public boolean test(Object object) {
        return this.test((Integer)object);
    }

    @Override
    default public java.util.function.IntPredicate or(java.util.function.IntPredicate intPredicate) {
        return this.or(intPredicate);
    }

    @Override
    default public java.util.function.IntPredicate negate() {
        return this.negate();
    }

    @Override
    default public java.util.function.IntPredicate and(java.util.function.IntPredicate intPredicate) {
        return this.and(intPredicate);
    }

    private boolean lambda$or$2(java.util.function.IntPredicate intPredicate, int n) {
        return this.test(n) || intPredicate.test(n);
    }

    private boolean lambda$negate$1(int n) {
        return !this.test(n);
    }

    private boolean lambda$and$0(java.util.function.IntPredicate intPredicate, int n) {
        return this.test(n) && intPredicate.test(n);
    }
}

