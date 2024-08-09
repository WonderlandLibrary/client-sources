/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import java.util.Comparator;

@FunctionalInterface
public interface DoubleComparator
extends Comparator<Double> {
    @Override
    public int compare(double var1, double var3);

    @Override
    @Deprecated
    default public int compare(Double d, Double d2) {
        return this.compare((double)d, (double)d2);
    }

    @Override
    @Deprecated
    default public int compare(Object object, Object object2) {
        return this.compare((Double)object, (Double)object2);
    }
}

