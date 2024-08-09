/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.IndirectPriorityQueue;
import it.unimi.dsi.fastutil.ints.IntComparator;
import java.util.Comparator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface IntIndirectPriorityQueue
extends IndirectPriorityQueue<Integer> {
    public IntComparator comparator();

    @Override
    default public Comparator comparator() {
        return this.comparator();
    }
}

