/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongListIterator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface LongList
extends List<Long>,
Comparable<List<? extends Long>>,
LongCollection {
    @Override
    public LongListIterator iterator();

    public LongListIterator listIterator();

    public LongListIterator listIterator(int var1);

    public LongList subList(int var1, int var2);

    public void size(int var1);

    public void getElements(int var1, long[] var2, int var3, int var4);

    public void removeElements(int var1, int var2);

    public void addElements(int var1, long[] var2);

    public void addElements(int var1, long[] var2, int var3, int var4);

    @Override
    public boolean add(long var1);

    @Override
    public void add(int var1, long var2);

    @Override
    @Deprecated
    default public void add(int n, Long l) {
        this.add(n, (long)l);
    }

    public boolean addAll(int var1, LongCollection var2);

    public boolean addAll(int var1, LongList var2);

    public boolean addAll(LongList var1);

    @Override
    public long set(int var1, long var2);

    public long getLong(int var1);

    public int indexOf(long var1);

    public int lastIndexOf(long var1);

    @Override
    @Deprecated
    default public boolean contains(Object object) {
        return LongCollection.super.contains(object);
    }

    @Override
    @Deprecated
    default public Long get(int n) {
        return this.getLong(n);
    }

    @Override
    @Deprecated
    default public int indexOf(Object object) {
        return this.indexOf((Long)object);
    }

    @Override
    @Deprecated
    default public int lastIndexOf(Object object) {
        return this.lastIndexOf((Long)object);
    }

    @Override
    @Deprecated
    default public boolean add(Long l) {
        return this.add((long)l);
    }

    public long removeLong(int var1);

    @Override
    @Deprecated
    default public boolean remove(Object object) {
        return LongCollection.super.remove(object);
    }

    @Override
    @Deprecated
    default public Long remove(int n) {
        return this.removeLong(n);
    }

    @Override
    @Deprecated
    default public Long set(int n, Long l) {
        return this.set(n, (long)l);
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
        this.add(n, (Long)object);
    }

    @Override
    @Deprecated
    default public Object set(int n, Object object) {
        return this.set(n, (Long)object);
    }

    @Override
    @Deprecated
    default public Object get(int n) {
        return this.get(n);
    }

    @Override
    @Deprecated
    default public boolean add(Object object) {
        return this.add((Long)object);
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }

    @Override
    default public LongIterator iterator() {
        return this.iterator();
    }
}

