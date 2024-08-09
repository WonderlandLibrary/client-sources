/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.objects.AbstractObject2IntMap;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntSortedMap;
import it.unimi.dsi.fastutil.objects.Object2IntSortedMaps;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractObject2IntSortedMap<K>
extends AbstractObject2IntMap<K>
implements Object2IntSortedMap<K> {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractObject2IntSortedMap() {
    }

    @Override
    public ObjectSortedSet<K> keySet() {
        return new KeySet(this);
    }

    @Override
    public IntCollection values() {
        return new ValuesCollection(this);
    }

    @Override
    public ObjectSet keySet() {
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
    implements IntIterator {
        protected final ObjectBidirectionalIterator<Object2IntMap.Entry<K>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Object2IntMap.Entry<K>> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public int nextInt() {
            return ((Object2IntMap.Entry)this.i.next()).getIntValue();
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
    extends AbstractIntCollection {
        final AbstractObject2IntSortedMap this$0;

        protected ValuesCollection(AbstractObject2IntSortedMap abstractObject2IntSortedMap) {
            this.this$0 = abstractObject2IntSortedMap;
        }

        @Override
        public IntIterator iterator() {
            return new ValuesIterator(Object2IntSortedMaps.fastIterator(this.this$0));
        }

        @Override
        public boolean contains(int n) {
            return this.this$0.containsValue(n);
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
        protected final ObjectBidirectionalIterator<Object2IntMap.Entry<K>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Object2IntMap.Entry<K>> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public K next() {
            return ((Object2IntMap.Entry)this.i.next()).getKey();
        }

        @Override
        public K previous() {
            return ((Object2IntMap.Entry)this.i.previous()).getKey();
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
    extends AbstractObjectSortedSet<K> {
        final AbstractObject2IntSortedMap this$0;

        protected KeySet(AbstractObject2IntSortedMap abstractObject2IntSortedMap) {
            this.this$0 = abstractObject2IntSortedMap;
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
        public ObjectSortedSet<K> headSet(K k) {
            return this.this$0.headMap(k).keySet();
        }

        @Override
        public ObjectSortedSet<K> tailSet(K k) {
            return this.this$0.tailMap(k).keySet();
        }

        @Override
        public ObjectSortedSet<K> subSet(K k, K k2) {
            return this.this$0.subMap(k, k2).keySet();
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator(K k) {
            return new KeySetIterator(this.this$0.object2IntEntrySet().iterator(new AbstractObject2IntMap.BasicEntry(k, 0)));
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator() {
            return new KeySetIterator(Object2IntSortedMaps.fastIterator(this.this$0));
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

