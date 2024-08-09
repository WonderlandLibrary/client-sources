/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.Size64;
import it.unimi.dsi.fastutil.shorts.ShortBigListIterator;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import java.util.Iterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface ShortBigList
extends BigList<Short>,
ShortCollection,
Size64,
Comparable<BigList<? extends Short>> {
    @Override
    public ShortBigListIterator iterator();

    public ShortBigListIterator listIterator();

    public ShortBigListIterator listIterator(long var1);

    public ShortBigList subList(long var1, long var3);

    public void getElements(long var1, short[][] var3, long var4, long var6);

    public void removeElements(long var1, long var3);

    public void addElements(long var1, short[][] var3);

    public void addElements(long var1, short[][] var3, long var4, long var6);

    @Override
    public void add(long var1, short var3);

    public boolean addAll(long var1, ShortCollection var3);

    public boolean addAll(long var1, ShortBigList var3);

    public boolean addAll(ShortBigList var1);

    public short getShort(long var1);

    public short removeShort(long var1);

    @Override
    public short set(long var1, short var3);

    public long indexOf(short var1);

    public long lastIndexOf(short var1);

    @Override
    @Deprecated
    public void add(long var1, Short var3);

    @Override
    @Deprecated
    public Short get(long var1);

    @Override
    @Deprecated
    public long indexOf(Object var1);

    @Override
    @Deprecated
    public long lastIndexOf(Object var1);

    @Override
    @Deprecated
    public Short remove(long var1);

    @Override
    @Deprecated
    public Short set(long var1, Short var3);

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
        this.add(l, (Short)object);
    }

    @Override
    @Deprecated
    default public Object set(long l, Object object) {
        return this.set(l, (Short)object);
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
    default public ShortIterator iterator() {
        return this.iterator();
    }
}

