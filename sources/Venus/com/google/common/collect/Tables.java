/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.AbstractTable;
import com.google.common.collect.CollectSpliterators;
import com.google.common.collect.Collections2;
import com.google.common.collect.ForwardingTable;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.RowSortedTable;
import com.google.common.collect.StandardTable;
import com.google.common.collect.Table;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Spliterator;
import java.util.function.BinaryOperator;
import java.util.stream.Collector;
import javax.annotation.Nullable;

@GwtCompatible
public final class Tables {
    private static final Function<? extends Map<?, ?>, ? extends Map<?, ?>> UNMODIFIABLE_WRAPPER = new Function<Map<Object, Object>, Map<Object, Object>>(){

        @Override
        public Map<Object, Object> apply(Map<Object, Object> map) {
            return Collections.unmodifiableMap(map);
        }

        @Override
        public Object apply(Object object) {
            return this.apply((Map)object);
        }
    };

    private Tables() {
    }

    @Beta
    public static <T, R, C, V, I extends Table<R, C, V>> Collector<T, ?, I> toTable(java.util.function.Function<? super T, ? extends R> function, java.util.function.Function<? super T, ? extends C> function2, java.util.function.Function<? super T, ? extends V> function3, java.util.function.Supplier<I> supplier) {
        return Tables.toTable(function, function2, function3, Tables::lambda$toTable$0, supplier);
    }

    public static <T, R, C, V, I extends Table<R, C, V>> Collector<T, ?, I> toTable(java.util.function.Function<? super T, ? extends R> function, java.util.function.Function<? super T, ? extends C> function2, java.util.function.Function<? super T, ? extends V> function3, BinaryOperator<V> binaryOperator, java.util.function.Supplier<I> supplier) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(function2);
        Preconditions.checkNotNull(function3);
        Preconditions.checkNotNull(binaryOperator);
        Preconditions.checkNotNull(supplier);
        return Collector.of(supplier, (arg_0, arg_1) -> Tables.lambda$toTable$1(function, function2, function3, binaryOperator, arg_0, arg_1), (arg_0, arg_1) -> Tables.lambda$toTable$2(binaryOperator, arg_0, arg_1), new Collector.Characteristics[0]);
    }

    private static <R, C, V> void merge(Table<R, C, V> table, R r, C c, V v, BinaryOperator<V> binaryOperator) {
        Preconditions.checkNotNull(v);
        V v2 = table.get(r, c);
        if (v2 == null) {
            table.put(r, c, v);
        } else {
            Object r2 = binaryOperator.apply(v2, v);
            if (r2 == null) {
                table.remove(r, c);
            } else {
                table.put(r, c, r2);
            }
        }
    }

    public static <R, C, V> Table.Cell<R, C, V> immutableCell(@Nullable R r, @Nullable C c, @Nullable V v) {
        return new ImmutableCell<R, C, V>(r, c, v);
    }

    public static <R, C, V> Table<C, R, V> transpose(Table<R, C, V> table) {
        return table instanceof TransposeTable ? ((TransposeTable)table).original : new TransposeTable<C, R, V>(table);
    }

    @Beta
    public static <R, C, V> Table<R, C, V> newCustomTable(Map<R, Map<C, V>> map, Supplier<? extends Map<C, V>> supplier) {
        Preconditions.checkArgument(map.isEmpty());
        Preconditions.checkNotNull(supplier);
        return new StandardTable<R, C, V>(map, supplier);
    }

    @Beta
    public static <R, C, V1, V2> Table<R, C, V2> transformValues(Table<R, C, V1> table, Function<? super V1, V2> function) {
        return new TransformedTable<R, C, V1, V2>(table, function);
    }

    public static <R, C, V> Table<R, C, V> unmodifiableTable(Table<? extends R, ? extends C, ? extends V> table) {
        return new UnmodifiableTable<R, C, V>(table);
    }

    @Beta
    public static <R, C, V> RowSortedTable<R, C, V> unmodifiableRowSortedTable(RowSortedTable<R, ? extends C, ? extends V> rowSortedTable) {
        return new UnmodifiableRowSortedMap<R, C, V>(rowSortedTable);
    }

    private static <K, V> Function<Map<K, V>, Map<K, V>> unmodifiableWrapper() {
        return UNMODIFIABLE_WRAPPER;
    }

    static boolean equalsImpl(Table<?, ?, ?> table, @Nullable Object object) {
        if (object == table) {
            return false;
        }
        if (object instanceof Table) {
            Table table2 = (Table)object;
            return table.cellSet().equals(table2.cellSet());
        }
        return true;
    }

    private static Table lambda$toTable$2(BinaryOperator binaryOperator, Table table, Table table2) {
        for (Table.Cell cell : table2.cellSet()) {
            Tables.merge(table, cell.getRowKey(), cell.getColumnKey(), cell.getValue(), binaryOperator);
        }
        return table;
    }

    private static void lambda$toTable$1(java.util.function.Function function, java.util.function.Function function2, java.util.function.Function function3, BinaryOperator binaryOperator, Table table, Object object) {
        Tables.merge(table, function.apply(object), function2.apply(object), function3.apply(object), binaryOperator);
    }

    private static Object lambda$toTable$0(Object object, Object object2) {
        throw new IllegalStateException("Conflicting values " + object + " and " + object2);
    }

    static Function access$000() {
        return Tables.unmodifiableWrapper();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static final class UnmodifiableRowSortedMap<R, C, V>
    extends UnmodifiableTable<R, C, V>
    implements RowSortedTable<R, C, V> {
        private static final long serialVersionUID = 0L;

        public UnmodifiableRowSortedMap(RowSortedTable<R, ? extends C, ? extends V> rowSortedTable) {
            super(rowSortedTable);
        }

        @Override
        protected RowSortedTable<R, C, V> delegate() {
            return (RowSortedTable)super.delegate();
        }

        @Override
        public SortedMap<R, Map<C, V>> rowMap() {
            Function function = Tables.access$000();
            return Collections.unmodifiableSortedMap(Maps.transformValues(this.delegate().rowMap(), function));
        }

        @Override
        public SortedSet<R> rowKeySet() {
            return Collections.unmodifiableSortedSet(this.delegate().rowKeySet());
        }

        @Override
        public Map rowMap() {
            return this.rowMap();
        }

        @Override
        public Set rowKeySet() {
            return this.rowKeySet();
        }

        @Override
        protected Table delegate() {
            return this.delegate();
        }

        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }

    private static class UnmodifiableTable<R, C, V>
    extends ForwardingTable<R, C, V>
    implements Serializable {
        final Table<? extends R, ? extends C, ? extends V> delegate;
        private static final long serialVersionUID = 0L;

        UnmodifiableTable(Table<? extends R, ? extends C, ? extends V> table) {
            this.delegate = Preconditions.checkNotNull(table);
        }

        @Override
        protected Table<R, C, V> delegate() {
            return this.delegate;
        }

        @Override
        public Set<Table.Cell<R, C, V>> cellSet() {
            return Collections.unmodifiableSet(super.cellSet());
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Map<R, V> column(@Nullable C c) {
            return Collections.unmodifiableMap(super.column(c));
        }

        @Override
        public Set<C> columnKeySet() {
            return Collections.unmodifiableSet(super.columnKeySet());
        }

        @Override
        public Map<C, Map<R, V>> columnMap() {
            Function function = Tables.access$000();
            return Collections.unmodifiableMap(Maps.transformValues(super.columnMap(), function));
        }

        @Override
        public V put(@Nullable R r, @Nullable C c, @Nullable V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V remove(@Nullable Object object, @Nullable Object object2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Map<C, V> row(@Nullable R r) {
            return Collections.unmodifiableMap(super.row(r));
        }

        @Override
        public Set<R> rowKeySet() {
            return Collections.unmodifiableSet(super.rowKeySet());
        }

        @Override
        public Map<R, Map<C, V>> rowMap() {
            Function function = Tables.access$000();
            return Collections.unmodifiableMap(Maps.transformValues(super.rowMap(), function));
        }

        @Override
        public Collection<V> values() {
            return Collections.unmodifiableCollection(super.values());
        }

        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }

    private static class TransformedTable<R, C, V1, V2>
    extends AbstractTable<R, C, V2> {
        final Table<R, C, V1> fromTable;
        final Function<? super V1, V2> function;

        TransformedTable(Table<R, C, V1> table, Function<? super V1, V2> function) {
            this.fromTable = Preconditions.checkNotNull(table);
            this.function = Preconditions.checkNotNull(function);
        }

        @Override
        public boolean contains(Object object, Object object2) {
            return this.fromTable.contains(object, object2);
        }

        @Override
        public V2 get(Object object, Object object2) {
            return this.contains(object, object2) ? (V2)this.function.apply((V1)this.fromTable.get(object, object2)) : null;
        }

        @Override
        public int size() {
            return this.fromTable.size();
        }

        @Override
        public void clear() {
            this.fromTable.clear();
        }

        @Override
        public V2 put(R r, C c, V2 V2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void putAll(Table<? extends R, ? extends C, ? extends V2> table) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V2 remove(Object object, Object object2) {
            return this.contains(object, object2) ? (V2)this.function.apply((V1)this.fromTable.remove(object, object2)) : null;
        }

        @Override
        public Map<C, V2> row(R r) {
            return Maps.transformValues(this.fromTable.row(r), this.function);
        }

        @Override
        public Map<R, V2> column(C c) {
            return Maps.transformValues(this.fromTable.column(c), this.function);
        }

        Function<Table.Cell<R, C, V1>, Table.Cell<R, C, V2>> cellFunction() {
            return new Function<Table.Cell<R, C, V1>, Table.Cell<R, C, V2>>(this){
                final TransformedTable this$0;
                {
                    this.this$0 = transformedTable;
                }

                @Override
                public Table.Cell<R, C, V2> apply(Table.Cell<R, C, V1> cell) {
                    return Tables.immutableCell(cell.getRowKey(), cell.getColumnKey(), this.this$0.function.apply(cell.getValue()));
                }

                @Override
                public Object apply(Object object) {
                    return this.apply((Table.Cell)object);
                }
            };
        }

        @Override
        Iterator<Table.Cell<R, C, V2>> cellIterator() {
            return Iterators.transform(this.fromTable.cellSet().iterator(), this.cellFunction());
        }

        @Override
        Spliterator<Table.Cell<R, C, V2>> cellSpliterator() {
            return CollectSpliterators.map(this.fromTable.cellSet().spliterator(), this.cellFunction());
        }

        @Override
        public Set<R> rowKeySet() {
            return this.fromTable.rowKeySet();
        }

        @Override
        public Set<C> columnKeySet() {
            return this.fromTable.columnKeySet();
        }

        @Override
        Collection<V2> createValues() {
            return Collections2.transform(this.fromTable.values(), this.function);
        }

        @Override
        public Map<R, Map<C, V2>> rowMap() {
            Function function = new Function<Map<C, V1>, Map<C, V2>>(this){
                final TransformedTable this$0;
                {
                    this.this$0 = transformedTable;
                }

                @Override
                public Map<C, V2> apply(Map<C, V1> map) {
                    return Maps.transformValues(map, this.this$0.function);
                }

                @Override
                public Object apply(Object object) {
                    return this.apply((Map)object);
                }
            };
            return Maps.transformValues(this.fromTable.rowMap(), function);
        }

        @Override
        public Map<C, Map<R, V2>> columnMap() {
            Function function = new Function<Map<R, V1>, Map<R, V2>>(this){
                final TransformedTable this$0;
                {
                    this.this$0 = transformedTable;
                }

                @Override
                public Map<R, V2> apply(Map<R, V1> map) {
                    return Maps.transformValues(map, this.this$0.function);
                }

                @Override
                public Object apply(Object object) {
                    return this.apply((Map)object);
                }
            };
            return Maps.transformValues(this.fromTable.columnMap(), function);
        }
    }

    private static class TransposeTable<C, R, V>
    extends AbstractTable<C, R, V> {
        final Table<R, C, V> original;
        private static final Function<Table.Cell<?, ?, ?>, Table.Cell<?, ?, ?>> TRANSPOSE_CELL = new Function<Table.Cell<?, ?, ?>, Table.Cell<?, ?, ?>>(){

            @Override
            public Table.Cell<?, ?, ?> apply(Table.Cell<?, ?, ?> cell) {
                return Tables.immutableCell(cell.getColumnKey(), cell.getRowKey(), cell.getValue());
            }

            @Override
            public Object apply(Object object) {
                return this.apply((Table.Cell)object);
            }
        };

        TransposeTable(Table<R, C, V> table) {
            this.original = Preconditions.checkNotNull(table);
        }

        @Override
        public void clear() {
            this.original.clear();
        }

        @Override
        public Map<C, V> column(R r) {
            return this.original.row(r);
        }

        @Override
        public Set<R> columnKeySet() {
            return this.original.rowKeySet();
        }

        @Override
        public Map<R, Map<C, V>> columnMap() {
            return this.original.rowMap();
        }

        @Override
        public boolean contains(@Nullable Object object, @Nullable Object object2) {
            return this.original.contains(object2, object);
        }

        @Override
        public boolean containsColumn(@Nullable Object object) {
            return this.original.containsRow(object);
        }

        @Override
        public boolean containsRow(@Nullable Object object) {
            return this.original.containsColumn(object);
        }

        @Override
        public boolean containsValue(@Nullable Object object) {
            return this.original.containsValue(object);
        }

        @Override
        public V get(@Nullable Object object, @Nullable Object object2) {
            return this.original.get(object2, object);
        }

        @Override
        public V put(C c, R r, V v) {
            return this.original.put(r, c, v);
        }

        @Override
        public void putAll(Table<? extends C, ? extends R, ? extends V> table) {
            this.original.putAll(Tables.transpose(table));
        }

        @Override
        public V remove(@Nullable Object object, @Nullable Object object2) {
            return this.original.remove(object2, object);
        }

        @Override
        public Map<R, V> row(C c) {
            return this.original.column(c);
        }

        @Override
        public Set<C> rowKeySet() {
            return this.original.columnKeySet();
        }

        @Override
        public Map<C, Map<R, V>> rowMap() {
            return this.original.columnMap();
        }

        @Override
        public int size() {
            return this.original.size();
        }

        @Override
        public Collection<V> values() {
            return this.original.values();
        }

        @Override
        Iterator<Table.Cell<C, R, V>> cellIterator() {
            return Iterators.transform(this.original.cellSet().iterator(), TRANSPOSE_CELL);
        }

        @Override
        Spliterator<Table.Cell<C, R, V>> cellSpliterator() {
            return CollectSpliterators.map(this.original.cellSet().spliterator(), TRANSPOSE_CELL);
        }
    }

    static abstract class AbstractCell<R, C, V>
    implements Table.Cell<R, C, V> {
        AbstractCell() {
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (object instanceof Table.Cell) {
                Table.Cell cell = (Table.Cell)object;
                return Objects.equal(this.getRowKey(), cell.getRowKey()) && Objects.equal(this.getColumnKey(), cell.getColumnKey()) && Objects.equal(this.getValue(), cell.getValue());
            }
            return true;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.getRowKey(), this.getColumnKey(), this.getValue());
        }

        public String toString() {
            return "(" + this.getRowKey() + "," + this.getColumnKey() + ")=" + this.getValue();
        }
    }

    static final class ImmutableCell<R, C, V>
    extends AbstractCell<R, C, V>
    implements Serializable {
        private final R rowKey;
        private final C columnKey;
        private final V value;
        private static final long serialVersionUID = 0L;

        ImmutableCell(@Nullable R r, @Nullable C c, @Nullable V v) {
            this.rowKey = r;
            this.columnKey = c;
            this.value = v;
        }

        @Override
        public R getRowKey() {
            return this.rowKey;
        }

        @Override
        public C getColumnKey() {
            return this.columnKey;
        }

        @Override
        public V getValue() {
            return this.value;
        }
    }
}

