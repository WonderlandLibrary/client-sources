/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.IndirectPriorityQueue;
import it.unimi.dsi.fastutil.longs.LongComparator;
import java.util.Comparator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface LongIndirectPriorityQueue
extends IndirectPriorityQueue<Long> {
    public LongComparator comparator();

    @Override
    default public Comparator comparator() {
        return this.comparator();
    }
}

