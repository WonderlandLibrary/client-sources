/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ForwardingObject;
import com.google.common.collect.Table;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@GwtCompatible
public abstract class ForwardingTable<R, C, V>
extends ForwardingObject
implements Table<R, C, V> {
    protected ForwardingTable() {
    }

    @Override
    protected abstract Table<R, C, V> delegate();

    @Override
    public Set<Table.Cell<R, C, V>> cellSet() {
        return this.delegate().cellSet();
    }

    @Override
    public void clear() {
        this.delegate().clear();
    }

    @Override
    public Map<R, V> column(C c) {
        return this.delegate().column(c);
    }

    @Override
    public Set<C> columnKeySet() {
        return this.delegate().columnKeySet();
    }

    @Override
    public Map<C, Map<R, V>> columnMap() {
        return this.delegate().columnMap();
    }

    @Override
    public boolean contains(Object object, Object object2) {
        return this.delegate().contains(object, object2);
    }

    @Override
    public boolean containsColumn(Object object) {
        return this.delegate().containsColumn(object);
    }

    @Override
    public boolean containsRow(Object object) {
        return this.delegate().containsRow(object);
    }

    @Override
    public boolean containsValue(Object object) {
        return this.delegate().containsValue(object);
    }

    @Override
    public V get(Object object, Object object2) {
        return this.delegate().get(object, object2);
    }

    @Override
    public boolean isEmpty() {
        return this.delegate().isEmpty();
    }

    @Override
    @CanIgnoreReturnValue
    public V put(R r, C c, V v) {
        return this.delegate().put(r, c, v);
    }

    @Override
    public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
        this.delegate().putAll(table);
    }

    @Override
    @CanIgnoreReturnValue
    public V remove(Object object, Object object2) {
        return this.delegate().remove(object, object2);
    }

    @Override
    public Map<C, V> row(R r) {
        return this.delegate().row(r);
    }

    @Override
    public Set<R> rowKeySet() {
        return this.delegate().rowKeySet();
    }

    @Override
    public Map<R, Map<C, V>> rowMap() {
        return this.delegate().rowMap();
    }

    @Override
    public int size() {
        return this.delegate().size();
    }

    @Override
    public Collection<V> values() {
        return this.delegate().values();
    }

    @Override
    public boolean equals(Object object) {
        return object == this || this.delegate().equals(object);
    }

    @Override
    public int hashCode() {
        return this.delegate().hashCode();
    }

    @Override
    protected Object delegate() {
        return this.delegate();
    }
}

