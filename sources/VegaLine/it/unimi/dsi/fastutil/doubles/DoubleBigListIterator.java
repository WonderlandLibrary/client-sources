/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator;

public interface DoubleBigListIterator
extends DoubleBidirectionalIterator,
BigListIterator<Double> {
    public void set(double var1);

    public void add(double var1);

    public void set(Double var1);

    public void add(Double var1);
}

