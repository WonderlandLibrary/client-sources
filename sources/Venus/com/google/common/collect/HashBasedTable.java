/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Supplier;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.StandardTable;
import com.google.common.collect.Table;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible(serializable=true)
public class HashBasedTable<R, C, V>
extends StandardTable<R, C, V> {
    private static final long serialVersionUID = 0L;

    public static <R, C, V> HashBasedTable<R, C, V> create() {
        return new HashBasedTable(new LinkedHashMap(), new Factory(0));
    }

    public static <R, C, V> HashBasedTable<R, C, V> create(int n, int n2) {
        CollectPreconditions.checkNonnegative(n2, "expectedCellsPerRow");
        LinkedHashMap linkedHashMap = Maps.newLinkedHashMapWithExpectedSize(n);
        return new HashBasedTable(linkedHashMap, new Factory(n2));
    }

    public static <R, C, V> HashBasedTable<R, C, V> create(Table<? extends R, ? extends C, ? extends V> table) {
        HashBasedTable<R, C, V> hashBasedTable = HashBasedTable.create();
        hashBasedTable.putAll((Table)table);
        return hashBasedTable;
    }

    HashBasedTable(Map<R, Map<C, V>> map, Factory<C, V> factory) {
        super(map, factory);
    }

    @Override
    public boolean contains(@Nullable Object object, @Nullable Object object2) {
        return super.contains(object, object2);
    }

    @Override
    public boolean containsColumn(@Nullable Object object) {
        return super.containsColumn(object);
    }

    @Override
    public boolean containsRow(@Nullable Object object) {
        return super.containsRow(object);
    }

    @Override
    public boolean containsValue(@Nullable Object object) {
        return super.containsValue(object);
    }

    @Override
    public V get(@Nullable Object object, @Nullable Object object2) {
        return super.get(object, object2);
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return super.equals(object);
    }

    @Override
    @CanIgnoreReturnValue
    public V remove(@Nullable Object object, @Nullable Object object2) {
        return super.remove(object, object2);
    }

    @Override
    public Map columnMap() {
        return super.columnMap();
    }

    @Override
    public Map rowMap() {
        return super.rowMap();
    }

    @Override
    public Collection values() {
        return super.values();
    }

    @Override
    public Set columnKeySet() {
        return super.columnKeySet();
    }

    @Override
    public Set rowKeySet() {
        return super.rowKeySet();
    }

    @Override
    public Map column(Object object) {
        return super.column(object);
    }

    @Override
    public Map row(Object object) {
        return super.row(object);
    }

    @Override
    public Set cellSet() {
        return super.cellSet();
    }

    @Override
    @CanIgnoreReturnValue
    public Object put(Object object, Object object2, Object object3) {
        return super.put(object, object2, object3);
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public int size() {
        return super.size();
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public void putAll(Table table) {
        super.putAll(table);
    }

    private static class Factory<C, V>
    implements Supplier<Map<C, V>>,
    Serializable {
        final int expectedSize;
        private static final long serialVersionUID = 0L;

        Factory(int n) {
            this.expectedSize = n;
        }

        @Override
        public Map<C, V> get() {
            return Maps.newLinkedHashMapWithExpectedSize(this.expectedSize);
        }

        @Override
        public Object get() {
            return this.get();
        }
    }
}

