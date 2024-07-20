/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil;

import it.unimi.dsi.fastutil.AbstractIndirectPriorityQueue;
import it.unimi.dsi.fastutil.IndirectDoublePriorityQueue;

public abstract class AbstractIndirectDoublePriorityQueue<K>
extends AbstractIndirectPriorityQueue<K>
implements IndirectDoublePriorityQueue<K> {
    @Override
    public int secondaryLast() {
        throw new UnsupportedOperationException();
    }
}

