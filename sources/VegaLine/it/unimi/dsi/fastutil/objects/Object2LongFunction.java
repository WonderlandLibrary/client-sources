/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;

public interface Object2LongFunction<K>
extends Function<K, Long> {
    @Override
    public long put(K var1, long var2);

    public long getLong(Object var1);

    public long removeLong(Object var1);

    public void defaultReturnValue(long var1);

    public long defaultReturnValue();
}

