/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;

public interface Object2BooleanFunction<K>
extends Function<K, Boolean> {
    @Override
    public boolean put(K var1, boolean var2);

    public boolean getBoolean(Object var1);

    public boolean removeBoolean(Object var1);

    public void defaultReturnValue(boolean var1);

    public boolean defaultReturnValue();
}

