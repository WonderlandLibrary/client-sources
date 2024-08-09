/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.tuple;

import org.apache.commons.lang3.tuple.Triple;

public class MutableTriple<L, M, R>
extends Triple<L, M, R> {
    private static final long serialVersionUID = 1L;
    public L left;
    public M middle;
    public R right;

    public static <L, M, R> MutableTriple<L, M, R> of(L l, M m, R r) {
        return new MutableTriple<L, M, R>(l, m, r);
    }

    public MutableTriple() {
    }

    public MutableTriple(L l, M m, R r) {
        this.left = l;
        this.middle = m;
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
    public M getMiddle() {
        return this.middle;
    }

    public void setMiddle(M m) {
        this.middle = m;
    }

    @Override
    public R getRight() {
        return this.right;
    }

    public void setRight(R r) {
        this.right = r;
    }
}

