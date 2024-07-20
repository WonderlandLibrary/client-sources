/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import java.util.ListIterator;

public interface IntListIterator
extends ListIterator<Integer>,
IntBidirectionalIterator {
    @Override
    public void set(int var1);

    @Override
    public void add(int var1);
}

