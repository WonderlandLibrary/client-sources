/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.ShortComparator;
import java.io.Serializable;

public abstract class AbstractShortComparator
implements ShortComparator,
Serializable {
    private static final long serialVersionUID = 0L;

    protected AbstractShortComparator() {
    }

    @Override
    public int compare(Short ok1, Short ok2) {
        return this.compare((short)ok1, (short)ok2);
    }

    @Override
    public abstract int compare(short var1, short var2);
}

