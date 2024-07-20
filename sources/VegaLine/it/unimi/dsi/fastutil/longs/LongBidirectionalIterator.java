/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

public interface LongBidirectionalIterator
extends LongIterator,
ObjectBidirectionalIterator<Long> {
    public long previousLong();

    @Override
    public int back(int var1);
}

