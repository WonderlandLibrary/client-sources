/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.fastutil;

import com.viaversion.viaversion.libs.fastutil.Pair;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectObjectImmutableSortedPair;
import java.util.Objects;

public interface SortedPair<K extends Comparable<K>>
extends Pair<K, K> {
    public static <K extends Comparable<K>> SortedPair<K> of(K l, K r) {
        return ObjectObjectImmutableSortedPair.of(l, r);
    }

    default public boolean contains(Object o) {
        if (Objects.equals(o, this.left())) return true;
        if (Objects.equals(o, this.right())) return true;
        return false;
    }
}

