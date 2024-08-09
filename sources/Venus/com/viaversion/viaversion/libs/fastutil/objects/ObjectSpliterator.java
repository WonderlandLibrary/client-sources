/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.Spliterator;

public interface ObjectSpliterator<K>
extends Spliterator<K> {
    default public long skip(long l) {
        if (l < 0L) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + l);
        }
        long l2 = l;
        while (l2-- != 0L && this.tryAdvance(ObjectSpliterator::lambda$skip$0)) {
        }
        return l - l2 - 1L;
    }

    @Override
    public ObjectSpliterator<K> trySplit();

    @Override
    default public Spliterator trySplit() {
        return this.trySplit();
    }

    private static void lambda$skip$0(Object object) {
    }
}

