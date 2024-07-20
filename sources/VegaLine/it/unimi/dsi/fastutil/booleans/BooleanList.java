/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanListIterator;
import java.util.List;

public interface BooleanList
extends List<Boolean>,
Comparable<List<? extends Boolean>>,
BooleanCollection {
    @Override
    public BooleanListIterator iterator();

    @Deprecated
    public BooleanListIterator booleanListIterator();

    @Deprecated
    public BooleanListIterator booleanListIterator(int var1);

    public BooleanListIterator listIterator();

    public BooleanListIterator listIterator(int var1);

    @Deprecated
    public BooleanList booleanSubList(int var1, int var2);

    public BooleanList subList(int var1, int var2);

    public void size(int var1);

    public void getElements(int var1, boolean[] var2, int var3, int var4);

    public void removeElements(int var1, int var2);

    public void addElements(int var1, boolean[] var2);

    public void addElements(int var1, boolean[] var2, int var3, int var4);

    @Override
    public boolean add(boolean var1);

    @Override
    public void add(int var1, boolean var2);

    public boolean addAll(int var1, BooleanCollection var2);

    public boolean addAll(int var1, BooleanList var2);

    public boolean addAll(BooleanList var1);

    public boolean getBoolean(int var1);

    public int indexOf(boolean var1);

    public int lastIndexOf(boolean var1);

    public boolean removeBoolean(int var1);

    @Override
    public boolean set(int var1, boolean var2);
}

