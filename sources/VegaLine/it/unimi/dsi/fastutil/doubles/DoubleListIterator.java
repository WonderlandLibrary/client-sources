/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator;
import java.util.ListIterator;

public interface DoubleListIterator
extends ListIterator<Double>,
DoubleBidirectionalIterator {
    @Override
    public void set(double var1);

    @Override
    public void add(double var1);
}

