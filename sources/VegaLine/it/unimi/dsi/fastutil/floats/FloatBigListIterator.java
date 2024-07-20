/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator;

public interface FloatBigListIterator
extends FloatBidirectionalIterator,
BigListIterator<Float> {
    public void set(float var1);

    public void add(float var1);

    public void set(Float var1);

    public void add(Float var1);
}

