/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import java.util.Set;

public interface DoubleSet
extends DoubleCollection,
Set<Double> {
    @Override
    public DoubleIterator iterator();

    public boolean remove(double var1);
}

