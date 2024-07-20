/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import java.io.Serializable;

public abstract class AbstractDoubleComparator
implements DoubleComparator,
Serializable {
    private static final long serialVersionUID = 0L;

    protected AbstractDoubleComparator() {
    }

    @Override
    public int compare(Double ok1, Double ok2) {
        return this.compare((double)ok1, (double)ok2);
    }

    @Override
    public abstract int compare(double var1, double var3);
}

