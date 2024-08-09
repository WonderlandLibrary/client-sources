/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.Maps;
import com.google.common.collect.RowSortedTable;
import com.google.common.collect.StandardTable;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

@GwtCompatible
class StandardRowSortedTable<R, C, V>
extends StandardTable<R, C, V>
implements RowSortedTable<R, C, V> {
    private static final long serialVersionUID = 0L;

    StandardRowSortedTable(SortedMap<R, Map<C, V>> sortedMap, Supplier<? extends Map<C, V>> supplier) {
        super(sortedMap, supplier);
    }

    private SortedMap<R, Map<C, V>> sortedBackingMap() {
        return (SortedMap)this.backingMap;
    }

    @Override
    public SortedSet<R> rowKeySet() {
        return (SortedSet)this.rowMap().keySet();
    }

    @Override
    public SortedMap<R, Map<C, V>> rowMap() {
        return (SortedMap)super.rowMap();
    }

    @Override
    SortedMap<R, Map<C, V>> createRowMap() {
        return new RowSortedMap(this, null);
    }

    @Override
    Map createRowMap() {
        return this.createRowMap();
    }

    @Override
    public Map rowMap() {
        return this.rowMap();
    }

    @Override
    public Set rowKeySet() {
        return this.rowKeySet();
    }

    static SortedMap access$100(StandardRowSortedTable standardRowSortedTable) {
        return standardRowSortedTable.sortedBackingMap();
    }

    private class RowSortedMap
    extends StandardTable.RowMap
    implements SortedMap<R, Map<C, V>> {
        final StandardRowSortedTable this$0;

        private RowSortedMap(StandardRowSortedTable standardRowSortedTable) {
            this.this$0 = standardRowSortedTable;
            super(standardRowSortedTable);
        }

        @Override
        public SortedSet<R> keySet() {
            return (SortedSet)super.keySet();
        }

        @Override
        SortedSet<R> createKeySet() {
            return new Maps.SortedKeySet(this);
        }

        @Override
        public Comparator<? super R> comparator() {
            return StandardRowSortedTable.access$100(this.this$0).comparator();
        }

        @Override
        public R firstKey() {
            return StandardRowSortedTable.access$100(this.this$0).firstKey();
        }

        @Override
        public R lastKey() {
            return StandardRowSortedTable.access$100(this.this$0).lastKey();
        }

        @Override
        public SortedMap<R, Map<C, V>> headMap(R r) {
            Preconditions.checkNotNull(r);
            return new StandardRowSortedTable(StandardRowSortedTable.access$100(this.this$0).headMap(r), this.this$0.factory).rowMap();
        }

        @Override
        public SortedMap<R, Map<C, V>> subMap(R r, R r2) {
            Preconditions.checkNotNull(r);
            Preconditions.checkNotNull(r2);
            return new StandardRowSortedTable(StandardRowSortedTable.access$100(this.this$0).subMap(r, r2), this.this$0.factory).rowMap();
        }

        @Override
        public SortedMap<R, Map<C, V>> tailMap(R r) {
            Preconditions.checkNotNull(r);
            return new StandardRowSortedTable(StandardRowSortedTable.access$100(this.this$0).tailMap(r), this.this$0.factory).rowMap();
        }

        @Override
        Set createKeySet() {
            return this.createKeySet();
        }

        @Override
        public Set keySet() {
            return this.keySet();
        }

        RowSortedMap(StandardRowSortedTable standardRowSortedTable, 1 var2_2) {
            this(standardRowSortedTable);
        }
    }
}

