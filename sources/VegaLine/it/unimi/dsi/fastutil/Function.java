/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil;

public interface Function<K, V> {
    public V put(K var1, V var2);

    public V get(Object var1);

    public boolean containsKey(Object var1);

    public V remove(Object var1);

    public int size();

    public void clear();
}

