/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface IntList
extends List<Integer>,
Comparable<List<? extends Integer>>,
IntCollection {
    @Override
    public IntListIterator iterator();

    public IntListIterator listIterator();

    public IntListIterator listIterator(int var1);

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

    @Override
    @Deprecated
    default public void add(int n, Integer n2) {
        this.add(n, (int)n2);
    }

    public boolean addAll(int var1, IntCollection var2);

    public boolean addAll(int var1, IntList var2);

    public boolean addAll(IntList var1);

    @Override
    public int set(int var1, int var2);

    public int getInt(int var1);

    public int indexOf(int var1);

    public int lastIndexOf(int var1);

    @Override
    @Deprecated
    default public boolean contains(Object object) {
        return IntCollection.super.contains(object);
    }

    @Override
    @Deprecated
    default public Integer get(int n) {
        return this.getInt(n);
    }

    @Override
    @Deprecated
    default public int indexOf(Object object) {
        return this.indexOf((Integer)object);
    }

    @Override
    @Deprecated
    default public int lastIndexOf(Object object) {
        return this.lastIndexOf((Integer)object);
    }

    @Override
    @Deprecated
    default public boolean add(Integer n) {
        return this.add((int)n);
    }

    public int removeInt(int var1);

    @Override
    @Deprecated
    default public boolean remove(Object object) {
        return IntCollection.super.remove(object);
    }

    @Override
    @Deprecated
    default public Integer remove(int n) {
        return this.removeInt(n);
    }

    @Override
    @Deprecated
    default public Integer set(int n, Integer n2) {
        return this.set(n, (int)n2);
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
        this.add(n, (Integer)object);
    }

    @Override
    @Deprecated
    default public Object set(int n, Object object) {
        return this.set(n, (Integer)object);
    }

    @Override
    @Deprecated
    default public Object get(int n) {
        return this.get(n);
    }

    @Override
    @Deprecated
    default public boolean add(Object object) {
        return this.add((Integer)object);
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }

    @Override
    default public IntIterator iterator() {
        return this.iterator();
    }
}

