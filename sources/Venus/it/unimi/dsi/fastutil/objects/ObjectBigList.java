/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.Size64;
import it.unimi.dsi.fastutil.objects.ObjectBigListIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Iterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface ObjectBigList<K>
extends BigList<K>,
ObjectCollection<K>,
Size64,
Comparable<BigList<? extends K>> {
    @Override
    public ObjectBigListIterator<K> iterator();

    @Override
    public ObjectBigListIterator<K> listIterator();

    @Override
    public ObjectBigListIterator<K> listIterator(long var1);

    @Override
    public ObjectBigList<K> subList(long var1, long var3);

    public void getElements(long var1, Object[][] var3, long var4, long var6);

    public void removeElements(long var1, long var3);

    public void addElements(long var1, K[][] var3);

    public void addElements(long var1, K[][] var3, long var4, long var6);

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
    default public Iterator iterator() {
        return this.iterator();
    }

    @Override
    default public ObjectIterator iterator() {
        return this.iterator();
    }
}

