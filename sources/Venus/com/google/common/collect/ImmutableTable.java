/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractTable;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.RegularImmutableTable;
import com.google.common.collect.SingletonImmutableTable;
import com.google.common.collect.SparseImmutableTable;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class ImmutableTable<R, C, V>
extends AbstractTable<R, C, V>
implements Serializable {
    @Beta
    public static <T, R, C, V> Collector<T, ?, ImmutableTable<R, C, V>> toImmutableTable(Function<? super T, ? extends R> function, Function<? super T, ? extends C> function2, Function<? super T, ? extends V> function3) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(function2);
        Preconditions.checkNotNull(function3);
        return Collector.of(ImmutableTable::lambda$toImmutableTable$0, (arg_0, arg_1) -> ImmutableTable.lambda$toImmutableTable$1(function, function2, function3, arg_0, arg_1), ImmutableTable::lambda$toImmutableTable$2, ImmutableTable::lambda$toImmutableTable$3, new Collector.Characteristics[0]);
    }

    public static <T, R, C, V> Collector<T, ?, ImmutableTable<R, C, V>> toImmutableTable(Function<? super T, ? extends R> function, Function<? super T, ? extends C> function2, Function<? super T, ? extends V> function3, BinaryOperator<V> binaryOperator) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(function2);
        Preconditions.checkNotNull(function3);
        Preconditions.checkNotNull(binaryOperator);
        return Collector.of(ImmutableTable::lambda$toImmutableTable$4, (arg_0, arg_1) -> ImmutableTable.lambda$toImmutableTable$5(function, function2, function3, binaryOperator, arg_0, arg_1), (arg_0, arg_1) -> ImmutableTable.lambda$toImmutableTable$6(binaryOperator, arg_0, arg_1), ImmutableTable::lambda$toImmutableTable$7, new Collector.Characteristics[0]);
    }

    public static <R, C, V> ImmutableTable<R, C, V> of() {
        return SparseImmutableTable.EMPTY;
    }

    public static <R, C, V> ImmutableTable<R, C, V> of(R r, C c, V v) {
        return new SingletonImmutableTable<R, C, V>(r, c, v);
    }

    public static <R, C, V> ImmutableTable<R, C, V> copyOf(Table<? extends R, ? extends C, ? extends V> table) {
        if (table instanceof ImmutableTable) {
            ImmutableTable immutableTable = (ImmutableTable)table;
            return immutableTable;
        }
        return ImmutableTable.copyOf(table.cellSet());
    }

    private static <R, C, V> ImmutableTable<R, C, V> copyOf(Iterable<? extends Table.Cell<? extends R, ? extends C, ? extends V>> iterable) {
        Builder<R, C, V> builder = ImmutableTable.builder();
        for (Table.Cell<R, C, V> cell : iterable) {
            builder.put(cell);
        }
        return builder.build();
    }

    public static <R, C, V> Builder<R, C, V> builder() {
        return new Builder();
    }

    static <R, C, V> Table.Cell<R, C, V> cellOf(R r, C c, V v) {
        return Tables.immutableCell(Preconditions.checkNotNull(r), Preconditions.checkNotNull(c), Preconditions.checkNotNull(v));
    }

    ImmutableTable() {
    }

    @Override
    public ImmutableSet<Table.Cell<R, C, V>> cellSet() {
        return (ImmutableSet)super.cellSet();
    }

    @Override
    abstract ImmutableSet<Table.Cell<R, C, V>> createCellSet();

    @Override
    final UnmodifiableIterator<Table.Cell<R, C, V>> cellIterator() {
        throw new AssertionError((Object)"should never be called");
    }

    @Override
    final Spliterator<Table.Cell<R, C, V>> cellSpliterator() {
        throw new AssertionError((Object)"should never be called");
    }

    @Override
    public ImmutableCollection<V> values() {
        return (ImmutableCollection)super.values();
    }

    @Override
    abstract ImmutableCollection<V> createValues();

    @Override
    final Iterator<V> valuesIterator() {
        throw new AssertionError((Object)"should never be called");
    }

    @Override
    public ImmutableMap<R, V> column(C c) {
        Preconditions.checkNotNull(c);
        return MoreObjects.firstNonNull((ImmutableMap)((ImmutableMap)this.columnMap()).get(c), ImmutableMap.of());
    }

    @Override
    public ImmutableSet<C> columnKeySet() {
        return ((ImmutableMap)this.columnMap()).keySet();
    }

    @Override
    public abstract ImmutableMap<C, Map<R, V>> columnMap();

    @Override
    public ImmutableMap<C, V> row(R r) {
        Preconditions.checkNotNull(r);
        return MoreObjects.firstNonNull((ImmutableMap)((ImmutableMap)this.rowMap()).get(r), ImmutableMap.of());
    }

    @Override
    public ImmutableSet<R> rowKeySet() {
        return ((ImmutableMap)this.rowMap()).keySet();
    }

    @Override
    public abstract ImmutableMap<R, Map<C, V>> rowMap();

    @Override
    public boolean contains(@Nullable Object object, @Nullable Object object2) {
        return this.get(object, object2) != null;
    }

    @Override
    public boolean containsValue(@Nullable Object object) {
        return ((ImmutableCollection)this.values()).contains(object);
    }

    @Override
    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public final V put(R r, C c, V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public final void putAll(Table<? extends R, ? extends C, ? extends V> table) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public final V remove(Object object, Object object2) {
        throw new UnsupportedOperationException();
    }

    abstract SerializedForm createSerializedForm();

    final Object writeReplace() {
        return this.createSerializedForm();
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
    public boolean equals(@Nullable Object object) {
        return super.equals(object);
    }

    @Override
    Collection createValues() {
        return this.createValues();
    }

    @Override
    public Collection values() {
        return this.values();
    }

    @Override
    Iterator cellIterator() {
        return this.cellIterator();
    }

    @Override
    Set createCellSet() {
        return this.createCellSet();
    }

    @Override
    public Set cellSet() {
        return this.cellSet();
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public Object get(@Nullable Object object, @Nullable Object object2) {
        return super.get(object, object2);
    }

    @Override
    public Set columnKeySet() {
        return this.columnKeySet();
    }

    @Override
    public Set rowKeySet() {
        return this.rowKeySet();
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
    public Map columnMap() {
        return this.columnMap();
    }

    @Override
    public Map rowMap() {
        return this.rowMap();
    }

    @Override
    public Map column(Object object) {
        return this.column(object);
    }

    @Override
    public Map row(Object object) {
        return this.row(object);
    }

    private static ImmutableTable lambda$toImmutableTable$7(CollectorState collectorState) {
        return collectorState.toTable();
    }

    private static CollectorState lambda$toImmutableTable$6(BinaryOperator binaryOperator, CollectorState collectorState, CollectorState collectorState2) {
        return collectorState.combine(collectorState2, binaryOperator);
    }

    private static void lambda$toImmutableTable$5(Function function, Function function2, Function function3, BinaryOperator binaryOperator, CollectorState collectorState, Object object) {
        collectorState.put(function.apply(object), function2.apply(object), function3.apply(object), binaryOperator);
    }

    private static CollectorState lambda$toImmutableTable$4() {
        return new CollectorState(null);
    }

    private static ImmutableTable lambda$toImmutableTable$3(Builder builder) {
        return builder.build();
    }

    private static Builder lambda$toImmutableTable$2(Builder builder, Builder builder2) {
        return builder.combine(builder2);
    }

    private static void lambda$toImmutableTable$1(Function function, Function function2, Function function3, Builder builder, Object object) {
        builder.put(function.apply(object), function2.apply(object), function3.apply(object));
    }

    private static Builder lambda$toImmutableTable$0() {
        return new Builder();
    }

    static ImmutableTable access$000(Iterable iterable) {
        return ImmutableTable.copyOf(iterable);
    }

    static final class SerializedForm
    implements Serializable {
        private final Object[] rowKeys;
        private final Object[] columnKeys;
        private final Object[] cellValues;
        private final int[] cellRowIndices;
        private final int[] cellColumnIndices;
        private static final long serialVersionUID = 0L;

        private SerializedForm(Object[] objectArray, Object[] objectArray2, Object[] objectArray3, int[] nArray, int[] nArray2) {
            this.rowKeys = objectArray;
            this.columnKeys = objectArray2;
            this.cellValues = objectArray3;
            this.cellRowIndices = nArray;
            this.cellColumnIndices = nArray2;
        }

        static SerializedForm create(ImmutableTable<?, ?, ?> immutableTable, int[] nArray, int[] nArray2) {
            return new SerializedForm(((ImmutableCollection)((Object)immutableTable.rowKeySet())).toArray(), ((ImmutableCollection)((Object)immutableTable.columnKeySet())).toArray(), ((ImmutableCollection)immutableTable.values()).toArray(), nArray, nArray2);
        }

        Object readResolve() {
            if (this.cellValues.length == 0) {
                return ImmutableTable.of();
            }
            if (this.cellValues.length == 1) {
                return ImmutableTable.of(this.rowKeys[0], this.columnKeys[0], this.cellValues[0]);
            }
            ImmutableList.Builder builder = new ImmutableList.Builder(this.cellValues.length);
            for (int i = 0; i < this.cellValues.length; ++i) {
                builder.add(ImmutableTable.cellOf(this.rowKeys[this.cellRowIndices[i]], this.columnKeys[this.cellColumnIndices[i]], this.cellValues[i]));
            }
            return RegularImmutableTable.forOrderedComponents(builder.build(), ImmutableSet.copyOf(this.rowKeys), ImmutableSet.copyOf(this.columnKeys));
        }
    }

    public static final class Builder<R, C, V> {
        private final List<Table.Cell<R, C, V>> cells = Lists.newArrayList();
        private Comparator<? super R> rowComparator;
        private Comparator<? super C> columnComparator;

        @CanIgnoreReturnValue
        public Builder<R, C, V> orderRowsBy(Comparator<? super R> comparator) {
            this.rowComparator = Preconditions.checkNotNull(comparator);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<R, C, V> orderColumnsBy(Comparator<? super C> comparator) {
            this.columnComparator = Preconditions.checkNotNull(comparator);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<R, C, V> put(R r, C c, V v) {
            this.cells.add(ImmutableTable.cellOf(r, c, v));
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<R, C, V> put(Table.Cell<? extends R, ? extends C, ? extends V> cell) {
            if (cell instanceof Tables.ImmutableCell) {
                Preconditions.checkNotNull(cell.getRowKey());
                Preconditions.checkNotNull(cell.getColumnKey());
                Preconditions.checkNotNull(cell.getValue());
                Table.Cell<R, C, V> cell2 = cell;
                this.cells.add(cell2);
            } else {
                this.put(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
            }
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<R, C, V> putAll(Table<? extends R, ? extends C, ? extends V> table) {
            for (Table.Cell<R, C, V> cell : table.cellSet()) {
                this.put(cell);
            }
            return this;
        }

        Builder<R, C, V> combine(Builder<R, C, V> builder) {
            this.cells.addAll(builder.cells);
            return this;
        }

        public ImmutableTable<R, C, V> build() {
            int n = this.cells.size();
            switch (n) {
                case 0: {
                    return ImmutableTable.of();
                }
                case 1: {
                    return new SingletonImmutableTable<R, C, V>(Iterables.getOnlyElement(this.cells));
                }
            }
            return RegularImmutableTable.forCells(this.cells, this.rowComparator, this.columnComparator);
        }
    }

    private static final class MutableCell<R, C, V>
    extends Tables.AbstractCell<R, C, V> {
        private final R row;
        private final C column;
        private V value;

        MutableCell(R r, C c, V v) {
            this.row = Preconditions.checkNotNull(r);
            this.column = Preconditions.checkNotNull(c);
            this.value = Preconditions.checkNotNull(v);
        }

        @Override
        public R getRowKey() {
            return this.row;
        }

        @Override
        public C getColumnKey() {
            return this.column;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        void merge(V v, BinaryOperator<V> binaryOperator) {
            Preconditions.checkNotNull(v);
            this.value = Preconditions.checkNotNull(binaryOperator.apply(this.value, v));
        }
    }

    private static final class CollectorState<R, C, V> {
        final List<MutableCell<R, C, V>> insertionOrder = new ArrayList<MutableCell<R, C, V>>();
        final Table<R, C, MutableCell<R, C, V>> table = HashBasedTable.create();

        private CollectorState() {
        }

        void put(R r, C c, V v, BinaryOperator<V> binaryOperator) {
            MutableCell<R, C, V> mutableCell = this.table.get(r, c);
            if (mutableCell == null) {
                MutableCell<R, C, V> mutableCell2 = new MutableCell<R, C, V>(r, c, v);
                this.insertionOrder.add(mutableCell2);
                this.table.put(r, c, mutableCell2);
            } else {
                mutableCell.merge(v, binaryOperator);
            }
        }

        CollectorState<R, C, V> combine(CollectorState<R, C, V> collectorState, BinaryOperator<V> binaryOperator) {
            for (MutableCell<R, C, V> mutableCell : collectorState.insertionOrder) {
                this.put(mutableCell.getRowKey(), mutableCell.getColumnKey(), mutableCell.getValue(), binaryOperator);
            }
            return this;
        }

        ImmutableTable<R, C, V> toTable() {
            return ImmutableTable.access$000(this.insertionOrder);
        }

        CollectorState(1 var1_1) {
            this();
        }
    }
}

