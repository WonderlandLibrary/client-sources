/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.lang.DelegatingMap;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.lang.Registry;
import io.jsonwebtoken.lang.Strings;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultRegistry<K, V>
extends DelegatingMap<K, V, Map<K, V>>
implements Registry<K, V>,
Function<K, V> {
    private final String qualifiedKeyName;

    private static <K, V> Map<K, V> toMap(Collection<? extends V> collection, Function<V, K> function) {
        Assert.notEmpty(collection, "Collection of values may not be null or empty.");
        Assert.notNull(function, "Key function cannot be null.");
        LinkedHashMap<K, V> linkedHashMap = new LinkedHashMap<K, V>(collection.size());
        for (V v : collection) {
            K k = Assert.notNull(function.apply(v), "Key function cannot return a null value.");
            linkedHashMap.put(k, v);
        }
        return Collections.immutable(linkedHashMap);
    }

    public DefaultRegistry(String string, String string2, Collection<? extends V> collection, Function<V, K> function) {
        super(DefaultRegistry.toMap(collection, function));
        string = Assert.hasText(Strings.clean(string), "name cannot be null or empty.");
        string2 = Assert.hasText(Strings.clean(string2), "keyName cannot be null or empty.");
        this.qualifiedKeyName = string + " " + string2;
    }

    @Override
    public V apply(K k) {
        return this.get(k);
    }

    @Override
    public V forKey(K k) {
        Object v = this.get(k);
        if (v == null) {
            String string = "Unrecognized " + this.qualifiedKeyName + ": " + k;
            throw new IllegalArgumentException(string);
        }
        return v;
    }

    static <T> T immutable() {
        throw new UnsupportedOperationException("Registries are immutable and cannot be modified.");
    }

    @Override
    public V put(K k, V v) {
        return (V)DefaultRegistry.immutable();
    }

    @Override
    public V remove(Object object) {
        return (V)DefaultRegistry.immutable();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        DefaultRegistry.immutable();
    }

    @Override
    public void clear() {
        DefaultRegistry.immutable();
    }

    @Override
    public int hashCode() {
        return this.DELEGATE.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (object instanceof DefaultRegistry) {
            DefaultRegistry defaultRegistry = (DefaultRegistry)object;
            return this.qualifiedKeyName.equals(defaultRegistry.qualifiedKeyName) && this.DELEGATE.equals(defaultRegistry.DELEGATE);
        }
        return true;
    }

    public String toString() {
        return this.DELEGATE.toString();
    }
}

