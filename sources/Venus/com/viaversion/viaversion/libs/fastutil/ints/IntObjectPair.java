/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Pair;
import com.viaversion.viaversion.libs.fastutil.ints.IntObjectImmutablePair;
import java.util.Comparator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface IntObjectPair<V>
extends Pair<Integer, V> {
    public int leftInt();

    @Override
    @Deprecated
    default public Integer left() {
        return this.leftInt();
    }

    default public IntObjectPair<V> left(int n) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    default public IntObjectPair<V> left(Integer n) {
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

    default public IntObjectPair<V> first(int n) {
        return this.left(n);
    }

    @Deprecated
    default public IntObjectPair<V> first(Integer n) {
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

    default public IntObjectPair<V> key(int n) {
        return this.left(n);
    }

    @Deprecated
    default public IntObjectPair<V> key(Integer n) {
        return this.key((int)n);
    }

    public static <V> IntObjectPair<V> of(int n, V v) {
        return new IntObjectImmutablePair<V>(n, v);
    }

    public static <V> Comparator<IntObjectPair<V>> lexComparator() {
        return IntObjectPair::lambda$lexComparator$0;
    }

    @Override
    @Deprecated
    default public Object key() {
        return this.key();
    }

    @Override
    @Deprecated
    default public Pair key(Object object) {
        return this.key((Integer)object);
    }

    @Override
    @Deprecated
    default public Pair first(Object object) {
        return this.first((Integer)object);
    }

    @Override
    @Deprecated
    default public Object first() {
        return this.first();
    }

    @Override
    @Deprecated
    default public Pair left(Object object) {
        return this.left((Integer)object);
    }

    @Override
    @Deprecated
    default public Object left() {
        return this.left();
    }

    private static int lambda$lexComparator$0(IntObjectPair intObjectPair, IntObjectPair intObjectPair2) {
        int n = Integer.compare(intObjectPair.leftInt(), intObjectPair2.leftInt());
        if (n != 0) {
            return n;
        }
        return ((Comparable)intObjectPair.right()).compareTo(intObjectPair2.right());
    }
}

