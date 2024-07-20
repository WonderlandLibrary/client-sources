/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import java.util.Set;

public interface IntSet
extends IntCollection,
Set<Integer> {
    @Override
    public IntIterator iterator();

    public boolean remove(int var1);
}

