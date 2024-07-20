/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;

public interface LongBigListIterator
extends LongBidirectionalIterator,
BigListIterator<Long> {
    public void set(long var1);

    public void add(long var1);

    public void set(Long var1);

    public void add(Long var1);
}

