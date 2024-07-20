/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;

public interface Reference2ReferenceFunction<K, V>
extends Function<K, V> {
    public void defaultReturnValue(V var1);

    public V defaultReturnValue();
}

