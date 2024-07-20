/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

public abstract class AbstractObjectBidirectionalIterator<K>
extends AbstractObjectIterator<K>
implements ObjectBidirectionalIterator<K> {
    protected AbstractObjectBidirectionalIterator() {
    }

    @Override
    public int back(int n) {
        int i = n;
        while (i-- != 0 && this.hasPrevious()) {
            this.previous();
        }
        return n - i - 1;
    }
}

