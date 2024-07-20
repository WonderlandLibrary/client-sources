/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil;

import it.unimi.dsi.fastutil.PriorityQueue;

public abstract class AbstractPriorityQueue<K>
implements PriorityQueue<K> {
    @Override
    public void changed() {
        throw new UnsupportedOperationException();
    }

    @Override
    public K last() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }
}

