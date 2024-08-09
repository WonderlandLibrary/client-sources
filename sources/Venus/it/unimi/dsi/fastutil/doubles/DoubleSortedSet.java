/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterable;
import it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface DoubleSortedSet
extends DoubleSet,
SortedSet<Double>,
DoubleBidirectionalIterable {
    public DoubleBidirectionalIterator iterator(double var1);

    @Override
    public DoubleBidirectionalIterator iterator();

    public DoubleSortedSet subSet(double var1, double var3);

    public DoubleSortedSet headSet(double var1);

    public DoubleSortedSet tailSet(double var1);

    public DoubleComparator comparator();

    public double firstDouble();

    public double lastDouble();

    @Deprecated
    default public DoubleSortedSet subSet(Double d, Double d2) {
        return this.subSet((double)d, (double)d2);
    }

    @Deprecated
    default public DoubleSortedSet headSet(Double d) {
        return this.headSet((double)d);
    }

    @Deprecated
    default public DoubleSortedSet tailSet(Double d) {
        return this.tailSet((double)d);
    }

    @Override
    @Deprecated
    default public Double first() {
        return this.firstDouble();
    }

    @Override
    @Deprecated
    default public Double last() {
        return this.lastDouble();
    }

    @Override
    default public DoubleIterator iterator() {
        return this.iterator();
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }

    @Override
    @Deprecated
    default public Object last() {
        return this.last();
    }

    @Override
    @Deprecated
    default public Object first() {
        return this.first();
    }

    @Override
    @Deprecated
    default public SortedSet tailSet(Object object) {
        return this.tailSet((Double)object);
    }

    @Override
    @Deprecated
    default public SortedSet headSet(Object object) {
        return this.headSet((Double)object);
    }

    @Override
    @Deprecated
    default public SortedSet subSet(Object object, Object object2) {
        return this.subSet((Double)object, (Double)object2);
    }

    @Override
    default public Comparator comparator() {
        return this.comparator();
    }
}

