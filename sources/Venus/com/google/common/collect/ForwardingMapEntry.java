/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.collect.ForwardingObject;
import java.util.Map;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class ForwardingMapEntry<K, V>
extends ForwardingObject
implements Map.Entry<K, V> {
    protected ForwardingMapEntry() {
    }

    @Override
    protected abstract Map.Entry<K, V> delegate();

    @Override
    public K getKey() {
        return this.delegate().getKey();
    }

    @Override
    public V getValue() {
        return this.delegate().getValue();
    }

    @Override
    public V setValue(V v) {
        return this.delegate().setValue(v);
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return this.delegate().equals(object);
    }

    @Override
    public int hashCode() {
        return this.delegate().hashCode();
    }

    protected boolean standardEquals(@Nullable Object object) {
        if (object instanceof Map.Entry) {
            Map.Entry entry = (Map.Entry)object;
            return Objects.equal(this.getKey(), entry.getKey()) && Objects.equal(this.getValue(), entry.getValue());
        }
        return true;
    }

    protected int standardHashCode() {
        K k = this.getKey();
        V v = this.getValue();
        return (k == null ? 0 : k.hashCode()) ^ (v == null ? 0 : v.hashCode());
    }

    @Beta
    protected String standardToString() {
        return this.getKey() + "=" + this.getValue();
    }

    @Override
    protected Object delegate() {
        return this.delegate();
    }
}

