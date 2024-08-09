/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.BidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;

public interface ObjectBidirectionalIterator<K>
extends ObjectIterator<K>,
BidirectionalIterator<K> {
    default public int back(int n) {
        int n2 = n;
        while (n2-- != 0 && this.hasPrevious()) {
            this.previous();
        }
        return n - n2 - 1;
    }

    @Override
    default public int skip(int n) {
        return ObjectIterator.super.skip(n);
    }
}

