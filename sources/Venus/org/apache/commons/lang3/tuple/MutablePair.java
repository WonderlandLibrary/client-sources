/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.tuple;

import org.apache.commons.lang3.tuple.Pair;

public class MutablePair<L, R>
extends Pair<L, R> {
    private static final long serialVersionUID = 4954918890077093841L;
    public L left;
    public R right;

    public static <L, R> MutablePair<L, R> of(L l, R r) {
        return new MutablePair<L, R>(l, r);
    }

    public MutablePair() {
    }

    public MutablePair(L l, R r) {
        this.left = l;
        this.right = r;
    }

    @Override
    public L getLeft() {
        return this.left;
    }

    public void setLeft(L l) {
        this.left = l;
    }

    @Override
    public R getRight() {
        return this.right;
    }

    public void setRight(R r) {
        this.right = r;
    }

    @Override
    public R setValue(R r) {
        R r2 = this.getRight();
        this.setRight(r);
        return r2;
    }
}

