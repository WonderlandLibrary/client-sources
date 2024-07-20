/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;

public interface IntBigListIterator
extends IntBidirectionalIterator,
BigListIterator<Integer> {
    public void set(int var1);

    public void add(int var1);

    public void set(Integer var1);

    public void add(Integer var1);
}

