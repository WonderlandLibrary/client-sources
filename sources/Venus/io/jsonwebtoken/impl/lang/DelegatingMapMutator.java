/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.lang.DelegatingMap;
import io.jsonwebtoken.lang.MapMutator;
import java.util.Map;

public class DelegatingMapMutator<K, V, D extends Map<K, V>, T extends MapMutator<K, V, T>>
extends DelegatingMap<K, V, D>
implements MapMutator<K, V, T> {
    protected DelegatingMapMutator(D d) {
        super(d);
    }

    protected final T self() {
        return (T)this;
    }

    @Override
    public T empty() {
        this.clear();
        return this.self();
    }

    @Override
    public T add(K k, V v) {
        this.put(k, v);
        return this.self();
    }

    @Override
    public T add(Map<? extends K, ? extends V> map) {
        this.putAll(map);
        return this.self();
    }

    @Override
    public T delete(K k) {
        this.remove(k);
        return this.self();
    }
}

