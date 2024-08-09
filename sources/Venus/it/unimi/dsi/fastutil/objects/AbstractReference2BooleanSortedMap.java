/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.objects.AbstractReference2BooleanMap;
import it.unimi.dsi.fastutil.objects.AbstractReferenceSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Reference2BooleanMap;
import it.unimi.dsi.fastutil.objects.Reference2BooleanSortedMap;
import it.unimi.dsi.fastutil.objects.Reference2BooleanSortedMaps;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractReference2BooleanSortedMap<K>
extends AbstractReference2BooleanMap<K>
implements Reference2BooleanSortedMap<K> {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractReference2BooleanSortedMap() {
    }

    @Override
    public ReferenceSortedSet<K> keySet() {
        return new KeySet(this);
    }

    @Override
    public BooleanCollection values() {
        return new ValuesCollection(this);
    }

    @Override
    public ReferenceSet keySet() {
        return this.keySet();
    }

    @Override
    public Collection values() {
        return this.values();
    }

    @Override
    public Set keySet() {
        return this.keySet();
    }

    protected static class ValuesIterator<K>
    implements BooleanIterator {
        protected final ObjectBidirectionalIterator<Reference2BooleanMap.Entry<K>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Reference2BooleanMap.Entry<K>> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public boolean nextBoolean() {
            return ((Reference2BooleanMap.Entry)this.i.next()).getBooleanValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    protected class ValuesCollection
    extends AbstractBooleanCollection {
        final AbstractReference2BooleanSortedMap this$0;

        protected ValuesCollection(AbstractReference2BooleanSortedMap abstractReference2BooleanSortedMap) {
            this.this$0 = abstractReference2BooleanSortedMap;
        }

        @Override
        public BooleanIterator iterator() {
            return new ValuesIterator(Reference2BooleanSortedMaps.fastIterator(this.this$0));
        }

        @Override
        public boolean contains(boolean bl) {
            return this.this$0.containsValue(bl);
        }

        @Override
        public int size() {
            return this.this$0.size();
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }

    protected static class KeySetIterator<K>
    implements ObjectBidirectionalIterator<K> {
        protected final ObjectBidirectionalIterator<Reference2BooleanMap.Entry<K>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Reference2BooleanMap.Entry<K>> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public K next() {
            return ((Reference2BooleanMap.Entry)this.i.next()).getKey();
        }

        @Override
        public K previous() {
            return ((Reference2BooleanMap.Entry)this.i.previous()).getKey();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }

        @Override
        public boolean hasPrevious() {
            return this.i.hasPrevious();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    protected class KeySet
    extends AbstractReferenceSortedSet<K> {
        final AbstractReference2BooleanSortedMap this$0;

        protected KeySet(AbstractReference2BooleanSortedMap abstractReference2BooleanSortedMap) {
            this.this$0 = abstractReference2BooleanSortedMap;
        }

        @Override
        public boolean contains(Object object) {
            return this.this$0.containsKey(object);
        }

        @Override
        public int size() {
            return this.this$0.size();
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.this$0.comparator();
        }

        @Override
        public K first() {
            return this.this$0.firstKey();
        }

        @Override
        public K last() {
            return this.this$0.lastKey();
        }

        @Override
        public ReferenceSortedSet<K> headSet(K k) {
            return this.this$0.headMap(k).keySet();
        }

        @Override
        public ReferenceSortedSet<K> tailSet(K k) {
            return this.this$0.tailMap(k).keySet();
        }

        @Override
        public ReferenceSortedSet<K> subSet(K k, K k2) {
            return this.this$0.subMap(k, k2).keySet();
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator(K k) {
            return new KeySetIterator(this.this$0.reference2BooleanEntrySet().iterator(new AbstractReference2BooleanMap.BasicEntry(k, false)));
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator() {
            return new KeySetIterator(Reference2BooleanSortedMaps.fastIterator(this.this$0));
        }

        @Override
        public ObjectIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        @Override
        public SortedSet tailSet(Object object) {
            return this.tailSet(object);
        }

        @Override
        public SortedSet headSet(Object object) {
            return this.headSet(object);
        }

        @Override
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet(object, object2);
        }
    }
}

