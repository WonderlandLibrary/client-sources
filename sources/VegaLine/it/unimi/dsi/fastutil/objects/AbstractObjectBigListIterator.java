/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectBigListIterator;

public abstract class AbstractObjectBigListIterator<K>
extends AbstractObjectBidirectionalIterator<K>
implements ObjectBigListIterator<K> {
    protected AbstractObjectBigListIterator() {
    }

    @Override
    public void set(K k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(K k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long skip(long n) {
        long i = n;
        while (i-- != 0L && this.hasNext()) {
            this.next();
        }
        return n - i - 1L;
    }

    public long back(long n) {
        long i = n;
        while (i-- != 0L && this.hasPrevious()) {
            this.previous();
        }
        return n - i - 1L;
    }
}

