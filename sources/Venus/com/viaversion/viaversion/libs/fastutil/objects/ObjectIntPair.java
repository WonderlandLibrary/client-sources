/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Pair;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIntImmutablePair;
import java.util.Comparator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface ObjectIntPair<K>
extends Pair<K, Integer> {
    public int rightInt();

    @Override
    @Deprecated
    default public Integer right() {
        return this.rightInt();
    }

    default public ObjectIntPair<K> right(int n) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    default public ObjectIntPair<K> right(Integer n) {
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

    default public ObjectIntPair<K> second(int n) {
        return this.right(n);
    }

    @Deprecated
    default public ObjectIntPair<K> second(Integer n) {
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

    default public ObjectIntPair<K> value(int n) {
        return this.right(n);
    }

    @Deprecated
    default public ObjectIntPair<K> value(Integer n) {
        return this.value((int)n);
    }

    public static <K> ObjectIntPair<K> of(K k, int n) {
        return new ObjectIntImmutablePair<K>(k, n);
    }

    public static <K> Comparator<ObjectIntPair<K>> lexComparator() {
        return ObjectIntPair::lambda$lexComparator$0;
    }

    @Override
    @Deprecated
    default public Object value() {
        return this.value();
    }

    @Override
    @Deprecated
    default public Pair value(Object object) {
        return this.value((Integer)object);
    }

    @Override
    @Deprecated
    default public Pair second(Object object) {
        return this.second((Integer)object);
    }

    @Override
    @Deprecated
    default public Object second() {
        return this.second();
    }

    @Override
    @Deprecated
    default public Pair right(Object object) {
        return this.right((Integer)object);
    }

    @Override
    @Deprecated
    default public Object right() {
        return this.right();
    }

    private static int lambda$lexComparator$0(ObjectIntPair objectIntPair, ObjectIntPair objectIntPair2) {
        int n = ((Comparable)objectIntPair.left()).compareTo(objectIntPair2.left());
        if (n != 0) {
            return n;
        }
        return Integer.compare(objectIntPair.rightInt(), objectIntPair2.rightInt());
    }
}

