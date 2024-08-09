/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Maps;
import com.google.common.collect.RegularImmutableTable;
import com.google.common.collect.Table;
import java.util.AbstractCollection;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.concurrent.Immutable;

@GwtCompatible
@Immutable
final class SparseImmutableTable<R, C, V>
extends RegularImmutableTable<R, C, V> {
    static final ImmutableTable<Object, Object, Object> EMPTY = new SparseImmutableTable<Object, Object, Object>(ImmutableList.of(), ImmutableSet.of(), ImmutableSet.of());
    private final ImmutableMap<R, Map<C, V>> rowMap;
    private final ImmutableMap<C, Map<R, V>> columnMap;
    private final int[] cellRowIndices;
    private final int[] cellColumnInRowIndices;

    SparseImmutableTable(ImmutableList<Table.Cell<R, C, V>> immutableList, ImmutableSet<R> immutableSet, ImmutableSet<C> immutableSet2) {
        ImmutableMap.Builder builder;
        Object object32;
        Object object222;
        ImmutableMap<R, Integer> immutableMap = Maps.indexMap(immutableSet);
        LinkedHashMap linkedHashMap = Maps.newLinkedHashMap();
        for (Object object222 : immutableSet) {
            linkedHashMap.put(object222, new LinkedHashMap());
        }
        LinkedHashMap linkedHashMap2 = Maps.newLinkedHashMap();
        for (Object object32 : immutableSet2) {
            linkedHashMap2.put(object32, new LinkedHashMap());
        }
        object222 = new int[immutableList.size()];
        object32 = new int[immutableList.size()];
        for (int i = 0; i < immutableList.size(); ++i) {
            builder = (Table.Cell)immutableList.get(i);
            Object object4 = builder.getRowKey();
            Object c = builder.getColumnKey();
            Object v = builder.getValue();
            object222[i] = (Integer)immutableMap.get(object4);
            Map map = (Map)linkedHashMap.get(object4);
            object32[i] = map.size();
            Object v2 = map.put(c, v);
            if (v2 != null) {
                throw new IllegalArgumentException("Duplicate value for row=" + object4 + ", column=" + c + ": " + v + ", " + v2);
            }
            ((Map)linkedHashMap2.get(c)).put(object4, v);
        }
        this.cellRowIndices = (int[])object222;
        this.cellColumnInRowIndices = (int[])object32;
        ImmutableMap.Builder builder2 = new ImmutableMap.Builder(linkedHashMap.size());
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            builder2.put(entry.getKey(), ImmutableMap.copyOf((Map)entry.getValue()));
        }
        this.rowMap = builder2.build();
        builder = new ImmutableMap.Builder(linkedHashMap2.size());
        for (Map.Entry entry : linkedHashMap2.entrySet()) {
            builder.put(entry.getKey(), ImmutableMap.copyOf((Map)entry.getValue()));
        }
        this.columnMap = builder.build();
    }

    @Override
    public ImmutableMap<C, Map<R, V>> columnMap() {
        return this.columnMap;
    }

    @Override
    public ImmutableMap<R, Map<C, V>> rowMap() {
        return this.rowMap;
    }

    @Override
    public int size() {
        return this.cellRowIndices.length;
    }

    @Override
    Table.Cell<R, C, V> getCell(int n) {
        int n2 = this.cellRowIndices[n];
        Map.Entry entry = (Map.Entry)((ImmutableSet)this.rowMap.entrySet()).asList().get(n2);
        ImmutableMap immutableMap = (ImmutableMap)entry.getValue();
        int n3 = this.cellColumnInRowIndices[n];
        Map.Entry entry2 = (Map.Entry)((ImmutableSet)immutableMap.entrySet()).asList().get(n3);
        return SparseImmutableTable.cellOf(entry.getKey(), entry2.getKey(), entry2.getValue());
    }

    @Override
    V getValue(int n) {
        int n2 = this.cellRowIndices[n];
        ImmutableMap immutableMap = (ImmutableMap)((ImmutableCollection)this.rowMap.values()).asList().get(n2);
        int n3 = this.cellColumnInRowIndices[n];
        return (V)((ImmutableCollection)immutableMap.values()).asList().get(n3);
    }

    @Override
    ImmutableTable.SerializedForm createSerializedForm() {
        ImmutableMap immutableMap = Maps.indexMap(this.columnKeySet());
        int[] nArray = new int[((AbstractCollection)((Object)this.cellSet())).size()];
        int n = 0;
        for (Table.Cell cell : this.cellSet()) {
            nArray[n++] = (Integer)immutableMap.get(cell.getColumnKey());
        }
        return ImmutableTable.SerializedForm.create(this, this.cellRowIndices, nArray);
    }

    @Override
    public Map columnMap() {
        return this.columnMap();
    }

    @Override
    public Map rowMap() {
        return this.rowMap();
    }
}

