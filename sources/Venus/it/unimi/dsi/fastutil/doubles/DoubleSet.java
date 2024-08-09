/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import java.util.Iterator;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface DoubleSet
extends DoubleCollection,
Set<Double> {
    @Override
    public DoubleIterator iterator();

    public boolean remove(double var1);

    @Override
    @Deprecated
    default public boolean remove(Object object) {
        return DoubleCollection.super.remove(object);
    }

    @Override
    @Deprecated
    default public boolean add(Double d) {
        return DoubleCollection.super.add(d);
    }

    @Override
    @Deprecated
    default public boolean contains(Object object) {
        return DoubleCollection.super.contains(object);
    }

    @Override
    @Deprecated
    default public boolean rem(double d) {
        return this.remove(d);
    }

    @Override
    @Deprecated
    default public boolean add(Object object) {
        return this.add((Double)object);
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }
}

