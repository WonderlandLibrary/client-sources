/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Pair;
import com.viaversion.viaversion.libs.fastutil.ints.IntIntImmutablePair;
import java.util.Comparator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface IntIntPair
extends Pair<Integer, Integer> {
    public int leftInt();

    @Override
    @Deprecated
    default public Integer left() {
        return this.leftInt();
    }

    default public IntIntPair left(int n) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    default public IntIntPair left(Integer n) {
        return this.left((int)n);
    }

    default public int firstInt() {
        return this.leftInt();
    }

    @Override
    @Deprecated
    default public Integer first() {
        return this.firstInt();
    }

    default public IntIntPair first(int n) {
        return this.left(n);
    }

    @Deprecated
    default public IntIntPair first(Integer n) {
        return this.first((int)n);
    }

    default public int keyInt() {
        return this.firstInt();
    }

    @Override
    @Deprecated
    default public Integer key() {
        return this.keyInt();
    }

    default public IntIntPair key(int n) {
        return this.left(n);
    }

    @Deprecated
    default public IntIntPair key(Integer n) {
        return this.key((int)n);
    }

    public int rightInt();

    @Override
    @Deprecated
    default public Integer right() {
        return this.rightInt();
    }

    default public IntIntPair right(int n) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    default public IntIntPair right(Integer n) {
        return this.right((int)n);
    }

    default public int secondInt() {
        return this.rightInt();
    }

    @Override
    @Deprecated
    default public Integer second() {
        return this.secondInt();
    }

    default public IntIntPair second(int n) {
        return this.right(n);
    }

    @Deprecated
    default public IntIntPair second(Integer n) {
        return this.second((int)n);
    }

    default public int valueInt() {
        return this.rightInt();
    }

    @Override
    @Deprecated
    default public Integer value() {
        return this.valueInt();
    }

    default public IntIntPair value(int n) {
        return this.right(n);
    }

    @Deprecated
    default public IntIntPair value(Integer n) {
        return this.value((int)n);
    }

    public static IntIntPair of(int n, int n2) {
        return new IntIntImmutablePair(n, n2);
    }

    public static Comparator<IntIntPair> lexComparator() {
        return IntIntPair::lambda$lexComparator$0;
    }

    @Override
    @Deprecated
    default public Object value() {
        return this.value();
    }

    @Override
    @Deprecated
    default public Object key() {
        return this.key();
    }

    @Override
    @Deprecated
    default public Pair value(Object object) {
        return this.value((Integer)object);
    }

    @Override
    @Deprecated
    default public Pair key(Object object) {
        return this.key((Integer)object);
    }

    @Override
    @Deprecated
    default public Pair second(Object object) {
        return this.second((Integer)object);
    }

    @Override
    @Deprecated
    default public Pair first(Object object) {
        return this.first((Integer)object);
    }

    @Override
    @Deprecated
    default public Object second() {
        return this.second();
    }

    @Override
    @Deprecated
    default public Object first() {
        return this.first();
    }

    @Override
    @Deprecated
    default public Pair right(Object object) {
        return this.right((Integer)object);
    }

    @Override
    @Deprecated
    default public Pair left(Object object) {
        return this.left((Integer)object);
    }

    @Override
    @Deprecated
    default public Object right() {
        return this.right();
    }

    @Override
    @Deprecated
    default public Object left() {
        return this.left();
    }

    private static int lambda$lexComparator$0(IntIntPair intIntPair, IntIntPair intIntPair2) {
        int n = Integer.compare(intIntPair.leftInt(), intIntPair2.leftInt());
        if (n != 0) {
            return n;
        }
        return Integer.compare(intIntPair.rightInt(), intIntPair2.rightInt());
    }
}

