/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Pair;
import com.viaversion.viaversion.libs.fastutil.ints.IntObjectPair;
import java.io.Serializable;
import java.util.Objects;

public class IntObjectImmutablePair<V>
implements IntObjectPair<V>,
Serializable {
    private static final long serialVersionUID = 0L;
    protected final int left;
    protected final V right;

    public IntObjectImmutablePair(int n, V v) {
        this.left = n;
        this.right = v;
    }

    public static <V> IntObjectImmutablePair<V> of(int n, V v) {
        return new IntObjectImmutablePair<V>(n, v);
    }

    @Override
    public int leftInt() {
        return this.left;
    }

    @Override
    public V right() {
        return this.right;
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
}

