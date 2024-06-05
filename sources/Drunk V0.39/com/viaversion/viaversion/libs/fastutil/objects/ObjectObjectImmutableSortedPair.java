/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.SortedPair;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectObjectImmutablePair;
import java.io.Serializable;
import java.util.Objects;

public class ObjectObjectImmutableSortedPair<K extends Comparable<K>>
extends ObjectObjectImmutablePair<K, K>
implements SortedPair<K>,
Serializable {
    private static final long serialVersionUID = 0L;

    private ObjectObjectImmutableSortedPair(K left, K right) {
        super(left, right);
    }

    public static <K extends Comparable<K>> ObjectObjectImmutableSortedPair<K> of(K left, K right) {
        if (left.compareTo(right) > 0) return new ObjectObjectImmutableSortedPair<K>(right, left);
        return new ObjectObjectImmutableSortedPair<K>(left, right);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (!(other instanceof SortedPair)) return false;
        if (!Objects.equals(this.left, ((SortedPair)other).left())) return false;
        if (!Objects.equals(this.right, ((SortedPair)other).right())) return false;
        return true;
    }

    @Override
    public String toString() {
        return "{" + this.left() + "," + this.right() + "}";
    }
}

