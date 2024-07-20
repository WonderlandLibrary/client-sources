/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import java.util.List;

public interface IntList
extends List<Integer>,
Comparable<List<? extends Integer>>,
IntCollection {
    @Override
    public IntListIterator iterator();

    @Deprecated
    public IntListIterator intListIterator();

    @Deprecated
    public IntListIterator intListIterator(int var1);

    public IntListIterator listIterator();

    public IntListIterator listIterator(int var1);

    @Deprecated
    public IntList intSubList(int var1, int var2);

    public IntList subList(int var1, int var2);

    public void size(int var1);

    public void getElements(int var1, int[] var2, int var3, int var4);

    public void removeElements(int var1, int var2);

    public void addElements(int var1, int[] var2);

    public void addElements(int var1, int[] var2, int var3, int var4);

    @Override
    public boolean add(int var1);

    @Override
    public void add(int var1, int var2);

    public boolean addAll(int var1, IntCollection var2);

    public boolean addAll(int var1, IntList var2);

    public boolean addAll(IntList var1);

    public int getInt(int var1);

    public int indexOf(int var1);

    public int lastIndexOf(int var1);

    public int removeInt(int var1);

    @Override
    public int set(int var1, int var2);
}

