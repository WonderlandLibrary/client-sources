/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator;
import java.util.ListIterator;

public interface FloatListIterator
extends ListIterator<Float>,
FloatBidirectionalIterator {
    @Override
    public void set(float var1);

    @Override
    public void add(float var1);
}

