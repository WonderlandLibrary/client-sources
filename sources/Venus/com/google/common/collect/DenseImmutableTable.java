/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Maps;
import com.google.common.collect.RegularImmutableTable;
import com.google.common.collect.Table;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Map;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@GwtCompatible
@Immutable
final class DenseImmutableTable<R, C, V>
extends RegularImmutableTable<R, C, V> {
    private final ImmutableMap<R, Integer> rowKeyToIndex;
    private final ImmutableMap<C, Integer> columnKeyToIndex;
    private final ImmutableMap<R, Map<C, V>> rowMap;
    private final ImmutableMap<C, Map<R, V>> columnMap;
    private final int[] rowCounts;
    private final int[] columnCounts;
    private final V[][] values;
    private final int[] cellRowIndices;
    private final int[] cellColumnIndices;

    DenseImmutableTable(ImmutableList<Table.Cell<R, C, V>> immutableList, ImmutableSet<R> immutableSet, ImmutableSet<C> immutableSet2) {
        Object[][] objectArray = new Object[immutableSet.size()][immutableSet2.size()];
        this.values = objectArray;
        this.rowKeyToIndex = Maps.indexMap(immutableSet);
        this.columnKeyToIndex = Maps.indexMap(immutableSet2);
        this.rowCounts = new int[this.rowKeyToIndex.size()];
        this.columnCounts = new int[this.columnKeyToIndex.size()];
        int[] nArray = new int[immutableList.size()];
        int[] nArray2 = new int[immutableList.size()];
        for (int i = 0; i < immutableList.size(); ++i) {
            int n;
            Table.Cell cell = (Table.Cell)immutableList.get(i);
            Object r = cell.getRowKey();
            Object c = cell.getColumnKey();
            int n2 = this.rowKeyToIndex.get(r);
            V v = this.values[n2][n = this.columnKeyToIndex.get(c).intValue()];
            Preconditions.checkArgument(v == null, "duplicate key: (%s, %s)", r, c);
            this.values[n2][n] = cell.getValue();
            int n3 = n2;
            this.rowCounts[n3] = this.rowCounts[n3] + 1;
            int n4 = n;
            this.columnCounts[n4] = this.columnCounts[n4] + 1;
            nArray[i] = n2;
            nArray2[i] = n;
        }
        this.cellRowIndices = nArray;
        this.cellColumnIndices = nArray2;
        this.rowMap = new RowMap(this, null);
        this.columnMap = new ColumnMap(this, null);
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
    public V get(@Nullable Object object, @Nullable Object object2) {
        Integer n = this.rowKeyToIndex.get(object);
        Integer n2 = this.columnKeyToIndex.get(object2);
        return n == null || n2 == null ? null : (V)this.values[n][n2];
    }

    @Override
    public int size() {
        return this.cellRowIndices.length;
    }

    @Override
    Table.Cell<R, C, V> getCell(int n) {
        int n2 = this.cellRowIndices[n];
        int n3 = this.cellColumnIndices[n];
        Object e = ((ImmutableSet)this.rowKeySet()).asList().get(n2);
        Object e2 = ((ImmutableSet)this.columnKeySet()).asList().get(n3);
        V v = this.values[n2][n3];
        return DenseImmutableTable.cellOf(e, e2, v);
    }

    @Override
    V getValue(int n) {
        return this.values[this.cellRowIndices[n]][this.cellColumnIndices[n]];
    }

    @Override
    ImmutableTable.SerializedForm createSerializedForm() {
        return ImmutableTable.SerializedForm.create(this, this.cellRowIndices, this.cellColumnIndices);
    }

    @Override
    public Map columnMap() {
        return this.columnMap();
    }

    @Override
    public Map rowMap() {
        return this.rowMap();
    }

    static int[] access$200(DenseImmutableTable denseImmutableTable) {
        return denseImmutableTable.rowCounts;
    }

    static ImmutableMap access$300(DenseImmutableTable denseImmutableTable) {
        return denseImmutableTable.columnKeyToIndex;
    }

    static Object[][] access$400(DenseImmutableTable denseImmutableTable) {
        return denseImmutableTable.values;
    }

    static int[] access$500(DenseImmutableTable denseImmutableTable) {
        return denseImmutableTable.columnCounts;
    }

    static ImmutableMap access$600(DenseImmutableTable denseImmutableTable) {
        return denseImmutableTable.rowKeyToIndex;
    }

    private final class ColumnMap
    extends ImmutableArrayMap<C, Map<R, V>> {
        final DenseImmutableTable this$0;

        private ColumnMap(DenseImmutableTable denseImmutableTable) {
            this.this$0 = denseImmutableTable;
            super(DenseImmutableTable.access$500(denseImmutableTable).length);
        }

        @Override
        ImmutableMap<C, Integer> keyToIndex() {
            return DenseImmutableTable.access$300(this.this$0);
        }

        @Override
        Map<R, V> getValue(int n) {
            return new Column(this.this$0, n);
        }

        @Override
        boolean isPartialView() {
            return true;
        }

        @Override
        Object getValue(int n) {
            return this.getValue(n);
        }

        ColumnMap(DenseImmutableTable denseImmutableTable, 1 var2_2) {
            this(denseImmutableTable);
        }
    }

    private final class RowMap
    extends ImmutableArrayMap<R, Map<C, V>> {
        final DenseImmutableTable this$0;

        private RowMap(DenseImmutableTable denseImmutableTable) {
            this.this$0 = denseImmutableTable;
            super(DenseImmutableTable.access$200(denseImmutableTable).length);
        }

        @Override
        ImmutableMap<R, Integer> keyToIndex() {
            return DenseImmutableTable.access$600(this.this$0);
        }

        @Override
        Map<C, V> getValue(int n) {
            return new Row(this.this$0, n);
        }

        @Override
        boolean isPartialView() {
            return true;
        }

        @Override
        Object getValue(int n) {
            return this.getValue(n);
        }

        RowMap(DenseImmutableTable denseImmutableTable, 1 var2_2) {
            this(denseImmutableTable);
        }
    }

    private final class Column
    extends ImmutableArrayMap<R, V> {
        private final int columnIndex;
        final DenseImmutableTable this$0;

        Column(DenseImmutableTable denseImmutableTable, int n) {
            this.this$0 = denseImmutableTable;
            super(DenseImmutableTable.access$500(denseImmutableTable)[n]);
            this.columnIndex = n;
        }

        @Override
        ImmutableMap<R, Integer> keyToIndex() {
            return DenseImmutableTable.access$600(this.this$0);
        }

        @Override
        V getValue(int n) {
            return DenseImmutableTable.access$400(this.this$0)[n][this.columnIndex];
        }

        @Override
        boolean isPartialView() {
            return false;
        }
    }

    private final class Row
    extends ImmutableArrayMap<C, V> {
        private final int rowIndex;
        final DenseImmutableTable this$0;

        Row(DenseImmutableTable denseImmutableTable, int n) {
            this.this$0 = denseImmutableTable;
            super(DenseImmutableTable.access$200(denseImmutableTable)[n]);
            this.rowIndex = n;
        }

        @Override
        ImmutableMap<C, Integer> keyToIndex() {
            return DenseImmutableTable.access$300(this.this$0);
        }

        @Override
        V getValue(int n) {
            return DenseImmutableTable.access$400(this.this$0)[this.rowIndex][n];
        }

        @Override
        boolean isPartialView() {
            return false;
        }
    }

    private static abstract class ImmutableArrayMap<K, V>
    extends ImmutableMap.IteratorBasedImmutableMap<K, V> {
        private final int size;

        ImmutableArrayMap(int n) {
            this.size = n;
        }

        abstract ImmutableMap<K, Integer> keyToIndex();

        private boolean isFull() {
            return this.size == this.keyToIndex().size();
        }

        K getKey(int n) {
            return (K)((ImmutableSet)this.keyToIndex().keySet()).asList().get(n);
        }

        @Nullable
        abstract V getValue(int var1);

        @Override
        ImmutableSet<K> createKeySet() {
            return this.isFull() ? this.keyToIndex().keySet() : super.createKeySet();
        }

        @Override
        public int size() {
            return this.size;
        }

        @Override
        public V get(@Nullable Object object) {
            Integer n = this.keyToIndex().get(object);
            return n == null ? null : (V)this.getValue(n);
        }

        @Override
        UnmodifiableIterator<Map.Entry<K, V>> entryIterator() {
            return new AbstractIterator<Map.Entry<K, V>>(this){
                private int index;
                private final int maxIndex;
                final ImmutableArrayMap this$0;
                {
                    this.this$0 = immutableArrayMap;
                    this.index = -1;
                    this.maxIndex = this.this$0.keyToIndex().size();
                }

                @Override
                protected Map.Entry<K, V> computeNext() {
                    ++this.index;
                    while (this.index < this.maxIndex) {
                        Object v = this.this$0.getValue(this.index);
                        if (v != null) {
                            return Maps.immutableEntry(this.this$0.getKey(this.index), v);
                        }
                        ++this.index;
                    }
                    return (Map.Entry)this.endOfData();
                }

                @Override
                protected Object computeNext() {
                    return this.computeNext();
                }
            };
        }
    }
}

