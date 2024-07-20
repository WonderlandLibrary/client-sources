/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

public interface FloatBidirectionalIterator
extends FloatIterator,
ObjectBidirectionalIterator<Float> {
    public float previousFloat();

    @Override
    public int back(int var1);
}

