/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.Size64;
import it.unimi.dsi.fastutil.longs.LongBigListIterator;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import java.util.Iterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface LongBigList
extends BigList<Long>,
LongCollection,
Size64,
Comparable<BigList<? extends Long>> {
    @Override
    public LongBigListIterator iterator();

    public LongBigListIterator listIterator();

    public LongBigListIterator listIterator(long var1);

    public LongBigList subList(long var1, long var3);

    public void getElements(long var1, long[][] var3, long var4, long var6);

    public void removeElements(long var1, long var3);

    public void addElements(long var1, long[][] var3);

    public void addElements(long var1, long[][] var3, long var4, long var6);

    @Override
    public void add(long var1, long var3);

    public boolean addAll(long var1, LongCollection var3);

    public boolean addAll(long var1, LongBigList var3);

    public boolean addAll(LongBigList var1);

    public long getLong(long var1);

    public long removeLong(long var1);

    @Override
    public long set(long var1, long var3);

    public long indexOf(long var1);

    public long lastIndexOf(long var1);

    @Override
    @Deprecated
    public void add(long var1, Long var3);

    @Override
    @Deprecated
    public Long get(long var1);

    @Override
    @Deprecated
    public long indexOf(Object var1);

    @Override
    @Deprecated
    public long lastIndexOf(Object var1);

    @Override
    @Deprecated
    public Long remove(long var1);

    @Override
    @Deprecated
    public Long set(long var1, Long var3);

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
        this.add(l, (Long)object);
    }

    @Override
    @Deprecated
    default public Object set(long l, Object object) {
        return this.set(l, (Long)object);
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
    default public LongIterator iterator() {
        return this.iterator();
    }
}

