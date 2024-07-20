/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil;

import it.unimi.dsi.fastutil.BidirectionalIterator;

public interface BigListIterator<K>
extends BidirectionalIterator<K> {
    public long nextIndex();

    public long previousIndex();

    public long skip(long var1);
}

