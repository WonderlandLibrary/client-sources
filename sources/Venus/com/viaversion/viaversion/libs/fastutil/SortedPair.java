/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil;

import com.viaversion.viaversion.libs.fastutil.Pair;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectObjectImmutableSortedPair;
import java.util.Objects;

public interface SortedPair<K extends Comparable<K>>
extends Pair<K, K> {
    public static <K extends Comparable<K>> SortedPair<K> of(K k, K k2) {
        return ObjectObjectImmutableSortedPair.of(k, k2);
    }

    default public boolean contains(Object object) {
        return Objects.equals(object, this.left()) || Objects.equals(object, this.right());
    }
}

