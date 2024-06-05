/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Pair;
import com.viaversion.viaversion.libs.fastutil.ints.IntObjectPair;
import java.io.Serializable;
import java.util.Objects;

public class IntObjectMutablePair<V>
implements IntObjectPair<V>,
Serializable {
    private static final long serialVersionUID = 0L;
    protected int left;
    protected V right;

    public IntObjectMutablePair(int left, V right) {
        this.left = left;
        this.right = right;
    }

    public static <V> IntObjectMutablePair<V> of(int left, V right) {
        return new IntObjectMutablePair<V>(left, right);
    }

    @Override
    public int leftInt() {
        return this.left;
    }

    @Override
    public IntObjectMutablePair<V> left(int l) {
        this.left = l;
        return this;
    }

    @Override
    public V right() {
        return this.right;
    }

    public IntObjectMutablePair<V> right(V r) {
        this.right = r;
        return this;
    }

    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other instanceof IntObjectPair) {
            if (this.left != ((IntObjectPair)other).leftInt()) return false;
            if (!Objects.equals(this.right, ((IntObjectPair)other).right())) return false;
            return true;
        }
        if (!(other instanceof Pair)) return false;
        if (!Objects.equals(this.left, ((Pair)other).left())) return false;
        if (!Objects.equals(this.right, ((Pair)other).right())) return false;
        return true;
    }

    public int hashCode() {
        int n;
        if (this.right == null) {
            n = 0;
            return this.left * 19 + n;
        }
        n = this.right.hashCode();
        return this.left * 19 + n;
    }

    public String toString() {
        return "<" + this.leftInt() + "," + this.right() + ">";
    }
}

