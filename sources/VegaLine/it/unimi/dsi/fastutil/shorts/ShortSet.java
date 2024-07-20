/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import java.util.Set;

public interface ShortSet
extends ShortCollection,
Set<Short> {
    @Override
    public ShortIterator iterator();

    public boolean remove(short var1);
}

