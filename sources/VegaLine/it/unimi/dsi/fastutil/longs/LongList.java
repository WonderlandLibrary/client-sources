/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongListIterator;
import java.util.List;

public interface LongList
extends List<Long>,
Comparable<List<? extends Long>>,
LongCollection {
    @Override
    public LongListIterator iterator();

    @Deprecated
    public LongListIterator longListIterator();

    @Deprecated
    public LongListIterator longListIterator(int var1);

    public LongListIterator listIterator();

    public LongListIterator listIterator(int var1);

    @Deprecated
    public LongList longSubList(int var1, int var2);

    public LongList subList(int var1, int var2);

    public void size(int var1);

    public void getElements(int var1, long[] var2, int var3, int var4);

    public void removeElements(int var1, int var2);

    public void addElements(int var1, long[] var2);

    public void addElements(int var1, long[] var2, int var3, int var4);

    @Override
    public boolean add(long var1);

    @Override
    public void add(int var1, long var2);

    public boolean addAll(int var1, LongCollection var2);

    public boolean addAll(int var1, LongList var2);

    public boolean addAll(LongList var1);

    public long getLong(int var1);

    public int indexOf(long var1);

    public int lastIndexOf(long var1);

    public long removeLong(int var1);

    @Override
    public long set(int var1, long var2);
}

