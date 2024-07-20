/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortListIterator;
import java.util.List;

public interface ShortList
extends List<Short>,
Comparable<List<? extends Short>>,
ShortCollection {
    @Override
    public ShortListIterator iterator();

    @Deprecated
    public ShortListIterator shortListIterator();

    @Deprecated
    public ShortListIterator shortListIterator(int var1);

    public ShortListIterator listIterator();

    public ShortListIterator listIterator(int var1);

    @Deprecated
    public ShortList shortSubList(int var1, int var2);

    public ShortList subList(int var1, int var2);

    public void size(int var1);

    public void getElements(int var1, short[] var2, int var3, int var4);

    public void removeElements(int var1, int var2);

    public void addElements(int var1, short[] var2);

    public void addElements(int var1, short[] var2, int var3, int var4);

    @Override
    public boolean add(short var1);

    @Override
    public void add(int var1, short var2);

    public boolean addAll(int var1, ShortCollection var2);

    public boolean addAll(int var1, ShortList var2);

    public boolean addAll(ShortList var1);

    public short getShort(int var1);

    public int indexOf(short var1);

    public int lastIndexOf(short var1);

    public short removeShort(int var1);

    @Override
    public short set(int var1, short var2);
}

