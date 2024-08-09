/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.Size64;
import it.unimi.dsi.fastutil.floats.FloatBigListIterator;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import java.util.Iterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface FloatBigList
extends BigList<Float>,
FloatCollection,
Size64,
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

    public float removeFloat(long var1);

    @Override
    public float set(long var1, float var3);

    public long indexOf(float var1);

    public long lastIndexOf(float var1);

    @Override
    @Deprecated
    public void add(long var1, Float var3);

    @Override
    @Deprecated
    public Float get(long var1);

    @Override
    @Deprecated
    public long indexOf(Object var1);

    @Override
    @Deprecated
    public long lastIndexOf(Object var1);

    @Override
    @Deprecated
    public Float remove(long var1);

    @Override
    @Deprecated
    public Float set(long var1, Float var3);

    @Override
    default public BigList subList(long l, long l2) {
        return this.subList(l, l2);
    }

    @Override
    default public BigListIterator listIterator(long l) {
        return this.listIterator(l);
    }

    @Override
    default public BigListIterator listIterator() {
        return this.listIterator();
    }

    @Override
    @Deprecated
    default public void add(long l, Object object) {
        this.add(l, (Float)object);
    }

    @Override
    @Deprecated
    default public Object set(long l, Object object) {
        return this.set(l, (Float)object);
    }

    @Override
    @Deprecated
    default public Object remove(long l) {
        return this.remove(l);
    }

    @Override
    @Deprecated
    default public Object get(long l) {
        return this.get(l);
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }

    @Override
    default public FloatIterator iterator() {
        return this.iterator();
    }
}

