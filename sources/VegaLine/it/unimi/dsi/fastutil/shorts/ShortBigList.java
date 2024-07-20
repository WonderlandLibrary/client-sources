/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.shorts.ShortBigListIterator;
import it.unimi.dsi.fastutil.shorts.ShortCollection;

public interface ShortBigList
extends BigList<Short>,
ShortCollection,
Comparable<BigList<? extends Short>> {
    @Override
    public ShortBigListIterator iterator();

    public ShortBigListIterator listIterator();

    public ShortBigListIterator listIterator(long var1);

    public ShortBigList subList(long var1, long var3);

    public void getElements(long var1, short[][] var3, long var4, long var6);

    public void removeElements(long var1, long var3);

    public void addElements(long var1, short[][] var3);

    public void addElements(long var1, short[][] var3, long var4, long var6);

    @Override
    public void add(long var1, short var3);

    public boolean addAll(long var1, ShortCollection var3);

    public boolean addAll(long var1, ShortBigList var3);

    public boolean addAll(ShortBigList var1);

    public short getShort(long var1);

    public long indexOf(short var1);

    public long lastIndexOf(short var1);

    public short removeShort(long var1);

    @Override
    public short set(long var1, short var3);
}

