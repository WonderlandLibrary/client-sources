/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.StandardRowSortedTable;
import com.google.common.collect.StandardTable;
import com.google.common.collect.Table;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import javax.annotation.Nullable;

@GwtCompatible(serializable=true)
public class TreeBasedTable<R, C, V>
extends StandardRowSortedTable<R, C, V> {
    private final Comparator<? super C> columnComparator;
    private static final long serialVersionUID = 0L;

    public static <R extends Comparable, C extends Comparable, V> TreeBasedTable<R, C, V> create() {
        return new TreeBasedTable(Ordering.natural(), Ordering.natural());
    }

    public static <R, C, V> TreeBasedTable<R, C, V> create(Comparator<? super R> comparator, Comparator<? super C> comparator2) {
        Preconditions.checkNotNull(comparator);
        Preconditions.checkNotNull(comparator2);
        return new TreeBasedTable<R, C, V>(comparator, comparator2);
    }

    public static <R, C, V> TreeBasedTable<R, C, V> create(TreeBasedTable<R, C, ? extends V> treeBasedTable) {
        TreeBasedTable<R, C, V> treeBasedTable2 = new TreeBasedTable<R, C, V>(treeBasedTable.rowComparator(), treeBasedTable.columnComparator());
        treeBasedTable2.putAll((Table)treeBasedTable);
        return treeBasedTable2;
    }

    TreeBasedTable(Comparator<? super R> comparator, Comparator<? super C> comparator2) {
        super(new TreeMap(comparator), new Factory(comparator2));
        this.columnComparator = comparator2;
    }

    @Deprecated
    public Comparator<? super R> rowComparator() {
        return this.rowKeySet().comparator();
    }

    @Deprecated
    public Comparator<? super C> columnComparator() {
        return this.columnComparator;
    }

    @Override
    public SortedMap<C, V> row(R r) {
        return new TreeRow(this, r);
    }

    @Override
    public SortedSet<R> rowKeySet() {
        return super.rowKeySet();
    }

    @Override
    public SortedMap<R, Map<C, V>> rowMap() {
        return super.rowMap();
    }

    @Override
    Iterator<C> createColumnKeyIterator() {
        Comparator<C> comparator = this.columnComparator();
        UnmodifiableIterator<C> unmodifiableIterator = Iterators.mergeSorted(Iterables.transform(this.backingMap.values(), new Function<Map<C, V>, Iterator<C>>(this){
            final TreeBasedTable this$0;
            {
                this.this$0 = treeBasedTable;
            }

            @Override
            public Iterator<C> apply(Map<C, V> map) {
                return map.keySet().iterator();
            }

            @Override
            public Object apply(Object object) {
                return this.apply((Map)object);
            }
        }), comparator);
        return new AbstractIterator<C>(this, unmodifiableIterator, comparator){
            C lastValue;
            final Iterator val$merged;
            final Comparator val$comparator;
            final TreeBasedTable this$0;
            {
                this.this$0 = treeBasedTable;
                this.val$merged = iterator2;
                this.val$comparator = comparator;
            }

            @Override
            protected C computeNext() {
                while (this.val$merged.hasNext()) {
                    Object e = this.val$merged.next();
                    boolean bl = this.lastValue != null && this.val$comparator.compare(e, this.lastValue) == 0;
                    if (bl) continue;
                    this.lastValue = e;
                    return this.lastValue;
                }
                this.lastValue = null;
                return this.endOfData();
            }
        };
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
    public Map row(Object object) {
        return this.row(object);
    }

    @Override
    public Map columnMap() {
        return super.columnMap();
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
    public Map column(Object object) {
        return super.column(object);
    }

    @Override
    public Set cellSet() {
        return super.cellSet();
    }

    @Override
    @CanIgnoreReturnValue
    public Object remove(@Nullable Object object, @Nullable Object object2) {
        return super.remove(object, object2);
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
    public Object get(@Nullable Object object, @Nullable Object object2) {
        return super.get(object, object2);
    }

    @Override
    public boolean containsValue(@Nullable Object object) {
        return super.containsValue(object);
    }

    @Override
    public boolean containsRow(@Nullable Object object) {
        return super.containsRow(object);
    }

    @Override
    public boolean containsColumn(@Nullable Object object) {
        return super.containsColumn(object);
    }

    @Override
    public boolean contains(@Nullable Object object, @Nullable Object object2) {
        return super.contains(object, object2);
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
    public void putAll(Table table) {
        super.putAll(table);
    }

    private class TreeRow
    extends StandardTable.Row
    implements SortedMap<C, V> {
        @Nullable
        final C lowerBound;
        @Nullable
        final C upperBound;
        transient SortedMap<C, V> wholeRow;
        final TreeBasedTable this$0;

        TreeRow(TreeBasedTable treeBasedTable, R r) {
            this(treeBasedTable, r, null, null);
        }

        TreeRow(TreeBasedTable treeBasedTable, @Nullable R r, @Nullable C c, C c2) {
            this.this$0 = treeBasedTable;
            super(treeBasedTable, r);
            this.lowerBound = c;
            this.upperBound = c2;
            Preconditions.checkArgument(c == null || c2 == null || this.compare(c, c2) <= 0);
        }

        @Override
        public SortedSet<C> keySet() {
            return new Maps.SortedKeySet(this);
        }

        @Override
        public Comparator<? super C> comparator() {
            return this.this$0.columnComparator();
        }

        int compare(Object object, Object object2) {
            Comparator comparator = this.comparator();
            return comparator.compare(object, object2);
        }

        boolean rangeContains(@Nullable Object object) {
            return !(object == null || this.lowerBound != null && this.compare(this.lowerBound, object) > 0 || this.upperBound != null && this.compare(this.upperBound, object) <= 0);
        }

        @Override
        public SortedMap<C, V> subMap(C c, C c2) {
            Preconditions.checkArgument(this.rangeContains(Preconditions.checkNotNull(c)) && this.rangeContains(Preconditions.checkNotNull(c2)));
            return new TreeRow(this.this$0, this.rowKey, c, c2);
        }

        @Override
        public SortedMap<C, V> headMap(C c) {
            Preconditions.checkArgument(this.rangeContains(Preconditions.checkNotNull(c)));
            return new TreeRow(this.this$0, this.rowKey, this.lowerBound, c);
        }

        @Override
        public SortedMap<C, V> tailMap(C c) {
            Preconditions.checkArgument(this.rangeContains(Preconditions.checkNotNull(c)));
            return new TreeRow(this.this$0, this.rowKey, c, this.upperBound);
        }

        @Override
        public C firstKey() {
            Map map = this.backingRowMap();
            if (map == null) {
                throw new NoSuchElementException();
            }
            return this.backingRowMap().firstKey();
        }

        @Override
        public C lastKey() {
            Map map = this.backingRowMap();
            if (map == null) {
                throw new NoSuchElementException();
            }
            return this.backingRowMap().lastKey();
        }

        SortedMap<C, V> wholeRow() {
            if (this.wholeRow == null || this.wholeRow.isEmpty() && this.this$0.backingMap.containsKey(this.rowKey)) {
                this.wholeRow = (SortedMap)this.this$0.backingMap.get(this.rowKey);
            }
            return this.wholeRow;
        }

        SortedMap<C, V> backingRowMap() {
            return (SortedMap)super.backingRowMap();
        }

        SortedMap<C, V> computeBackingRowMap() {
            SortedMap sortedMap = this.wholeRow();
            if (sortedMap != null) {
                if (this.lowerBound != null) {
                    sortedMap = sortedMap.tailMap(this.lowerBound);
                }
                if (this.upperBound != null) {
                    sortedMap = sortedMap.headMap(this.upperBound);
                }
                return sortedMap;
            }
            return null;
        }

        @Override
        void maintainEmptyInvariant() {
            if (this.wholeRow() != null && this.wholeRow.isEmpty()) {
                this.this$0.backingMap.remove(this.rowKey);
                this.wholeRow = null;
                this.backingRowMap = null;
            }
        }

        @Override
        public boolean containsKey(Object object) {
            return this.rangeContains(object) && super.containsKey(object);
        }

        @Override
        public V put(C c, V v) {
            Preconditions.checkArgument(this.rangeContains(Preconditions.checkNotNull(c)));
            return super.put(c, v);
        }

        Map computeBackingRowMap() {
            return this.computeBackingRowMap();
        }

        Map backingRowMap() {
            return this.backingRowMap();
        }

        @Override
        public Set keySet() {
            return this.keySet();
        }
    }

    private static class Factory<C, V>
    implements Supplier<TreeMap<C, V>>,
    Serializable {
        final Comparator<? super C> comparator;
        private static final long serialVersionUID = 0L;

        Factory(Comparator<? super C> comparator) {
            this.comparator = comparator;
        }

        @Override
        public TreeMap<C, V> get() {
            return new TreeMap(this.comparator);
        }

        @Override
        public Object get() {
            return this.get();
        }
    }
}

