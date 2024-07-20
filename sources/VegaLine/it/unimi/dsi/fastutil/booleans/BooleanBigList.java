/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.booleans.BooleanBigListIterator;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;

public interface BooleanBigList
extends BigList<Boolean>,
BooleanCollection,
Comparable<BigList<? extends Boolean>> {
    @Override
    public BooleanBigListIterator iterator();

    public BooleanBigListIterator listIterator();

    public BooleanBigListIterator listIterator(long var1);

    public BooleanBigList subList(long var1, long var3);

    public void getElements(long var1, boolean[][] var3, long var4, long var6);

    public void removeElements(long var1, long var3);

    public void addElements(long var1, boolean[][] var3);

    public void addElements(long var1, boolean[][] var3, long var4, long var6);

    @Override
    public void add(long var1, boolean var3);

    public boolean addAll(long var1, BooleanCollection var3);

    public boolean addAll(long var1, BooleanBigList var3);

    public boolean addAll(BooleanBigList var1);

    public boolean getBoolean(long var1);

    public long indexOf(boolean var1);

    public long lastIndexOf(boolean var1);

    public boolean removeBoolean(long var1);

    @Override
    public boolean set(long var1, boolean var3);
}

