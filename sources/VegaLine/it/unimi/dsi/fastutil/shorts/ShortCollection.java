/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.ShortIterable;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import java.util.Collection;

public interface ShortCollection
extends Collection<Short>,
ShortIterable {
    @Override
    public ShortIterator iterator();

    @Deprecated
    public ShortIterator shortIterator();

    @Override
    public <T> T[] toArray(T[] var1);

    public boolean contains(short var1);

    public short[] toShortArray();

    public short[] toShortArray(short[] var1);

    public short[] toArray(short[] var1);

    @Override
    public boolean add(short var1);

    public boolean rem(short var1);

    public boolean addAll(ShortCollection var1);

    public boolean containsAll(ShortCollection var1);

    public boolean removeAll(ShortCollection var1);

    public boolean retainAll(ShortCollection var1);
}

