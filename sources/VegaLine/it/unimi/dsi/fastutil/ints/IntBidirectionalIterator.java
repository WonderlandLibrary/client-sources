/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

public interface IntBidirectionalIterator
extends IntIterator,
ObjectBidirectionalIterator<Integer> {
    public int previousInt();

    @Override
    public int back(int var1);
}

