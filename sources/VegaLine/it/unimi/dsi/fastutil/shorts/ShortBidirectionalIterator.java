/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.ShortIterator;

public interface ShortBidirectionalIterator
extends ShortIterator,
ObjectBidirectionalIterator<Short> {
    public short previousShort();

    @Override
    public int back(int var1);
}

