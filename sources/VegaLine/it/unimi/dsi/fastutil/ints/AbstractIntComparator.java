/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntComparator;
import java.io.Serializable;

public abstract class AbstractIntComparator
implements IntComparator,
Serializable {
    private static final long serialVersionUID = 0L;

    protected AbstractIntComparator() {
    }

    @Override
    public int compare(Integer ok1, Integer ok2) {
        return this.compare((int)ok1, (int)ok2);
    }

    @Override
    public abstract int compare(int var1, int var2);
}

