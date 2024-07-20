/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.FloatComparator;
import java.io.Serializable;

public abstract class AbstractFloatComparator
implements FloatComparator,
Serializable {
    private static final long serialVersionUID = 0L;

    protected AbstractFloatComparator() {
    }

    @Override
    public int compare(Float ok1, Float ok2) {
        return this.compare(ok1.floatValue(), ok2.floatValue());
    }

    @Override
    public abstract int compare(float var1, float var2);
}

