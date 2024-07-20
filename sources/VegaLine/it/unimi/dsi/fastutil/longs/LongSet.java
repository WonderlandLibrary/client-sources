/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import java.util.Set;

public interface LongSet
extends LongCollection,
Set<Long> {
    @Override
    public LongIterator iterator();

    public boolean remove(long var1);
}

