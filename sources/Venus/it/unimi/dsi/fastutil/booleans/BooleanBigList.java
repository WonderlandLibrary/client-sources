/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.Size64;
import it.unimi.dsi.fastutil.booleans.BooleanBigListIterator;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import java.util.Iterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface BooleanBigList
extends BigList<Boolean>,
BooleanCollection,
Size64,
Comparable<BigList<? extends Boolean>> {
    @Override
    public BooleanBigListIterator iterator();

    public BooleanBigListIterator listIterator();

    public BooleanBigListIterator listIterator(long var1);

    public BooleanBigList subList(long var1, long var3);

    public void getElements(long var1, boolean[][] var3, long var4, long var6);

    public void removeElements(long var1, long var3);

    public void addElements(long var1, boolean[][] var3);

    public void addElements(long var1, boolean[][] var3, long var4, long var6);

    @Override
    public void add(long var1, boolean var3);

    public boolean addAll(long var1, BooleanCollection var3);

    public boolean addAll(long var1, BooleanBigList var3);

    public boolean addAll(BooleanBigList var1);

    public boolean getBoolean(long var1);

    public boolean removeBoolean(long var1);

    @Override
    public boolean set(long var1, boolean var3);

    public long indexOf(boolean var1);

    public long lastIndexOf(boolean var1);

    @Override
    @Deprecated
    public void add(long var1, Boolean var3);

    @Override
    @Deprecated
    public Boolean get(long var1);

    @Override
    @Deprecated
    public long indexOf(Object var1);

    @Override
    @Deprecated
    public long lastIndexOf(Object var1);

    @Override
    @Deprecated
    public Boolean remove(long var1);

    @Override
    @Deprecated
    public Boolean set(long var1, Boolean var3);

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
        this.add(l, (Boolean)object);
    }

    @Override
    @Deprecated
    default public Object set(long l, Object object) {
        return this.set(l, (Boolean)object);
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
    default public BooleanIterator iterator() {
        return this.iterator();
    }
}

