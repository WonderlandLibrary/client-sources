/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.LongIterable;
import it.unimi.dsi.fastutil.longs.LongIterator;
import java.util.Collection;

public interface LongCollection
extends Collection<Long>,
LongIterable {
    @Override
    public LongIterator iterator();

    @Deprecated
    public LongIterator longIterator();

    @Override
    public <T> T[] toArray(T[] var1);

    public boolean contains(long var1);

    public long[] toLongArray();

    public long[] toLongArray(long[] var1);

    public long[] toArray(long[] var1);

    @Override
    public boolean add(long var1);

    public boolean rem(long var1);

    public boolean addAll(LongCollection var1);

    public boolean containsAll(LongCollection var1);

    public boolean removeAll(LongCollection var1);

    public boolean retainAll(LongCollection var1);
}

