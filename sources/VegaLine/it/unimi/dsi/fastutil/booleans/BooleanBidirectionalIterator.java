/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

public interface BooleanBidirectionalIterator
extends BooleanIterator,
ObjectBidirectionalIterator<Boolean> {
    public boolean previousBoolean();

    @Override
    public int back(int var1);
}

