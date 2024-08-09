/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatListIterator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface FloatList
extends List<Float>,
Comparable<List<? extends Float>>,
FloatCollection {
    @Override
    public FloatListIterator iterator();

    public FloatListIterator listIterator();

    public FloatListIterator listIterator(int var1);

    public FloatList subList(int var1, int var2);

    public void size(int var1);

    public void getElements(int var1, float[] var2, int var3, int var4);

    public void removeElements(int var1, int var2);

    public void addElements(int var1, float[] var2);

    public void addElements(int var1, float[] var2, int var3, int var4);

    @Override
    public boolean add(float var1);

    @Override
    public void add(int var1, float var2);

    @Override
    @Deprecated
    default public void add(int n, Float f) {
        this.add(n, f.floatValue());
    }

    public boolean addAll(int var1, FloatCollection var2);

    public boolean addAll(int var1, FloatList var2);

    public boolean addAll(FloatList var1);

    @Override
    public float set(int var1, float var2);

    public float getFloat(int var1);

    public int indexOf(float var1);

    public int lastIndexOf(float var1);

    @Override
    @Deprecated
    default public boolean contains(Object object) {
        return FloatCollection.super.contains(object);
    }

    @Override
    @Deprecated
    default public Float get(int n) {
        return Float.valueOf(this.getFloat(n));
    }

    @Override
    @Deprecated
    default public int indexOf(Object object) {
        return this.indexOf(((Float)object).floatValue());
    }

    @Override
    @Deprecated
    default public int lastIndexOf(Object object) {
        return this.lastIndexOf(((Float)object).floatValue());
    }

    @Override
    @Deprecated
    default public boolean add(Float f) {
        return this.add(f.floatValue());
    }

    public float removeFloat(int var1);

    @Override
    @Deprecated
    default public boolean remove(Object object) {
        return FloatCollection.super.remove(object);
    }

    @Override
    @Deprecated
    default public Float remove(int n) {
        return Float.valueOf(this.removeFloat(n));
    }

    @Override
    @Deprecated
    default public Float set(int n, Float f) {
        return Float.valueOf(this.set(n, f.floatValue()));
    }

    @Override
    default public List subList(int n, int n2) {
        return this.subList(n, n2);
    }

    @Override
    default public ListIterator listIterator(int n) {
        return this.listIterator(n);
    }

    @Override
    default public ListIterator listIterator() {
        return this.listIterator();
    }

    @Override
    @Deprecated
    default public Object remove(int n) {
        return this.remove(n);
    }

    @Override
    @Deprecated
    default public void add(int n, Object object) {
        this.add(n, (Float)object);
    }

    @Override
    @Deprecated
    default public Object set(int n, Object object) {
        return this.set(n, (Float)object);
    }

    @Override
    @Deprecated
    default public Object get(int n) {
        return this.get(n);
    }

    @Override
    @Deprecated
    default public boolean add(Object object) {
        return this.add((Float)object);
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

