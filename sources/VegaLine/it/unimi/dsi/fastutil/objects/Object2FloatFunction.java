/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;

public interface Object2FloatFunction<K>
extends Function<K, Float> {
    @Override
    public float put(K var1, float var2);

    public float getFloat(Object var1);

    public float removeFloat(Object var1);

    public void defaultReturnValue(float var1);

    public float defaultReturnValue();
}

