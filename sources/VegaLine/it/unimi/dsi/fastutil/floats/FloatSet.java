/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import java.util.Set;

public interface FloatSet
extends FloatCollection,
Set<Float> {
    @Override
    public FloatIterator iterator();

    public boolean remove(float var1);
}

