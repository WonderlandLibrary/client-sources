/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import java.util.Iterator;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface ShortSet
extends ShortCollection,
Set<Short> {
    @Override
    public ShortIterator iterator();

    public boolean remove(short var1);

    @Override
    @Deprecated
    default public boolean remove(Object object) {
        return ShortCollection.super.remove(object);
    }

    @Override
    @Deprecated
    default public boolean add(Short s) {
        return ShortCollection.super.add(s);
    }

    @Override
    @Deprecated
    default public boolean contains(Object object) {
        return ShortCollection.super.contains(object);
    }

    @Override
    @Deprecated
    default public boolean rem(short s) {
        return this.remove(s);
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
}

