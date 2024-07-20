/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.BidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;

public interface ObjectBidirectionalIterator<K>
extends ObjectIterator<K>,
BidirectionalIterator<K> {
    public int back(int var1);
}

