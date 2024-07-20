/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;

public interface Object2ShortFunction<K>
extends Function<K, Short> {
    @Override
    public short put(K var1, short var2);

    public short getShort(Object var1);

    public short removeShort(Object var1);

    public void defaultReturnValue(short var1);

    public short defaultReturnValue();
}

