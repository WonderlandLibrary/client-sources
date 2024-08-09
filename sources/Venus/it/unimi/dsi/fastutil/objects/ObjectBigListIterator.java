/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

public interface ObjectBigListIterator<K>
extends ObjectBidirectionalIterator<K>,
BigListIterator<K> {
    @Override
    default public void set(K k) {
        throw new UnsupportedOperationException();
    }

    @Override
    default public void add(K k) {
        throw new UnsupportedOperationException();
    }

    default public long skip(long l) {
        long l2 = l;
        while (l2-- != 0L && this.hasNext()) {
            this.next();
        }
        return l - l2 - 1L;
    }

    default public long back(long l) {
        long l2 = l;
        while (l2-- != 0L && this.hasPrevious()) {
            this.previous();
        }
        return l - l2 - 1L;
    }

    @Override
    default public int skip(int n) {
        return SafeMath.safeLongToInt(this.skip((long)n));
    }
}

