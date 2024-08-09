/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import java.util.Iterator;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface LongSet
extends LongCollection,
Set<Long> {
    @Override
    public LongIterator iterator();

    public boolean remove(long var1);

    @Override
    @Deprecated
    default public boolean remove(Object object) {
        return LongCollection.super.remove(object);
    }

    @Override
    @Deprecated
    default public boolean add(Long l) {
        return LongCollection.super.add(l);
    }

    @Override
    @Deprecated
    default public boolean contains(Object object) {
        return LongCollection.super.contains(object);
    }

    @Override
    @Deprecated
    default public boolean rem(long l) {
        return this.remove(l);
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
}

