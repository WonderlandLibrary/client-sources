/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Equivalence;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMapEntry;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.ForwardingConcurrentMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.MapMaker;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.Weak;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

@GwtIncompatible
class MapMakerInternalMap<K, V, E extends InternalEntry<K, V, E>, S extends Segment<K, V, E, S>>
extends AbstractMap<K, V>
implements ConcurrentMap<K, V>,
Serializable {
    static final int MAXIMUM_CAPACITY = 0x40000000;
    static final int MAX_SEGMENTS = 65536;
    static final int CONTAINS_VALUE_RETRIES = 3;
    static final int DRAIN_THRESHOLD = 63;
    static final int DRAIN_MAX = 16;
    static final long CLEANUP_EXECUTOR_DELAY_SECS = 60L;
    final transient int segmentMask;
    final transient int segmentShift;
    final transient Segment<K, V, E, S>[] segments;
    final int concurrencyLevel;
    final Equivalence<Object> keyEquivalence;
    final transient InternalEntryHelper<K, V, E, S> entryHelper;
    static final WeakValueReference<Object, Object, DummyInternalEntry> UNSET_WEAK_VALUE_REFERENCE = new WeakValueReference<Object, Object, DummyInternalEntry>(){

        @Override
        public DummyInternalEntry getEntry() {
            return null;
        }

        @Override
        public void clear() {
        }

        @Override
        public Object get() {
            return null;
        }

        @Override
        public WeakValueReference<Object, Object, DummyInternalEntry> copyFor(ReferenceQueue<Object> referenceQueue, DummyInternalEntry dummyInternalEntry) {
            return this;
        }

        @Override
        public WeakValueReference copyFor(ReferenceQueue referenceQueue, InternalEntry internalEntry) {
            return this.copyFor((ReferenceQueue<Object>)referenceQueue, (DummyInternalEntry)internalEntry);
        }

        @Override
        public InternalEntry getEntry() {
            return this.getEntry();
        }
    };
    transient Set<K> keySet;
    transient Collection<V> values;
    transient Set<Map.Entry<K, V>> entrySet;
    private static final long serialVersionUID = 5L;

    private MapMakerInternalMap(MapMaker mapMaker, InternalEntryHelper<K, V, E, S> internalEntryHelper) {
        int n;
        int n2;
        this.concurrencyLevel = Math.min(mapMaker.getConcurrencyLevel(), 65536);
        this.keyEquivalence = mapMaker.getKeyEquivalence();
        this.entryHelper = internalEntryHelper;
        int n3 = Math.min(mapMaker.getInitialCapacity(), 0x40000000);
        int n4 = 0;
        for (n2 = 1; n2 < this.concurrencyLevel; n2 <<= 1) {
            ++n4;
        }
        this.segmentShift = 32 - n4;
        this.segmentMask = n2 - 1;
        this.segments = this.newSegmentArray(n2);
        int n5 = n3 / n2;
        if (n5 * n2 < n3) {
            ++n5;
        }
        for (n = 1; n < n5; n <<= 1) {
        }
        for (int i = 0; i < this.segments.length; ++i) {
            this.segments[i] = this.createSegment(n, -1);
        }
    }

    static <K, V> MapMakerInternalMap<K, V, ? extends InternalEntry<K, V, ?>, ?> create(MapMaker mapMaker) {
        if (mapMaker.getKeyStrength() == Strength.STRONG && mapMaker.getValueStrength() == Strength.STRONG) {
            return new MapMakerInternalMap(mapMaker, StrongKeyStrongValueEntry.Helper.instance());
        }
        if (mapMaker.getKeyStrength() == Strength.STRONG && mapMaker.getValueStrength() == Strength.WEAK) {
            return new MapMakerInternalMap(mapMaker, StrongKeyWeakValueEntry.Helper.instance());
        }
        if (mapMaker.getKeyStrength() == Strength.WEAK && mapMaker.getValueStrength() == Strength.STRONG) {
            return new MapMakerInternalMap(mapMaker, WeakKeyStrongValueEntry.Helper.instance());
        }
        if (mapMaker.getKeyStrength() == Strength.WEAK && mapMaker.getValueStrength() == Strength.WEAK) {
            return new MapMakerInternalMap(mapMaker, WeakKeyWeakValueEntry.Helper.instance());
        }
        throw new AssertionError();
    }

    static <K, V, E extends InternalEntry<K, V, E>> WeakValueReference<K, V, E> unsetWeakValueReference() {
        return UNSET_WEAK_VALUE_REFERENCE;
    }

    static int rehash(int n) {
        n += n << 15 ^ 0xFFFFCD7D;
        n ^= n >>> 10;
        n += n << 3;
        n ^= n >>> 6;
        n += (n << 2) + (n << 14);
        return n ^ n >>> 16;
    }

    @VisibleForTesting
    E copyEntry(E e, E e2) {
        int n = e.getHash();
        return this.segmentFor(n).copyEntry(e, e2);
    }

    int hash(Object object) {
        int n = this.keyEquivalence.hash(object);
        return MapMakerInternalMap.rehash(n);
    }

    void reclaimValue(WeakValueReference<K, V, E> weakValueReference) {
        E e = weakValueReference.getEntry();
        int n = e.getHash();
        this.segmentFor(n).reclaimValue(e.getKey(), n, weakValueReference);
    }

    void reclaimKey(E e) {
        int n = e.getHash();
        this.segmentFor(n).reclaimKey(e, n);
    }

    @VisibleForTesting
    boolean isLiveForTesting(InternalEntry<K, V, ?> internalEntry) {
        return this.segmentFor(internalEntry.getHash()).getLiveValueForTesting(internalEntry) != null;
    }

    Segment<K, V, E, S> segmentFor(int n) {
        return this.segments[n >>> this.segmentShift & this.segmentMask];
    }

    Segment<K, V, E, S> createSegment(int n, int n2) {
        return this.entryHelper.newSegment(this, n, n2);
    }

    V getLiveValue(E e) {
        if (e.getKey() == null) {
            return null;
        }
        Object v = e.getValue();
        if (v == null) {
            return null;
        }
        return v;
    }

    final Segment<K, V, E, S>[] newSegmentArray(int n) {
        return new Segment[n];
    }

    @VisibleForTesting
    Strength keyStrength() {
        return this.entryHelper.keyStrength();
    }

    @VisibleForTesting
    Strength valueStrength() {
        return this.entryHelper.valueStrength();
    }

    @VisibleForTesting
    Equivalence<Object> valueEquivalence() {
        return this.entryHelper.valueStrength().defaultEquivalence();
    }

    @Override
    public boolean isEmpty() {
        int n;
        long l = 0L;
        Segment<K, V, E, S>[] segmentArray = this.segments;
        for (n = 0; n < segmentArray.length; ++n) {
            if (segmentArray[n].count != 0) {
                return true;
            }
            l += (long)segmentArray[n].modCount;
        }
        if (l != 0L) {
            for (n = 0; n < segmentArray.length; ++n) {
                if (segmentArray[n].count != 0) {
                    return true;
                }
                l -= (long)segmentArray[n].modCount;
            }
            if (l != 0L) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        Segment<K, V, E, S>[] segmentArray = this.segments;
        long l = 0L;
        for (int i = 0; i < segmentArray.length; ++i) {
            l += (long)segmentArray[i].count;
        }
        return Ints.saturatedCast(l);
    }

    @Override
    public V get(@Nullable Object object) {
        if (object == null) {
            return null;
        }
        int n = this.hash(object);
        return this.segmentFor(n).get(object, n);
    }

    E getEntry(@Nullable Object object) {
        if (object == null) {
            return null;
        }
        int n = this.hash(object);
        return this.segmentFor(n).getEntry(object, n);
    }

    @Override
    public boolean containsKey(@Nullable Object object) {
        if (object == null) {
            return true;
        }
        int n = this.hash(object);
        return this.segmentFor(n).containsKey(object, n);
    }

    @Override
    public boolean containsValue(@Nullable Object object) {
        if (object == null) {
            return true;
        }
        Segment<K, V, E, S>[] segmentArray = this.segments;
        long l = -1L;
        for (int i = 0; i < 3; ++i) {
            long l2 = 0L;
            for (Segment<K, V, InternalEntry, S> segment : segmentArray) {
                int n = segment.count;
                AtomicReferenceArray atomicReferenceArray = segment.table;
                for (int j = 0; j < atomicReferenceArray.length(); ++j) {
                    for (InternalEntry internalEntry = (InternalEntry)atomicReferenceArray.get(j); internalEntry != null; internalEntry = internalEntry.getNext()) {
                        V v = segment.getLiveValue(internalEntry);
                        if (v == null || !this.valueEquivalence().equivalent(object, v)) continue;
                        return false;
                    }
                }
                l2 += (long)segment.modCount;
            }
            if (l2 == l) break;
            l = l2;
        }
        return true;
    }

    @Override
    @CanIgnoreReturnValue
    public V put(K k, V v) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(v);
        int n = this.hash(k);
        return this.segmentFor(n).put(k, n, v, true);
    }

    @Override
    @CanIgnoreReturnValue
    public V putIfAbsent(K k, V v) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(v);
        int n = this.hash(k);
        return this.segmentFor(n).put(k, n, v, false);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    @CanIgnoreReturnValue
    public V remove(@Nullable Object object) {
        if (object == null) {
            return null;
        }
        int n = this.hash(object);
        return this.segmentFor(n).remove(object, n);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean remove(@Nullable Object object, @Nullable Object object2) {
        if (object == null || object2 == null) {
            return true;
        }
        int n = this.hash(object);
        return this.segmentFor(n).remove(object, n, object2);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean replace(K k, @Nullable V v, V v2) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(v2);
        if (v == null) {
            return true;
        }
        int n = this.hash(k);
        return this.segmentFor(n).replace(k, n, v, v2);
    }

    @Override
    @CanIgnoreReturnValue
    public V replace(K k, V v) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(v);
        int n = this.hash(k);
        return this.segmentFor(n).replace(k, n, v);
    }

    @Override
    public void clear() {
        for (Segment<K, V, E, S> segment : this.segments) {
            segment.clear();
        }
    }

    @Override
    public Set<K> keySet() {
        KeySet keySet = this.keySet;
        return keySet != null ? keySet : (this.keySet = new KeySet(this));
    }

    @Override
    public Collection<V> values() {
        Values values2 = this.values;
        return values2 != null ? values2 : (this.values = new Values(this));
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        EntrySet entrySet = this.entrySet;
        return entrySet != null ? entrySet : (this.entrySet = new EntrySet(this));
    }

    private static <E> ArrayList<E> toArrayList(Collection<E> collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        Iterators.addAll(arrayList, collection.iterator());
        return arrayList;
    }

    Object writeReplace() {
        return new SerializationProxy(this.entryHelper.keyStrength(), this.entryHelper.valueStrength(), this.keyEquivalence, this.entryHelper.valueStrength().defaultEquivalence(), this.concurrencyLevel, this);
    }

    static ArrayList access$800(Collection collection) {
        return MapMakerInternalMap.toArrayList(collection);
    }

    private static final class SerializationProxy<K, V>
    extends AbstractSerializationProxy<K, V> {
        private static final long serialVersionUID = 3L;

        SerializationProxy(Strength strength, Strength strength2, Equivalence<Object> equivalence, Equivalence<Object> equivalence2, int n, ConcurrentMap<K, V> concurrentMap) {
            super(strength, strength2, equivalence, equivalence2, n, concurrentMap);
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            this.writeMapTo(objectOutputStream);
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            MapMaker mapMaker = this.readMapMaker(objectInputStream);
            this.delegate = mapMaker.makeMap();
            this.readEntries(objectInputStream);
        }

        private Object readResolve() {
            return this.delegate;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static abstract class AbstractSerializationProxy<K, V>
    extends ForwardingConcurrentMap<K, V>
    implements Serializable {
        private static final long serialVersionUID = 3L;
        final Strength keyStrength;
        final Strength valueStrength;
        final Equivalence<Object> keyEquivalence;
        final Equivalence<Object> valueEquivalence;
        final int concurrencyLevel;
        transient ConcurrentMap<K, V> delegate;

        AbstractSerializationProxy(Strength strength, Strength strength2, Equivalence<Object> equivalence, Equivalence<Object> equivalence2, int n, ConcurrentMap<K, V> concurrentMap) {
            this.keyStrength = strength;
            this.valueStrength = strength2;
            this.keyEquivalence = equivalence;
            this.valueEquivalence = equivalence2;
            this.concurrencyLevel = n;
            this.delegate = concurrentMap;
        }

        @Override
        protected ConcurrentMap<K, V> delegate() {
            return this.delegate;
        }

        void writeMapTo(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.writeInt(this.delegate.size());
            for (Map.Entry entry : this.delegate.entrySet()) {
                objectOutputStream.writeObject(entry.getKey());
                objectOutputStream.writeObject(entry.getValue());
            }
            objectOutputStream.writeObject(null);
        }

        MapMaker readMapMaker(ObjectInputStream objectInputStream) throws IOException {
            int n = objectInputStream.readInt();
            return new MapMaker().initialCapacity(n).setKeyStrength(this.keyStrength).setValueStrength(this.valueStrength).keyEquivalence(this.keyEquivalence).concurrencyLevel(this.concurrencyLevel);
        }

        void readEntries(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            Object object;
            while ((object = objectInputStream.readObject()) != null) {
                Object object2 = objectInputStream.readObject();
                this.delegate.put(object, object2);
            }
        }

        @Override
        protected Map delegate() {
            return this.delegate();
        }

        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }

    private static abstract class SafeToArraySet<E>
    extends AbstractSet<E> {
        private SafeToArraySet() {
        }

        @Override
        public Object[] toArray() {
            return MapMakerInternalMap.access$800(this).toArray();
        }

        @Override
        public <E> E[] toArray(E[] EArray) {
            return MapMakerInternalMap.access$800(this).toArray(EArray);
        }

        SafeToArraySet(1 var1_1) {
            this();
        }
    }

    final class EntrySet
    extends SafeToArraySet<Map.Entry<K, V>> {
        final MapMakerInternalMap this$0;

        EntrySet(MapMakerInternalMap mapMakerInternalMap) {
            this.this$0 = mapMakerInternalMap;
            super(null);
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator(this.this$0);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null) {
                return true;
            }
            Object v = this.this$0.get(k);
            return v != null && this.this$0.valueEquivalence().equivalent(entry.getValue(), v);
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            return k != null && this.this$0.remove(k, entry.getValue());
        }

        @Override
        public int size() {
            return this.this$0.size();
        }

        @Override
        public boolean isEmpty() {
            return this.this$0.isEmpty();
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }
    }

    final class Values
    extends AbstractCollection<V> {
        final MapMakerInternalMap this$0;

        Values(MapMakerInternalMap mapMakerInternalMap) {
            this.this$0 = mapMakerInternalMap;
        }

        @Override
        public Iterator<V> iterator() {
            return new ValueIterator(this.this$0);
        }

        @Override
        public int size() {
            return this.this$0.size();
        }

        @Override
        public boolean isEmpty() {
            return this.this$0.isEmpty();
        }

        @Override
        public boolean contains(Object object) {
            return this.this$0.containsValue(object);
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }

        @Override
        public Object[] toArray() {
            return MapMakerInternalMap.access$800(this).toArray();
        }

        @Override
        public <E> E[] toArray(E[] EArray) {
            return MapMakerInternalMap.access$800(this).toArray(EArray);
        }
    }

    final class KeySet
    extends SafeToArraySet<K> {
        final MapMakerInternalMap this$0;

        KeySet(MapMakerInternalMap mapMakerInternalMap) {
            this.this$0 = mapMakerInternalMap;
            super(null);
        }

        @Override
        public Iterator<K> iterator() {
            return new KeyIterator(this.this$0);
        }

        @Override
        public int size() {
            return this.this$0.size();
        }

        @Override
        public boolean isEmpty() {
            return this.this$0.isEmpty();
        }

        @Override
        public boolean contains(Object object) {
            return this.this$0.containsKey(object);
        }

        @Override
        public boolean remove(Object object) {
            return this.this$0.remove(object) != null;
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }
    }

    final class EntryIterator
    extends HashIterator<Map.Entry<K, V>> {
        final MapMakerInternalMap this$0;

        EntryIterator(MapMakerInternalMap mapMakerInternalMap) {
            this.this$0 = mapMakerInternalMap;
            super(mapMakerInternalMap);
        }

        @Override
        public Map.Entry<K, V> next() {
            return this.nextEntry();
        }

        @Override
        public Object next() {
            return this.next();
        }
    }

    final class WriteThroughEntry
    extends AbstractMapEntry<K, V> {
        final K key;
        V value;
        final MapMakerInternalMap this$0;

        WriteThroughEntry(MapMakerInternalMap mapMakerInternalMap, K k, V v) {
            this.this$0 = mapMakerInternalMap;
            this.key = k;
            this.value = v;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry)object;
                return this.key.equals(entry.getKey()) && this.value.equals(entry.getValue());
            }
            return true;
        }

        @Override
        public int hashCode() {
            return this.key.hashCode() ^ this.value.hashCode();
        }

        @Override
        public V setValue(V v) {
            Object v2 = this.this$0.put(this.key, v);
            this.value = v;
            return v2;
        }
    }

    final class ValueIterator
    extends HashIterator<V> {
        final MapMakerInternalMap this$0;

        ValueIterator(MapMakerInternalMap mapMakerInternalMap) {
            this.this$0 = mapMakerInternalMap;
            super(mapMakerInternalMap);
        }

        @Override
        public V next() {
            return this.nextEntry().getValue();
        }
    }

    final class KeyIterator
    extends HashIterator<K> {
        final MapMakerInternalMap this$0;

        KeyIterator(MapMakerInternalMap mapMakerInternalMap) {
            this.this$0 = mapMakerInternalMap;
            super(mapMakerInternalMap);
        }

        @Override
        public K next() {
            return this.nextEntry().getKey();
        }
    }

    abstract class HashIterator<T>
    implements Iterator<T> {
        int nextSegmentIndex;
        int nextTableIndex;
        Segment<K, V, E, S> currentSegment;
        AtomicReferenceArray<E> currentTable;
        E nextEntry;
        WriteThroughEntry nextExternal;
        WriteThroughEntry lastReturned;
        final MapMakerInternalMap this$0;

        HashIterator(MapMakerInternalMap mapMakerInternalMap) {
            this.this$0 = mapMakerInternalMap;
            this.nextSegmentIndex = mapMakerInternalMap.segments.length - 1;
            this.nextTableIndex = -1;
            this.advance();
        }

        @Override
        public abstract T next();

        final void advance() {
            this.nextExternal = null;
            if (this.nextInChain()) {
                return;
            }
            if (this.nextInTable()) {
                return;
            }
            while (this.nextSegmentIndex >= 0) {
                this.currentSegment = this.this$0.segments[this.nextSegmentIndex--];
                if (this.currentSegment.count == 0) continue;
                this.currentTable = this.currentSegment.table;
                this.nextTableIndex = this.currentTable.length() - 1;
                if (!this.nextInTable()) continue;
                return;
            }
        }

        boolean nextInChain() {
            if (this.nextEntry != null) {
                this.nextEntry = this.nextEntry.getNext();
                while (this.nextEntry != null) {
                    if (this.advanceTo(this.nextEntry)) {
                        return false;
                    }
                    this.nextEntry = this.nextEntry.getNext();
                }
            }
            return true;
        }

        boolean nextInTable() {
            while (this.nextTableIndex >= 0) {
                if ((this.nextEntry = (InternalEntry)this.currentTable.get(this.nextTableIndex--)) == null || !this.advanceTo(this.nextEntry) && !this.nextInChain()) continue;
                return false;
            }
            return true;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        boolean advanceTo(E e) {
            try {
                Object k = e.getKey();
                Object v = this.this$0.getLiveValue(e);
                if (v != null) {
                    this.nextExternal = new WriteThroughEntry(this.this$0, k, v);
                    boolean bl = true;
                    return bl;
                }
                boolean bl = false;
                return bl;
            } finally {
                this.currentSegment.postReadCleanup();
            }
        }

        @Override
        public boolean hasNext() {
            return this.nextExternal != null;
        }

        WriteThroughEntry nextEntry() {
            if (this.nextExternal == null) {
                throw new NoSuchElementException();
            }
            this.lastReturned = this.nextExternal;
            this.advance();
            return this.lastReturned;
        }

        @Override
        public void remove() {
            CollectPreconditions.checkRemove(this.lastReturned != null);
            this.this$0.remove(this.lastReturned.getKey());
            this.lastReturned = null;
        }
    }

    static final class CleanupMapTask
    implements Runnable {
        final WeakReference<MapMakerInternalMap<?, ?, ?, ?>> mapReference;

        public CleanupMapTask(MapMakerInternalMap<?, ?, ?, ?> mapMakerInternalMap) {
            this.mapReference = new WeakReference(mapMakerInternalMap);
        }

        @Override
        public void run() {
            MapMakerInternalMap mapMakerInternalMap = (MapMakerInternalMap)this.mapReference.get();
            if (mapMakerInternalMap == null) {
                throw new CancellationException();
            }
            for (Segment segment : mapMakerInternalMap.segments) {
                segment.runCleanup();
            }
        }
    }

    static final class WeakKeyWeakValueSegment<K, V>
    extends Segment<K, V, WeakKeyWeakValueEntry<K, V>, WeakKeyWeakValueSegment<K, V>> {
        private final ReferenceQueue<K> queueForKeys = new ReferenceQueue();
        private final ReferenceQueue<V> queueForValues = new ReferenceQueue();

        WeakKeyWeakValueSegment(MapMakerInternalMap<K, V, WeakKeyWeakValueEntry<K, V>, WeakKeyWeakValueSegment<K, V>> mapMakerInternalMap, int n, int n2) {
            super(mapMakerInternalMap, n, n2);
        }

        @Override
        WeakKeyWeakValueSegment<K, V> self() {
            return this;
        }

        @Override
        ReferenceQueue<K> getKeyReferenceQueueForTesting() {
            return this.queueForKeys;
        }

        @Override
        ReferenceQueue<V> getValueReferenceQueueForTesting() {
            return this.queueForValues;
        }

        @Override
        public WeakKeyWeakValueEntry<K, V> castForTesting(InternalEntry<K, V, ?> internalEntry) {
            return (WeakKeyWeakValueEntry)internalEntry;
        }

        @Override
        public WeakValueReference<K, V, WeakKeyWeakValueEntry<K, V>> getWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry) {
            return ((WeakKeyWeakValueEntry)this.castForTesting((InternalEntry)internalEntry)).getValueReference();
        }

        @Override
        public WeakValueReference<K, V, WeakKeyWeakValueEntry<K, V>> newWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry, V v) {
            return new WeakValueReferenceImpl(this.queueForValues, v, this.castForTesting((InternalEntry)internalEntry));
        }

        @Override
        public void setWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry, WeakValueReference<K, V, ? extends InternalEntry<K, V, ?>> weakValueReference) {
            InternalEntry internalEntry2 = this.castForTesting((InternalEntry)internalEntry);
            WeakValueReference<K, V, ? extends InternalEntry<K, V, ?>> weakValueReference2 = weakValueReference;
            WeakValueReference weakValueReference3 = WeakKeyWeakValueEntry.access$600((WeakKeyWeakValueEntry)internalEntry2);
            WeakKeyWeakValueEntry.access$602((WeakKeyWeakValueEntry)internalEntry2, weakValueReference2);
            weakValueReference3.clear();
        }

        @Override
        void maybeDrainReferenceQueues() {
            this.drainKeyReferenceQueue(this.queueForKeys);
            this.drainValueReferenceQueue(this.queueForValues);
        }

        @Override
        void maybeClearReferenceQueues() {
            this.clearReferenceQueue(this.queueForKeys);
        }

        @Override
        public InternalEntry castForTesting(InternalEntry internalEntry) {
            return this.castForTesting(internalEntry);
        }

        @Override
        Segment self() {
            return this.self();
        }

        static ReferenceQueue access$300(WeakKeyWeakValueSegment weakKeyWeakValueSegment) {
            return weakKeyWeakValueSegment.queueForKeys;
        }

        static ReferenceQueue access$400(WeakKeyWeakValueSegment weakKeyWeakValueSegment) {
            return weakKeyWeakValueSegment.queueForValues;
        }
    }

    static final class WeakKeyStrongValueSegment<K, V>
    extends Segment<K, V, WeakKeyStrongValueEntry<K, V>, WeakKeyStrongValueSegment<K, V>> {
        private final ReferenceQueue<K> queueForKeys = new ReferenceQueue();

        WeakKeyStrongValueSegment(MapMakerInternalMap<K, V, WeakKeyStrongValueEntry<K, V>, WeakKeyStrongValueSegment<K, V>> mapMakerInternalMap, int n, int n2) {
            super(mapMakerInternalMap, n, n2);
        }

        @Override
        WeakKeyStrongValueSegment<K, V> self() {
            return this;
        }

        @Override
        ReferenceQueue<K> getKeyReferenceQueueForTesting() {
            return this.queueForKeys;
        }

        @Override
        public WeakKeyStrongValueEntry<K, V> castForTesting(InternalEntry<K, V, ?> internalEntry) {
            return (WeakKeyStrongValueEntry)internalEntry;
        }

        @Override
        void maybeDrainReferenceQueues() {
            this.drainKeyReferenceQueue(this.queueForKeys);
        }

        @Override
        void maybeClearReferenceQueues() {
            this.clearReferenceQueue(this.queueForKeys);
        }

        @Override
        public InternalEntry castForTesting(InternalEntry internalEntry) {
            return this.castForTesting(internalEntry);
        }

        @Override
        Segment self() {
            return this.self();
        }

        static ReferenceQueue access$200(WeakKeyStrongValueSegment weakKeyStrongValueSegment) {
            return weakKeyStrongValueSegment.queueForKeys;
        }
    }

    static final class StrongKeyWeakValueSegment<K, V>
    extends Segment<K, V, StrongKeyWeakValueEntry<K, V>, StrongKeyWeakValueSegment<K, V>> {
        private final ReferenceQueue<V> queueForValues = new ReferenceQueue();

        StrongKeyWeakValueSegment(MapMakerInternalMap<K, V, StrongKeyWeakValueEntry<K, V>, StrongKeyWeakValueSegment<K, V>> mapMakerInternalMap, int n, int n2) {
            super(mapMakerInternalMap, n, n2);
        }

        @Override
        StrongKeyWeakValueSegment<K, V> self() {
            return this;
        }

        @Override
        ReferenceQueue<V> getValueReferenceQueueForTesting() {
            return this.queueForValues;
        }

        @Override
        public StrongKeyWeakValueEntry<K, V> castForTesting(InternalEntry<K, V, ?> internalEntry) {
            return (StrongKeyWeakValueEntry)internalEntry;
        }

        @Override
        public WeakValueReference<K, V, StrongKeyWeakValueEntry<K, V>> getWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry) {
            return ((StrongKeyWeakValueEntry)this.castForTesting((InternalEntry)internalEntry)).getValueReference();
        }

        @Override
        public WeakValueReference<K, V, StrongKeyWeakValueEntry<K, V>> newWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry, V v) {
            return new WeakValueReferenceImpl(this.queueForValues, v, this.castForTesting((InternalEntry)internalEntry));
        }

        @Override
        public void setWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry, WeakValueReference<K, V, ? extends InternalEntry<K, V, ?>> weakValueReference) {
            InternalEntry internalEntry2 = this.castForTesting((InternalEntry)internalEntry);
            WeakValueReference<K, V, ? extends InternalEntry<K, V, ?>> weakValueReference2 = weakValueReference;
            WeakValueReference weakValueReference3 = StrongKeyWeakValueEntry.access$500((StrongKeyWeakValueEntry)internalEntry2);
            StrongKeyWeakValueEntry.access$502((StrongKeyWeakValueEntry)internalEntry2, weakValueReference2);
            weakValueReference3.clear();
        }

        @Override
        void maybeDrainReferenceQueues() {
            this.drainValueReferenceQueue(this.queueForValues);
        }

        @Override
        void maybeClearReferenceQueues() {
            this.clearReferenceQueue(this.queueForValues);
        }

        @Override
        public InternalEntry castForTesting(InternalEntry internalEntry) {
            return this.castForTesting(internalEntry);
        }

        @Override
        Segment self() {
            return this.self();
        }

        static ReferenceQueue access$100(StrongKeyWeakValueSegment strongKeyWeakValueSegment) {
            return strongKeyWeakValueSegment.queueForValues;
        }
    }

    static final class StrongKeyStrongValueSegment<K, V>
    extends Segment<K, V, StrongKeyStrongValueEntry<K, V>, StrongKeyStrongValueSegment<K, V>> {
        StrongKeyStrongValueSegment(MapMakerInternalMap<K, V, StrongKeyStrongValueEntry<K, V>, StrongKeyStrongValueSegment<K, V>> mapMakerInternalMap, int n, int n2) {
            super(mapMakerInternalMap, n, n2);
        }

        @Override
        StrongKeyStrongValueSegment<K, V> self() {
            return this;
        }

        @Override
        public StrongKeyStrongValueEntry<K, V> castForTesting(InternalEntry<K, V, ?> internalEntry) {
            return (StrongKeyStrongValueEntry)internalEntry;
        }

        @Override
        public InternalEntry castForTesting(InternalEntry internalEntry) {
            return this.castForTesting(internalEntry);
        }

        @Override
        Segment self() {
            return this.self();
        }
    }

    static abstract class Segment<K, V, E extends InternalEntry<K, V, E>, S extends Segment<K, V, E, S>>
    extends ReentrantLock {
        @Weak
        final MapMakerInternalMap<K, V, E, S> map;
        volatile int count;
        int modCount;
        int threshold;
        volatile AtomicReferenceArray<E> table;
        final int maxSegmentSize;
        final AtomicInteger readCount = new AtomicInteger();

        Segment(MapMakerInternalMap<K, V, E, S> mapMakerInternalMap, int n, int n2) {
            this.map = mapMakerInternalMap;
            this.maxSegmentSize = n2;
            this.initTable(this.newEntryArray(n));
        }

        abstract S self();

        @GuardedBy(value="this")
        void maybeDrainReferenceQueues() {
        }

        void maybeClearReferenceQueues() {
        }

        void setValue(E e, V v) {
            this.map.entryHelper.setValue(this.self(), e, v);
        }

        E copyEntry(E e, E e2) {
            return this.map.entryHelper.copy(this.self(), e, e2);
        }

        AtomicReferenceArray<E> newEntryArray(int n) {
            return new AtomicReferenceArray(n);
        }

        void initTable(AtomicReferenceArray<E> atomicReferenceArray) {
            this.threshold = atomicReferenceArray.length() * 3 / 4;
            if (this.threshold == this.maxSegmentSize) {
                ++this.threshold;
            }
            this.table = atomicReferenceArray;
        }

        abstract E castForTesting(InternalEntry<K, V, ?> var1);

        ReferenceQueue<K> getKeyReferenceQueueForTesting() {
            throw new AssertionError();
        }

        ReferenceQueue<V> getValueReferenceQueueForTesting() {
            throw new AssertionError();
        }

        WeakValueReference<K, V, E> getWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry) {
            throw new AssertionError();
        }

        WeakValueReference<K, V, E> newWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry, V v) {
            throw new AssertionError();
        }

        void setWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry, WeakValueReference<K, V, ? extends InternalEntry<K, V, ?>> weakValueReference) {
            throw new AssertionError();
        }

        void setTableEntryForTesting(int n, InternalEntry<K, V, ?> internalEntry) {
            this.table.set(n, this.castForTesting(internalEntry));
        }

        E copyForTesting(InternalEntry<K, V, ?> internalEntry, @Nullable InternalEntry<K, V, ?> internalEntry2) {
            return this.map.entryHelper.copy(this.self(), this.castForTesting(internalEntry), this.castForTesting(internalEntry2));
        }

        void setValueForTesting(InternalEntry<K, V, ?> internalEntry, V v) {
            this.map.entryHelper.setValue(this.self(), this.castForTesting(internalEntry), v);
        }

        E newEntryForTesting(K k, int n, @Nullable InternalEntry<K, V, ?> internalEntry) {
            return this.map.entryHelper.newEntry(this.self(), k, n, this.castForTesting(internalEntry));
        }

        @CanIgnoreReturnValue
        boolean removeTableEntryForTesting(InternalEntry<K, V, ?> internalEntry) {
            return this.removeEntryForTesting(this.castForTesting(internalEntry));
        }

        E removeFromChainForTesting(InternalEntry<K, V, ?> internalEntry, InternalEntry<K, V, ?> internalEntry2) {
            return this.removeFromChain(this.castForTesting(internalEntry), this.castForTesting(internalEntry2));
        }

        @Nullable
        V getLiveValueForTesting(InternalEntry<K, V, ?> internalEntry) {
            return this.getLiveValue(this.castForTesting(internalEntry));
        }

        void tryDrainReferenceQueues() {
            if (this.tryLock()) {
                try {
                    this.maybeDrainReferenceQueues();
                } finally {
                    this.unlock();
                }
            }
        }

        @GuardedBy(value="this")
        void drainKeyReferenceQueue(ReferenceQueue<K> referenceQueue) {
            Reference<K> reference;
            int n = 0;
            while ((reference = referenceQueue.poll()) != null) {
                InternalEntry internalEntry = (InternalEntry)((Object)reference);
                this.map.reclaimKey(internalEntry);
                if (++n != 16) continue;
                break;
            }
        }

        @GuardedBy(value="this")
        void drainValueReferenceQueue(ReferenceQueue<V> referenceQueue) {
            Reference<V> reference;
            int n = 0;
            while ((reference = referenceQueue.poll()) != null) {
                WeakValueReference weakValueReference = (WeakValueReference)((Object)reference);
                this.map.reclaimValue(weakValueReference);
                if (++n != 16) continue;
                break;
            }
        }

        <T> void clearReferenceQueue(ReferenceQueue<T> referenceQueue) {
            while (referenceQueue.poll() != null) {
            }
        }

        E getFirst(int n) {
            AtomicReferenceArray<E> atomicReferenceArray = this.table;
            return (E)((InternalEntry)atomicReferenceArray.get(n & atomicReferenceArray.length() - 1));
        }

        E getEntry(Object object, int n) {
            if (this.count != 0) {
                for (E e = this.getFirst(n); e != null; e = e.getNext()) {
                    if (e.getHash() != n) continue;
                    Object k = e.getKey();
                    if (k == null) {
                        this.tryDrainReferenceQueues();
                        continue;
                    }
                    if (!this.map.keyEquivalence.equivalent(object, k)) continue;
                    return e;
                }
            }
            return null;
        }

        E getLiveEntry(Object object, int n) {
            return this.getEntry(object, n);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        V get(Object object, int n) {
            try {
                E e = this.getLiveEntry(object, n);
                if (e == null) {
                    V v = null;
                    return v;
                }
                Object v = e.getValue();
                if (v == null) {
                    this.tryDrainReferenceQueues();
                }
                Object v2 = v;
                return v2;
            } finally {
                this.postReadCleanup();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        boolean containsKey(Object object, int n) {
            try {
                if (this.count != 0) {
                    E e = this.getLiveEntry(object, n);
                    boolean bl = e != null && e.getValue() != null;
                    return bl;
                }
                boolean bl = false;
                return bl;
            } finally {
                this.postReadCleanup();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @VisibleForTesting
        boolean containsValue(Object object) {
            try {
                if (this.count != 0) {
                    AtomicReferenceArray<E> atomicReferenceArray = this.table;
                    int n = atomicReferenceArray.length();
                    for (int i = 0; i < n; ++i) {
                        for (InternalEntry internalEntry = (InternalEntry)atomicReferenceArray.get(i); internalEntry != null; internalEntry = internalEntry.getNext()) {
                            V v = this.getLiveValue(internalEntry);
                            if (v == null || !this.map.valueEquivalence().equivalent(object, v)) continue;
                            boolean bl = true;
                            return bl;
                        }
                    }
                }
                boolean bl = false;
                return bl;
            } finally {
                this.postReadCleanup();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        V put(K k, int n, V v, boolean bl) {
            this.lock();
            try {
                Object k2;
                InternalEntry internalEntry;
                this.preWriteCleanup();
                int n2 = this.count + 1;
                if (n2 > this.threshold) {
                    this.expand();
                    n2 = this.count + 1;
                }
                AtomicReferenceArray atomicReferenceArray = this.table;
                int n3 = n & atomicReferenceArray.length() - 1;
                InternalEntry internalEntry2 = (InternalEntry)atomicReferenceArray.get(n3);
                for (internalEntry = internalEntry2; internalEntry != null; internalEntry = internalEntry.getNext()) {
                    k2 = internalEntry.getKey();
                    if (internalEntry.getHash() != n || k2 == null || !this.map.keyEquivalence.equivalent(k, k2)) continue;
                    Object v2 = internalEntry.getValue();
                    if (v2 == null) {
                        ++this.modCount;
                        this.setValue(internalEntry, v);
                        this.count = n2 = this.count;
                        V v3 = null;
                        return v3;
                    }
                    if (bl) {
                        Object v4 = v2;
                        return v4;
                    }
                    ++this.modCount;
                    this.setValue(internalEntry, v);
                    Object v5 = v2;
                    return v5;
                }
                ++this.modCount;
                internalEntry = this.map.entryHelper.newEntry(this.self(), k, n, internalEntry2);
                this.setValue(internalEntry, v);
                atomicReferenceArray.set(n3, internalEntry);
                this.count = n2;
                k2 = null;
                return (V)k2;
            } finally {
                this.unlock();
            }
        }

        @GuardedBy(value="this")
        void expand() {
            AtomicReferenceArray<E> atomicReferenceArray = this.table;
            int n = atomicReferenceArray.length();
            if (n >= 0x40000000) {
                return;
            }
            int n2 = this.count;
            AtomicReferenceArray<InternalEntry> atomicReferenceArray2 = this.newEntryArray(n << 1);
            this.threshold = atomicReferenceArray2.length() * 3 / 4;
            int n3 = atomicReferenceArray2.length() - 1;
            for (int i = 0; i < n; ++i) {
                int n4;
                Object object;
                InternalEntry internalEntry = (InternalEntry)atomicReferenceArray.get(i);
                if (internalEntry == null) continue;
                Object e = internalEntry.getNext();
                int n5 = internalEntry.getHash() & n3;
                if (e == null) {
                    atomicReferenceArray2.set(n5, internalEntry);
                    continue;
                }
                Object object2 = internalEntry;
                int n6 = n5;
                for (object = e; object != null; object = object.getNext()) {
                    n4 = object.getHash() & n3;
                    if (n4 == n6) continue;
                    n6 = n4;
                    object2 = object;
                }
                atomicReferenceArray2.set(n6, (InternalEntry)object2);
                for (object = internalEntry; object != object2; object = object.getNext()) {
                    n4 = object.getHash() & n3;
                    InternalEntry internalEntry2 = (InternalEntry)atomicReferenceArray2.get(n4);
                    InternalEntry internalEntry3 = this.copyEntry(object, internalEntry2);
                    if (internalEntry3 != null) {
                        atomicReferenceArray2.set(n4, internalEntry3);
                        continue;
                    }
                    --n2;
                }
            }
            this.table = atomicReferenceArray2;
            this.count = n2;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        boolean replace(K k, int n, V v, V v2) {
            this.lock();
            try {
                this.preWriteCleanup();
                AtomicReferenceArray atomicReferenceArray = this.table;
                int n2 = n & atomicReferenceArray.length() - 1;
                InternalEntry internalEntry = (InternalEntry)atomicReferenceArray.get(n2);
                for (InternalEntry internalEntry2 = internalEntry; internalEntry2 != null; internalEntry2 = internalEntry2.getNext()) {
                    Object k2 = internalEntry2.getKey();
                    if (internalEntry2.getHash() != n || k2 == null || !this.map.keyEquivalence.equivalent(k, k2)) continue;
                    Object v3 = internalEntry2.getValue();
                    if (v3 == null) {
                        int n3;
                        if (Segment.isCollected(internalEntry2)) {
                            n3 = this.count - 1;
                            ++this.modCount;
                            InternalEntry internalEntry3 = this.removeFromChain(internalEntry, internalEntry2);
                            n3 = this.count - 1;
                            atomicReferenceArray.set(n2, internalEntry3);
                            this.count = n3;
                        }
                        n3 = 0;
                        return n3 != 0;
                    }
                    if (this.map.valueEquivalence().equivalent(v, v3)) {
                        ++this.modCount;
                        this.setValue(internalEntry2, v2);
                        boolean bl = true;
                        return bl;
                    }
                    boolean bl = false;
                    return bl;
                }
                boolean bl = false;
                return bl;
            } finally {
                this.unlock();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        V replace(K k, int n, V v) {
            this.lock();
            try {
                InternalEntry internalEntry;
                this.preWriteCleanup();
                AtomicReferenceArray atomicReferenceArray = this.table;
                int n2 = n & atomicReferenceArray.length() - 1;
                InternalEntry internalEntry2 = (InternalEntry)atomicReferenceArray.get(n2);
                for (internalEntry = internalEntry2; internalEntry != null; internalEntry = internalEntry.getNext()) {
                    Object k2 = internalEntry.getKey();
                    if (internalEntry.getHash() != n || k2 == null || !this.map.keyEquivalence.equivalent(k, k2)) continue;
                    Object v2 = internalEntry.getValue();
                    if (v2 == null) {
                        if (Segment.isCollected(internalEntry)) {
                            int n3 = this.count - 1;
                            ++this.modCount;
                            InternalEntry internalEntry3 = this.removeFromChain(internalEntry2, internalEntry);
                            n3 = this.count - 1;
                            atomicReferenceArray.set(n2, internalEntry3);
                            this.count = n3;
                        }
                        V v3 = null;
                        return v3;
                    }
                    ++this.modCount;
                    this.setValue(internalEntry, v);
                    Object v4 = v2;
                    return v4;
                }
                internalEntry = null;
                return (V)internalEntry;
            } finally {
                this.unlock();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CanIgnoreReturnValue
        V remove(Object object, int n) {
            this.lock();
            try {
                InternalEntry internalEntry;
                this.preWriteCleanup();
                int n2 = this.count - 1;
                AtomicReferenceArray atomicReferenceArray = this.table;
                int n3 = n & atomicReferenceArray.length() - 1;
                InternalEntry internalEntry2 = (InternalEntry)atomicReferenceArray.get(n3);
                for (internalEntry = internalEntry2; internalEntry != null; internalEntry = internalEntry.getNext()) {
                    Object k = internalEntry.getKey();
                    if (internalEntry.getHash() != n || k == null || !this.map.keyEquivalence.equivalent(object, k)) continue;
                    Object v = internalEntry.getValue();
                    if (v == null && !Segment.isCollected(internalEntry)) {
                        V v2 = null;
                        return v2;
                    }
                    ++this.modCount;
                    InternalEntry internalEntry3 = this.removeFromChain(internalEntry2, internalEntry);
                    n2 = this.count - 1;
                    atomicReferenceArray.set(n3, internalEntry3);
                    this.count = n2;
                    Object v3 = v;
                    return v3;
                }
                internalEntry = null;
                return (V)internalEntry;
            } finally {
                this.unlock();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        boolean remove(Object object, int n, Object object2) {
            this.lock();
            try {
                this.preWriteCleanup();
                int n2 = this.count - 1;
                AtomicReferenceArray atomicReferenceArray = this.table;
                int n3 = n & atomicReferenceArray.length() - 1;
                InternalEntry internalEntry = (InternalEntry)atomicReferenceArray.get(n3);
                for (InternalEntry internalEntry2 = internalEntry; internalEntry2 != null; internalEntry2 = internalEntry2.getNext()) {
                    Object k = internalEntry2.getKey();
                    if (internalEntry2.getHash() != n || k == null || !this.map.keyEquivalence.equivalent(object, k)) continue;
                    Object v = internalEntry2.getValue();
                    boolean bl = false;
                    if (this.map.valueEquivalence().equivalent(object2, v)) {
                        bl = true;
                    } else if (!Segment.isCollected(internalEntry2)) {
                        boolean bl2 = false;
                        return bl2;
                    }
                    ++this.modCount;
                    InternalEntry internalEntry3 = this.removeFromChain(internalEntry, internalEntry2);
                    n2 = this.count - 1;
                    atomicReferenceArray.set(n3, internalEntry3);
                    this.count = n2;
                    boolean bl3 = bl;
                    return bl3;
                }
                boolean bl = false;
                return bl;
            } finally {
                this.unlock();
            }
        }

        void clear() {
            if (this.count != 0) {
                this.lock();
                try {
                    AtomicReferenceArray<E> atomicReferenceArray = this.table;
                    for (int i = 0; i < atomicReferenceArray.length(); ++i) {
                        atomicReferenceArray.set(i, null);
                    }
                    this.maybeClearReferenceQueues();
                    this.readCount.set(0);
                    ++this.modCount;
                    this.count = 0;
                } finally {
                    this.unlock();
                }
            }
        }

        @GuardedBy(value="this")
        E removeFromChain(E e, E e2) {
            int n = this.count;
            Object e3 = e2.getNext();
            for (E e4 = e; e4 != e2; e4 = e4.getNext()) {
                E e5 = this.copyEntry(e4, e3);
                if (e5 != null) {
                    e3 = e5;
                    continue;
                }
                --n;
            }
            this.count = n;
            return e3;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CanIgnoreReturnValue
        boolean reclaimKey(E e, int n) {
            this.lock();
            try {
                InternalEntry internalEntry;
                int n2 = this.count - 1;
                AtomicReferenceArray<InternalEntry> atomicReferenceArray = this.table;
                int n3 = n & atomicReferenceArray.length() - 1;
                for (InternalEntry internalEntry2 = internalEntry = (InternalEntry)atomicReferenceArray.get(n3); internalEntry2 != null; internalEntry2 = internalEntry2.getNext()) {
                    if (internalEntry2 != e) continue;
                    ++this.modCount;
                    InternalEntry internalEntry3 = this.removeFromChain(internalEntry, internalEntry2);
                    n2 = this.count - 1;
                    atomicReferenceArray.set(n3, internalEntry3);
                    this.count = n2;
                    boolean bl = true;
                    return bl;
                }
                boolean bl = false;
                return bl;
            } finally {
                this.unlock();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CanIgnoreReturnValue
        boolean reclaimValue(K k, int n, WeakValueReference<K, V, E> weakValueReference) {
            this.lock();
            try {
                InternalEntry internalEntry;
                int n2 = this.count - 1;
                AtomicReferenceArray<InternalEntry> atomicReferenceArray = this.table;
                int n3 = n & atomicReferenceArray.length() - 1;
                for (InternalEntry internalEntry2 = internalEntry = (InternalEntry)atomicReferenceArray.get(n3); internalEntry2 != null; internalEntry2 = internalEntry2.getNext()) {
                    Object k2 = internalEntry2.getKey();
                    if (internalEntry2.getHash() != n || k2 == null || !this.map.keyEquivalence.equivalent(k, k2)) continue;
                    WeakValueReference weakValueReference2 = ((WeakValueEntry)internalEntry2).getValueReference();
                    if (weakValueReference2 == weakValueReference) {
                        ++this.modCount;
                        InternalEntry internalEntry3 = this.removeFromChain(internalEntry, internalEntry2);
                        n2 = this.count - 1;
                        atomicReferenceArray.set(n3, internalEntry3);
                        this.count = n2;
                        boolean bl = true;
                        return bl;
                    }
                    boolean bl = false;
                    return bl;
                }
                boolean bl = false;
                return bl;
            } finally {
                this.unlock();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CanIgnoreReturnValue
        boolean clearValueForTesting(K k, int n, WeakValueReference<K, V, ? extends InternalEntry<K, V, ?>> weakValueReference) {
            this.lock();
            try {
                InternalEntry internalEntry;
                AtomicReferenceArray<InternalEntry> atomicReferenceArray = this.table;
                int n2 = n & atomicReferenceArray.length() - 1;
                for (InternalEntry internalEntry2 = internalEntry = (InternalEntry)atomicReferenceArray.get(n2); internalEntry2 != null; internalEntry2 = internalEntry2.getNext()) {
                    Object k2 = internalEntry2.getKey();
                    if (internalEntry2.getHash() != n || k2 == null || !this.map.keyEquivalence.equivalent(k, k2)) continue;
                    WeakValueReference weakValueReference2 = ((WeakValueEntry)internalEntry2).getValueReference();
                    if (weakValueReference2 == weakValueReference) {
                        InternalEntry internalEntry3 = this.removeFromChain(internalEntry, internalEntry2);
                        atomicReferenceArray.set(n2, internalEntry3);
                        boolean bl = true;
                        return bl;
                    }
                    boolean bl = false;
                    return bl;
                }
                boolean bl = false;
                return bl;
            } finally {
                this.unlock();
            }
        }

        @GuardedBy(value="this")
        boolean removeEntryForTesting(E e) {
            InternalEntry internalEntry;
            int n = e.getHash();
            int n2 = this.count - 1;
            AtomicReferenceArray<InternalEntry> atomicReferenceArray = this.table;
            int n3 = n & atomicReferenceArray.length() - 1;
            for (InternalEntry internalEntry2 = internalEntry = (InternalEntry)atomicReferenceArray.get(n3); internalEntry2 != null; internalEntry2 = internalEntry2.getNext()) {
                if (internalEntry2 != e) continue;
                ++this.modCount;
                InternalEntry internalEntry3 = this.removeFromChain(internalEntry, internalEntry2);
                n2 = this.count - 1;
                atomicReferenceArray.set(n3, internalEntry3);
                this.count = n2;
                return false;
            }
            return true;
        }

        static <K, V, E extends InternalEntry<K, V, E>> boolean isCollected(E e) {
            return e.getValue() == null;
        }

        @Nullable
        V getLiveValue(E e) {
            if (e.getKey() == null) {
                this.tryDrainReferenceQueues();
                return null;
            }
            Object v = e.getValue();
            if (v == null) {
                this.tryDrainReferenceQueues();
                return null;
            }
            return v;
        }

        void postReadCleanup() {
            if ((this.readCount.incrementAndGet() & 0x3F) == 0) {
                this.runCleanup();
            }
        }

        @GuardedBy(value="this")
        void preWriteCleanup() {
            this.runLockedCleanup();
        }

        void runCleanup() {
            this.runLockedCleanup();
        }

        void runLockedCleanup() {
            if (this.tryLock()) {
                try {
                    this.maybeDrainReferenceQueues();
                    this.readCount.set(0);
                } finally {
                    this.unlock();
                }
            }
        }
    }

    static final class WeakValueReferenceImpl<K, V, E extends InternalEntry<K, V, E>>
    extends WeakReference<V>
    implements WeakValueReference<K, V, E> {
        @Weak
        final E entry;

        WeakValueReferenceImpl(ReferenceQueue<V> referenceQueue, V v, E e) {
            super(v, referenceQueue);
            this.entry = e;
        }

        @Override
        public E getEntry() {
            return this.entry;
        }

        @Override
        public WeakValueReference<K, V, E> copyFor(ReferenceQueue<V> referenceQueue, E e) {
            return new WeakValueReferenceImpl<K, V, E>(referenceQueue, this.get(), e);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static final class DummyInternalEntry
    implements InternalEntry<Object, Object, DummyInternalEntry> {
        private DummyInternalEntry() {
            throw new AssertionError();
        }

        @Override
        public DummyInternalEntry getNext() {
            throw new AssertionError();
        }

        @Override
        public int getHash() {
            throw new AssertionError();
        }

        @Override
        public Object getKey() {
            throw new AssertionError();
        }

        @Override
        public Object getValue() {
            throw new AssertionError();
        }

        @Override
        public InternalEntry getNext() {
            return this.getNext();
        }
    }

    static interface WeakValueReference<K, V, E extends InternalEntry<K, V, E>> {
        @Nullable
        public V get();

        public E getEntry();

        public void clear();

        public WeakValueReference<K, V, E> copyFor(ReferenceQueue<V> var1, E var2);
    }

    static final class WeakKeyWeakValueEntry<K, V>
    extends AbstractWeakKeyEntry<K, V, WeakKeyWeakValueEntry<K, V>>
    implements WeakValueEntry<K, V, WeakKeyWeakValueEntry<K, V>> {
        private volatile WeakValueReference<K, V, WeakKeyWeakValueEntry<K, V>> valueReference = MapMakerInternalMap.unsetWeakValueReference();

        WeakKeyWeakValueEntry(ReferenceQueue<K> referenceQueue, K k, int n, @Nullable WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry) {
            super(referenceQueue, k, n, weakKeyWeakValueEntry);
        }

        @Override
        public V getValue() {
            return this.valueReference.get();
        }

        WeakKeyWeakValueEntry<K, V> copy(ReferenceQueue<K> referenceQueue, ReferenceQueue<V> referenceQueue2, WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry) {
            WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry2 = new WeakKeyWeakValueEntry<K, V>(referenceQueue, this.getKey(), this.hash, weakKeyWeakValueEntry);
            weakKeyWeakValueEntry2.valueReference = this.valueReference.copyFor(referenceQueue2, weakKeyWeakValueEntry2);
            return weakKeyWeakValueEntry2;
        }

        @Override
        public void clearValue() {
            this.valueReference.clear();
        }

        void setValue(V v, ReferenceQueue<V> referenceQueue) {
            WeakValueReference<K, V, WeakKeyWeakValueEntry<K, V>> weakValueReference = this.valueReference;
            this.valueReference = new WeakValueReferenceImpl(referenceQueue, v, this);
            weakValueReference.clear();
        }

        @Override
        public WeakValueReference<K, V, WeakKeyWeakValueEntry<K, V>> getValueReference() {
            return this.valueReference;
        }

        static WeakValueReference access$600(WeakKeyWeakValueEntry weakKeyWeakValueEntry) {
            return weakKeyWeakValueEntry.valueReference;
        }

        static WeakValueReference access$602(WeakKeyWeakValueEntry weakKeyWeakValueEntry, WeakValueReference weakValueReference) {
            weakKeyWeakValueEntry.valueReference = weakValueReference;
            return weakKeyWeakValueEntry.valueReference;
        }

        static final class Helper<K, V>
        implements InternalEntryHelper<K, V, WeakKeyWeakValueEntry<K, V>, WeakKeyWeakValueSegment<K, V>> {
            private static final Helper<?, ?> INSTANCE = new Helper();

            Helper() {
            }

            static <K, V> Helper<K, V> instance() {
                return INSTANCE;
            }

            @Override
            public Strength keyStrength() {
                return Strength.WEAK;
            }

            @Override
            public Strength valueStrength() {
                return Strength.WEAK;
            }

            @Override
            public WeakKeyWeakValueSegment<K, V> newSegment(MapMakerInternalMap<K, V, WeakKeyWeakValueEntry<K, V>, WeakKeyWeakValueSegment<K, V>> mapMakerInternalMap, int n, int n2) {
                return new WeakKeyWeakValueSegment<K, V>(mapMakerInternalMap, n, n2);
            }

            @Override
            public WeakKeyWeakValueEntry<K, V> copy(WeakKeyWeakValueSegment<K, V> weakKeyWeakValueSegment, WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry, @Nullable WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry2) {
                if (weakKeyWeakValueEntry.getKey() == null) {
                    return null;
                }
                if (Segment.isCollected(weakKeyWeakValueEntry)) {
                    return null;
                }
                return weakKeyWeakValueEntry.copy(WeakKeyWeakValueSegment.access$300(weakKeyWeakValueSegment), WeakKeyWeakValueSegment.access$400(weakKeyWeakValueSegment), weakKeyWeakValueEntry2);
            }

            @Override
            public void setValue(WeakKeyWeakValueSegment<K, V> weakKeyWeakValueSegment, WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry, V v) {
                weakKeyWeakValueEntry.setValue(v, WeakKeyWeakValueSegment.access$400(weakKeyWeakValueSegment));
            }

            @Override
            public WeakKeyWeakValueEntry<K, V> newEntry(WeakKeyWeakValueSegment<K, V> weakKeyWeakValueSegment, K k, int n, @Nullable WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry) {
                return new WeakKeyWeakValueEntry<K, V>(WeakKeyWeakValueSegment.access$300(weakKeyWeakValueSegment), k, n, weakKeyWeakValueEntry);
            }

            @Override
            public void setValue(Segment segment, InternalEntry internalEntry, Object object) {
                this.setValue((WeakKeyWeakValueSegment)segment, (WeakKeyWeakValueEntry)internalEntry, object);
            }

            @Override
            public InternalEntry copy(Segment segment, InternalEntry internalEntry, @Nullable InternalEntry internalEntry2) {
                return this.copy((WeakKeyWeakValueSegment)segment, (WeakKeyWeakValueEntry)internalEntry, (WeakKeyWeakValueEntry)internalEntry2);
            }

            @Override
            public InternalEntry newEntry(Segment segment, Object object, int n, @Nullable InternalEntry internalEntry) {
                return this.newEntry((WeakKeyWeakValueSegment)segment, object, n, (WeakKeyWeakValueEntry)internalEntry);
            }

            @Override
            public Segment newSegment(MapMakerInternalMap mapMakerInternalMap, int n, int n2) {
                return this.newSegment(mapMakerInternalMap, n, n2);
            }
        }
    }

    static final class WeakKeyStrongValueEntry<K, V>
    extends AbstractWeakKeyEntry<K, V, WeakKeyStrongValueEntry<K, V>>
    implements StrongValueEntry<K, V, WeakKeyStrongValueEntry<K, V>> {
        @Nullable
        private volatile V value = null;

        WeakKeyStrongValueEntry(ReferenceQueue<K> referenceQueue, K k, int n, @Nullable WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry) {
            super(referenceQueue, k, n, weakKeyStrongValueEntry);
        }

        @Override
        @Nullable
        public V getValue() {
            return this.value;
        }

        void setValue(V v) {
            this.value = v;
        }

        WeakKeyStrongValueEntry<K, V> copy(ReferenceQueue<K> referenceQueue, WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry) {
            WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry2 = new WeakKeyStrongValueEntry<K, V>(referenceQueue, this.getKey(), this.hash, weakKeyStrongValueEntry);
            weakKeyStrongValueEntry2.setValue(this.value);
            return weakKeyStrongValueEntry2;
        }

        static final class Helper<K, V>
        implements InternalEntryHelper<K, V, WeakKeyStrongValueEntry<K, V>, WeakKeyStrongValueSegment<K, V>> {
            private static final Helper<?, ?> INSTANCE = new Helper();

            Helper() {
            }

            static <K, V> Helper<K, V> instance() {
                return INSTANCE;
            }

            @Override
            public Strength keyStrength() {
                return Strength.WEAK;
            }

            @Override
            public Strength valueStrength() {
                return Strength.STRONG;
            }

            @Override
            public WeakKeyStrongValueSegment<K, V> newSegment(MapMakerInternalMap<K, V, WeakKeyStrongValueEntry<K, V>, WeakKeyStrongValueSegment<K, V>> mapMakerInternalMap, int n, int n2) {
                return new WeakKeyStrongValueSegment<K, V>(mapMakerInternalMap, n, n2);
            }

            @Override
            public WeakKeyStrongValueEntry<K, V> copy(WeakKeyStrongValueSegment<K, V> weakKeyStrongValueSegment, WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry, @Nullable WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry2) {
                if (weakKeyStrongValueEntry.getKey() == null) {
                    return null;
                }
                return weakKeyStrongValueEntry.copy(WeakKeyStrongValueSegment.access$200(weakKeyStrongValueSegment), weakKeyStrongValueEntry2);
            }

            @Override
            public void setValue(WeakKeyStrongValueSegment<K, V> weakKeyStrongValueSegment, WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry, V v) {
                weakKeyStrongValueEntry.setValue(v);
            }

            @Override
            public WeakKeyStrongValueEntry<K, V> newEntry(WeakKeyStrongValueSegment<K, V> weakKeyStrongValueSegment, K k, int n, @Nullable WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry) {
                return new WeakKeyStrongValueEntry<K, V>(WeakKeyStrongValueSegment.access$200(weakKeyStrongValueSegment), k, n, weakKeyStrongValueEntry);
            }

            @Override
            public void setValue(Segment segment, InternalEntry internalEntry, Object object) {
                this.setValue((WeakKeyStrongValueSegment)segment, (WeakKeyStrongValueEntry)internalEntry, object);
            }

            @Override
            public InternalEntry copy(Segment segment, InternalEntry internalEntry, @Nullable InternalEntry internalEntry2) {
                return this.copy((WeakKeyStrongValueSegment)segment, (WeakKeyStrongValueEntry)internalEntry, (WeakKeyStrongValueEntry)internalEntry2);
            }

            @Override
            public InternalEntry newEntry(Segment segment, Object object, int n, @Nullable InternalEntry internalEntry) {
                return this.newEntry((WeakKeyStrongValueSegment)segment, object, n, (WeakKeyStrongValueEntry)internalEntry);
            }

            @Override
            public Segment newSegment(MapMakerInternalMap mapMakerInternalMap, int n, int n2) {
                return this.newSegment(mapMakerInternalMap, n, n2);
            }
        }
    }

    static abstract class AbstractWeakKeyEntry<K, V, E extends InternalEntry<K, V, E>>
    extends WeakReference<K>
    implements InternalEntry<K, V, E> {
        final int hash;
        final E next;

        AbstractWeakKeyEntry(ReferenceQueue<K> referenceQueue, K k, int n, @Nullable E e) {
            super(k, referenceQueue);
            this.hash = n;
            this.next = e;
        }

        @Override
        public K getKey() {
            return (K)this.get();
        }

        @Override
        public int getHash() {
            return this.hash;
        }

        @Override
        public E getNext() {
            return this.next;
        }
    }

    static final class StrongKeyWeakValueEntry<K, V>
    extends AbstractStrongKeyEntry<K, V, StrongKeyWeakValueEntry<K, V>>
    implements WeakValueEntry<K, V, StrongKeyWeakValueEntry<K, V>> {
        private volatile WeakValueReference<K, V, StrongKeyWeakValueEntry<K, V>> valueReference = MapMakerInternalMap.unsetWeakValueReference();

        StrongKeyWeakValueEntry(K k, int n, @Nullable StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntry) {
            super(k, n, strongKeyWeakValueEntry);
        }

        @Override
        public V getValue() {
            return this.valueReference.get();
        }

        @Override
        public void clearValue() {
            this.valueReference.clear();
        }

        void setValue(V v, ReferenceQueue<V> referenceQueue) {
            WeakValueReference<K, V, StrongKeyWeakValueEntry<K, V>> weakValueReference = this.valueReference;
            this.valueReference = new WeakValueReferenceImpl(referenceQueue, v, this);
            weakValueReference.clear();
        }

        StrongKeyWeakValueEntry<K, V> copy(ReferenceQueue<V> referenceQueue, StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntry) {
            StrongKeyWeakValueEntry<Object, V> strongKeyWeakValueEntry2 = new StrongKeyWeakValueEntry<Object, V>(this.key, this.hash, strongKeyWeakValueEntry);
            strongKeyWeakValueEntry2.valueReference = this.valueReference.copyFor(referenceQueue, strongKeyWeakValueEntry2);
            return strongKeyWeakValueEntry2;
        }

        @Override
        public WeakValueReference<K, V, StrongKeyWeakValueEntry<K, V>> getValueReference() {
            return this.valueReference;
        }

        static WeakValueReference access$500(StrongKeyWeakValueEntry strongKeyWeakValueEntry) {
            return strongKeyWeakValueEntry.valueReference;
        }

        static WeakValueReference access$502(StrongKeyWeakValueEntry strongKeyWeakValueEntry, WeakValueReference weakValueReference) {
            strongKeyWeakValueEntry.valueReference = weakValueReference;
            return strongKeyWeakValueEntry.valueReference;
        }

        static final class Helper<K, V>
        implements InternalEntryHelper<K, V, StrongKeyWeakValueEntry<K, V>, StrongKeyWeakValueSegment<K, V>> {
            private static final Helper<?, ?> INSTANCE = new Helper();

            Helper() {
            }

            static <K, V> Helper<K, V> instance() {
                return INSTANCE;
            }

            @Override
            public Strength keyStrength() {
                return Strength.STRONG;
            }

            @Override
            public Strength valueStrength() {
                return Strength.WEAK;
            }

            @Override
            public StrongKeyWeakValueSegment<K, V> newSegment(MapMakerInternalMap<K, V, StrongKeyWeakValueEntry<K, V>, StrongKeyWeakValueSegment<K, V>> mapMakerInternalMap, int n, int n2) {
                return new StrongKeyWeakValueSegment<K, V>(mapMakerInternalMap, n, n2);
            }

            @Override
            public StrongKeyWeakValueEntry<K, V> copy(StrongKeyWeakValueSegment<K, V> strongKeyWeakValueSegment, StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntry, @Nullable StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntry2) {
                if (Segment.isCollected(strongKeyWeakValueEntry)) {
                    return null;
                }
                return strongKeyWeakValueEntry.copy(StrongKeyWeakValueSegment.access$100(strongKeyWeakValueSegment), strongKeyWeakValueEntry2);
            }

            @Override
            public void setValue(StrongKeyWeakValueSegment<K, V> strongKeyWeakValueSegment, StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntry, V v) {
                strongKeyWeakValueEntry.setValue(v, StrongKeyWeakValueSegment.access$100(strongKeyWeakValueSegment));
            }

            @Override
            public StrongKeyWeakValueEntry<K, V> newEntry(StrongKeyWeakValueSegment<K, V> strongKeyWeakValueSegment, K k, int n, @Nullable StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntry) {
                return new StrongKeyWeakValueEntry<K, V>(k, n, strongKeyWeakValueEntry);
            }

            @Override
            public void setValue(Segment segment, InternalEntry internalEntry, Object object) {
                this.setValue((StrongKeyWeakValueSegment)segment, (StrongKeyWeakValueEntry)internalEntry, object);
            }

            @Override
            public InternalEntry copy(Segment segment, InternalEntry internalEntry, @Nullable InternalEntry internalEntry2) {
                return this.copy((StrongKeyWeakValueSegment)segment, (StrongKeyWeakValueEntry)internalEntry, (StrongKeyWeakValueEntry)internalEntry2);
            }

            @Override
            public InternalEntry newEntry(Segment segment, Object object, int n, @Nullable InternalEntry internalEntry) {
                return this.newEntry((StrongKeyWeakValueSegment)segment, object, n, (StrongKeyWeakValueEntry)internalEntry);
            }

            @Override
            public Segment newSegment(MapMakerInternalMap mapMakerInternalMap, int n, int n2) {
                return this.newSegment(mapMakerInternalMap, n, n2);
            }
        }
    }

    static final class StrongKeyStrongValueEntry<K, V>
    extends AbstractStrongKeyEntry<K, V, StrongKeyStrongValueEntry<K, V>>
    implements StrongValueEntry<K, V, StrongKeyStrongValueEntry<K, V>> {
        @Nullable
        private volatile V value = null;

        StrongKeyStrongValueEntry(K k, int n, @Nullable StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntry) {
            super(k, n, strongKeyStrongValueEntry);
        }

        @Override
        @Nullable
        public V getValue() {
            return this.value;
        }

        void setValue(V v) {
            this.value = v;
        }

        StrongKeyStrongValueEntry<K, V> copy(StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntry) {
            StrongKeyStrongValueEntry<Object, V> strongKeyStrongValueEntry2 = new StrongKeyStrongValueEntry<Object, V>(this.key, this.hash, strongKeyStrongValueEntry);
            strongKeyStrongValueEntry2.value = this.value;
            return strongKeyStrongValueEntry2;
        }

        static final class Helper<K, V>
        implements InternalEntryHelper<K, V, StrongKeyStrongValueEntry<K, V>, StrongKeyStrongValueSegment<K, V>> {
            private static final Helper<?, ?> INSTANCE = new Helper();

            Helper() {
            }

            static <K, V> Helper<K, V> instance() {
                return INSTANCE;
            }

            @Override
            public Strength keyStrength() {
                return Strength.STRONG;
            }

            @Override
            public Strength valueStrength() {
                return Strength.STRONG;
            }

            @Override
            public StrongKeyStrongValueSegment<K, V> newSegment(MapMakerInternalMap<K, V, StrongKeyStrongValueEntry<K, V>, StrongKeyStrongValueSegment<K, V>> mapMakerInternalMap, int n, int n2) {
                return new StrongKeyStrongValueSegment<K, V>(mapMakerInternalMap, n, n2);
            }

            @Override
            public StrongKeyStrongValueEntry<K, V> copy(StrongKeyStrongValueSegment<K, V> strongKeyStrongValueSegment, StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntry, @Nullable StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntry2) {
                return strongKeyStrongValueEntry.copy(strongKeyStrongValueEntry2);
            }

            @Override
            public void setValue(StrongKeyStrongValueSegment<K, V> strongKeyStrongValueSegment, StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntry, V v) {
                strongKeyStrongValueEntry.setValue(v);
            }

            @Override
            public StrongKeyStrongValueEntry<K, V> newEntry(StrongKeyStrongValueSegment<K, V> strongKeyStrongValueSegment, K k, int n, @Nullable StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntry) {
                return new StrongKeyStrongValueEntry<K, V>(k, n, strongKeyStrongValueEntry);
            }

            @Override
            public void setValue(Segment segment, InternalEntry internalEntry, Object object) {
                this.setValue((StrongKeyStrongValueSegment)segment, (StrongKeyStrongValueEntry)internalEntry, object);
            }

            @Override
            public InternalEntry copy(Segment segment, InternalEntry internalEntry, @Nullable InternalEntry internalEntry2) {
                return this.copy((StrongKeyStrongValueSegment)segment, (StrongKeyStrongValueEntry)internalEntry, (StrongKeyStrongValueEntry)internalEntry2);
            }

            @Override
            public InternalEntry newEntry(Segment segment, Object object, int n, @Nullable InternalEntry internalEntry) {
                return this.newEntry((StrongKeyStrongValueSegment)segment, object, n, (StrongKeyStrongValueEntry)internalEntry);
            }

            @Override
            public Segment newSegment(MapMakerInternalMap mapMakerInternalMap, int n, int n2) {
                return this.newSegment(mapMakerInternalMap, n, n2);
            }
        }
    }

    static interface WeakValueEntry<K, V, E extends InternalEntry<K, V, E>>
    extends InternalEntry<K, V, E> {
        public WeakValueReference<K, V, E> getValueReference();

        public void clearValue();
    }

    static interface StrongValueEntry<K, V, E extends InternalEntry<K, V, E>>
    extends InternalEntry<K, V, E> {
    }

    static abstract class AbstractStrongKeyEntry<K, V, E extends InternalEntry<K, V, E>>
    implements InternalEntry<K, V, E> {
        final K key;
        final int hash;
        final E next;

        AbstractStrongKeyEntry(K k, int n, @Nullable E e) {
            this.key = k;
            this.hash = n;
            this.next = e;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public int getHash() {
            return this.hash;
        }

        @Override
        public E getNext() {
            return this.next;
        }
    }

    static interface InternalEntry<K, V, E extends InternalEntry<K, V, E>> {
        public E getNext();

        public int getHash();

        public K getKey();

        public V getValue();
    }

    static interface InternalEntryHelper<K, V, E extends InternalEntry<K, V, E>, S extends Segment<K, V, E, S>> {
        public Strength keyStrength();

        public Strength valueStrength();

        public S newSegment(MapMakerInternalMap<K, V, E, S> var1, int var2, int var3);

        public E newEntry(S var1, K var2, int var3, @Nullable E var4);

        public E copy(S var1, E var2, @Nullable E var3);

        public void setValue(S var1, E var2, V var3);
    }

    static enum Strength {
        STRONG{

            @Override
            Equivalence<Object> defaultEquivalence() {
                return Equivalence.equals();
            }
        }
        ,
        WEAK{

            @Override
            Equivalence<Object> defaultEquivalence() {
                return Equivalence.identity();
            }
        };


        private Strength() {
        }

        abstract Equivalence<Object> defaultEquivalence();

        Strength(1 var3_3) {
            this();
        }
    }
}

