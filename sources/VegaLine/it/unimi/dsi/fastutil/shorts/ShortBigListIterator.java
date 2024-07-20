/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.shorts.ShortBidirectionalIterator;

public interface ShortBigListIterator
extends ShortBidirectionalIterator,
BigListIterator<Short> {
    public void set(short var1);

    public void add(short var1);

    public void set(Short var1);

    public void add(Short var1);
}

