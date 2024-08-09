/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.lang;

import java.util.Map;

public interface MapMutator<K, V, T extends MapMutator<K, V, T>> {
    public T delete(K var1);

    public T empty();

    public T add(K var1, V var2);

    public T add(Map<? extends K, ? extends V> var1);
}

