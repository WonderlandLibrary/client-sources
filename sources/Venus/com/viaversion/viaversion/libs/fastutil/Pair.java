/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectObjectImmutablePair;
import java.util.Comparator;

public interface Pair<L, R> {
    public L left();

    public R right();

    default public Pair<L, R> left(L l) {
        throw new UnsupportedOperationException();
    }

    default public Pair<L, R> right(R r) {
        throw new UnsupportedOperationException();
    }

    default public L first() {
        return this.left();
    }

    default public R second() {
        return this.right();
    }

    default public Pair<L, R> first(L l) {
        return this.left(l);
    }

    default public Pair<L, R> second(R r) {
        return this.right(r);
    }

    default public Pair<L, R> key(L l) {
        return this.left(l);
    }

    default public Pair<L, R> value(R r) {
        return this.right(r);
    }

    default public L key() {
        return this.left();
    }

    default public R value() {
        return this.right();
    }

    public static <L, R> Pair<L, R> of(L l, R r) {
        return new ObjectObjectImmutablePair<L, R>(l, r);
    }

    public static <L, R> Comparator<Pair<L, R>> lexComparator() {
        return Pair::lambda$lexComparator$0;
    }

    private static int lambda$lexComparator$0(Pair pair, Pair pair2) {
        int n = ((Comparable)pair.left()).compareTo(pair2.left());
        if (n != 0) {
            return n;
        }
        return ((Comparable)pair.right()).compareTo(pair2.right());
    }
}

