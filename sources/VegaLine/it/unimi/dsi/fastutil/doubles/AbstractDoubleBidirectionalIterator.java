/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator;

public abstract class AbstractDoubleBidirectionalIterator
extends AbstractDoubleIterator
implements DoubleBidirectionalIterator {
    protected AbstractDoubleBidirectionalIterator() {
    }

    @Override
    public double previousDouble() {
        return this.previous();
    }

    @Override
    public Double previous() {
        return this.previousDouble();
    }

    @Override
    public int back(int n) {
        int i = n;
        while (i-- != 0 && this.hasPrevious()) {
            this.previousDouble();
        }
        return n - i - 1;
    }
}

