/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;

public abstract class AbstractObjectListIterator<K>
extends AbstractObjectBidirectionalIterator<K>
implements ObjectListIterator<K> {
    protected AbstractObjectListIterator() {
    }

    @Override
    public void set(K k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(K k) {
        throw new UnsupportedOperationException();
    }
}

