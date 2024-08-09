/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2ReferenceMap;
import it.unimi.dsi.fastutil.bytes.AbstractByteSortedSet;
import it.unimi.dsi.fastutil.bytes.Byte2ReferenceMap;
import it.unimi.dsi.fastutil.bytes.Byte2ReferenceSortedMap;
import it.unimi.dsi.fastutil.bytes.Byte2ReferenceSortedMaps;
import it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.bytes.ByteSortedSet;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractByte2ReferenceSortedMap<V>
extends AbstractByte2ReferenceMap<V>
implements Byte2ReferenceSortedMap<V> {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractByte2ReferenceSortedMap() {
    }

    @Override
    public ByteSortedSet keySet() {
        return new KeySet(this);
    }

    @Override
    public ReferenceCollection<V> values() {
        return new ValuesCollection(this);
    }

    @Override
    public ByteSet keySet() {
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

    protected static class ValuesIterator<V>
    implements ObjectIterator<V> {
        protected final ObjectBidirectionalIterator<Byte2ReferenceMap.Entry<V>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Byte2ReferenceMap.Entry<V>> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public V next() {
            return ((Byte2ReferenceMap.Entry)this.i.next()).getValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    protected class ValuesCollection
    extends AbstractReferenceCollection<V> {
        final AbstractByte2ReferenceSortedMap this$0;

        protected ValuesCollection(AbstractByte2ReferenceSortedMap abstractByte2ReferenceSortedMap) {
            this.this$0 = abstractByte2ReferenceSortedMap;
        }

        @Override
        public ObjectIterator<V> iterator() {
            return new ValuesIterator(Byte2ReferenceSortedMaps.fastIterator(this.this$0));
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

    protected static class KeySetIterator<V>
    implements ByteBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Byte2ReferenceMap.Entry<V>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Byte2ReferenceMap.Entry<V>> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public byte nextByte() {
            return ((Byte2ReferenceMap.Entry)this.i.next()).getByteKey();
        }

        @Override
        public byte previousByte() {
            return ((Byte2ReferenceMap.Entry)this.i.previous()).getByteKey();
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
    extends AbstractByteSortedSet {
        final AbstractByte2ReferenceSortedMap this$0;

        protected KeySet(AbstractByte2ReferenceSortedMap abstractByte2ReferenceSortedMap) {
            this.this$0 = abstractByte2ReferenceSortedMap;
        }

        @Override
        public boolean contains(byte by) {
            return this.this$0.containsKey(by);
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
        public ByteComparator comparator() {
            return this.this$0.comparator();
        }

        @Override
        public byte firstByte() {
            return this.this$0.firstByteKey();
        }

        @Override
        public byte lastByte() {
            return this.this$0.lastByteKey();
        }

        @Override
        public ByteSortedSet headSet(byte by) {
            return this.this$0.headMap(by).keySet();
        }

        @Override
        public ByteSortedSet tailSet(byte by) {
            return this.this$0.tailMap(by).keySet();
        }

        @Override
        public ByteSortedSet subSet(byte by, byte by2) {
            return this.this$0.subMap(by, by2).keySet();
        }

        @Override
        public ByteBidirectionalIterator iterator(byte by) {
            return new KeySetIterator(this.this$0.byte2ReferenceEntrySet().iterator(new AbstractByte2ReferenceMap.BasicEntry<Object>(by, null)));
        }

        @Override
        public ByteBidirectionalIterator iterator() {
            return new KeySetIterator(Byte2ReferenceSortedMaps.fastIterator(this.this$0));
        }

        @Override
        public ByteIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }
}

