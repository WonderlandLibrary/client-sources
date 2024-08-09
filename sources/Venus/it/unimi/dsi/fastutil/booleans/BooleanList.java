/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.booleans.BooleanListIterator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface BooleanList
extends List<Boolean>,
Comparable<List<? extends Boolean>>,
BooleanCollection {
    @Override
    public BooleanListIterator iterator();

    public BooleanListIterator listIterator();

    public BooleanListIterator listIterator(int var1);

    public BooleanList subList(int var1, int var2);

    public void size(int var1);

    public void getElements(int var1, boolean[] var2, int var3, int var4);

    public void removeElements(int var1, int var2);

    public void addElements(int var1, boolean[] var2);

    public void addElements(int var1, boolean[] var2, int var3, int var4);

    @Override
    public boolean add(boolean var1);

    @Override
    public void add(int var1, boolean var2);

    @Override
    @Deprecated
    default public void add(int n, Boolean bl) {
        this.add(n, (boolean)bl);
    }

    public boolean addAll(int var1, BooleanCollection var2);

    public boolean addAll(int var1, BooleanList var2);

    public boolean addAll(BooleanList var1);

    @Override
    public boolean set(int var1, boolean var2);

    public boolean getBoolean(int var1);

    public int indexOf(boolean var1);

    public int lastIndexOf(boolean var1);

    @Override
    @Deprecated
    default public boolean contains(Object object) {
        return BooleanCollection.super.contains(object);
    }

    @Override
    @Deprecated
    default public Boolean get(int n) {
        return this.getBoolean(n);
    }

    @Override
    @Deprecated
    default public int indexOf(Object object) {
        return this.indexOf((Boolean)object);
    }

    @Override
    @Deprecated
    default public int lastIndexOf(Object object) {
        return this.lastIndexOf((Boolean)object);
    }

    @Override
    @Deprecated
    default public boolean add(Boolean bl) {
        return this.add((boolean)bl);
    }

    public boolean removeBoolean(int var1);

    @Override
    @Deprecated
    default public boolean remove(Object object) {
        return BooleanCollection.super.remove(object);
    }

    @Override
    @Deprecated
    default public Boolean remove(int n) {
        return this.removeBoolean(n);
    }

    @Override
    @Deprecated
    default public Boolean set(int n, Boolean bl) {
        return this.set(n, (boolean)bl);
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
        this.add(n, (Boolean)object);
    }

    @Override
    @Deprecated
    default public Object set(int n, Object object) {
        return this.set(n, (Boolean)object);
    }

    @Override
    @Deprecated
    default public Object get(int n) {
        return this.get(n);
    }

    @Override
    @Deprecated
    default public boolean add(Object object) {
        return this.add((Boolean)object);
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }

    @Override
    default public BooleanIterator iterator() {
        return this.iterator();
    }
}

