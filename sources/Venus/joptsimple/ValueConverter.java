/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple;

public interface ValueConverter<V> {
    public V convert(String var1);

    public Class<? extends V> valueType();

    public String valuePattern();
}

