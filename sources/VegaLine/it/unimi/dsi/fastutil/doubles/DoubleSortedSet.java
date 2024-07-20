/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import java.util.SortedSet;

public interface DoubleSortedSet
extends DoubleSet,
SortedSet<Double> {
    public DoubleBidirectionalIterator iterator(double var1);

    @Override
    @Deprecated
    public DoubleBidirectionalIterator doubleIterator();

    @Override
    public DoubleBidirectionalIterator iterator();

    public DoubleSortedSet subSet(Double var1, Double var2);

    public DoubleSortedSet headSet(Double var1);

    public DoubleSortedSet tailSet(Double var1);

    public DoubleComparator comparator();

    public DoubleSortedSet subSet(double var1, double var3);

    public DoubleSortedSet headSet(double var1);

    public DoubleSortedSet tailSet(double var1);

    public double firstDouble();

    public double lastDouble();
}

