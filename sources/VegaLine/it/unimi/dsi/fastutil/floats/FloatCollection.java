/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.FloatIterable;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import java.util.Collection;

public interface FloatCollection
extends Collection<Float>,
FloatIterable {
    @Override
    public FloatIterator iterator();

    @Deprecated
    public FloatIterator floatIterator();

    @Override
    public <T> T[] toArray(T[] var1);

    public boolean contains(float var1);

    public float[] toFloatArray();

    public float[] toFloatArray(float[] var1);

    public float[] toArray(float[] var1);

    @Override
    public boolean add(float var1);

    public boolean rem(float var1);

    public boolean addAll(FloatCollection var1);

    public boolean containsAll(FloatCollection var1);

    public boolean removeAll(FloatCollection var1);

    public boolean retainAll(FloatCollection var1);
}

