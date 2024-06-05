/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Pair;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIntPair;
import java.io.Serializable;
import java.util.Objects;

public class ObjectIntImmutablePair<K>
implements ObjectIntPair<K>,
Serializable {
    private static final long serialVersionUID = 0L;
    protected final K left;
    protected final int right;

    public ObjectIntImmutablePair(K left, int right) {
        this.left = left;
        this.right = right;
    }

    public static <K> ObjectIntImmutablePair<K> of(K left, int right) {
        return new ObjectIntImmutablePair<K>(left, right);
    }

    @Override
    public K left() {
        return this.left;
    }

    @Override
    public int rightInt() {
        return this.right;
    }

    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other instanceof ObjectIntPair) {
            if (!Objects.equals(this.left, ((ObjectIntPair)other).left())) return false;
            if (this.right != ((ObjectIntPair)other).rightInt()) return false;
            return true;
        }
        if (!(other instanceof Pair)) return false;
        if (!Objects.equals(this.left, ((Pair)other).left())) return false;
        if (!Objects.equals(this.right, ((Pair)other).right())) return false;
        return true;
    }

    public int hashCode() {
        int n;
        if (this.left == null) {
            n = 0;
            return n * 19 + this.right;
        }
        n = this.left.hashCode();
        return n * 19 + this.right;
    }

    public String toString() {
        return "<" + this.left() + "," + this.rightInt() + ">";
    }
}

