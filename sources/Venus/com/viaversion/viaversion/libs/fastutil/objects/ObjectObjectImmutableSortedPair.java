/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
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

    private ObjectObjectImmutableSortedPair(K k, K k2) {
        super(k, k2);
    }

    public static <K extends Comparable<K>> ObjectObjectImmutableSortedPair<K> of(K k, K k2) {
        if (k.compareTo(k2) <= 0) {
            return new ObjectObjectImmutableSortedPair<K>(k, k2);
        }
        return new ObjectObjectImmutableSortedPair<K>(k2, k);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof SortedPair) {
            return Objects.equals(this.left, ((SortedPair)object).left()) && Objects.equals(this.right, ((SortedPair)object).right());
        }
        return true;
    }

    @Override
    public String toString() {
        return "{" + this.left() + "," + this.right() + "}";
    }
}

