/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.collect.ForwardingObject;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class ForwardingMap<K, V>
extends ForwardingObject
implements Map<K, V> {
    protected ForwardingMap() {
    }

    @Override
    protected abstract Map<K, V> delegate();

    @Override
    public int size() {
        return this.delegate().size();
    }

    @Override
    public boolean isEmpty() {
        return this.delegate().isEmpty();
    }

    @Override
    @CanIgnoreReturnValue
    public V remove(Object object) {
        return this.delegate().remove(object);
    }

    @Override
    public void clear() {
        this.delegate().clear();
    }

    @Override
    public boolean containsKey(@Nullable Object object) {
        return this.delegate().containsKey(object);
    }

    @Override
    public boolean containsValue(@Nullable Object object) {
        return this.delegate().containsValue(object);
    }

    @Override
    public V get(@Nullable Object object) {
        return this.delegate().get(object);
    }

    @Override
    @CanIgnoreReturnValue
    public V put(K k, V v) {
        return this.delegate().put(k, v);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        this.delegate().putAll(map);
    }

    @Override
    public Set<K> keySet() {
        return this.delegate().keySet();
    }

    @Override
    public Collection<V> values() {
        return this.delegate().values();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return this.delegate().entrySet();
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return object == this || this.delegate().equals(object);
    }

    @Override
    public int hashCode() {
        return this.delegate().hashCode();
    }

    protected void standardPutAll(Map<? extends K, ? extends V> map) {
        Maps.putAllImpl(this, map);
    }

    @Beta
    protected V standardRemove(@Nullable Object object) {
        Iterator<Map.Entry<K, V>> iterator2 = this.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<K, V> entry = iterator2.next();
            if (!Objects.equal(entry.getKey(), object)) continue;
            V v = entry.getValue();
            iterator2.remove();
            return v;
        }
        return null;
    }

    protected void standardClear() {
        Iterators.clear(this.entrySet().iterator());
    }

    @Beta
    protected boolean standardContainsKey(@Nullable Object object) {
        return Maps.containsKeyImpl(this, object);
    }

    protected boolean standardContainsValue(@Nullable Object object) {
        return Maps.containsValueImpl(this, object);
    }

    protected boolean standardIsEmpty() {
        return !this.entrySet().iterator().hasNext();
    }

    protected boolean standardEquals(@Nullable Object object) {
        return Maps.equalsImpl(this, object);
    }

    protected int standardHashCode() {
        return Sets.hashCodeImpl(this.entrySet());
    }

    protected String standardToString() {
        return Maps.toStringImpl(this);
    }

    @Override
    protected Object delegate() {
        return this.delegate();
    }

    @Beta
    protected abstract class StandardEntrySet
    extends Maps.EntrySet<K, V> {
        final ForwardingMap this$0;

        public StandardEntrySet(ForwardingMap forwardingMap) {
            this.this$0 = forwardingMap;
        }

        @Override
        Map<K, V> map() {
            return this.this$0;
        }
    }

    @Beta
    protected class StandardValues
    extends Maps.Values<K, V> {
        final ForwardingMap this$0;

        public StandardValues(ForwardingMap forwardingMap) {
            this.this$0 = forwardingMap;
            super(forwardingMap);
        }
    }

    @Beta
    protected class StandardKeySet
    extends Maps.KeySet<K, V> {
        final ForwardingMap this$0;

        public StandardKeySet(ForwardingMap forwardingMap) {
            this.this$0 = forwardingMap;
            super(forwardingMap);
        }
    }
}

