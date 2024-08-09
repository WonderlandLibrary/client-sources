/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.DenseImmutableTable;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.SparseImmutableTable;
import com.google.common.collect.Table;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
abstract class RegularImmutableTable<R, C, V>
extends ImmutableTable<R, C, V> {
    RegularImmutableTable() {
    }

    abstract Table.Cell<R, C, V> getCell(int var1);

    @Override
    final ImmutableSet<Table.Cell<R, C, V>> createCellSet() {
        return this.isEmpty() ? ImmutableSet.of() : new CellSet(this, null);
    }

    abstract V getValue(int var1);

    @Override
    final ImmutableCollection<V> createValues() {
        return this.isEmpty() ? ImmutableList.of() : new Values(this, null);
    }

    static <R, C, V> RegularImmutableTable<R, C, V> forCells(List<Table.Cell<R, C, V>> list, @Nullable Comparator<? super R> comparator, @Nullable Comparator<? super C> comparator2) {
        Preconditions.checkNotNull(list);
        if (comparator != null || comparator2 != null) {
            Comparator comparator3 = new Comparator<Table.Cell<R, C, V>>(comparator, comparator2){
                final Comparator val$rowComparator;
                final Comparator val$columnComparator;
                {
                    this.val$rowComparator = comparator;
                    this.val$columnComparator = comparator2;
                }

                @Override
                public int compare(Table.Cell<R, C, V> cell, Table.Cell<R, C, V> cell2) {
                    int n;
                    int n2 = n = this.val$rowComparator == null ? 0 : this.val$rowComparator.compare(cell.getRowKey(), cell2.getRowKey());
                    if (n != 0) {
                        return n;
                    }
                    return this.val$columnComparator == null ? 0 : this.val$columnComparator.compare(cell.getColumnKey(), cell2.getColumnKey());
                }

                @Override
                public int compare(Object object, Object object2) {
                    return this.compare((Table.Cell)object, (Table.Cell)object2);
                }
            };
            Collections.sort(list, comparator3);
        }
        return RegularImmutableTable.forCellsInternal(list, comparator, comparator2);
    }

    static <R, C, V> RegularImmutableTable<R, C, V> forCells(Iterable<Table.Cell<R, C, V>> iterable) {
        return RegularImmutableTable.forCellsInternal(iterable, null, null);
    }

    private static final <R, C, V> RegularImmutableTable<R, C, V> forCellsInternal(Iterable<Table.Cell<R, C, V>> iterable, @Nullable Comparator<? super R> comparator, @Nullable Comparator<? super C> comparator2) {
        LinkedHashSet<R> linkedHashSet = new LinkedHashSet<R>();
        LinkedHashSet<C> linkedHashSet2 = new LinkedHashSet<C>();
        ImmutableList<Table.Cell<R, C, V>> immutableList = ImmutableList.copyOf(iterable);
        for (Table.Cell<R, C, V> object2 : iterable) {
            linkedHashSet.add(object2.getRowKey());
            linkedHashSet2.add(object2.getColumnKey());
        }
        ImmutableSet<Object> immutableSet = comparator == null ? ImmutableSet.copyOf(linkedHashSet) : ImmutableSet.copyOf(ImmutableList.sortedCopyOf(comparator, linkedHashSet));
        ImmutableSet immutableSet2 = comparator2 == null ? ImmutableSet.copyOf(linkedHashSet2) : ImmutableSet.copyOf(ImmutableList.sortedCopyOf(comparator2, linkedHashSet2));
        return RegularImmutableTable.forOrderedComponents(immutableList, immutableSet, immutableSet2);
    }

    static <R, C, V> RegularImmutableTable<R, C, V> forOrderedComponents(ImmutableList<Table.Cell<R, C, V>> immutableList, ImmutableSet<R> immutableSet, ImmutableSet<C> immutableSet2) {
        return (long)immutableList.size() > (long)immutableSet.size() * (long)immutableSet2.size() / 2L ? new DenseImmutableTable<R, C, V>(immutableList, immutableSet, immutableSet2) : new SparseImmutableTable<R, C, V>(immutableList, immutableSet, immutableSet2);
    }

    @Override
    Collection createValues() {
        return this.createValues();
    }

    @Override
    Set createCellSet() {
        return this.createCellSet();
    }

    private final class Values
    extends ImmutableList<V> {
        final RegularImmutableTable this$0;

        private Values(RegularImmutableTable regularImmutableTable) {
            this.this$0 = regularImmutableTable;
        }

        @Override
        public int size() {
            return this.this$0.size();
        }

        @Override
        public V get(int n) {
            return this.this$0.getValue(n);
        }

        @Override
        boolean isPartialView() {
            return false;
        }

        Values(RegularImmutableTable regularImmutableTable, 1 var2_2) {
            this(regularImmutableTable);
        }
    }

    private final class CellSet
    extends ImmutableSet.Indexed<Table.Cell<R, C, V>> {
        final RegularImmutableTable this$0;

        private CellSet(RegularImmutableTable regularImmutableTable) {
            this.this$0 = regularImmutableTable;
        }

        @Override
        public int size() {
            return this.this$0.size();
        }

        @Override
        Table.Cell<R, C, V> get(int n) {
            return this.this$0.getCell(n);
        }

        @Override
        public boolean contains(@Nullable Object object) {
            if (object instanceof Table.Cell) {
                Table.Cell cell = (Table.Cell)object;
                Object object2 = this.this$0.get(cell.getRowKey(), cell.getColumnKey());
                return object2 != null && object2.equals(cell.getValue());
            }
            return true;
        }

        @Override
        boolean isPartialView() {
            return true;
        }

        @Override
        Object get(int n) {
            return this.get(n);
        }

        CellSet(RegularImmutableTable regularImmutableTable, 1 var2_2) {
            this(regularImmutableTable);
        }
    }
}

