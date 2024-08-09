/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.Size64;
import it.unimi.dsi.fastutil.ints.IntBigListIterator;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import java.util.Iterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface IntBigList
extends BigList<Integer>,
IntCollection,
Size64,
Comparable<BigList<? extends Integer>> {
    @Override
    public IntBigListIterator iterator();

    public IntBigListIterator listIterator();

    public IntBigListIterator listIterator(long var1);

    public IntBigList subList(long var1, long var3);

    public void getElements(long var1, int[][] var3, long var4, long var6);

    public void removeElements(long var1, long var3);

    public void addElements(long var1, int[][] var3);

    public void addElements(long var1, int[][] var3, long var4, long var6);

    @Override
    public void add(long var1, int var3);

    public boolean addAll(long var1, IntCollection var3);

    public boolean addAll(long var1, IntBigList var3);

    public boolean addAll(IntBigList var1);

    public int getInt(long var1);

    public int removeInt(long var1);

    @Override
    public int set(long var1, int var3);

    public long indexOf(int var1);

    public long lastIndexOf(int var1);

    @Override
    @Deprecated
    public void add(long var1, Integer var3);

    @Override
    @Deprecated
    public Integer get(long var1);

    @Override
    @Deprecated
    public long indexOf(Object var1);

    @Override
    @Deprecated
    public long lastIndexOf(Object var1);

    @Override
    @Deprecated
    public Integer remove(long var1);

    @Override
    @Deprecated
    public Integer set(long var1, Integer var3);

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
        this.add(l, (Integer)object);
    }

    @Override
    @Deprecated
    default public Object set(long l, Object object) {
        return this.set(l, (Integer)object);
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
    default public IntIterator iterator() {
        return this.iterator();
    }
}

