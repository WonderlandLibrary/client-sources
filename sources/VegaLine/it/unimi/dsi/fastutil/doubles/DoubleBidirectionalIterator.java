/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

public interface DoubleBidirectionalIterator
extends DoubleIterator,
ObjectBidirectionalIterator<Double> {
    public double previousDouble();

    @Override
    public int back(int var1);
}

