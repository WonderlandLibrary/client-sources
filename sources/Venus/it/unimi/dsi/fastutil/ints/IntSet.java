/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import java.util.Iterator;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface IntSet
extends IntCollection,
Set<Integer> {
    @Override
    public IntIterator iterator();

    public boolean remove(int var1);

    @Override
    @Deprecated
    default public boolean remove(Object object) {
        return IntCollection.super.remove(object);
    }

    @Override
    @Deprecated
    default public boolean add(Integer n) {
        return IntCollection.super.add(n);
    }

    @Override
    @Deprecated
    default public boolean contains(Object object) {
        return IntCollection.super.contains(object);
    }

    @Override
    @Deprecated
    default public boolean rem(int n) {
        return this.remove(n);
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
}

