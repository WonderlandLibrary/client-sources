/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;
import java.util.ListIterator;

public interface LongListIterator
extends ListIterator<Long>,
LongBidirectionalIterator {
    @Override
    public void set(long var1);

    @Override
    public void add(long var1);
}

