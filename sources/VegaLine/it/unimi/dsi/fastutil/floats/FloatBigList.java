/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.floats.FloatBigListIterator;
import it.unimi.dsi.fastutil.floats.FloatCollection;

public interface FloatBigList
extends BigList<Float>,
FloatCollection,
Comparable<BigList<? extends Float>> {
    @Override
    public FloatBigListIterator iterator();

    public FloatBigListIterator listIterator();

    public FloatBigListIterator listIterator(long var1);

    public FloatBigList subList(long var1, long var3);

    public void getElements(long var1, float[][] var3, long var4, long var6);

    public void removeElements(long var1, long var3);

    public void addElements(long var1, float[][] var3);

    public void addElements(long var1, float[][] var3, long var4, long var6);

    @Override
    public void add(long var1, float var3);

    public boolean addAll(long var1, FloatCollection var3);

    public boolean addAll(long var1, FloatBigList var3);

    public boolean addAll(FloatBigList var1);

    public float getFloat(long var1);

    public long indexOf(float var1);

    public long lastIndexOf(float var1);

    public float removeFloat(long var1);

    @Override
    public float set(long var1, float var3);
}

