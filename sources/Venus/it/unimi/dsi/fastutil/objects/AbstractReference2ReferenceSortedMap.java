/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractReference2ReferenceMap;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.AbstractReferenceSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceMap;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceSortedMap;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceSortedMaps;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
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
public abstract class AbstractReference2ReferenceSortedMap<K, V>
extends AbstractReference2ReferenceMap<K, V>
implements Reference2ReferenceSortedMap<K, V> {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractReference2ReferenceSortedMap() {
    }

    @Override
    public ReferenceSortedSet<K> keySet() {
        return new KeySet(this);
    }

    @Override
    public ReferenceCollection<V> values() {
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

    protected static class ValuesIterator<K, V>
    implements ObjectIterator<V> {
        protected final ObjectBidirectionalIterator<Reference2ReferenceMap.Entry<K, V>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Reference2ReferenceMap.Entry<K, V>> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public V next() {
            return ((Reference2ReferenceMap.Entry)this.i.next()).getValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    protected class ValuesCollection
    extends AbstractReferenceCollection<V> {
        final AbstractReference2ReferenceSortedMap this$0;

        protected ValuesCollection(AbstractReference2ReferenceSortedMap abstractReference2ReferenceSortedMap) {
            this.this$0 = abstractReference2ReferenceSortedMap;
        }

        @Override
        public ObjectIterator<V> iterator() {
            return new ValuesIterator(Reference2ReferenceSortedMaps.fastIterator(this.this$0));
        }

        @Override
        public boolean contains(Object object) {
            return this.this$0.containsValue(object);
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

    protected static class KeySetIterator<K, V>
    implements ObjectBidirectionalIterator<K> {
        protected final ObjectBidirectionalIterator<Reference2ReferenceMap.Entry<K, V>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Reference2ReferenceMap.Entry<K, V>> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public K next() {
            return ((Reference2ReferenceMap.Entry)this.i.next()).getKey();
        }

        @Override
        public K previous() {
            return ((Reference2ReferenceMap.Entry)this.i.previous()).getKey();
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
        final AbstractReference2ReferenceSortedMap this$0;

        protected KeySet(AbstractReference2ReferenceSortedMap abstractReference2ReferenceSortedMap) {
            this.this$0 = abstractReference2ReferenceSortedMap;
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
            return new KeySetIterator(this.this$0.reference2ReferenceEntrySet().iterator(new AbstractReference2ReferenceMap.BasicEntry(k, null)));
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator() {
            return new KeySetIterator(Reference2ReferenceSortedMaps.fastIterator(this.this$0));
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

