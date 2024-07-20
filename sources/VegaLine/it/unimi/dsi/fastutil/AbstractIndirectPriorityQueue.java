/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil;

import it.unimi.dsi.fastutil.IndirectPriorityQueue;

public abstract class AbstractIndirectPriorityQueue<K>
implements IndirectPriorityQueue<K> {
    @Override
    public int last() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void changed() {
        this.changed(this.first());
    }

    @Override
    public void changed(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void allChanged() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }
}

