/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Supplier;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.AbstractMapEntry;
import com.google.common.collect.AbstractTable;
import com.google.common.collect.CollectSpliterators;
import com.google.common.collect.Collections2;
import com.google.common.collect.ForwardingMapEntry;
import com.google.common.collect.GwtTransient;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import javax.annotation.Nullable;

@GwtCompatible
class StandardTable<R, C, V>
extends AbstractTable<R, C, V>
implements Serializable {
    @GwtTransient
    final Map<R, Map<C, V>> backingMap;
    @GwtTransient
    final Supplier<? extends Map<C, V>> factory;
    private transient Set<C> columnKeySet;
    private transient Map<R, Map<C, V>> rowMap;
    private transient ColumnMap columnMap;
    private static final long serialVersionUID = 0L;

    StandardTable(Map<R, Map<C, V>> map, Supplier<? extends Map<C, V>> supplier) {
        this.backingMap = map;
        this.factory = supplier;
    }

    @Override
    public boolean contains(@Nullable Object object, @Nullable Object object2) {
        return object != null && object2 != null && super.contains(object, object2);
    }

    @Override
    public boolean containsColumn(@Nullable Object object) {
        if (object == null) {
            return true;
        }
        for (Map<C, V> map : this.backingMap.values()) {
            if (!Maps.safeContainsKey(map, object)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean containsRow(@Nullable Object object) {
        return object != null && Maps.safeContainsKey(this.backingMap, object);
    }

    @Override
    public boolean containsValue(@Nullable Object object) {
        return object != null && super.containsValue(object);
    }

    @Override
    public V get(@Nullable Object object, @Nullable Object object2) {
        return object == null || object2 == null ? null : (V)super.get(object, object2);
    }

    @Override
    public boolean isEmpty() {
        return this.backingMap.isEmpty();
    }

    @Override
    public int size() {
        int n = 0;
        for (Map<C, V> map : this.backingMap.values()) {
            n += map.size();
        }
        return n;
    }

    @Override
    public void clear() {
        this.backingMap.clear();
    }

    private Map<C, V> getOrCreate(R r) {
        Map<C, V> map = this.backingMap.get(r);
        if (map == null) {
            map = this.factory.get();
            this.backingMap.put(r, map);
        }
        return map;
    }

    @Override
    @CanIgnoreReturnValue
    public V put(R r, C c, V v) {
        Preconditions.checkNotNull(r);
        Preconditions.checkNotNull(c);
        Preconditions.checkNotNull(v);
        return this.getOrCreate(r).put(c, v);
    }

    @Override
    @CanIgnoreReturnValue
    public V remove(@Nullable Object object, @Nullable Object object2) {
        if (object == null || object2 == null) {
            return null;
        }
        Map<C, V> map = Maps.safeGet(this.backingMap, object);
        if (map == null) {
            return null;
        }
        V v = map.remove(object2);
        if (map.isEmpty()) {
            this.backingMap.remove(object);
        }
        return v;
    }

    @CanIgnoreReturnValue
    private Map<R, V> removeColumn(Object object) {
        LinkedHashMap<R, V> linkedHashMap = new LinkedHashMap<R, V>();
        Iterator<Map.Entry<R, Map<C, V>>> iterator2 = this.backingMap.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<R, Map<C, V>> entry = iterator2.next();
            V v = entry.getValue().remove(object);
            if (v == null) continue;
            linkedHashMap.put(entry.getKey(), v);
            if (!entry.getValue().isEmpty()) continue;
            iterator2.remove();
        }
        return linkedHashMap;
    }

    private boolean containsMapping(Object object, Object object2, Object object3) {
        return object3 != null && object3.equals(this.get(object, object2));
    }

    private boolean removeMapping(Object object, Object object2, Object object3) {
        if (this.containsMapping(object, object2, object3)) {
            this.remove(object, object2);
            return false;
        }
        return true;
    }

    @Override
    public Set<Table.Cell<R, C, V>> cellSet() {
        return super.cellSet();
    }

    @Override
    Iterator<Table.Cell<R, C, V>> cellIterator() {
        return new CellIterator(this, null);
    }

    @Override
    Spliterator<Table.Cell<R, C, V>> cellSpliterator() {
        return CollectSpliterators.flatMap(this.backingMap.entrySet().spliterator(), StandardTable::lambda$cellSpliterator$1, 65, this.size());
    }

    @Override
    public Map<C, V> row(R r) {
        return new Row(this, r);
    }

    @Override
    public Map<R, V> column(C c) {
        return new Column(this, c);
    }

    @Override
    public Set<R> rowKeySet() {
        return this.rowMap().keySet();
    }

    @Override
    public Set<C> columnKeySet() {
        ColumnKeySet columnKeySet = this.columnKeySet;
        return columnKeySet == null ? (this.columnKeySet = new ColumnKeySet(this, null)) : columnKeySet;
    }

    Iterator<C> createColumnKeyIterator() {
        return new ColumnKeyIterator(this, null);
    }

    @Override
    public Collection<V> values() {
        return super.values();
    }

    @Override
    public Map<R, Map<C, V>> rowMap() {
        Map<R, Map<C, Map<C, V>>> map = this.rowMap;
        return map == null ? (this.rowMap = this.createRowMap()) : map;
    }

    Map<R, Map<C, V>> createRowMap() {
        return new RowMap(this);
    }

    @Override
    public Map<C, Map<R, V>> columnMap() {
        ColumnMap columnMap = this.columnMap;
        return columnMap == null ? (this.columnMap = new ColumnMap(this, null)) : columnMap;
    }

    private static Spliterator lambda$cellSpliterator$1(Map.Entry entry) {
        return CollectSpliterators.map(((Map)entry.getValue()).entrySet().spliterator(), arg_0 -> StandardTable.lambda$null$0(entry, arg_0));
    }

    private static Table.Cell lambda$null$0(Map.Entry entry, Map.Entry entry2) {
        return Tables.immutableCell(entry.getKey(), entry2.getKey(), entry2.getValue());
    }

    static boolean access$300(StandardTable standardTable, Object object, Object object2, Object object3) {
        return standardTable.containsMapping(object, object2, object3);
    }

    static boolean access$400(StandardTable standardTable, Object object, Object object2, Object object3) {
        return standardTable.removeMapping(object, object2, object3);
    }

    static Map access$900(StandardTable standardTable, Object object) {
        return standardTable.removeColumn(object);
    }

    private class ColumnMap
    extends Maps.ViewCachingAbstractMap<C, Map<R, V>> {
        final StandardTable this$0;

        private ColumnMap(StandardTable standardTable) {
            this.this$0 = standardTable;
        }

        @Override
        public Map<R, V> get(Object object) {
            return this.this$0.containsColumn(object) ? this.this$0.column(object) : null;
        }

        @Override
        public boolean containsKey(Object object) {
            return this.this$0.containsColumn(object);
        }

        @Override
        public Map<R, V> remove(Object object) {
            return this.this$0.containsColumn(object) ? StandardTable.access$900(this.this$0, object) : null;
        }

        @Override
        public Set<Map.Entry<C, Map<R, V>>> createEntrySet() {
            return new ColumnMapEntrySet(this);
        }

        @Override
        public Set<C> keySet() {
            return this.this$0.columnKeySet();
        }

        @Override
        Collection<Map<R, V>> createValues() {
            return new ColumnMapValues(this);
        }

        @Override
        public Object remove(Object object) {
            return this.remove(object);
        }

        @Override
        public Object get(Object object) {
            return this.get(object);
        }

        ColumnMap(StandardTable standardTable, 1 var2_2) {
            this(standardTable);
        }

        private class ColumnMapValues
        extends Maps.Values<C, Map<R, V>> {
            final ColumnMap this$1;

            ColumnMapValues(ColumnMap columnMap) {
                this.this$1 = columnMap;
                super(columnMap);
            }

            @Override
            public boolean remove(Object object) {
                for (Map.Entry entry : this.this$1.entrySet()) {
                    if (!((Map)entry.getValue()).equals(object)) continue;
                    StandardTable.access$900(this.this$1.this$0, entry.getKey());
                    return false;
                }
                return true;
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                boolean bl = false;
                for (Object c : Lists.newArrayList(this.this$1.this$0.columnKeySet().iterator())) {
                    if (!collection.contains(this.this$1.this$0.column(c))) continue;
                    StandardTable.access$900(this.this$1.this$0, c);
                    bl = true;
                }
                return bl;
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                boolean bl = false;
                for (Object c : Lists.newArrayList(this.this$1.this$0.columnKeySet().iterator())) {
                    if (collection.contains(this.this$1.this$0.column(c))) continue;
                    StandardTable.access$900(this.this$1.this$0, c);
                    bl = true;
                }
                return bl;
            }
        }

        class ColumnMapEntrySet
        extends TableSet<Map.Entry<C, Map<R, V>>> {
            final ColumnMap this$1;

            ColumnMapEntrySet(ColumnMap columnMap) {
                this.this$1 = columnMap;
                super(columnMap.this$0, null);
            }

            @Override
            public Iterator<Map.Entry<C, Map<R, V>>> iterator() {
                return Maps.asMapEntryIterator(this.this$1.this$0.columnKeySet(), new Function<C, Map<R, V>>(this){
                    final ColumnMapEntrySet this$2;
                    {
                        this.this$2 = columnMapEntrySet;
                    }

                    @Override
                    public Map<R, V> apply(C c) {
                        return this.this$2.this$1.this$0.column(c);
                    }

                    @Override
                    public Object apply(Object object) {
                        return this.apply(object);
                    }
                });
            }

            @Override
            public int size() {
                return this.this$1.this$0.columnKeySet().size();
            }

            @Override
            public boolean contains(Object object) {
                Map.Entry entry;
                if (object instanceof Map.Entry && this.this$1.this$0.containsColumn((entry = (Map.Entry)object).getKey())) {
                    Object k = entry.getKey();
                    return this.this$1.get(k).equals(entry.getValue());
                }
                return true;
            }

            @Override
            public boolean remove(Object object) {
                if (this.contains(object)) {
                    Map.Entry entry = (Map.Entry)object;
                    StandardTable.access$900(this.this$1.this$0, entry.getKey());
                    return false;
                }
                return true;
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                return Sets.removeAllImpl(this, collection.iterator());
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                boolean bl = false;
                for (Object c : Lists.newArrayList(this.this$1.this$0.columnKeySet().iterator())) {
                    if (collection.contains(Maps.immutableEntry(c, this.this$1.this$0.column(c)))) continue;
                    StandardTable.access$900(this.this$1.this$0, c);
                    bl = true;
                }
                return bl;
            }
        }
    }

    class RowMap
    extends Maps.ViewCachingAbstractMap<R, Map<C, V>> {
        final StandardTable this$0;

        RowMap(StandardTable standardTable) {
            this.this$0 = standardTable;
        }

        @Override
        public boolean containsKey(Object object) {
            return this.this$0.containsRow(object);
        }

        @Override
        public Map<C, V> get(Object object) {
            return this.this$0.containsRow(object) ? this.this$0.row(object) : null;
        }

        @Override
        public Map<C, V> remove(Object object) {
            return object == null ? null : this.this$0.backingMap.remove(object);
        }

        @Override
        protected Set<Map.Entry<R, Map<C, V>>> createEntrySet() {
            return new EntrySet(this);
        }

        @Override
        public Object remove(Object object) {
            return this.remove(object);
        }

        @Override
        public Object get(Object object) {
            return this.get(object);
        }

        class EntrySet
        extends TableSet<Map.Entry<R, Map<C, V>>> {
            final RowMap this$1;

            EntrySet(RowMap rowMap) {
                this.this$1 = rowMap;
                super(rowMap.this$0, null);
            }

            @Override
            public Iterator<Map.Entry<R, Map<C, V>>> iterator() {
                return Maps.asMapEntryIterator(this.this$1.this$0.backingMap.keySet(), new Function<R, Map<C, V>>(this){
                    final EntrySet this$2;
                    {
                        this.this$2 = entrySet;
                    }

                    @Override
                    public Map<C, V> apply(R r) {
                        return this.this$2.this$1.this$0.row(r);
                    }

                    @Override
                    public Object apply(Object object) {
                        return this.apply(object);
                    }
                });
            }

            @Override
            public int size() {
                return this.this$1.this$0.backingMap.size();
            }

            @Override
            public boolean contains(Object object) {
                if (object instanceof Map.Entry) {
                    Map.Entry entry = (Map.Entry)object;
                    return entry.getKey() != null && entry.getValue() instanceof Map && Collections2.safeContains(this.this$1.this$0.backingMap.entrySet(), entry);
                }
                return true;
            }

            @Override
            public boolean remove(Object object) {
                if (object instanceof Map.Entry) {
                    Map.Entry entry = (Map.Entry)object;
                    return entry.getKey() != null && entry.getValue() instanceof Map && this.this$1.this$0.backingMap.entrySet().remove(entry);
                }
                return true;
            }
        }
    }

    private class ColumnKeyIterator
    extends AbstractIterator<C> {
        final Map<C, V> seen;
        final Iterator<Map<C, V>> mapIterator;
        Iterator<Map.Entry<C, V>> entryIterator;
        final StandardTable this$0;

        private ColumnKeyIterator(StandardTable standardTable) {
            this.this$0 = standardTable;
            this.seen = this.this$0.factory.get();
            this.mapIterator = this.this$0.backingMap.values().iterator();
            this.entryIterator = Iterators.emptyIterator();
        }

        @Override
        protected C computeNext() {
            while (true) {
                if (this.entryIterator.hasNext()) {
                    Map.Entry entry = this.entryIterator.next();
                    if (this.seen.containsKey(entry.getKey())) continue;
                    this.seen.put(entry.getKey(), entry.getValue());
                    return entry.getKey();
                }
                if (!this.mapIterator.hasNext()) break;
                this.entryIterator = this.mapIterator.next().entrySet().iterator();
            }
            return this.endOfData();
        }

        ColumnKeyIterator(StandardTable standardTable, 1 var2_2) {
            this(standardTable);
        }
    }

    private class ColumnKeySet
    extends TableSet<C> {
        final StandardTable this$0;

        private ColumnKeySet(StandardTable standardTable) {
            this.this$0 = standardTable;
            super(standardTable, null);
        }

        @Override
        public Iterator<C> iterator() {
            return this.this$0.createColumnKeyIterator();
        }

        @Override
        public int size() {
            return Iterators.size(this.iterator());
        }

        @Override
        public boolean remove(Object object) {
            if (object == null) {
                return true;
            }
            boolean bl = false;
            Iterator iterator2 = this.this$0.backingMap.values().iterator();
            while (iterator2.hasNext()) {
                Map map = iterator2.next();
                if (!map.keySet().remove(object)) continue;
                bl = true;
                if (!map.isEmpty()) continue;
                iterator2.remove();
            }
            return bl;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            Preconditions.checkNotNull(collection);
            boolean bl = false;
            Iterator iterator2 = this.this$0.backingMap.values().iterator();
            while (iterator2.hasNext()) {
                Map map = iterator2.next();
                if (!Iterators.removeAll(map.keySet().iterator(), collection)) continue;
                bl = true;
                if (!map.isEmpty()) continue;
                iterator2.remove();
            }
            return bl;
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            Preconditions.checkNotNull(collection);
            boolean bl = false;
            Iterator iterator2 = this.this$0.backingMap.values().iterator();
            while (iterator2.hasNext()) {
                Map map = iterator2.next();
                if (!map.keySet().retainAll(collection)) continue;
                bl = true;
                if (!map.isEmpty()) continue;
                iterator2.remove();
            }
            return bl;
        }

        @Override
        public boolean contains(Object object) {
            return this.this$0.containsColumn(object);
        }

        ColumnKeySet(StandardTable standardTable, 1 var2_2) {
            this(standardTable);
        }
    }

    private class Column
    extends Maps.ViewCachingAbstractMap<R, V> {
        final C columnKey;
        final StandardTable this$0;

        Column(StandardTable standardTable, C c) {
            this.this$0 = standardTable;
            this.columnKey = Preconditions.checkNotNull(c);
        }

        @Override
        public V put(R r, V v) {
            return this.this$0.put(r, this.columnKey, v);
        }

        @Override
        public V get(Object object) {
            return this.this$0.get(object, this.columnKey);
        }

        @Override
        public boolean containsKey(Object object) {
            return this.this$0.contains(object, this.columnKey);
        }

        @Override
        public V remove(Object object) {
            return this.this$0.remove(object, this.columnKey);
        }

        @CanIgnoreReturnValue
        boolean removeFromColumnIf(Predicate<? super Map.Entry<R, V>> predicate) {
            boolean bl = false;
            Iterator iterator2 = this.this$0.backingMap.entrySet().iterator();
            while (iterator2.hasNext()) {
                Map.Entry entry = iterator2.next();
                Map map = entry.getValue();
                Object v = map.get(this.columnKey);
                if (v == null || !predicate.apply(Maps.immutableEntry(entry.getKey(), v))) continue;
                map.remove(this.columnKey);
                bl = true;
                if (!map.isEmpty()) continue;
                iterator2.remove();
            }
            return bl;
        }

        @Override
        Set<Map.Entry<R, V>> createEntrySet() {
            return new EntrySet(this, null);
        }

        @Override
        Set<R> createKeySet() {
            return new KeySet(this);
        }

        @Override
        Collection<V> createValues() {
            return new Values(this);
        }

        private class Values
        extends Maps.Values<R, V> {
            final Column this$1;

            Values(Column column) {
                this.this$1 = column;
                super(column);
            }

            @Override
            public boolean remove(Object object) {
                return object != null && this.this$1.removeFromColumnIf(Maps.valuePredicateOnEntries(Predicates.equalTo(object)));
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                return this.this$1.removeFromColumnIf(Maps.valuePredicateOnEntries(Predicates.in(collection)));
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                return this.this$1.removeFromColumnIf(Maps.valuePredicateOnEntries(Predicates.not(Predicates.in(collection))));
            }
        }

        private class KeySet
        extends Maps.KeySet<R, V> {
            final Column this$1;

            KeySet(Column column) {
                this.this$1 = column;
                super(column);
            }

            @Override
            public boolean contains(Object object) {
                return this.this$1.this$0.contains(object, this.this$1.columnKey);
            }

            @Override
            public boolean remove(Object object) {
                return this.this$1.this$0.remove(object, this.this$1.columnKey) != null;
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                return this.this$1.removeFromColumnIf(Maps.keyPredicateOnEntries(Predicates.not(Predicates.in(collection))));
            }
        }

        private class EntrySetIterator
        extends AbstractIterator<Map.Entry<R, V>> {
            final Iterator<Map.Entry<R, Map<C, V>>> iterator;
            final Column this$1;

            private EntrySetIterator(Column column) {
                this.this$1 = column;
                this.iterator = this.this$1.this$0.backingMap.entrySet().iterator();
            }

            @Override
            protected Map.Entry<R, V> computeNext() {
                while (this.iterator.hasNext()) {
                    Map.Entry entry = this.iterator.next();
                    if (!entry.getValue().containsKey(this.this$1.columnKey)) continue;
                    class EntryImpl
                    extends AbstractMapEntry<R, V> {
                        final Map.Entry val$entry;
                        final EntrySetIterator this$2;

                        EntryImpl() {
                            this.this$2 = entrySetIterator;
                            this.val$entry = entry;
                        }

                        @Override
                        public R getKey() {
                            return this.val$entry.getKey();
                        }

                        @Override
                        public V getValue() {
                            return ((Map)this.val$entry.getValue()).get(this.this$2.this$1.columnKey);
                        }

                        @Override
                        public V setValue(V v) {
                            return ((Map)this.val$entry.getValue()).put(this.this$2.this$1.columnKey, Preconditions.checkNotNull(v));
                        }
                    }
                    return new EntryImpl();
                }
                return (Map.Entry)this.endOfData();
            }

            @Override
            protected Object computeNext() {
                return this.computeNext();
            }

            EntrySetIterator(Column column, 1 var2_2) {
                this(column);
            }
        }

        private class EntrySet
        extends Sets.ImprovedAbstractSet<Map.Entry<R, V>> {
            final Column this$1;

            private EntrySet(Column column) {
                this.this$1 = column;
            }

            @Override
            public Iterator<Map.Entry<R, V>> iterator() {
                return new EntrySetIterator(this.this$1, null);
            }

            @Override
            public int size() {
                int n = 0;
                for (Map map : this.this$1.this$0.backingMap.values()) {
                    if (!map.containsKey(this.this$1.columnKey)) continue;
                    ++n;
                }
                return n;
            }

            @Override
            public boolean isEmpty() {
                return !this.this$1.this$0.containsColumn(this.this$1.columnKey);
            }

            @Override
            public void clear() {
                this.this$1.removeFromColumnIf(Predicates.alwaysTrue());
            }

            @Override
            public boolean contains(Object object) {
                if (object instanceof Map.Entry) {
                    Map.Entry entry = (Map.Entry)object;
                    return StandardTable.access$300(this.this$1.this$0, entry.getKey(), this.this$1.columnKey, entry.getValue());
                }
                return true;
            }

            @Override
            public boolean remove(Object object) {
                if (object instanceof Map.Entry) {
                    Map.Entry entry = (Map.Entry)object;
                    return StandardTable.access$400(this.this$1.this$0, entry.getKey(), this.this$1.columnKey, entry.getValue());
                }
                return true;
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                return this.this$1.removeFromColumnIf(Predicates.not(Predicates.in(collection)));
            }

            EntrySet(Column column, 1 var2_2) {
                this(column);
            }
        }
    }

    class Row
    extends Maps.IteratorBasedAbstractMap<C, V> {
        final R rowKey;
        Map<C, V> backingRowMap;
        final StandardTable this$0;

        Row(StandardTable standardTable, R r) {
            this.this$0 = standardTable;
            this.rowKey = Preconditions.checkNotNull(r);
        }

        Map<C, V> backingRowMap() {
            Map map;
            if (this.backingRowMap == null || this.backingRowMap.isEmpty() && this.this$0.backingMap.containsKey(this.rowKey)) {
                this.backingRowMap = this.computeBackingRowMap();
                map = this.backingRowMap;
            } else {
                map = this.backingRowMap;
            }
            return map;
        }

        Map<C, V> computeBackingRowMap() {
            return this.this$0.backingMap.get(this.rowKey);
        }

        void maintainEmptyInvariant() {
            if (this.backingRowMap() != null && this.backingRowMap.isEmpty()) {
                this.this$0.backingMap.remove(this.rowKey);
                this.backingRowMap = null;
            }
        }

        @Override
        public boolean containsKey(Object object) {
            Map map = this.backingRowMap();
            return object != null && map != null && Maps.safeContainsKey(map, object);
        }

        @Override
        public V get(Object object) {
            Map map = this.backingRowMap();
            return object != null && map != null ? (Object)Maps.safeGet(map, object) : null;
        }

        @Override
        public V put(C c, V v) {
            Preconditions.checkNotNull(c);
            Preconditions.checkNotNull(v);
            if (this.backingRowMap != null && !this.backingRowMap.isEmpty()) {
                return this.backingRowMap.put(c, v);
            }
            return this.this$0.put(this.rowKey, c, v);
        }

        @Override
        public V remove(Object object) {
            Map map = this.backingRowMap();
            if (map == null) {
                return null;
            }
            Object v = Maps.safeRemove(map, object);
            this.maintainEmptyInvariant();
            return v;
        }

        @Override
        public void clear() {
            Map map = this.backingRowMap();
            if (map != null) {
                map.clear();
            }
            this.maintainEmptyInvariant();
        }

        @Override
        public int size() {
            Map map = this.backingRowMap();
            return map == null ? 0 : map.size();
        }

        @Override
        Iterator<Map.Entry<C, V>> entryIterator() {
            Map map = this.backingRowMap();
            if (map == null) {
                return Iterators.emptyModifiableIterator();
            }
            Iterator iterator2 = map.entrySet().iterator();
            return new Iterator<Map.Entry<C, V>>(this, iterator2){
                final Iterator val$iterator;
                final Row this$1;
                {
                    this.this$1 = row;
                    this.val$iterator = iterator2;
                }

                @Override
                public boolean hasNext() {
                    return this.val$iterator.hasNext();
                }

                @Override
                public Map.Entry<C, V> next() {
                    return this.this$1.wrapEntry((Map.Entry)this.val$iterator.next());
                }

                @Override
                public void remove() {
                    this.val$iterator.remove();
                    this.this$1.maintainEmptyInvariant();
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        Spliterator<Map.Entry<C, V>> entrySpliterator() {
            Map map = this.backingRowMap();
            if (map == null) {
                return Spliterators.emptySpliterator();
            }
            return CollectSpliterators.map(map.entrySet().spliterator(), this::wrapEntry);
        }

        Map.Entry<C, V> wrapEntry(Map.Entry<C, V> entry) {
            return new ForwardingMapEntry<C, V>(this, entry){
                final Map.Entry val$entry;
                final Row this$1;
                {
                    this.this$1 = row;
                    this.val$entry = entry;
                }

                @Override
                protected Map.Entry<C, V> delegate() {
                    return this.val$entry;
                }

                @Override
                public V setValue(V v) {
                    return super.setValue(Preconditions.checkNotNull(v));
                }

                @Override
                public boolean equals(Object object) {
                    return this.standardEquals(object);
                }

                @Override
                protected Object delegate() {
                    return this.delegate();
                }
            };
        }
    }

    private class CellIterator
    implements Iterator<Table.Cell<R, C, V>> {
        final Iterator<Map.Entry<R, Map<C, V>>> rowIterator;
        Map.Entry<R, Map<C, V>> rowEntry;
        Iterator<Map.Entry<C, V>> columnIterator;
        final StandardTable this$0;

        private CellIterator(StandardTable standardTable) {
            this.this$0 = standardTable;
            this.rowIterator = this.this$0.backingMap.entrySet().iterator();
            this.columnIterator = Iterators.emptyModifiableIterator();
        }

        @Override
        public boolean hasNext() {
            return this.rowIterator.hasNext() || this.columnIterator.hasNext();
        }

        @Override
        public Table.Cell<R, C, V> next() {
            if (!this.columnIterator.hasNext()) {
                this.rowEntry = this.rowIterator.next();
                this.columnIterator = this.rowEntry.getValue().entrySet().iterator();
            }
            Map.Entry entry = this.columnIterator.next();
            return Tables.immutableCell(this.rowEntry.getKey(), entry.getKey(), entry.getValue());
        }

        @Override
        public void remove() {
            this.columnIterator.remove();
            if (this.rowEntry.getValue().isEmpty()) {
                this.rowIterator.remove();
            }
        }

        @Override
        public Object next() {
            return this.next();
        }

        CellIterator(StandardTable standardTable, 1 var2_2) {
            this(standardTable);
        }
    }

    private abstract class TableSet<T>
    extends Sets.ImprovedAbstractSet<T> {
        final StandardTable this$0;

        private TableSet(StandardTable standardTable) {
            this.this$0 = standardTable;
        }

        @Override
        public boolean isEmpty() {
            return this.this$0.backingMap.isEmpty();
        }

        @Override
        public void clear() {
            this.this$0.backingMap.clear();
        }

        TableSet(StandardTable standardTable, 1 var2_2) {
            this(standardTable);
        }
    }
}

