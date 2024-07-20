/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.ShortBidirectionalIterator;
import java.util.ListIterator;

public interface ShortListIterator
extends ListIterator<Short>,
ShortBidirectionalIterator {
    @Override
    public void set(short var1);

    @Override
    public void add(short var1);
}

