/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortListIterator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface ShortList
extends List<Short>,
Comparable<List<? extends Short>>,
ShortCollection {
    @Override
    public ShortListIterator iterator();

    public ShortListIterator listIterator();

    public ShortListIterator listIterator(int var1);

    public ShortList subList(int var1, int var2);

    public void size(int var1);

    public void getElements(int var1, short[] var2, int var3, int var4);

    public void removeElements(int var1, int var2);

    public void addElements(int var1, short[] var2);

    public void addElements(int var1, short[] var2, int var3, int var4);

    @Override
    public boolean add(short var1);

    @Override
    public void add(int var1, short var2);

    @Override
    @Deprecated
    default public void add(int n, Short s) {
        this.add(n, (short)s);
    }

    public boolean addAll(int var1, ShortCollection var2);

    public boolean addAll(int var1, ShortList var2);

    public boolean addAll(ShortList var1);

    @Override
    public short set(int var1, short var2);

    public short getShort(int var1);

    public int indexOf(short var1);

    public int lastIndexOf(short var1);

    @Override
    @Deprecated
    default public boolean contains(Object object) {
        return ShortCollection.super.contains(object);
    }

    @Override
    @Deprecated
    default public Short get(int n) {
        return this.getShort(n);
    }

    @Override
    @Deprecated
    default public int indexOf(Object object) {
        return this.indexOf((Short)object);
    }

    @Override
    @Deprecated
    default public int lastIndexOf(Object object) {
        return this.lastIndexOf((Short)object);
    }

    @Override
    @Deprecated
    default public boolean add(Short s) {
        return this.add((short)s);
    }

    public short removeShort(int var1);

    @Override
    @Deprecated
    default public boolean remove(Object object) {
        return ShortCollection.super.remove(object);
    }

    @Override
    @Deprecated
    default public Short remove(int n) {
        return this.removeShort(n);
    }

    @Override
    @Deprecated
    default public Short set(int n, Short s) {
        return this.set(n, (short)s);
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
        this.add(n, (Short)object);
    }

    @Override
    @Deprecated
    default public Object set(int n, Object object) {
        return this.set(n, (Short)object);
    }

    @Override
    @Deprecated
    default public Object get(int n) {
        return this.get(n);
    }

    @Override
    @Deprecated
    default public boolean add(Object object) {
        return this.add((Short)object);
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

