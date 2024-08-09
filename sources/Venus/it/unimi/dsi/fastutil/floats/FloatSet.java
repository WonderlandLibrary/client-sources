/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import java.util.Iterator;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface FloatSet
extends FloatCollection,
Set<Float> {
    @Override
    public FloatIterator iterator();

    public boolean remove(float var1);

    @Override
    @Deprecated
    default public boolean remove(Object object) {
        return FloatCollection.super.remove(object);
    }

    @Override
    @Deprecated
    default public boolean add(Float f) {
        return FloatCollection.super.add(f);
    }

    @Override
    @Deprecated
    default public boolean contains(Object object) {
        return FloatCollection.super.contains(object);
    }

    @Override
    @Deprecated
    default public boolean rem(float f) {
        return this.remove(f);
    }

    @Override
    @Deprecated
    default public boolean add(Object object) {
        return this.add((Float)object);
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }
}

