/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntIterable;
import it.unimi.dsi.fastutil.ints.IntIterator;
import java.util.Collection;

public interface IntCollection
extends Collection<Integer>,
IntIterable {
    @Override
    public IntIterator iterator();

    @Deprecated
    public IntIterator intIterator();

    @Override
    public <T> T[] toArray(T[] var1);

    public boolean contains(int var1);

    public int[] toIntArray();

    public int[] toIntArray(int[] var1);

    public int[] toArray(int[] var1);

    @Override
    public boolean add(int var1);

    public boolean rem(int var1);

    public boolean addAll(IntCollection var1);

    public boolean containsAll(IntCollection var1);

    public boolean removeAll(IntCollection var1);

    public boolean retainAll(IntCollection var1);
}

