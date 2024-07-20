/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil;

import java.util.Comparator;

public interface IndirectPriorityQueue<K> {
    public void enqueue(int var1);

    public int dequeue();

    public boolean isEmpty();

    public int size();

    public void clear();

    public int first();

    public int last();

    public void changed();

    public Comparator<? super K> comparator();

    public void changed(int var1);

    public void allChanged();

    public boolean contains(int var1);

    public boolean remove(int var1);

    public int front(int[] var1);
}

