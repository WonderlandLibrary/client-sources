/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.booleans.BooleanBidirectionalIterator;
import java.util.ListIterator;

public interface BooleanListIterator
extends ListIterator<Boolean>,
BooleanBidirectionalIterator {
    @Override
    public void set(boolean var1);

    @Override
    public void add(boolean var1);
}

