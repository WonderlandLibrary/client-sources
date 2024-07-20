/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.LongComparator;
import java.io.Serializable;

public abstract class AbstractLongComparator
implements LongComparator,
Serializable {
    private static final long serialVersionUID = 0L;

    protected AbstractLongComparator() {
    }

    @Override
    public int compare(Long ok1, Long ok2) {
        return this.compare((long)ok1, (long)ok2);
    }

    @Override
    public abstract int compare(long var1, long var3);
}

