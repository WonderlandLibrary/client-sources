/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
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

    public IntObjectMutablePair(int n, V v) {
        this.left = n;
        this.right = v;
    }

    public static <V> IntObjectMutablePair<V> of(int n, V v) {
        return new IntObjectMutablePair<V>(n, v);
    }

    @Override
    public int leftInt() {
        return this.left;
    }

    @Override
    public IntObjectMutablePair<V> left(int n) {
        this.left = n;
        return this;
    }

    @Override
    public V right() {
        return this.right;
    }

    public IntObjectMutablePair<V> right(V v) {
        this.right = v;
        return this;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof IntObjectPair) {
            return this.left == ((IntObjectPair)object).leftInt() && Objects.equals(this.right, ((IntObjectPair)object).right());
        }
        if (object instanceof Pair) {
            return Objects.equals(this.left, ((Pair)object).left()) && Objects.equals(this.right, ((Pair)object).right());
        }
        return true;
    }

    public int hashCode() {
        return this.left * 19 + (this.right == null ? 0 : this.right.hashCode());
    }

    public String toString() {
        return "<" + this.leftInt() + "," + this.right() + ">";
    }

    @Override
    public IntObjectPair left(int n) {
        return this.left(n);
    }

    @Override
    public Pair right(Object object) {
        return this.right(object);
    }
}

