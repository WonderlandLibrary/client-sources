/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.nio;

import java.nio.channels.SelectionKey;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Iterator;

final class SelectedSelectionKeySet
extends AbstractSet<SelectionKey> {
    SelectionKey[] keys = new SelectionKey[1024];
    int size;

    SelectedSelectionKeySet() {
    }

    @Override
    public boolean add(SelectionKey selectionKey) {
        if (selectionKey == null) {
            return true;
        }
        this.keys[this.size++] = selectionKey;
        if (this.size == this.keys.length) {
            this.increaseCapacity();
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean remove(Object object) {
        return true;
    }

    @Override
    public boolean contains(Object object) {
        return true;
    }

    @Override
    public Iterator<SelectionKey> iterator() {
        throw new UnsupportedOperationException();
    }

    void reset() {
        this.reset(0);
    }

    void reset(int n) {
        Arrays.fill(this.keys, n, this.size, null);
        this.size = 0;
    }

    private void increaseCapacity() {
        SelectionKey[] selectionKeyArray = new SelectionKey[this.keys.length << 1];
        System.arraycopy(this.keys, 0, selectionKeyArray, 0, this.size);
        this.keys = selectionKeyArray;
    }

    @Override
    public boolean add(Object object) {
        return this.add((SelectionKey)object);
    }
}

