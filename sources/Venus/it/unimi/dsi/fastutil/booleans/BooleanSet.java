/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import java.util.Iterator;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface BooleanSet
extends BooleanCollection,
Set<Boolean> {
    @Override
    public BooleanIterator iterator();

    public boolean remove(boolean var1);

    @Override
    @Deprecated
    default public boolean remove(Object object) {
        return BooleanCollection.super.remove(object);
    }

    @Override
    @Deprecated
    default public boolean add(Boolean bl) {
        return BooleanCollection.super.add(bl);
    }

    @Override
    @Deprecated
    default public boolean contains(Object object) {
        return BooleanCollection.super.contains(object);
    }

    @Override
    @Deprecated
    default public boolean rem(boolean bl) {
        return this.remove(bl);
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
}

