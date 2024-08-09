/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Ticker;
import com.google.common.cache.AbstractCache;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.ForwardingCache;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.cache.Weigher;
import com.google.common.collect.AbstractSequentialIterator;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import com.google.common.util.concurrent.ExecutionError;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.common.util.concurrent.Uninterruptibles;
import com.google.j2objc.annotations.Weak;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractQueue;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

@GwtCompatible(emulated=true)
class LocalCache<K, V>
extends AbstractMap<K, V>
implements ConcurrentMap<K, V> {
    static final int MAXIMUM_CAPACITY = 0x40000000;
    static final int MAX_SEGMENTS = 65536;
    static final int CONTAINS_VALUE_RETRIES = 3;
    static final int DRAIN_THRESHOLD = 63;
    static final int DRAIN_MAX = 16;
    static final Logger logger = Logger.getLogger(LocalCache.class.getName());
    final int segmentMask;
    final int segmentShift;
    final Segment<K, V>[] segments;
    final int concurrencyLevel;
    final Equivalence<Object> keyEquivalence;
    final Equivalence<Object> valueEquivalence;
    final Strength keyStrength;
    final Strength valueStrength;
    final long maxWeight;
    final Weigher<K, V> weigher;
    final long expireAfterAccessNanos;
    final long expireAfterWriteNanos;
    final long refreshNanos;
    final Queue<RemovalNotification<K, V>> removalNotificationQueue;
    final RemovalListener<K, V> removalListener;
    final Ticker ticker;
    final EntryFactory entryFactory;
    final AbstractCache.StatsCounter globalStatsCounter;
    @Nullable
    final CacheLoader<? super K, V> defaultLoader;
    static final ValueReference<Object, Object> UNSET = new ValueReference<Object, Object>(){

        @Override
        public Object get() {
            return null;
        }

        @Override
        public int getWeight() {
            return 1;
        }

        @Override
        public ReferenceEntry<Object, Object> getEntry() {
            return null;
        }

        @Override
        public ValueReference<Object, Object> copyFor(ReferenceQueue<Object> referenceQueue, @Nullable Object object, ReferenceEntry<Object, Object> referenceEntry) {
            return this;
        }

        @Override
        public boolean isLoading() {
            return true;
        }

        @Override
        public boolean isActive() {
            return true;
        }

        @Override
        public Object waitForValue() {
            return null;
        }

        @Override
        public void notifyNewValue(Object object) {
        }
    };
    static final Queue<? extends Object> DISCARDING_QUEUE = new AbstractQueue<Object>(){

        @Override
        public boolean offer(Object object) {
            return false;
        }

        @Override
        public Object peek() {
            return null;
        }

        @Override
        public Object poll() {
            return null;
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public Iterator<Object> iterator() {
            return ImmutableSet.of().iterator();
        }
    };
    Set<K> keySet;
    Collection<V> values;
    Set<Map.Entry<K, V>> entrySet;

    LocalCache(CacheBuilder<? super K, ? super V> cacheBuilder, @Nullable CacheLoader<? super K, V> cacheLoader) {
        int n;
        int n2;
        this.concurrencyLevel = Math.min(cacheBuilder.getConcurrencyLevel(), 65536);
        this.keyStrength = cacheBuilder.getKeyStrength();
        this.valueStrength = cacheBuilder.getValueStrength();
        this.keyEquivalence = cacheBuilder.getKeyEquivalence();
        this.valueEquivalence = cacheBuilder.getValueEquivalence();
        this.maxWeight = cacheBuilder.getMaximumWeight();
        this.weigher = cacheBuilder.getWeigher();
        this.expireAfterAccessNanos = cacheBuilder.getExpireAfterAccessNanos();
        this.expireAfterWriteNanos = cacheBuilder.getExpireAfterWriteNanos();
        this.refreshNanos = cacheBuilder.getRefreshNanos();
        this.removalListener = cacheBuilder.getRemovalListener();
        this.removalNotificationQueue = this.removalListener == CacheBuilder.NullListener.INSTANCE ? LocalCache.discardingQueue() : new ConcurrentLinkedQueue();
        this.ticker = cacheBuilder.getTicker(this.recordsTime());
        this.entryFactory = EntryFactory.getFactory(this.keyStrength, this.usesAccessEntries(), this.usesWriteEntries());
        this.globalStatsCounter = cacheBuilder.getStatsCounterSupplier().get();
        this.defaultLoader = cacheLoader;
        int n3 = Math.min(cacheBuilder.getInitialCapacity(), 0x40000000);
        if (this.evictsBySize() && !this.customWeigher()) {
            n3 = Math.min(n3, (int)this.maxWeight);
        }
        int n4 = 0;
        for (n2 = 1; !(n2 >= this.concurrencyLevel || this.evictsBySize() && (long)(n2 * 20) > this.maxWeight); n2 <<= 1) {
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
        if (this.evictsBySize()) {
            long l = this.maxWeight / (long)n2 + 1L;
            long l2 = this.maxWeight % (long)n2;
            for (int i = 0; i < this.segments.length; ++i) {
                if ((long)i == l2) {
                    --l;
                }
                this.segments[i] = this.createSegment(n, l, cacheBuilder.getStatsCounterSupplier().get());
            }
        } else {
            for (int i = 0; i < this.segments.length; ++i) {
                this.segments[i] = this.createSegment(n, -1L, cacheBuilder.getStatsCounterSupplier().get());
            }
        }
    }

    boolean evictsBySize() {
        return this.maxWeight >= 0L;
    }

    boolean customWeigher() {
        return this.weigher != CacheBuilder.OneWeigher.INSTANCE;
    }

    boolean expires() {
        return this.expiresAfterWrite() || this.expiresAfterAccess();
    }

    boolean expiresAfterWrite() {
        return this.expireAfterWriteNanos > 0L;
    }

    boolean expiresAfterAccess() {
        return this.expireAfterAccessNanos > 0L;
    }

    boolean refreshes() {
        return this.refreshNanos > 0L;
    }

    boolean usesAccessQueue() {
        return this.expiresAfterAccess() || this.evictsBySize();
    }

    boolean usesWriteQueue() {
        return this.expiresAfterWrite();
    }

    boolean recordsWrite() {
        return this.expiresAfterWrite() || this.refreshes();
    }

    boolean recordsAccess() {
        return this.expiresAfterAccess();
    }

    boolean recordsTime() {
        return this.recordsWrite() || this.recordsAccess();
    }

    boolean usesWriteEntries() {
        return this.usesWriteQueue() || this.recordsWrite();
    }

    boolean usesAccessEntries() {
        return this.usesAccessQueue() || this.recordsAccess();
    }

    boolean usesKeyReferences() {
        return this.keyStrength != Strength.STRONG;
    }

    boolean usesValueReferences() {
        return this.valueStrength != Strength.STRONG;
    }

    static <K, V> ValueReference<K, V> unset() {
        return UNSET;
    }

    static <K, V> ReferenceEntry<K, V> nullEntry() {
        return NullEntry.INSTANCE;
    }

    static <E> Queue<E> discardingQueue() {
        return DISCARDING_QUEUE;
    }

    static int rehash(int n) {
        n += n << 15 ^ 0xFFFFCD7D;
        n ^= n >>> 10;
        n += n << 3;
        n ^= n >>> 6;
        n += (n << 2) + (n << 14);
        return n ^ n >>> 16;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @VisibleForTesting
    ReferenceEntry<K, V> newEntry(K k, int n, @Nullable ReferenceEntry<K, V> referenceEntry) {
        Segment<K, V> segment = this.segmentFor(n);
        segment.lock();
        try {
            ReferenceEntry<K, V> referenceEntry2 = segment.newEntry(k, n, referenceEntry);
            return referenceEntry2;
        } finally {
            segment.unlock();
        }
    }

    @VisibleForTesting
    ReferenceEntry<K, V> copyEntry(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
        int n = referenceEntry.getHash();
        return this.segmentFor(n).copyEntry(referenceEntry, referenceEntry2);
    }

    @VisibleForTesting
    ValueReference<K, V> newValueReference(ReferenceEntry<K, V> referenceEntry, V v, int n) {
        int n2 = referenceEntry.getHash();
        return this.valueStrength.referenceValue(this.segmentFor(n2), referenceEntry, Preconditions.checkNotNull(v), n);
    }

    int hash(@Nullable Object object) {
        int n = this.keyEquivalence.hash(object);
        return LocalCache.rehash(n);
    }

    void reclaimValue(ValueReference<K, V> valueReference) {
        ReferenceEntry<K, V> referenceEntry = valueReference.getEntry();
        int n = referenceEntry.getHash();
        this.segmentFor(n).reclaimValue(referenceEntry.getKey(), n, valueReference);
    }

    void reclaimKey(ReferenceEntry<K, V> referenceEntry) {
        int n = referenceEntry.getHash();
        this.segmentFor(n).reclaimKey(referenceEntry, n);
    }

    @VisibleForTesting
    boolean isLive(ReferenceEntry<K, V> referenceEntry, long l) {
        return this.segmentFor(referenceEntry.getHash()).getLiveValue(referenceEntry, l) != null;
    }

    Segment<K, V> segmentFor(int n) {
        return this.segments[n >>> this.segmentShift & this.segmentMask];
    }

    Segment<K, V> createSegment(int n, long l, AbstractCache.StatsCounter statsCounter) {
        return new Segment(this, n, l, statsCounter);
    }

    @Nullable
    V getLiveValue(ReferenceEntry<K, V> referenceEntry, long l) {
        if (referenceEntry.getKey() == null) {
            return null;
        }
        V v = referenceEntry.getValueReference().get();
        if (v == null) {
            return null;
        }
        if (this.isExpired(referenceEntry, l)) {
            return null;
        }
        return v;
    }

    boolean isExpired(ReferenceEntry<K, V> referenceEntry, long l) {
        Preconditions.checkNotNull(referenceEntry);
        if (this.expiresAfterAccess() && l - referenceEntry.getAccessTime() >= this.expireAfterAccessNanos) {
            return false;
        }
        return !this.expiresAfterWrite() || l - referenceEntry.getWriteTime() < this.expireAfterWriteNanos;
    }

    static <K, V> void connectAccessOrder(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
        referenceEntry.setNextInAccessQueue(referenceEntry2);
        referenceEntry2.setPreviousInAccessQueue(referenceEntry);
    }

    static <K, V> void nullifyAccessOrder(ReferenceEntry<K, V> referenceEntry) {
        ReferenceEntry<K, V> referenceEntry2 = LocalCache.nullEntry();
        referenceEntry.setNextInAccessQueue(referenceEntry2);
        referenceEntry.setPreviousInAccessQueue(referenceEntry2);
    }

    static <K, V> void connectWriteOrder(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
        referenceEntry.setNextInWriteQueue(referenceEntry2);
        referenceEntry2.setPreviousInWriteQueue(referenceEntry);
    }

    static <K, V> void nullifyWriteOrder(ReferenceEntry<K, V> referenceEntry) {
        ReferenceEntry<K, V> referenceEntry2 = LocalCache.nullEntry();
        referenceEntry.setNextInWriteQueue(referenceEntry2);
        referenceEntry.setPreviousInWriteQueue(referenceEntry2);
    }

    void processPendingNotifications() {
        RemovalNotification<K, V> removalNotification;
        while ((removalNotification = this.removalNotificationQueue.poll()) != null) {
            try {
                this.removalListener.onRemoval(removalNotification);
            } catch (Throwable throwable) {
                logger.log(Level.WARNING, "Exception thrown by removal listener", throwable);
            }
        }
    }

    final Segment<K, V>[] newSegmentArray(int n) {
        return new Segment[n];
    }

    public void cleanUp() {
        for (Segment<K, V> segment : this.segments) {
            segment.cleanUp();
        }
    }

    @Override
    public boolean isEmpty() {
        int n;
        long l = 0L;
        Segment<K, V>[] segmentArray = this.segments;
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

    long longSize() {
        Segment<K, V>[] segmentArray = this.segments;
        long l = 0L;
        for (int i = 0; i < segmentArray.length; ++i) {
            l += (long)Math.max(0, segmentArray[i].count);
        }
        return l;
    }

    @Override
    public int size() {
        return Ints.saturatedCast(this.longSize());
    }

    @Override
    @Nullable
    public V get(@Nullable Object object) {
        if (object == null) {
            return null;
        }
        int n = this.hash(object);
        return this.segmentFor(n).get(object, n);
    }

    @Nullable
    public V getIfPresent(Object object) {
        int n = this.hash(Preconditions.checkNotNull(object));
        V v = this.segmentFor(n).get(object, n);
        if (v == null) {
            this.globalStatsCounter.recordMisses(1);
        } else {
            this.globalStatsCounter.recordHits(1);
        }
        return v;
    }

    @Override
    @Nullable
    public V getOrDefault(@Nullable Object object, @Nullable V v) {
        V v2 = this.get(object);
        return v2 != null ? v2 : v;
    }

    V get(K k, CacheLoader<? super K, V> cacheLoader) throws ExecutionException {
        int n = this.hash(Preconditions.checkNotNull(k));
        return this.segmentFor(n).get((K)k, n, cacheLoader);
    }

    V getOrLoad(K k) throws ExecutionException {
        return this.get(k, this.defaultLoader);
    }

    ImmutableMap<K, V> getAllPresent(Iterable<?> iterable) {
        int n = 0;
        int n2 = 0;
        LinkedHashMap<?, V> linkedHashMap = Maps.newLinkedHashMap();
        for (Object obj : iterable) {
            V v = this.get(obj);
            if (v == null) {
                ++n2;
                continue;
            }
            Object obj2 = obj;
            linkedHashMap.put(obj2, v);
            ++n;
        }
        this.globalStatsCounter.recordHits(n);
        this.globalStatsCounter.recordMisses(n2);
        return ImmutableMap.copyOf(linkedHashMap);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    ImmutableMap<K, V> getAll(Iterable<? extends K> iterable) throws ExecutionException {
        int n = 0;
        int n2 = 0;
        LinkedHashMap<Object, V> linkedHashMap = Maps.newLinkedHashMap();
        LinkedHashSet<Object> linkedHashSet = Sets.newLinkedHashSet();
        for (Object object : iterable) {
            V object2 = this.get(object);
            if (linkedHashMap.containsKey(object)) continue;
            linkedHashMap.put(object, object2);
            if (object2 == null) {
                ++n2;
                linkedHashSet.add(object);
                continue;
            }
            ++n;
        }
        try {
            Map<K, V> map;
            if (!linkedHashSet.isEmpty()) {
                try {
                    map = this.loadAll(linkedHashSet, this.defaultLoader);
                    for (Object e : linkedHashSet) {
                        V v = map.get(e);
                        if (v == null) {
                            throw new CacheLoader.InvalidCacheLoadException("loadAll failed to return a value for " + e);
                        }
                        linkedHashMap.put(e, v);
                    }
                } catch (CacheLoader.UnsupportedLoadingOperationException unsupportedLoadingOperationException) {
                    for (Object e : linkedHashSet) {
                        --n2;
                        linkedHashMap.put(e, this.get(e, this.defaultLoader));
                    }
                }
            }
            map = ImmutableMap.copyOf(linkedHashMap);
            return map;
        } finally {
            this.globalStatsCounter.recordHits(n);
            this.globalStatsCounter.recordMisses(n2);
        }
    }

    @Nullable
    Map<K, V> loadAll(Set<? extends K> set, CacheLoader<? super K, V> cacheLoader) throws ExecutionException {
        Map<K, V> map;
        Preconditions.checkNotNull(cacheLoader);
        Preconditions.checkNotNull(set);
        Stopwatch stopwatch = Stopwatch.createStarted();
        boolean bl = false;
        try {
            Map<K, V> map2;
            map = map2 = cacheLoader.loadAll(set);
            bl = true;
        } catch (CacheLoader.UnsupportedLoadingOperationException unsupportedLoadingOperationException) {
            bl = true;
            throw unsupportedLoadingOperationException;
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            throw new ExecutionException(interruptedException);
        } catch (RuntimeException runtimeException) {
            throw new UncheckedExecutionException(runtimeException);
        } catch (Exception exception) {
            throw new ExecutionException(exception);
        } catch (Error error2) {
            throw new ExecutionError(error2);
        } finally {
            if (!bl) {
                this.globalStatsCounter.recordLoadException(stopwatch.elapsed(TimeUnit.NANOSECONDS));
            }
        }
        if (map == null) {
            this.globalStatsCounter.recordLoadException(stopwatch.elapsed(TimeUnit.NANOSECONDS));
            throw new CacheLoader.InvalidCacheLoadException(cacheLoader + " returned null map from loadAll");
        }
        stopwatch.stop();
        boolean bl2 = false;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            K k = entry.getKey();
            V v = entry.getValue();
            if (k == null || v == null) {
                bl2 = true;
                continue;
            }
            this.put(k, v);
        }
        if (bl2) {
            this.globalStatsCounter.recordLoadException(stopwatch.elapsed(TimeUnit.NANOSECONDS));
            throw new CacheLoader.InvalidCacheLoadException(cacheLoader + " returned null keys or values from loadAll");
        }
        this.globalStatsCounter.recordLoadSuccess(stopwatch.elapsed(TimeUnit.NANOSECONDS));
        return map;
    }

    ReferenceEntry<K, V> getEntry(@Nullable Object object) {
        if (object == null) {
            return null;
        }
        int n = this.hash(object);
        return this.segmentFor(n).getEntry(object, n);
    }

    void refresh(K k) {
        int n = this.hash(Preconditions.checkNotNull(k));
        this.segmentFor(n).refresh((K)k, n, this.defaultLoader, true);
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
        long l = this.ticker.read();
        Segment<K, V>[] segmentArray = this.segments;
        long l2 = -1L;
        for (int i = 0; i < 3; ++i) {
            long l3 = 0L;
            for (Segment segment : segmentArray) {
                int n = segment.count;
                AtomicReferenceArray atomicReferenceArray = segment.table;
                for (int j = 0; j < atomicReferenceArray.length(); ++j) {
                    for (ReferenceEntry referenceEntry = atomicReferenceArray.get(j); referenceEntry != null; referenceEntry = referenceEntry.getNext()) {
                        V v = segment.getLiveValue(referenceEntry, l);
                        if (v == null || !this.valueEquivalence.equivalent(object, v)) continue;
                        return false;
                    }
                }
                l3 += (long)segment.modCount;
            }
            if (l3 == l2) break;
            l2 = l3;
        }
        return true;
    }

    @Override
    public V put(K k, V v) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(v);
        int n = this.hash(k);
        return this.segmentFor(n).put(k, n, v, true);
    }

    @Override
    public V putIfAbsent(K k, V v) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(v);
        int n = this.hash(k);
        return this.segmentFor(n).put(k, n, v, false);
    }

    @Override
    public V compute(K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(biFunction);
        int n = this.hash(k);
        return this.segmentFor(n).compute((K)k, n, (BiFunction<? super K, ? extends V, ? extends V>)biFunction);
    }

    @Override
    public V computeIfAbsent(K k, java.util.function.Function<? super K, ? extends V> function) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(function);
        return (V)this.compute(k, (arg_0, arg_1) -> LocalCache.lambda$computeIfAbsent$0(function, k, arg_0, arg_1));
    }

    @Override
    public V computeIfPresent(K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(biFunction);
        return (V)this.compute(k, (arg_0, arg_1) -> LocalCache.lambda$computeIfPresent$1(biFunction, arg_0, arg_1));
    }

    @Override
    public V merge(K k, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(v);
        Preconditions.checkNotNull(biFunction);
        return (V)this.compute(k, (arg_0, arg_1) -> LocalCache.lambda$merge$2(v, biFunction, arg_0, arg_1));
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public V remove(@Nullable Object object) {
        if (object == null) {
            return null;
        }
        int n = this.hash(object);
        return this.segmentFor(n).remove(object, n);
    }

    @Override
    public boolean remove(@Nullable Object object, @Nullable Object object2) {
        if (object == null || object2 == null) {
            return true;
        }
        int n = this.hash(object);
        return this.segmentFor(n).remove(object, n, object2);
    }

    @Override
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
    public V replace(K k, V v) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(v);
        int n = this.hash(k);
        return this.segmentFor(n).replace(k, n, v);
    }

    @Override
    public void clear() {
        for (Segment<K, V> segment : this.segments) {
            segment.clear();
        }
    }

    void invalidateAll(Iterable<?> iterable) {
        for (Object obj : iterable) {
            this.remove(obj);
        }
    }

    @Override
    public Set<K> keySet() {
        KeySet keySet = this.keySet;
        return keySet != null ? keySet : (this.keySet = new KeySet(this, this));
    }

    @Override
    public Collection<V> values() {
        Values values2 = this.values;
        return values2 != null ? values2 : (this.values = new Values(this, this));
    }

    @Override
    @GwtIncompatible
    public Set<Map.Entry<K, V>> entrySet() {
        EntrySet entrySet = this.entrySet;
        return entrySet != null ? entrySet : (this.entrySet = new EntrySet(this, this));
    }

    private static <E> ArrayList<E> toArrayList(Collection<E> collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        Iterators.addAll(arrayList, collection.iterator());
        return arrayList;
    }

    boolean removeIf(BiPredicate<? super K, ? super V> biPredicate) {
        Preconditions.checkNotNull(biPredicate);
        boolean bl = false;
        block0: for (K k : this.keySet()) {
            V v;
            while ((v = this.get(k)) != null && biPredicate.test(k, v)) {
                if (!this.remove(k, v)) continue;
                bl = true;
                continue block0;
            }
        }
        return bl;
    }

    private static Object lambda$merge$2(Object object, BiFunction biFunction, Object object2, Object object3) {
        return object3 == null ? object : biFunction.apply(object3, object);
    }

    private static Object lambda$computeIfPresent$1(BiFunction biFunction, Object object, Object object2) {
        return object2 == null ? null : biFunction.apply(object, object2);
    }

    private static Object lambda$computeIfAbsent$0(java.util.function.Function function, Object object, Object object2, Object object3) {
        return object3 == null ? function.apply(object) : object3;
    }

    static ArrayList access$200(Collection collection) {
        return LocalCache.toArrayList(collection);
    }

    static class LocalLoadingCache<K, V>
    extends LocalManualCache<K, V>
    implements LoadingCache<K, V> {
        private static final long serialVersionUID = 1L;

        LocalLoadingCache(CacheBuilder<? super K, ? super V> cacheBuilder, CacheLoader<? super K, V> cacheLoader) {
            super(new LocalCache<K, V>(cacheBuilder, Preconditions.checkNotNull(cacheLoader)), null);
        }

        @Override
        public V get(K k) throws ExecutionException {
            return this.localCache.getOrLoad(k);
        }

        @Override
        public V getUnchecked(K k) {
            try {
                return this.get(k);
            } catch (ExecutionException executionException) {
                throw new UncheckedExecutionException(executionException.getCause());
            }
        }

        @Override
        public ImmutableMap<K, V> getAll(Iterable<? extends K> iterable) throws ExecutionException {
            return this.localCache.getAll(iterable);
        }

        @Override
        public void refresh(K k) {
            this.localCache.refresh(k);
        }

        @Override
        public final V apply(K k) {
            return this.getUnchecked(k);
        }

        @Override
        Object writeReplace() {
            return new LoadingSerializationProxy(this.localCache);
        }
    }

    static class LocalManualCache<K, V>
    implements Cache<K, V>,
    Serializable {
        final LocalCache<K, V> localCache;
        private static final long serialVersionUID = 1L;

        LocalManualCache(CacheBuilder<? super K, ? super V> cacheBuilder) {
            this(new LocalCache<K, V>(cacheBuilder, null));
        }

        private LocalManualCache(LocalCache<K, V> localCache) {
            this.localCache = localCache;
        }

        @Override
        @Nullable
        public V getIfPresent(Object object) {
            return this.localCache.getIfPresent(object);
        }

        @Override
        public V get(K k, Callable<? extends V> callable) throws ExecutionException {
            Preconditions.checkNotNull(callable);
            return this.localCache.get(k, new CacheLoader<Object, V>(this, callable){
                final Callable val$valueLoader;
                final LocalManualCache this$0;
                {
                    this.this$0 = localManualCache;
                    this.val$valueLoader = callable;
                }

                @Override
                public V load(Object object) throws Exception {
                    return this.val$valueLoader.call();
                }
            });
        }

        @Override
        public ImmutableMap<K, V> getAllPresent(Iterable<?> iterable) {
            return this.localCache.getAllPresent(iterable);
        }

        @Override
        public void put(K k, V v) {
            this.localCache.put(k, v);
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> map) {
            this.localCache.putAll(map);
        }

        @Override
        public void invalidate(Object object) {
            Preconditions.checkNotNull(object);
            this.localCache.remove(object);
        }

        @Override
        public void invalidateAll(Iterable<?> iterable) {
            this.localCache.invalidateAll(iterable);
        }

        @Override
        public void invalidateAll() {
            this.localCache.clear();
        }

        @Override
        public long size() {
            return this.localCache.longSize();
        }

        @Override
        public ConcurrentMap<K, V> asMap() {
            return this.localCache;
        }

        @Override
        public CacheStats stats() {
            AbstractCache.SimpleStatsCounter simpleStatsCounter = new AbstractCache.SimpleStatsCounter();
            simpleStatsCounter.incrementBy(this.localCache.globalStatsCounter);
            for (Segment segment : this.localCache.segments) {
                simpleStatsCounter.incrementBy(segment.statsCounter);
            }
            return simpleStatsCounter.snapshot();
        }

        @Override
        public void cleanUp() {
            this.localCache.cleanUp();
        }

        Object writeReplace() {
            return new ManualSerializationProxy<K, V>(this.localCache);
        }

        LocalManualCache(LocalCache localCache, 1 var2_2) {
            this(localCache);
        }
    }

    static final class LoadingSerializationProxy<K, V>
    extends ManualSerializationProxy<K, V>
    implements LoadingCache<K, V>,
    Serializable {
        private static final long serialVersionUID = 1L;
        transient LoadingCache<K, V> autoDelegate;

        LoadingSerializationProxy(LocalCache<K, V> localCache) {
            super(localCache);
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            CacheBuilder cacheBuilder = this.recreateCacheBuilder();
            this.autoDelegate = cacheBuilder.build(this.loader);
        }

        @Override
        public V get(K k) throws ExecutionException {
            return this.autoDelegate.get(k);
        }

        @Override
        public V getUnchecked(K k) {
            return this.autoDelegate.getUnchecked(k);
        }

        @Override
        public ImmutableMap<K, V> getAll(Iterable<? extends K> iterable) throws ExecutionException {
            return this.autoDelegate.getAll(iterable);
        }

        @Override
        public final V apply(K k) {
            return this.autoDelegate.apply(k);
        }

        @Override
        public void refresh(K k) {
            this.autoDelegate.refresh(k);
        }

        private Object readResolve() {
            return this.autoDelegate;
        }
    }

    static class ManualSerializationProxy<K, V>
    extends ForwardingCache<K, V>
    implements Serializable {
        private static final long serialVersionUID = 1L;
        final Strength keyStrength;
        final Strength valueStrength;
        final Equivalence<Object> keyEquivalence;
        final Equivalence<Object> valueEquivalence;
        final long expireAfterWriteNanos;
        final long expireAfterAccessNanos;
        final long maxWeight;
        final Weigher<K, V> weigher;
        final int concurrencyLevel;
        final RemovalListener<? super K, ? super V> removalListener;
        final Ticker ticker;
        final CacheLoader<? super K, V> loader;
        transient Cache<K, V> delegate;

        ManualSerializationProxy(LocalCache<K, V> localCache) {
            this(localCache.keyStrength, localCache.valueStrength, localCache.keyEquivalence, localCache.valueEquivalence, localCache.expireAfterWriteNanos, localCache.expireAfterAccessNanos, localCache.maxWeight, localCache.weigher, localCache.concurrencyLevel, localCache.removalListener, localCache.ticker, localCache.defaultLoader);
        }

        private ManualSerializationProxy(Strength strength, Strength strength2, Equivalence<Object> equivalence, Equivalence<Object> equivalence2, long l, long l2, long l3, Weigher<K, V> weigher, int n, RemovalListener<? super K, ? super V> removalListener, Ticker ticker, CacheLoader<? super K, V> cacheLoader) {
            this.keyStrength = strength;
            this.valueStrength = strength2;
            this.keyEquivalence = equivalence;
            this.valueEquivalence = equivalence2;
            this.expireAfterWriteNanos = l;
            this.expireAfterAccessNanos = l2;
            this.maxWeight = l3;
            this.weigher = weigher;
            this.concurrencyLevel = n;
            this.removalListener = removalListener;
            this.ticker = ticker == Ticker.systemTicker() || ticker == CacheBuilder.NULL_TICKER ? null : ticker;
            this.loader = cacheLoader;
        }

        CacheBuilder<K, V> recreateCacheBuilder() {
            CacheBuilder<K, V> cacheBuilder = CacheBuilder.newBuilder().setKeyStrength(this.keyStrength).setValueStrength(this.valueStrength).keyEquivalence(this.keyEquivalence).valueEquivalence(this.valueEquivalence).concurrencyLevel(this.concurrencyLevel).removalListener(this.removalListener);
            cacheBuilder.strictParsing = false;
            if (this.expireAfterWriteNanos > 0L) {
                cacheBuilder.expireAfterWrite(this.expireAfterWriteNanos, TimeUnit.NANOSECONDS);
            }
            if (this.expireAfterAccessNanos > 0L) {
                cacheBuilder.expireAfterAccess(this.expireAfterAccessNanos, TimeUnit.NANOSECONDS);
            }
            if (this.weigher != CacheBuilder.OneWeigher.INSTANCE) {
                cacheBuilder.weigher(this.weigher);
                if (this.maxWeight != -1L) {
                    cacheBuilder.maximumWeight(this.maxWeight);
                }
            } else if (this.maxWeight != -1L) {
                cacheBuilder.maximumSize(this.maxWeight);
            }
            if (this.ticker != null) {
                cacheBuilder.ticker(this.ticker);
            }
            return cacheBuilder;
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            CacheBuilder<K, V> cacheBuilder = this.recreateCacheBuilder();
            this.delegate = cacheBuilder.build();
        }

        private Object readResolve() {
            return this.delegate;
        }

        @Override
        protected Cache<K, V> delegate() {
            return this.delegate;
        }

        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }

    final class EntrySet
    extends AbstractCacheSet<Map.Entry<K, V>> {
        final LocalCache this$0;

        EntrySet(LocalCache localCache, ConcurrentMap<?, ?> concurrentMap) {
            this.this$0 = localCache;
            super(localCache, concurrentMap);
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator(this.this$0);
        }

        @Override
        public boolean removeIf(Predicate<? super Map.Entry<K, V>> predicate) {
            Preconditions.checkNotNull(predicate);
            return this.this$0.removeIf((arg_0, arg_1) -> EntrySet.lambda$removeIf$0(predicate, arg_0, arg_1));
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
            return v != null && this.this$0.valueEquivalence.equivalent(entry.getValue(), v);
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

        private static boolean lambda$removeIf$0(Predicate predicate, Object object, Object object2) {
            return predicate.test(Maps.immutableEntry(object, object2));
        }
    }

    final class Values
    extends AbstractCollection<V> {
        private final ConcurrentMap<?, ?> map;
        final LocalCache this$0;

        Values(LocalCache localCache, ConcurrentMap<?, ?> concurrentMap) {
            this.this$0 = localCache;
            this.map = concurrentMap;
        }

        @Override
        public int size() {
            return this.map.size();
        }

        @Override
        public boolean isEmpty() {
            return this.map.isEmpty();
        }

        @Override
        public void clear() {
            this.map.clear();
        }

        @Override
        public Iterator<V> iterator() {
            return new ValueIterator(this.this$0);
        }

        @Override
        public boolean removeIf(Predicate<? super V> predicate) {
            Preconditions.checkNotNull(predicate);
            return this.this$0.removeIf((arg_0, arg_1) -> Values.lambda$removeIf$0(predicate, arg_0, arg_1));
        }

        @Override
        public boolean contains(Object object) {
            return this.map.containsValue(object);
        }

        @Override
        public Object[] toArray() {
            return LocalCache.access$200(this).toArray();
        }

        @Override
        public <E> E[] toArray(E[] EArray) {
            return LocalCache.access$200(this).toArray(EArray);
        }

        private static boolean lambda$removeIf$0(Predicate predicate, Object object, Object object2) {
            return predicate.test(object2);
        }
    }

    final class KeySet
    extends AbstractCacheSet<K> {
        final LocalCache this$0;

        KeySet(LocalCache localCache, ConcurrentMap<?, ?> concurrentMap) {
            this.this$0 = localCache;
            super(localCache, concurrentMap);
        }

        @Override
        public Iterator<K> iterator() {
            return new KeyIterator(this.this$0);
        }

        @Override
        public boolean contains(Object object) {
            return this.map.containsKey(object);
        }

        @Override
        public boolean remove(Object object) {
            return this.map.remove(object) != null;
        }
    }

    abstract class AbstractCacheSet<T>
    extends AbstractSet<T> {
        @Weak
        final ConcurrentMap<?, ?> map;
        final LocalCache this$0;

        AbstractCacheSet(LocalCache localCache, ConcurrentMap<?, ?> concurrentMap) {
            this.this$0 = localCache;
            this.map = concurrentMap;
        }

        @Override
        public int size() {
            return this.map.size();
        }

        @Override
        public boolean isEmpty() {
            return this.map.isEmpty();
        }

        @Override
        public void clear() {
            this.map.clear();
        }

        @Override
        public Object[] toArray() {
            return LocalCache.access$200(this).toArray();
        }

        @Override
        public <E> E[] toArray(E[] EArray) {
            return LocalCache.access$200(this).toArray(EArray);
        }
    }

    final class EntryIterator
    extends HashIterator<Map.Entry<K, V>> {
        final LocalCache this$0;

        EntryIterator(LocalCache localCache) {
            this.this$0 = localCache;
            super(localCache);
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
    implements Map.Entry<K, V> {
        final K key;
        V value;
        final LocalCache this$0;

        WriteThroughEntry(LocalCache localCache, K k, V v) {
            this.this$0 = localCache;
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

        public String toString() {
            return this.getKey() + "=" + this.getValue();
        }
    }

    final class ValueIterator
    extends HashIterator<V> {
        final LocalCache this$0;

        ValueIterator(LocalCache localCache) {
            this.this$0 = localCache;
            super(localCache);
        }

        @Override
        public V next() {
            return this.nextEntry().getValue();
        }
    }

    final class KeyIterator
    extends HashIterator<K> {
        final LocalCache this$0;

        KeyIterator(LocalCache localCache) {
            this.this$0 = localCache;
            super(localCache);
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
        Segment<K, V> currentSegment;
        AtomicReferenceArray<ReferenceEntry<K, V>> currentTable;
        ReferenceEntry<K, V> nextEntry;
        WriteThroughEntry nextExternal;
        WriteThroughEntry lastReturned;
        final LocalCache this$0;

        HashIterator(LocalCache localCache) {
            this.this$0 = localCache;
            this.nextSegmentIndex = localCache.segments.length - 1;
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
                if ((this.nextEntry = this.currentTable.get(this.nextTableIndex--)) == null || !this.advanceTo(this.nextEntry) && !this.nextInChain()) continue;
                return false;
            }
            return true;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        boolean advanceTo(ReferenceEntry<K, V> referenceEntry) {
            try {
                long l = this.this$0.ticker.read();
                Object k = referenceEntry.getKey();
                Object v = this.this$0.getLiveValue(referenceEntry, l);
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
            Preconditions.checkState(this.lastReturned != null);
            this.this$0.remove(this.lastReturned.getKey());
            this.lastReturned = null;
        }
    }

    static final class AccessQueue<K, V>
    extends AbstractQueue<ReferenceEntry<K, V>> {
        final ReferenceEntry<K, V> head = new AbstractReferenceEntry<K, V>(this){
            ReferenceEntry<K, V> nextAccess;
            ReferenceEntry<K, V> previousAccess;
            final AccessQueue this$0;
            {
                this.this$0 = accessQueue;
                this.nextAccess = this;
                this.previousAccess = this;
            }

            @Override
            public long getAccessTime() {
                return Long.MAX_VALUE;
            }

            @Override
            public void setAccessTime(long l) {
            }

            @Override
            public ReferenceEntry<K, V> getNextInAccessQueue() {
                return this.nextAccess;
            }

            @Override
            public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
                this.nextAccess = referenceEntry;
            }

            @Override
            public ReferenceEntry<K, V> getPreviousInAccessQueue() {
                return this.previousAccess;
            }

            @Override
            public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
                this.previousAccess = referenceEntry;
            }
        };

        AccessQueue() {
        }

        @Override
        public boolean offer(ReferenceEntry<K, V> referenceEntry) {
            LocalCache.connectAccessOrder(referenceEntry.getPreviousInAccessQueue(), referenceEntry.getNextInAccessQueue());
            LocalCache.connectAccessOrder(this.head.getPreviousInAccessQueue(), referenceEntry);
            LocalCache.connectAccessOrder(referenceEntry, this.head);
            return false;
        }

        @Override
        public ReferenceEntry<K, V> peek() {
            ReferenceEntry<K, V> referenceEntry = this.head.getNextInAccessQueue();
            return referenceEntry == this.head ? null : referenceEntry;
        }

        @Override
        public ReferenceEntry<K, V> poll() {
            ReferenceEntry<K, V> referenceEntry = this.head.getNextInAccessQueue();
            if (referenceEntry == this.head) {
                return null;
            }
            this.remove(referenceEntry);
            return referenceEntry;
        }

        @Override
        public boolean remove(Object object) {
            ReferenceEntry referenceEntry = (ReferenceEntry)object;
            ReferenceEntry referenceEntry2 = referenceEntry.getPreviousInAccessQueue();
            ReferenceEntry referenceEntry3 = referenceEntry.getNextInAccessQueue();
            LocalCache.connectAccessOrder(referenceEntry2, referenceEntry3);
            LocalCache.nullifyAccessOrder(referenceEntry);
            return referenceEntry3 != NullEntry.INSTANCE;
        }

        @Override
        public boolean contains(Object object) {
            ReferenceEntry referenceEntry = (ReferenceEntry)object;
            return referenceEntry.getNextInAccessQueue() != NullEntry.INSTANCE;
        }

        @Override
        public boolean isEmpty() {
            return this.head.getNextInAccessQueue() == this.head;
        }

        @Override
        public int size() {
            int n = 0;
            for (ReferenceEntry<K, V> referenceEntry = this.head.getNextInAccessQueue(); referenceEntry != this.head; referenceEntry = referenceEntry.getNextInAccessQueue()) {
                ++n;
            }
            return n;
        }

        @Override
        public void clear() {
            ReferenceEntry<K, V> referenceEntry = this.head.getNextInAccessQueue();
            while (referenceEntry != this.head) {
                ReferenceEntry<K, V> referenceEntry2 = referenceEntry.getNextInAccessQueue();
                LocalCache.nullifyAccessOrder(referenceEntry);
                referenceEntry = referenceEntry2;
            }
            this.head.setNextInAccessQueue(this.head);
            this.head.setPreviousInAccessQueue(this.head);
        }

        @Override
        public Iterator<ReferenceEntry<K, V>> iterator() {
            return new AbstractSequentialIterator<ReferenceEntry<K, V>>(this, (ReferenceEntry)this.peek()){
                final AccessQueue this$0;
                {
                    this.this$0 = accessQueue;
                    super(referenceEntry);
                }

                @Override
                protected ReferenceEntry<K, V> computeNext(ReferenceEntry<K, V> referenceEntry) {
                    ReferenceEntry referenceEntry2 = referenceEntry.getNextInAccessQueue();
                    return referenceEntry2 == this.this$0.head ? null : referenceEntry2;
                }

                @Override
                protected Object computeNext(Object object) {
                    return this.computeNext((ReferenceEntry)object);
                }
            };
        }

        @Override
        public Object peek() {
            return this.peek();
        }

        @Override
        public Object poll() {
            return this.poll();
        }

        @Override
        public boolean offer(Object object) {
            return this.offer((ReferenceEntry)object);
        }
    }

    static final class WriteQueue<K, V>
    extends AbstractQueue<ReferenceEntry<K, V>> {
        final ReferenceEntry<K, V> head = new AbstractReferenceEntry<K, V>(this){
            ReferenceEntry<K, V> nextWrite;
            ReferenceEntry<K, V> previousWrite;
            final WriteQueue this$0;
            {
                this.this$0 = writeQueue;
                this.nextWrite = this;
                this.previousWrite = this;
            }

            @Override
            public long getWriteTime() {
                return Long.MAX_VALUE;
            }

            @Override
            public void setWriteTime(long l) {
            }

            @Override
            public ReferenceEntry<K, V> getNextInWriteQueue() {
                return this.nextWrite;
            }

            @Override
            public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
                this.nextWrite = referenceEntry;
            }

            @Override
            public ReferenceEntry<K, V> getPreviousInWriteQueue() {
                return this.previousWrite;
            }

            @Override
            public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
                this.previousWrite = referenceEntry;
            }
        };

        WriteQueue() {
        }

        @Override
        public boolean offer(ReferenceEntry<K, V> referenceEntry) {
            LocalCache.connectWriteOrder(referenceEntry.getPreviousInWriteQueue(), referenceEntry.getNextInWriteQueue());
            LocalCache.connectWriteOrder(this.head.getPreviousInWriteQueue(), referenceEntry);
            LocalCache.connectWriteOrder(referenceEntry, this.head);
            return false;
        }

        @Override
        public ReferenceEntry<K, V> peek() {
            ReferenceEntry<K, V> referenceEntry = this.head.getNextInWriteQueue();
            return referenceEntry == this.head ? null : referenceEntry;
        }

        @Override
        public ReferenceEntry<K, V> poll() {
            ReferenceEntry<K, V> referenceEntry = this.head.getNextInWriteQueue();
            if (referenceEntry == this.head) {
                return null;
            }
            this.remove(referenceEntry);
            return referenceEntry;
        }

        @Override
        public boolean remove(Object object) {
            ReferenceEntry referenceEntry = (ReferenceEntry)object;
            ReferenceEntry referenceEntry2 = referenceEntry.getPreviousInWriteQueue();
            ReferenceEntry referenceEntry3 = referenceEntry.getNextInWriteQueue();
            LocalCache.connectWriteOrder(referenceEntry2, referenceEntry3);
            LocalCache.nullifyWriteOrder(referenceEntry);
            return referenceEntry3 != NullEntry.INSTANCE;
        }

        @Override
        public boolean contains(Object object) {
            ReferenceEntry referenceEntry = (ReferenceEntry)object;
            return referenceEntry.getNextInWriteQueue() != NullEntry.INSTANCE;
        }

        @Override
        public boolean isEmpty() {
            return this.head.getNextInWriteQueue() == this.head;
        }

        @Override
        public int size() {
            int n = 0;
            for (ReferenceEntry<K, V> referenceEntry = this.head.getNextInWriteQueue(); referenceEntry != this.head; referenceEntry = referenceEntry.getNextInWriteQueue()) {
                ++n;
            }
            return n;
        }

        @Override
        public void clear() {
            ReferenceEntry<K, V> referenceEntry = this.head.getNextInWriteQueue();
            while (referenceEntry != this.head) {
                ReferenceEntry<K, V> referenceEntry2 = referenceEntry.getNextInWriteQueue();
                LocalCache.nullifyWriteOrder(referenceEntry);
                referenceEntry = referenceEntry2;
            }
            this.head.setNextInWriteQueue(this.head);
            this.head.setPreviousInWriteQueue(this.head);
        }

        @Override
        public Iterator<ReferenceEntry<K, V>> iterator() {
            return new AbstractSequentialIterator<ReferenceEntry<K, V>>(this, (ReferenceEntry)this.peek()){
                final WriteQueue this$0;
                {
                    this.this$0 = writeQueue;
                    super(referenceEntry);
                }

                @Override
                protected ReferenceEntry<K, V> computeNext(ReferenceEntry<K, V> referenceEntry) {
                    ReferenceEntry referenceEntry2 = referenceEntry.getNextInWriteQueue();
                    return referenceEntry2 == this.this$0.head ? null : referenceEntry2;
                }

                @Override
                protected Object computeNext(Object object) {
                    return this.computeNext((ReferenceEntry)object);
                }
            };
        }

        @Override
        public Object peek() {
            return this.peek();
        }

        @Override
        public Object poll() {
            return this.poll();
        }

        @Override
        public boolean offer(Object object) {
            return this.offer((ReferenceEntry)object);
        }
    }

    static class LoadingValueReference<K, V>
    implements ValueReference<K, V> {
        volatile ValueReference<K, V> oldValue;
        final SettableFuture<V> futureValue = SettableFuture.create();
        final Stopwatch stopwatch = Stopwatch.createUnstarted();

        public LoadingValueReference() {
            this(null);
        }

        public LoadingValueReference(ValueReference<K, V> valueReference) {
            this.oldValue = valueReference == null ? LocalCache.unset() : valueReference;
        }

        @Override
        public boolean isLoading() {
            return false;
        }

        @Override
        public boolean isActive() {
            return this.oldValue.isActive();
        }

        @Override
        public int getWeight() {
            return this.oldValue.getWeight();
        }

        public boolean set(@Nullable V v) {
            return this.futureValue.set(v);
        }

        public boolean setException(Throwable throwable) {
            return this.futureValue.setException(throwable);
        }

        private ListenableFuture<V> fullyFailedFuture(Throwable throwable) {
            return Futures.immediateFailedFuture(throwable);
        }

        @Override
        public void notifyNewValue(@Nullable V v) {
            if (v != null) {
                this.set(v);
            } else {
                this.oldValue = LocalCache.unset();
            }
        }

        public ListenableFuture<V> loadFuture(K k, CacheLoader<? super K, V> cacheLoader) {
            try {
                this.stopwatch.start();
                V v = this.oldValue.get();
                if (v == null) {
                    V v2 = cacheLoader.load(k);
                    return this.set(v2) ? this.futureValue : Futures.immediateFuture(v2);
                }
                ListenableFuture<V> listenableFuture = cacheLoader.reload(k, v);
                if (listenableFuture == null) {
                    return Futures.immediateFuture(null);
                }
                return Futures.transform(listenableFuture, new Function<V, V>(this){
                    final LoadingValueReference this$0;
                    {
                        this.this$0 = loadingValueReference;
                    }

                    @Override
                    public V apply(V v) {
                        this.this$0.set(v);
                        return v;
                    }
                });
            } catch (Throwable throwable) {
                ListenableFuture<V> listenableFuture;
                ListenableFuture<V> listenableFuture2 = listenableFuture = this.setException(throwable) ? this.futureValue : this.fullyFailedFuture(throwable);
                if (throwable instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
                return listenableFuture;
            }
        }

        public V compute(K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {
            V v;
            this.stopwatch.start();
            try {
                v = this.oldValue.waitForValue();
            } catch (ExecutionException executionException) {
                v = null;
            }
            V v2 = biFunction.apply(k, v);
            this.set(v2);
            return v2;
        }

        public long elapsedNanos() {
            return this.stopwatch.elapsed(TimeUnit.NANOSECONDS);
        }

        @Override
        public V waitForValue() throws ExecutionException {
            return Uninterruptibles.getUninterruptibly(this.futureValue);
        }

        @Override
        public V get() {
            return this.oldValue.get();
        }

        public ValueReference<K, V> getOldValue() {
            return this.oldValue;
        }

        @Override
        public ReferenceEntry<K, V> getEntry() {
            return null;
        }

        @Override
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, @Nullable V v, ReferenceEntry<K, V> referenceEntry) {
            return this;
        }
    }

    static class Segment<K, V>
    extends ReentrantLock {
        @Weak
        final LocalCache<K, V> map;
        volatile int count;
        @GuardedBy(value="this")
        long totalWeight;
        int modCount;
        int threshold;
        volatile AtomicReferenceArray<ReferenceEntry<K, V>> table;
        final long maxSegmentWeight;
        final ReferenceQueue<K> keyReferenceQueue;
        final ReferenceQueue<V> valueReferenceQueue;
        final Queue<ReferenceEntry<K, V>> recencyQueue;
        final AtomicInteger readCount = new AtomicInteger();
        @GuardedBy(value="this")
        final Queue<ReferenceEntry<K, V>> writeQueue;
        @GuardedBy(value="this")
        final Queue<ReferenceEntry<K, V>> accessQueue;
        final AbstractCache.StatsCounter statsCounter;

        Segment(LocalCache<K, V> localCache, int n, long l, AbstractCache.StatsCounter statsCounter) {
            this.map = localCache;
            this.maxSegmentWeight = l;
            this.statsCounter = Preconditions.checkNotNull(statsCounter);
            this.initTable(this.newEntryArray(n));
            this.keyReferenceQueue = localCache.usesKeyReferences() ? new ReferenceQueue() : null;
            this.valueReferenceQueue = localCache.usesValueReferences() ? new ReferenceQueue() : null;
            this.recencyQueue = localCache.usesAccessQueue() ? new ConcurrentLinkedQueue() : LocalCache.discardingQueue();
            this.writeQueue = localCache.usesWriteQueue() ? new WriteQueue() : LocalCache.discardingQueue();
            this.accessQueue = localCache.usesAccessQueue() ? new AccessQueue() : LocalCache.discardingQueue();
        }

        AtomicReferenceArray<ReferenceEntry<K, V>> newEntryArray(int n) {
            return new AtomicReferenceArray<ReferenceEntry<K, V>>(n);
        }

        void initTable(AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray) {
            this.threshold = atomicReferenceArray.length() * 3 / 4;
            if (!this.map.customWeigher() && (long)this.threshold == this.maxSegmentWeight) {
                ++this.threshold;
            }
            this.table = atomicReferenceArray;
        }

        @GuardedBy(value="this")
        ReferenceEntry<K, V> newEntry(K k, int n, @Nullable ReferenceEntry<K, V> referenceEntry) {
            return this.map.entryFactory.newEntry(this, Preconditions.checkNotNull(k), n, referenceEntry);
        }

        @GuardedBy(value="this")
        ReferenceEntry<K, V> copyEntry(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            if (referenceEntry.getKey() == null) {
                return null;
            }
            ValueReference<K, V> valueReference = referenceEntry.getValueReference();
            V v = valueReference.get();
            if (v == null && valueReference.isActive()) {
                return null;
            }
            ReferenceEntry<K, V> referenceEntry3 = this.map.entryFactory.copyEntry(this, referenceEntry, referenceEntry2);
            referenceEntry3.setValueReference(valueReference.copyFor(this.valueReferenceQueue, v, referenceEntry3));
            return referenceEntry3;
        }

        @GuardedBy(value="this")
        void setValue(ReferenceEntry<K, V> referenceEntry, K k, V v, long l) {
            ValueReference<K, V> valueReference = referenceEntry.getValueReference();
            int n = this.map.weigher.weigh(k, v);
            Preconditions.checkState(n >= 0, "Weights must be non-negative");
            ValueReference<K, V> valueReference2 = this.map.valueStrength.referenceValue(this, referenceEntry, v, n);
            referenceEntry.setValueReference(valueReference2);
            this.recordWrite(referenceEntry, n, l);
            valueReference.notifyNewValue(v);
        }

        V get(K k, int n, CacheLoader<? super K, V> cacheLoader) throws ExecutionException {
            Preconditions.checkNotNull(k);
            Preconditions.checkNotNull(cacheLoader);
            try {
                ReferenceEntry<K, V> referenceEntry;
                if (this.count != 0 && (referenceEntry = this.getEntry(k, n)) != null) {
                    long l = this.map.ticker.read();
                    V v = this.getLiveValue(referenceEntry, l);
                    if (v != null) {
                        this.recordRead(referenceEntry, l);
                        this.statsCounter.recordHits(1);
                        V v2 = this.scheduleRefresh(referenceEntry, k, n, v, l, cacheLoader);
                        return v2;
                    }
                    ValueReference<K, V> valueReference = referenceEntry.getValueReference();
                    if (valueReference.isLoading()) {
                        V v3 = this.waitForLoadingValue(referenceEntry, k, valueReference);
                        return v3;
                    }
                }
                referenceEntry = this.lockedGetOrLoad(k, n, cacheLoader);
                return (V)referenceEntry;
            } catch (ExecutionException executionException) {
                Throwable throwable = executionException.getCause();
                if (throwable instanceof Error) {
                    throw new ExecutionError((Error)throwable);
                }
                if (throwable instanceof RuntimeException) {
                    throw new UncheckedExecutionException(throwable);
                }
                throw executionException;
            } finally {
                this.postReadCleanup();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        V lockedGetOrLoad(K k, int n, CacheLoader<? super K, V> cacheLoader) throws ExecutionException {
            ReferenceEntry referenceEntry;
            ValueReference<K, V> valueReference = null;
            LoadingValueReference loadingValueReference = null;
            boolean bl = true;
            this.lock();
            try {
                ReferenceEntry referenceEntry2;
                long l = this.map.ticker.read();
                this.preWriteCleanup(l);
                int n2 = this.count - 1;
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int n3 = n & atomicReferenceArray.length() - 1;
                for (referenceEntry = referenceEntry2 = atomicReferenceArray.get(n3); referenceEntry != null; referenceEntry = referenceEntry.getNext()) {
                    K k2 = referenceEntry.getKey();
                    if (referenceEntry.getHash() != n || k2 == null || !this.map.keyEquivalence.equivalent(k, k2)) continue;
                    valueReference = referenceEntry.getValueReference();
                    if (valueReference.isLoading()) {
                        bl = false;
                        break;
                    }
                    V v = valueReference.get();
                    if (v == null) {
                        this.enqueueNotification(k2, n, v, valueReference.getWeight(), RemovalCause.COLLECTED);
                    } else if (this.map.isExpired(referenceEntry, l)) {
                        this.enqueueNotification(k2, n, v, valueReference.getWeight(), RemovalCause.EXPIRED);
                    } else {
                        this.recordLockedRead(referenceEntry, l);
                        this.statsCounter.recordHits(1);
                        V v2 = v;
                        return v2;
                    }
                    this.writeQueue.remove(referenceEntry);
                    this.accessQueue.remove(referenceEntry);
                    this.count = n2;
                    break;
                }
                if (bl) {
                    loadingValueReference = new LoadingValueReference();
                    if (referenceEntry == null) {
                        referenceEntry = this.newEntry(k, n, referenceEntry2);
                        referenceEntry.setValueReference(loadingValueReference);
                        atomicReferenceArray.set(n3, referenceEntry);
                    } else {
                        referenceEntry.setValueReference(loadingValueReference);
                    }
                }
            } finally {
                this.unlock();
                this.postWriteCleanup();
            }
            if (bl) {
                try {
                    ReferenceEntry referenceEntry3 = referenceEntry;
                    synchronized (referenceEntry3) {
                        Object v = this.loadSync(k, n, loadingValueReference, cacheLoader);
                        return v;
                    }
                } finally {
                    this.statsCounter.recordMisses(1);
                }
            }
            return this.waitForLoadingValue(referenceEntry, k, valueReference);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        V waitForLoadingValue(ReferenceEntry<K, V> referenceEntry, K k, ValueReference<K, V> valueReference) throws ExecutionException {
            if (!valueReference.isLoading()) {
                throw new AssertionError();
            }
            Preconditions.checkState(!Thread.holdsLock(referenceEntry), "Recursive load of: %s", k);
            try {
                V v = valueReference.waitForValue();
                if (v == null) {
                    throw new CacheLoader.InvalidCacheLoadException("CacheLoader returned null for key " + k + ".");
                }
                long l = this.map.ticker.read();
                this.recordRead(referenceEntry, l);
                V v2 = v;
                return v2;
            } finally {
                this.statsCounter.recordMisses(1);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        V compute(K k, int n, BiFunction<? super K, ? super V, ? extends V> biFunction) {
            ReferenceEntry referenceEntry;
            ValueReference<K, V> valueReference = null;
            LoadingValueReference<? super K, ? extends V> loadingValueReference = null;
            boolean bl = true;
            this.lock();
            try {
                ReferenceEntry referenceEntry2;
                long l = this.map.ticker.read();
                this.preWriteCleanup(l);
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int n2 = n & atomicReferenceArray.length() - 1;
                for (referenceEntry = referenceEntry2 = atomicReferenceArray.get(n2); referenceEntry != null; referenceEntry = referenceEntry.getNext()) {
                    K k2 = referenceEntry.getKey();
                    if (referenceEntry.getHash() != n || k2 == null || !this.map.keyEquivalence.equivalent(k, k2)) continue;
                    valueReference = referenceEntry.getValueReference();
                    if (this.map.isExpired(referenceEntry, l)) {
                        this.enqueueNotification(k2, n, valueReference.get(), valueReference.getWeight(), RemovalCause.EXPIRED);
                    }
                    this.writeQueue.remove(referenceEntry);
                    this.accessQueue.remove(referenceEntry);
                    bl = false;
                    break;
                }
                loadingValueReference = new LoadingValueReference<K, V>(valueReference);
                if (referenceEntry == null) {
                    bl = true;
                    referenceEntry = this.newEntry(k, n, referenceEntry2);
                    referenceEntry.setValueReference(loadingValueReference);
                    atomicReferenceArray.set(n2, referenceEntry);
                } else {
                    referenceEntry.setValueReference(loadingValueReference);
                }
            } finally {
                this.unlock();
                this.postWriteCleanup();
            }
            ReferenceEntry referenceEntry3 = referenceEntry;
            synchronized (referenceEntry3) {
                V v = loadingValueReference.compute((K)k, (BiFunction<? super K, ? extends V, ? extends V>)biFunction);
                if (v != null) {
                    try {
                        return this.getAndRecordStats(k, n, loadingValueReference, Futures.immediateFuture(v));
                    } catch (ExecutionException executionException) {
                        throw new AssertionError((Object)"impossible; Futures.immediateFuture can't throw");
                    }
                }
                if (bl) {
                    this.removeLoadingValue(k, n, loadingValueReference);
                    return null;
                }
                this.lock();
                try {
                    this.removeEntry(referenceEntry, n, RemovalCause.EXPLICIT);
                } finally {
                    this.unlock();
                }
                return null;
            }
        }

        V loadSync(K k, int n, LoadingValueReference<K, V> loadingValueReference, CacheLoader<? super K, V> cacheLoader) throws ExecutionException {
            ListenableFuture<V> listenableFuture = loadingValueReference.loadFuture((K)k, cacheLoader);
            return this.getAndRecordStats(k, n, loadingValueReference, listenableFuture);
        }

        ListenableFuture<V> loadAsync(K k, int n, LoadingValueReference<K, V> loadingValueReference, CacheLoader<? super K, V> cacheLoader) {
            ListenableFuture<V> listenableFuture = loadingValueReference.loadFuture((K)k, cacheLoader);
            listenableFuture.addListener(new Runnable(this, k, n, loadingValueReference, listenableFuture){
                final Object val$key;
                final int val$hash;
                final LoadingValueReference val$loadingValueReference;
                final ListenableFuture val$loadingFuture;
                final Segment this$0;
                {
                    this.this$0 = segment;
                    this.val$key = object;
                    this.val$hash = n;
                    this.val$loadingValueReference = loadingValueReference;
                    this.val$loadingFuture = listenableFuture;
                }

                @Override
                public void run() {
                    try {
                        this.this$0.getAndRecordStats(this.val$key, this.val$hash, this.val$loadingValueReference, this.val$loadingFuture);
                    } catch (Throwable throwable) {
                        logger.log(Level.WARNING, "Exception thrown during refresh", throwable);
                        this.val$loadingValueReference.setException(throwable);
                    }
                }
            }, MoreExecutors.directExecutor());
            return listenableFuture;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        V getAndRecordStats(K k, int n, LoadingValueReference<K, V> loadingValueReference, ListenableFuture<V> listenableFuture) throws ExecutionException {
            V v = null;
            try {
                v = Uninterruptibles.getUninterruptibly(listenableFuture);
                if (v == null) {
                    throw new CacheLoader.InvalidCacheLoadException("CacheLoader returned null for key " + k + ".");
                }
                this.statsCounter.recordLoadSuccess(loadingValueReference.elapsedNanos());
                this.storeLoadedValue(k, n, loadingValueReference, v);
                V v2 = v;
                return v2;
            } finally {
                if (v == null) {
                    this.statsCounter.recordLoadException(loadingValueReference.elapsedNanos());
                    this.removeLoadingValue(k, n, loadingValueReference);
                }
            }
        }

        V scheduleRefresh(ReferenceEntry<K, V> referenceEntry, K k, int n, V v, long l, CacheLoader<? super K, V> cacheLoader) {
            V v2;
            if (this.map.refreshes() && l - referenceEntry.getWriteTime() > this.map.refreshNanos && !referenceEntry.getValueReference().isLoading() && (v2 = this.refresh(k, n, cacheLoader, false)) != null) {
                return v2;
            }
            return v;
        }

        @Nullable
        V refresh(K k, int n, CacheLoader<? super K, V> cacheLoader, boolean bl) {
            LoadingValueReference<K, V> loadingValueReference = this.insertLoadingValueReference(k, n, bl);
            if (loadingValueReference == null) {
                return null;
            }
            ListenableFuture<V> listenableFuture = this.loadAsync(k, n, loadingValueReference, cacheLoader);
            if (listenableFuture.isDone()) {
                try {
                    return Uninterruptibles.getUninterruptibly(listenableFuture);
                } catch (Throwable throwable) {
                    // empty catch block
                }
            }
            return null;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Nullable
        LoadingValueReference<K, V> insertLoadingValueReference(K k, int n, boolean bl) {
            ReferenceEntry<K, V> referenceEntry = null;
            this.lock();
            try {
                Object object;
                ReferenceEntry<K, V> referenceEntry2;
                long l = this.map.ticker.read();
                this.preWriteCleanup(l);
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int n2 = n & atomicReferenceArray.length() - 1;
                for (referenceEntry = referenceEntry2 = atomicReferenceArray.get(n2); referenceEntry != null; referenceEntry = referenceEntry.getNext()) {
                    object = referenceEntry.getKey();
                    if (referenceEntry.getHash() != n || object == null || !this.map.keyEquivalence.equivalent(k, object)) continue;
                    ValueReference<K, V> valueReference = referenceEntry.getValueReference();
                    if (valueReference.isLoading() || bl && l - referenceEntry.getWriteTime() < this.map.refreshNanos) {
                        LoadingValueReference<K, V> loadingValueReference = null;
                        return loadingValueReference;
                    }
                    ++this.modCount;
                    LoadingValueReference<K, V> loadingValueReference = new LoadingValueReference<K, V>(valueReference);
                    referenceEntry.setValueReference(loadingValueReference);
                    LoadingValueReference<K, V> loadingValueReference2 = loadingValueReference;
                    return loadingValueReference2;
                }
                ++this.modCount;
                object = new LoadingValueReference();
                referenceEntry = this.newEntry(k, n, referenceEntry2);
                referenceEntry.setValueReference((ValueReference<K, V>)object);
                atomicReferenceArray.set(n2, referenceEntry);
                Object object2 = object;
                return object2;
            } finally {
                this.unlock();
                this.postWriteCleanup();
            }
        }

        void tryDrainReferenceQueues() {
            if (this.tryLock()) {
                try {
                    this.drainReferenceQueues();
                } finally {
                    this.unlock();
                }
            }
        }

        @GuardedBy(value="this")
        void drainReferenceQueues() {
            if (this.map.usesKeyReferences()) {
                this.drainKeyReferenceQueue();
            }
            if (this.map.usesValueReferences()) {
                this.drainValueReferenceQueue();
            }
        }

        @GuardedBy(value="this")
        void drainKeyReferenceQueue() {
            Reference<K> reference;
            int n = 0;
            while ((reference = this.keyReferenceQueue.poll()) != null) {
                ReferenceEntry referenceEntry = (ReferenceEntry)((Object)reference);
                this.map.reclaimKey(referenceEntry);
                if (++n != 16) continue;
                break;
            }
        }

        @GuardedBy(value="this")
        void drainValueReferenceQueue() {
            Reference<V> reference;
            int n = 0;
            while ((reference = this.valueReferenceQueue.poll()) != null) {
                ValueReference valueReference = (ValueReference)((Object)reference);
                this.map.reclaimValue(valueReference);
                if (++n != 16) continue;
                break;
            }
        }

        void clearReferenceQueues() {
            if (this.map.usesKeyReferences()) {
                this.clearKeyReferenceQueue();
            }
            if (this.map.usesValueReferences()) {
                this.clearValueReferenceQueue();
            }
        }

        void clearKeyReferenceQueue() {
            while (this.keyReferenceQueue.poll() != null) {
            }
        }

        void clearValueReferenceQueue() {
            while (this.valueReferenceQueue.poll() != null) {
            }
        }

        void recordRead(ReferenceEntry<K, V> referenceEntry, long l) {
            if (this.map.recordsAccess()) {
                referenceEntry.setAccessTime(l);
            }
            this.recencyQueue.add(referenceEntry);
        }

        @GuardedBy(value="this")
        void recordLockedRead(ReferenceEntry<K, V> referenceEntry, long l) {
            if (this.map.recordsAccess()) {
                referenceEntry.setAccessTime(l);
            }
            this.accessQueue.add(referenceEntry);
        }

        @GuardedBy(value="this")
        void recordWrite(ReferenceEntry<K, V> referenceEntry, int n, long l) {
            this.drainRecencyQueue();
            this.totalWeight += (long)n;
            if (this.map.recordsAccess()) {
                referenceEntry.setAccessTime(l);
            }
            if (this.map.recordsWrite()) {
                referenceEntry.setWriteTime(l);
            }
            this.accessQueue.add(referenceEntry);
            this.writeQueue.add(referenceEntry);
        }

        @GuardedBy(value="this")
        void drainRecencyQueue() {
            ReferenceEntry<K, V> referenceEntry;
            while ((referenceEntry = this.recencyQueue.poll()) != null) {
                if (!this.accessQueue.contains(referenceEntry)) continue;
                this.accessQueue.add(referenceEntry);
            }
        }

        void tryExpireEntries(long l) {
            if (this.tryLock()) {
                try {
                    this.expireEntries(l);
                } finally {
                    this.unlock();
                }
            }
        }

        @GuardedBy(value="this")
        void expireEntries(long l) {
            ReferenceEntry<K, V> referenceEntry;
            this.drainRecencyQueue();
            while ((referenceEntry = this.writeQueue.peek()) != null && this.map.isExpired(referenceEntry, l)) {
                if (!this.removeEntry(referenceEntry, referenceEntry.getHash(), RemovalCause.EXPIRED)) {
                    throw new AssertionError();
                }
            }
            while ((referenceEntry = this.accessQueue.peek()) != null && this.map.isExpired(referenceEntry, l)) {
                if (!this.removeEntry(referenceEntry, referenceEntry.getHash(), RemovalCause.EXPIRED)) {
                    throw new AssertionError();
                }
            }
        }

        @GuardedBy(value="this")
        void enqueueNotification(@Nullable K k, int n, @Nullable V v, int n2, RemovalCause removalCause) {
            this.totalWeight -= (long)n2;
            if (removalCause.wasEvicted()) {
                this.statsCounter.recordEviction();
            }
            if (this.map.removalNotificationQueue != DISCARDING_QUEUE) {
                RemovalNotification<K, V> removalNotification = RemovalNotification.create(k, v, removalCause);
                this.map.removalNotificationQueue.offer(removalNotification);
            }
        }

        @GuardedBy(value="this")
        void evictEntries(ReferenceEntry<K, V> referenceEntry) {
            if (!this.map.evictsBySize()) {
                return;
            }
            this.drainRecencyQueue();
            if ((long)referenceEntry.getValueReference().getWeight() > this.maxSegmentWeight && !this.removeEntry(referenceEntry, referenceEntry.getHash(), RemovalCause.SIZE)) {
                throw new AssertionError();
            }
            while (this.totalWeight > this.maxSegmentWeight) {
                ReferenceEntry<K, V> referenceEntry2 = this.getNextEvictable();
                if (!this.removeEntry(referenceEntry2, referenceEntry2.getHash(), RemovalCause.SIZE)) {
                    throw new AssertionError();
                }
            }
        }

        @GuardedBy(value="this")
        ReferenceEntry<K, V> getNextEvictable() {
            for (ReferenceEntry referenceEntry : this.accessQueue) {
                int n = referenceEntry.getValueReference().getWeight();
                if (n <= 0) continue;
                return referenceEntry;
            }
            throw new AssertionError();
        }

        ReferenceEntry<K, V> getFirst(int n) {
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
            return atomicReferenceArray.get(n & atomicReferenceArray.length() - 1);
        }

        @Nullable
        ReferenceEntry<K, V> getEntry(Object object, int n) {
            for (ReferenceEntry<K, V> referenceEntry = this.getFirst(n); referenceEntry != null; referenceEntry = referenceEntry.getNext()) {
                if (referenceEntry.getHash() != n) continue;
                K k = referenceEntry.getKey();
                if (k == null) {
                    this.tryDrainReferenceQueues();
                    continue;
                }
                if (!this.map.keyEquivalence.equivalent(object, k)) continue;
                return referenceEntry;
            }
            return null;
        }

        @Nullable
        ReferenceEntry<K, V> getLiveEntry(Object object, int n, long l) {
            ReferenceEntry<K, V> referenceEntry = this.getEntry(object, n);
            if (referenceEntry == null) {
                return null;
            }
            if (this.map.isExpired(referenceEntry, l)) {
                this.tryExpireEntries(l);
                return null;
            }
            return referenceEntry;
        }

        V getLiveValue(ReferenceEntry<K, V> referenceEntry, long l) {
            if (referenceEntry.getKey() == null) {
                this.tryDrainReferenceQueues();
                return null;
            }
            V v = referenceEntry.getValueReference().get();
            if (v == null) {
                this.tryDrainReferenceQueues();
                return null;
            }
            if (this.map.isExpired(referenceEntry, l)) {
                this.tryExpireEntries(l);
                return null;
            }
            return v;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Nullable
        V get(Object object, int n) {
            try {
                if (this.count != 0) {
                    long l = this.map.ticker.read();
                    ReferenceEntry<K, V> referenceEntry = this.getLiveEntry(object, n, l);
                    if (referenceEntry == null) {
                        V v = null;
                        return v;
                    }
                    V v = referenceEntry.getValueReference().get();
                    if (v != null) {
                        this.recordRead(referenceEntry, l);
                        V v2 = this.scheduleRefresh(referenceEntry, referenceEntry.getKey(), n, v, l, this.map.defaultLoader);
                        return v2;
                    }
                    this.tryDrainReferenceQueues();
                }
                V v = null;
                return v;
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
                    long l = this.map.ticker.read();
                    ReferenceEntry<K, V> referenceEntry = this.getLiveEntry(object, n, l);
                    if (referenceEntry == null) {
                        boolean bl = false;
                        return bl;
                    }
                    boolean bl = referenceEntry.getValueReference().get() != null;
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
                    long l = this.map.ticker.read();
                    AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                    int n = atomicReferenceArray.length();
                    for (int i = 0; i < n; ++i) {
                        for (ReferenceEntry<K, V> referenceEntry = atomicReferenceArray.get(i); referenceEntry != null; referenceEntry = referenceEntry.getNext()) {
                            V v = this.getLiveValue(referenceEntry, l);
                            if (v == null || !this.map.valueEquivalence.equivalent(object, v)) continue;
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
        @Nullable
        V put(K k, int n, V v, boolean bl) {
            this.lock();
            try {
                K k2;
                ReferenceEntry<K, V> referenceEntry;
                ReferenceEntry<K, V> referenceEntry2;
                long l = this.map.ticker.read();
                this.preWriteCleanup(l);
                int n2 = this.count + 1;
                if (n2 > this.threshold) {
                    this.expand();
                    n2 = this.count + 1;
                }
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int n3 = n & atomicReferenceArray.length() - 1;
                for (referenceEntry2 = referenceEntry = atomicReferenceArray.get(n3); referenceEntry2 != null; referenceEntry2 = referenceEntry2.getNext()) {
                    k2 = referenceEntry2.getKey();
                    if (referenceEntry2.getHash() != n || k2 == null || !this.map.keyEquivalence.equivalent(k, k2)) continue;
                    ValueReference<K, V> valueReference = referenceEntry2.getValueReference();
                    V v2 = valueReference.get();
                    if (v2 == null) {
                        ++this.modCount;
                        if (valueReference.isActive()) {
                            this.enqueueNotification(k, n, v2, valueReference.getWeight(), RemovalCause.COLLECTED);
                            this.setValue(referenceEntry2, k, v, l);
                            n2 = this.count;
                        } else {
                            this.setValue(referenceEntry2, k, v, l);
                            n2 = this.count + 1;
                        }
                        this.count = n2;
                        this.evictEntries(referenceEntry2);
                        V v3 = null;
                        return v3;
                    }
                    if (bl) {
                        this.recordLockedRead(referenceEntry2, l);
                        V v4 = v2;
                        return v4;
                    }
                    ++this.modCount;
                    this.enqueueNotification(k, n, v2, valueReference.getWeight(), RemovalCause.REPLACED);
                    this.setValue(referenceEntry2, k, v, l);
                    this.evictEntries(referenceEntry2);
                    V v5 = v2;
                    return v5;
                }
                ++this.modCount;
                referenceEntry2 = this.newEntry(k, n, referenceEntry);
                this.setValue(referenceEntry2, k, v, l);
                atomicReferenceArray.set(n3, referenceEntry2);
                this.count = n2 = this.count + 1;
                this.evictEntries(referenceEntry2);
                k2 = null;
                return (V)k2;
            } finally {
                this.unlock();
                this.postWriteCleanup();
            }
        }

        @GuardedBy(value="this")
        void expand() {
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
            int n = atomicReferenceArray.length();
            if (n >= 0x40000000) {
                return;
            }
            int n2 = this.count;
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray2 = this.newEntryArray(n << 1);
            this.threshold = atomicReferenceArray2.length() * 3 / 4;
            int n3 = atomicReferenceArray2.length() - 1;
            for (int i = 0; i < n; ++i) {
                int n4;
                ReferenceEntry<K, V> referenceEntry;
                ReferenceEntry<K, V> referenceEntry2 = atomicReferenceArray.get(i);
                if (referenceEntry2 == null) continue;
                ReferenceEntry<K, V> referenceEntry3 = referenceEntry2.getNext();
                int n5 = referenceEntry2.getHash() & n3;
                if (referenceEntry3 == null) {
                    atomicReferenceArray2.set(n5, referenceEntry2);
                    continue;
                }
                ReferenceEntry<K, V> referenceEntry4 = referenceEntry2;
                int n6 = n5;
                for (referenceEntry = referenceEntry3; referenceEntry != null; referenceEntry = referenceEntry.getNext()) {
                    n4 = referenceEntry.getHash() & n3;
                    if (n4 == n6) continue;
                    n6 = n4;
                    referenceEntry4 = referenceEntry;
                }
                atomicReferenceArray2.set(n6, referenceEntry4);
                for (referenceEntry = referenceEntry2; referenceEntry != referenceEntry4; referenceEntry = referenceEntry.getNext()) {
                    n4 = referenceEntry.getHash() & n3;
                    ReferenceEntry<K, V> referenceEntry5 = atomicReferenceArray2.get(n4);
                    ReferenceEntry<K, V> referenceEntry6 = this.copyEntry(referenceEntry, referenceEntry5);
                    if (referenceEntry6 != null) {
                        atomicReferenceArray2.set(n4, referenceEntry6);
                        continue;
                    }
                    this.removeCollectedEntry(referenceEntry);
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
                ReferenceEntry<K, V> referenceEntry;
                long l = this.map.ticker.read();
                this.preWriteCleanup(l);
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int n2 = n & atomicReferenceArray.length() - 1;
                for (ReferenceEntry<K, V> referenceEntry2 = referenceEntry = atomicReferenceArray.get(n2); referenceEntry2 != null; referenceEntry2 = referenceEntry2.getNext()) {
                    K k2 = referenceEntry2.getKey();
                    if (referenceEntry2.getHash() != n || k2 == null || !this.map.keyEquivalence.equivalent(k, k2)) continue;
                    ValueReference<K, V> valueReference = referenceEntry2.getValueReference();
                    V v3 = valueReference.get();
                    if (v3 == null) {
                        int n3;
                        if (valueReference.isActive()) {
                            n3 = this.count - 1;
                            ++this.modCount;
                            ReferenceEntry<K, V> referenceEntry3 = this.removeValueFromChain(referenceEntry, referenceEntry2, k2, n, v3, valueReference, RemovalCause.COLLECTED);
                            n3 = this.count - 1;
                            atomicReferenceArray.set(n2, referenceEntry3);
                            this.count = n3;
                        }
                        n3 = 0;
                        return n3 != 0;
                    }
                    if (this.map.valueEquivalence.equivalent(v, v3)) {
                        ++this.modCount;
                        this.enqueueNotification(k, n, v3, valueReference.getWeight(), RemovalCause.REPLACED);
                        this.setValue(referenceEntry2, k, v2, l);
                        this.evictEntries(referenceEntry2);
                        boolean bl = true;
                        return bl;
                    }
                    this.recordLockedRead(referenceEntry2, l);
                    boolean bl = false;
                    return bl;
                }
                boolean bl = false;
                return bl;
            } finally {
                this.unlock();
                this.postWriteCleanup();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Nullable
        V replace(K k, int n, V v) {
            this.lock();
            try {
                ReferenceEntry<K, V> referenceEntry;
                ReferenceEntry<K, V> referenceEntry2;
                long l = this.map.ticker.read();
                this.preWriteCleanup(l);
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int n2 = n & atomicReferenceArray.length() - 1;
                for (referenceEntry2 = referenceEntry = atomicReferenceArray.get(n2); referenceEntry2 != null; referenceEntry2 = referenceEntry2.getNext()) {
                    K k2 = referenceEntry2.getKey();
                    if (referenceEntry2.getHash() != n || k2 == null || !this.map.keyEquivalence.equivalent(k, k2)) continue;
                    ValueReference<K, V> valueReference = referenceEntry2.getValueReference();
                    V v2 = valueReference.get();
                    if (v2 == null) {
                        if (valueReference.isActive()) {
                            int n3 = this.count - 1;
                            ++this.modCount;
                            ReferenceEntry<K, V> referenceEntry3 = this.removeValueFromChain(referenceEntry, referenceEntry2, k2, n, v2, valueReference, RemovalCause.COLLECTED);
                            n3 = this.count - 1;
                            atomicReferenceArray.set(n2, referenceEntry3);
                            this.count = n3;
                        }
                        V v3 = null;
                        return v3;
                    }
                    ++this.modCount;
                    this.enqueueNotification(k, n, v2, valueReference.getWeight(), RemovalCause.REPLACED);
                    this.setValue(referenceEntry2, k, v, l);
                    this.evictEntries(referenceEntry2);
                    V v4 = v2;
                    return v4;
                }
                referenceEntry2 = null;
                return (V)referenceEntry2;
            } finally {
                this.unlock();
                this.postWriteCleanup();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Nullable
        V remove(Object object, int n) {
            this.lock();
            try {
                ReferenceEntry<K, V> referenceEntry;
                ReferenceEntry<K, V> referenceEntry2;
                long l = this.map.ticker.read();
                this.preWriteCleanup(l);
                int n2 = this.count - 1;
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int n3 = n & atomicReferenceArray.length() - 1;
                for (referenceEntry2 = referenceEntry = atomicReferenceArray.get(n3); referenceEntry2 != null; referenceEntry2 = referenceEntry2.getNext()) {
                    RemovalCause removalCause;
                    K k = referenceEntry2.getKey();
                    if (referenceEntry2.getHash() != n || k == null || !this.map.keyEquivalence.equivalent(object, k)) continue;
                    ValueReference<K, V> valueReference = referenceEntry2.getValueReference();
                    V v = valueReference.get();
                    if (v != null) {
                        removalCause = RemovalCause.EXPLICIT;
                    } else if (valueReference.isActive()) {
                        removalCause = RemovalCause.COLLECTED;
                    } else {
                        V v2 = null;
                        return v2;
                    }
                    ++this.modCount;
                    ReferenceEntry<K, V> referenceEntry3 = this.removeValueFromChain(referenceEntry, referenceEntry2, k, n, v, valueReference, removalCause);
                    n2 = this.count - 1;
                    atomicReferenceArray.set(n3, referenceEntry3);
                    this.count = n2;
                    V v3 = v;
                    return v3;
                }
                referenceEntry2 = null;
                return (V)referenceEntry2;
            } finally {
                this.unlock();
                this.postWriteCleanup();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        boolean storeLoadedValue(K k, int n, LoadingValueReference<K, V> loadingValueReference, V v) {
            this.lock();
            try {
                ReferenceEntry<K, V> referenceEntry;
                ReferenceEntry<K, V> referenceEntry2;
                long l = this.map.ticker.read();
                this.preWriteCleanup(l);
                int n2 = this.count + 1;
                if (n2 > this.threshold) {
                    this.expand();
                    n2 = this.count + 1;
                }
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int n3 = n & atomicReferenceArray.length() - 1;
                for (referenceEntry2 = referenceEntry = atomicReferenceArray.get(n3); referenceEntry2 != null; referenceEntry2 = referenceEntry2.getNext()) {
                    K k2 = referenceEntry2.getKey();
                    if (referenceEntry2.getHash() != n || k2 == null || !this.map.keyEquivalence.equivalent(k, k2)) continue;
                    ValueReference<K, V> valueReference = referenceEntry2.getValueReference();
                    V v2 = valueReference.get();
                    if (loadingValueReference == valueReference || v2 == null && valueReference != UNSET) {
                        ++this.modCount;
                        if (loadingValueReference.isActive()) {
                            RemovalCause removalCause = v2 == null ? RemovalCause.COLLECTED : RemovalCause.REPLACED;
                            this.enqueueNotification(k, n, v2, loadingValueReference.getWeight(), removalCause);
                            --n2;
                        }
                        this.setValue(referenceEntry2, k, v, l);
                        this.count = n2;
                        this.evictEntries(referenceEntry2);
                        boolean bl = true;
                        return bl;
                    }
                    this.enqueueNotification(k, n, v, 0, RemovalCause.REPLACED);
                    boolean bl = false;
                    return bl;
                }
                ++this.modCount;
                referenceEntry2 = this.newEntry(k, n, referenceEntry);
                this.setValue(referenceEntry2, k, v, l);
                atomicReferenceArray.set(n3, referenceEntry2);
                this.count = n2;
                this.evictEntries(referenceEntry2);
                boolean bl = true;
                return bl;
            } finally {
                this.unlock();
                this.postWriteCleanup();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        boolean remove(Object object, int n, Object object2) {
            this.lock();
            try {
                ReferenceEntry<K, V> referenceEntry;
                long l = this.map.ticker.read();
                this.preWriteCleanup(l);
                int n2 = this.count - 1;
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int n3 = n & atomicReferenceArray.length() - 1;
                for (ReferenceEntry<K, V> referenceEntry2 = referenceEntry = atomicReferenceArray.get(n3); referenceEntry2 != null; referenceEntry2 = referenceEntry2.getNext()) {
                    RemovalCause removalCause;
                    K k = referenceEntry2.getKey();
                    if (referenceEntry2.getHash() != n || k == null || !this.map.keyEquivalence.equivalent(object, k)) continue;
                    ValueReference<K, V> valueReference = referenceEntry2.getValueReference();
                    V v = valueReference.get();
                    if (this.map.valueEquivalence.equivalent(object2, v)) {
                        removalCause = RemovalCause.EXPLICIT;
                    } else if (v == null && valueReference.isActive()) {
                        removalCause = RemovalCause.COLLECTED;
                    } else {
                        boolean bl = false;
                        return bl;
                    }
                    ++this.modCount;
                    ReferenceEntry<K, V> referenceEntry3 = this.removeValueFromChain(referenceEntry, referenceEntry2, k, n, v, valueReference, removalCause);
                    n2 = this.count - 1;
                    atomicReferenceArray.set(n3, referenceEntry3);
                    this.count = n2;
                    boolean bl = removalCause == RemovalCause.EXPLICIT;
                    return bl;
                }
                boolean bl = false;
                return bl;
            } finally {
                this.unlock();
                this.postWriteCleanup();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void clear() {
            if (this.count != 0) {
                this.lock();
                try {
                    int n;
                    long l = this.map.ticker.read();
                    this.preWriteCleanup(l);
                    AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                    for (n = 0; n < atomicReferenceArray.length(); ++n) {
                        for (ReferenceEntry<K, V> referenceEntry = atomicReferenceArray.get(n); referenceEntry != null; referenceEntry = referenceEntry.getNext()) {
                            if (!referenceEntry.getValueReference().isActive()) continue;
                            K k = referenceEntry.getKey();
                            V v = referenceEntry.getValueReference().get();
                            RemovalCause removalCause = k == null || v == null ? RemovalCause.COLLECTED : RemovalCause.EXPLICIT;
                            this.enqueueNotification(k, referenceEntry.getHash(), v, referenceEntry.getValueReference().getWeight(), removalCause);
                        }
                    }
                    for (n = 0; n < atomicReferenceArray.length(); ++n) {
                        atomicReferenceArray.set(n, null);
                    }
                    this.clearReferenceQueues();
                    this.writeQueue.clear();
                    this.accessQueue.clear();
                    this.readCount.set(0);
                    ++this.modCount;
                    this.count = 0;
                } finally {
                    this.unlock();
                    this.postWriteCleanup();
                }
            }
        }

        @Nullable
        @GuardedBy(value="this")
        ReferenceEntry<K, V> removeValueFromChain(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2, @Nullable K k, int n, V v, ValueReference<K, V> valueReference, RemovalCause removalCause) {
            this.enqueueNotification(k, n, v, valueReference.getWeight(), removalCause);
            this.writeQueue.remove(referenceEntry2);
            this.accessQueue.remove(referenceEntry2);
            if (valueReference.isLoading()) {
                valueReference.notifyNewValue(null);
                return referenceEntry;
            }
            return this.removeEntryFromChain(referenceEntry, referenceEntry2);
        }

        @Nullable
        @GuardedBy(value="this")
        ReferenceEntry<K, V> removeEntryFromChain(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            int n = this.count;
            ReferenceEntry<K, V> referenceEntry3 = referenceEntry2.getNext();
            for (ReferenceEntry<K, V> referenceEntry4 = referenceEntry; referenceEntry4 != referenceEntry2; referenceEntry4 = referenceEntry4.getNext()) {
                ReferenceEntry<K, V> referenceEntry5 = this.copyEntry(referenceEntry4, referenceEntry3);
                if (referenceEntry5 != null) {
                    referenceEntry3 = referenceEntry5;
                    continue;
                }
                this.removeCollectedEntry(referenceEntry4);
                --n;
            }
            this.count = n;
            return referenceEntry3;
        }

        @GuardedBy(value="this")
        void removeCollectedEntry(ReferenceEntry<K, V> referenceEntry) {
            this.enqueueNotification(referenceEntry.getKey(), referenceEntry.getHash(), referenceEntry.getValueReference().get(), referenceEntry.getValueReference().getWeight(), RemovalCause.COLLECTED);
            this.writeQueue.remove(referenceEntry);
            this.accessQueue.remove(referenceEntry);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        boolean reclaimKey(ReferenceEntry<K, V> referenceEntry, int n) {
            this.lock();
            try {
                ReferenceEntry<K, V> referenceEntry2;
                int n2 = this.count - 1;
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int n3 = n & atomicReferenceArray.length() - 1;
                for (ReferenceEntry<K, V> referenceEntry3 = referenceEntry2 = atomicReferenceArray.get(n3); referenceEntry3 != null; referenceEntry3 = referenceEntry3.getNext()) {
                    if (referenceEntry3 != referenceEntry) continue;
                    ++this.modCount;
                    ReferenceEntry<K, V> referenceEntry4 = this.removeValueFromChain(referenceEntry2, referenceEntry3, referenceEntry3.getKey(), n, referenceEntry3.getValueReference().get(), referenceEntry3.getValueReference(), RemovalCause.COLLECTED);
                    n2 = this.count - 1;
                    atomicReferenceArray.set(n3, referenceEntry4);
                    this.count = n2;
                    boolean bl = true;
                    return bl;
                }
                boolean bl = false;
                return bl;
            } finally {
                this.unlock();
                this.postWriteCleanup();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        boolean reclaimValue(K k, int n, ValueReference<K, V> valueReference) {
            this.lock();
            try {
                ReferenceEntry<K, V> referenceEntry;
                int n2 = this.count - 1;
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int n3 = n & atomicReferenceArray.length() - 1;
                for (ReferenceEntry<K, V> referenceEntry2 = referenceEntry = atomicReferenceArray.get(n3); referenceEntry2 != null; referenceEntry2 = referenceEntry2.getNext()) {
                    K k2 = referenceEntry2.getKey();
                    if (referenceEntry2.getHash() != n || k2 == null || !this.map.keyEquivalence.equivalent(k, k2)) continue;
                    ValueReference<K, V> valueReference2 = referenceEntry2.getValueReference();
                    if (valueReference2 == valueReference) {
                        ++this.modCount;
                        ReferenceEntry<K, V> referenceEntry3 = this.removeValueFromChain(referenceEntry, referenceEntry2, k2, n, valueReference.get(), valueReference, RemovalCause.COLLECTED);
                        n2 = this.count - 1;
                        atomicReferenceArray.set(n3, referenceEntry3);
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
                if (!this.isHeldByCurrentThread()) {
                    this.postWriteCleanup();
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        boolean removeLoadingValue(K k, int n, LoadingValueReference<K, V> loadingValueReference) {
            this.lock();
            try {
                ReferenceEntry<K, V> referenceEntry;
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int n2 = n & atomicReferenceArray.length() - 1;
                for (ReferenceEntry<K, V> referenceEntry2 = referenceEntry = atomicReferenceArray.get(n2); referenceEntry2 != null; referenceEntry2 = referenceEntry2.getNext()) {
                    K k2 = referenceEntry2.getKey();
                    if (referenceEntry2.getHash() != n || k2 == null || !this.map.keyEquivalence.equivalent(k, k2)) continue;
                    ValueReference<K, V> valueReference = referenceEntry2.getValueReference();
                    if (valueReference == loadingValueReference) {
                        if (loadingValueReference.isActive()) {
                            referenceEntry2.setValueReference(loadingValueReference.getOldValue());
                        } else {
                            ReferenceEntry<K, V> referenceEntry3 = this.removeEntryFromChain(referenceEntry, referenceEntry2);
                            atomicReferenceArray.set(n2, referenceEntry3);
                        }
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
                this.postWriteCleanup();
            }
        }

        @VisibleForTesting
        @GuardedBy(value="this")
        boolean removeEntry(ReferenceEntry<K, V> referenceEntry, int n, RemovalCause removalCause) {
            ReferenceEntry<K, V> referenceEntry2;
            int n2 = this.count - 1;
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
            int n3 = n & atomicReferenceArray.length() - 1;
            for (ReferenceEntry<K, V> referenceEntry3 = referenceEntry2 = atomicReferenceArray.get(n3); referenceEntry3 != null; referenceEntry3 = referenceEntry3.getNext()) {
                if (referenceEntry3 != referenceEntry) continue;
                ++this.modCount;
                ReferenceEntry<K, V> referenceEntry4 = this.removeValueFromChain(referenceEntry2, referenceEntry3, referenceEntry3.getKey(), n, referenceEntry3.getValueReference().get(), referenceEntry3.getValueReference(), removalCause);
                n2 = this.count - 1;
                atomicReferenceArray.set(n3, referenceEntry4);
                this.count = n2;
                return false;
            }
            return true;
        }

        void postReadCleanup() {
            if ((this.readCount.incrementAndGet() & 0x3F) == 0) {
                this.cleanUp();
            }
        }

        @GuardedBy(value="this")
        void preWriteCleanup(long l) {
            this.runLockedCleanup(l);
        }

        void postWriteCleanup() {
            this.runUnlockedCleanup();
        }

        void cleanUp() {
            long l = this.map.ticker.read();
            this.runLockedCleanup(l);
            this.runUnlockedCleanup();
        }

        void runLockedCleanup(long l) {
            if (this.tryLock()) {
                try {
                    this.drainReferenceQueues();
                    this.expireEntries(l);
                    this.readCount.set(0);
                } finally {
                    this.unlock();
                }
            }
        }

        void runUnlockedCleanup() {
            if (!this.isHeldByCurrentThread()) {
                this.map.processPendingNotifications();
            }
        }
    }

    static final class WeightedStrongValueReference<K, V>
    extends StrongValueReference<K, V> {
        final int weight;

        WeightedStrongValueReference(V v, int n) {
            super(v);
            this.weight = n;
        }

        @Override
        public int getWeight() {
            return this.weight;
        }
    }

    static final class WeightedSoftValueReference<K, V>
    extends SoftValueReference<K, V> {
        final int weight;

        WeightedSoftValueReference(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry, int n) {
            super(referenceQueue, v, referenceEntry);
            this.weight = n;
        }

        @Override
        public int getWeight() {
            return this.weight;
        }

        @Override
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            return new WeightedSoftValueReference<K, V>(referenceQueue, v, referenceEntry, this.weight);
        }
    }

    static final class WeightedWeakValueReference<K, V>
    extends WeakValueReference<K, V> {
        final int weight;

        WeightedWeakValueReference(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry, int n) {
            super(referenceQueue, v, referenceEntry);
            this.weight = n;
        }

        @Override
        public int getWeight() {
            return this.weight;
        }

        @Override
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            return new WeightedWeakValueReference<K, V>(referenceQueue, v, referenceEntry, this.weight);
        }
    }

    static class StrongValueReference<K, V>
    implements ValueReference<K, V> {
        final V referent;

        StrongValueReference(V v) {
            this.referent = v;
        }

        @Override
        public V get() {
            return this.referent;
        }

        @Override
        public int getWeight() {
            return 0;
        }

        @Override
        public ReferenceEntry<K, V> getEntry() {
            return null;
        }

        @Override
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            return this;
        }

        @Override
        public boolean isLoading() {
            return true;
        }

        @Override
        public boolean isActive() {
            return false;
        }

        @Override
        public V waitForValue() {
            return this.get();
        }

        @Override
        public void notifyNewValue(V v) {
        }
    }

    static class SoftValueReference<K, V>
    extends SoftReference<V>
    implements ValueReference<K, V> {
        final ReferenceEntry<K, V> entry;

        SoftValueReference(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            super(v, referenceQueue);
            this.entry = referenceEntry;
        }

        @Override
        public int getWeight() {
            return 0;
        }

        @Override
        public ReferenceEntry<K, V> getEntry() {
            return this.entry;
        }

        @Override
        public void notifyNewValue(V v) {
        }

        @Override
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            return new SoftValueReference<K, V>(referenceQueue, v, referenceEntry);
        }

        @Override
        public boolean isLoading() {
            return true;
        }

        @Override
        public boolean isActive() {
            return false;
        }

        @Override
        public V waitForValue() {
            return (V)this.get();
        }
    }

    static class WeakValueReference<K, V>
    extends WeakReference<V>
    implements ValueReference<K, V> {
        final ReferenceEntry<K, V> entry;

        WeakValueReference(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            super(v, referenceQueue);
            this.entry = referenceEntry;
        }

        @Override
        public int getWeight() {
            return 0;
        }

        @Override
        public ReferenceEntry<K, V> getEntry() {
            return this.entry;
        }

        @Override
        public void notifyNewValue(V v) {
        }

        @Override
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            return new WeakValueReference<K, V>(referenceQueue, v, referenceEntry);
        }

        @Override
        public boolean isLoading() {
            return true;
        }

        @Override
        public boolean isActive() {
            return false;
        }

        @Override
        public V waitForValue() {
            return (V)this.get();
        }
    }

    static final class WeakAccessWriteEntry<K, V>
    extends WeakEntry<K, V> {
        volatile long accessTime = Long.MAX_VALUE;
        ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();
        volatile long writeTime = Long.MAX_VALUE;
        ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();

        WeakAccessWriteEntry(ReferenceQueue<K> referenceQueue, K k, int n, @Nullable ReferenceEntry<K, V> referenceEntry) {
            super(referenceQueue, k, n, referenceEntry);
        }

        @Override
        public long getAccessTime() {
            return this.accessTime;
        }

        @Override
        public void setAccessTime(long l) {
            this.accessTime = l;
        }

        @Override
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        @Override
        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextAccess = referenceEntry;
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        @Override
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousAccess = referenceEntry;
        }

        @Override
        public long getWriteTime() {
            return this.writeTime;
        }

        @Override
        public void setWriteTime(long l) {
            this.writeTime = l;
        }

        @Override
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        @Override
        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextWrite = referenceEntry;
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        @Override
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousWrite = referenceEntry;
        }
    }

    static final class WeakWriteEntry<K, V>
    extends WeakEntry<K, V> {
        volatile long writeTime = Long.MAX_VALUE;
        ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();

        WeakWriteEntry(ReferenceQueue<K> referenceQueue, K k, int n, @Nullable ReferenceEntry<K, V> referenceEntry) {
            super(referenceQueue, k, n, referenceEntry);
        }

        @Override
        public long getWriteTime() {
            return this.writeTime;
        }

        @Override
        public void setWriteTime(long l) {
            this.writeTime = l;
        }

        @Override
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        @Override
        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextWrite = referenceEntry;
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        @Override
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousWrite = referenceEntry;
        }
    }

    static final class WeakAccessEntry<K, V>
    extends WeakEntry<K, V> {
        volatile long accessTime = Long.MAX_VALUE;
        ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();

        WeakAccessEntry(ReferenceQueue<K> referenceQueue, K k, int n, @Nullable ReferenceEntry<K, V> referenceEntry) {
            super(referenceQueue, k, n, referenceEntry);
        }

        @Override
        public long getAccessTime() {
            return this.accessTime;
        }

        @Override
        public void setAccessTime(long l) {
            this.accessTime = l;
        }

        @Override
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        @Override
        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextAccess = referenceEntry;
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        @Override
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousAccess = referenceEntry;
        }
    }

    static class WeakEntry<K, V>
    extends WeakReference<K>
    implements ReferenceEntry<K, V> {
        final int hash;
        final ReferenceEntry<K, V> next;
        volatile ValueReference<K, V> valueReference = LocalCache.unset();

        WeakEntry(ReferenceQueue<K> referenceQueue, K k, int n, @Nullable ReferenceEntry<K, V> referenceEntry) {
            super(k, referenceQueue);
            this.hash = n;
            this.next = referenceEntry;
        }

        @Override
        public K getKey() {
            return (K)this.get();
        }

        @Override
        public long getAccessTime() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setAccessTime(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long getWriteTime() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setWriteTime(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ValueReference<K, V> getValueReference() {
            return this.valueReference;
        }

        @Override
        public void setValueReference(ValueReference<K, V> valueReference) {
            this.valueReference = valueReference;
        }

        @Override
        public int getHash() {
            return this.hash;
        }

        @Override
        public ReferenceEntry<K, V> getNext() {
            return this.next;
        }
    }

    static final class StrongAccessWriteEntry<K, V>
    extends StrongEntry<K, V> {
        volatile long accessTime = Long.MAX_VALUE;
        ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();
        volatile long writeTime = Long.MAX_VALUE;
        ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();

        StrongAccessWriteEntry(K k, int n, @Nullable ReferenceEntry<K, V> referenceEntry) {
            super(k, n, referenceEntry);
        }

        @Override
        public long getAccessTime() {
            return this.accessTime;
        }

        @Override
        public void setAccessTime(long l) {
            this.accessTime = l;
        }

        @Override
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        @Override
        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextAccess = referenceEntry;
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        @Override
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousAccess = referenceEntry;
        }

        @Override
        public long getWriteTime() {
            return this.writeTime;
        }

        @Override
        public void setWriteTime(long l) {
            this.writeTime = l;
        }

        @Override
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        @Override
        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextWrite = referenceEntry;
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        @Override
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousWrite = referenceEntry;
        }
    }

    static final class StrongWriteEntry<K, V>
    extends StrongEntry<K, V> {
        volatile long writeTime = Long.MAX_VALUE;
        ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();

        StrongWriteEntry(K k, int n, @Nullable ReferenceEntry<K, V> referenceEntry) {
            super(k, n, referenceEntry);
        }

        @Override
        public long getWriteTime() {
            return this.writeTime;
        }

        @Override
        public void setWriteTime(long l) {
            this.writeTime = l;
        }

        @Override
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        @Override
        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextWrite = referenceEntry;
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        @Override
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousWrite = referenceEntry;
        }
    }

    static final class StrongAccessEntry<K, V>
    extends StrongEntry<K, V> {
        volatile long accessTime = Long.MAX_VALUE;
        ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();

        StrongAccessEntry(K k, int n, @Nullable ReferenceEntry<K, V> referenceEntry) {
            super(k, n, referenceEntry);
        }

        @Override
        public long getAccessTime() {
            return this.accessTime;
        }

        @Override
        public void setAccessTime(long l) {
            this.accessTime = l;
        }

        @Override
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        @Override
        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextAccess = referenceEntry;
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        @Override
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousAccess = referenceEntry;
        }
    }

    static class StrongEntry<K, V>
    extends AbstractReferenceEntry<K, V> {
        final K key;
        final int hash;
        final ReferenceEntry<K, V> next;
        volatile ValueReference<K, V> valueReference = LocalCache.unset();

        StrongEntry(K k, int n, @Nullable ReferenceEntry<K, V> referenceEntry) {
            this.key = k;
            this.hash = n;
            this.next = referenceEntry;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public ValueReference<K, V> getValueReference() {
            return this.valueReference;
        }

        @Override
        public void setValueReference(ValueReference<K, V> valueReference) {
            this.valueReference = valueReference;
        }

        @Override
        public int getHash() {
            return this.hash;
        }

        @Override
        public ReferenceEntry<K, V> getNext() {
            return this.next;
        }
    }

    static abstract class AbstractReferenceEntry<K, V>
    implements ReferenceEntry<K, V> {
        AbstractReferenceEntry() {
        }

        @Override
        public ValueReference<K, V> getValueReference() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setValueReference(ValueReference<K, V> valueReference) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReferenceEntry<K, V> getNext() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getHash() {
            throw new UnsupportedOperationException();
        }

        @Override
        public K getKey() {
            throw new UnsupportedOperationException();
        }

        @Override
        public long getAccessTime() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setAccessTime(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long getWriteTime() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setWriteTime(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }
    }

    private static enum NullEntry implements ReferenceEntry<Object, Object>
    {
        INSTANCE;


        @Override
        public ValueReference<Object, Object> getValueReference() {
            return null;
        }

        @Override
        public void setValueReference(ValueReference<Object, Object> valueReference) {
        }

        @Override
        public ReferenceEntry<Object, Object> getNext() {
            return null;
        }

        @Override
        public int getHash() {
            return 1;
        }

        @Override
        public Object getKey() {
            return null;
        }

        @Override
        public long getAccessTime() {
            return 0L;
        }

        @Override
        public void setAccessTime(long l) {
        }

        @Override
        public ReferenceEntry<Object, Object> getNextInAccessQueue() {
            return this;
        }

        @Override
        public void setNextInAccessQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }

        @Override
        public ReferenceEntry<Object, Object> getPreviousInAccessQueue() {
            return this;
        }

        @Override
        public void setPreviousInAccessQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }

        @Override
        public long getWriteTime() {
            return 0L;
        }

        @Override
        public void setWriteTime(long l) {
        }

        @Override
        public ReferenceEntry<Object, Object> getNextInWriteQueue() {
            return this;
        }

        @Override
        public void setNextInWriteQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }

        @Override
        public ReferenceEntry<Object, Object> getPreviousInWriteQueue() {
            return this;
        }

        @Override
        public void setPreviousInWriteQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }
    }

    static interface ReferenceEntry<K, V> {
        public ValueReference<K, V> getValueReference();

        public void setValueReference(ValueReference<K, V> var1);

        @Nullable
        public ReferenceEntry<K, V> getNext();

        public int getHash();

        @Nullable
        public K getKey();

        public long getAccessTime();

        public void setAccessTime(long var1);

        public ReferenceEntry<K, V> getNextInAccessQueue();

        public void setNextInAccessQueue(ReferenceEntry<K, V> var1);

        public ReferenceEntry<K, V> getPreviousInAccessQueue();

        public void setPreviousInAccessQueue(ReferenceEntry<K, V> var1);

        public long getWriteTime();

        public void setWriteTime(long var1);

        public ReferenceEntry<K, V> getNextInWriteQueue();

        public void setNextInWriteQueue(ReferenceEntry<K, V> var1);

        public ReferenceEntry<K, V> getPreviousInWriteQueue();

        public void setPreviousInWriteQueue(ReferenceEntry<K, V> var1);
    }

    static interface ValueReference<K, V> {
        @Nullable
        public V get();

        public V waitForValue() throws ExecutionException;

        public int getWeight();

        @Nullable
        public ReferenceEntry<K, V> getEntry();

        public ValueReference<K, V> copyFor(ReferenceQueue<V> var1, @Nullable V var2, ReferenceEntry<K, V> var3);

        public void notifyNewValue(@Nullable V var1);

        public boolean isLoading();

        public boolean isActive();
    }

    static enum EntryFactory {
        STRONG{

            @Override
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int n, @Nullable ReferenceEntry<K, V> referenceEntry) {
                return new StrongEntry<K, V>(k, n, referenceEntry);
            }
        }
        ,
        STRONG_ACCESS{

            @Override
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int n, @Nullable ReferenceEntry<K, V> referenceEntry) {
                return new StrongAccessEntry<K, V>(k, n, referenceEntry);
            }

            @Override
            <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> referenceEntry3 = super.copyEntry(segment, referenceEntry, referenceEntry2);
                this.copyAccessEntry(referenceEntry, referenceEntry3);
                return referenceEntry3;
            }
        }
        ,
        STRONG_WRITE{

            @Override
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int n, @Nullable ReferenceEntry<K, V> referenceEntry) {
                return new StrongWriteEntry<K, V>(k, n, referenceEntry);
            }

            @Override
            <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> referenceEntry3 = super.copyEntry(segment, referenceEntry, referenceEntry2);
                this.copyWriteEntry(referenceEntry, referenceEntry3);
                return referenceEntry3;
            }
        }
        ,
        STRONG_ACCESS_WRITE{

            @Override
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int n, @Nullable ReferenceEntry<K, V> referenceEntry) {
                return new StrongAccessWriteEntry<K, V>(k, n, referenceEntry);
            }

            @Override
            <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> referenceEntry3 = super.copyEntry(segment, referenceEntry, referenceEntry2);
                this.copyAccessEntry(referenceEntry, referenceEntry3);
                this.copyWriteEntry(referenceEntry, referenceEntry3);
                return referenceEntry3;
            }
        }
        ,
        WEAK{

            @Override
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int n, @Nullable ReferenceEntry<K, V> referenceEntry) {
                return new WeakEntry(segment.keyReferenceQueue, k, n, referenceEntry);
            }
        }
        ,
        WEAK_ACCESS{

            @Override
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int n, @Nullable ReferenceEntry<K, V> referenceEntry) {
                return new WeakAccessEntry(segment.keyReferenceQueue, k, n, referenceEntry);
            }

            @Override
            <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> referenceEntry3 = super.copyEntry(segment, referenceEntry, referenceEntry2);
                this.copyAccessEntry(referenceEntry, referenceEntry3);
                return referenceEntry3;
            }
        }
        ,
        WEAK_WRITE{

            @Override
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int n, @Nullable ReferenceEntry<K, V> referenceEntry) {
                return new WeakWriteEntry(segment.keyReferenceQueue, k, n, referenceEntry);
            }

            @Override
            <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> referenceEntry3 = super.copyEntry(segment, referenceEntry, referenceEntry2);
                this.copyWriteEntry(referenceEntry, referenceEntry3);
                return referenceEntry3;
            }
        }
        ,
        WEAK_ACCESS_WRITE{

            @Override
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int n, @Nullable ReferenceEntry<K, V> referenceEntry) {
                return new WeakAccessWriteEntry(segment.keyReferenceQueue, k, n, referenceEntry);
            }

            @Override
            <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> referenceEntry3 = super.copyEntry(segment, referenceEntry, referenceEntry2);
                this.copyAccessEntry(referenceEntry, referenceEntry3);
                this.copyWriteEntry(referenceEntry, referenceEntry3);
                return referenceEntry3;
            }
        };

        static final int ACCESS_MASK = 1;
        static final int WRITE_MASK = 2;
        static final int WEAK_MASK = 4;
        static final EntryFactory[] factories;

        private EntryFactory() {
        }

        static EntryFactory getFactory(Strength strength, boolean bl, boolean bl2) {
            int n = (strength == Strength.WEAK ? 4 : 0) | (bl ? 1 : 0) | (bl2 ? 2 : 0);
            return factories[n];
        }

        abstract <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> var1, K var2, int var3, @Nullable ReferenceEntry<K, V> var4);

        <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            return this.newEntry(segment, referenceEntry.getKey(), referenceEntry.getHash(), referenceEntry2);
        }

        <K, V> void copyAccessEntry(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            referenceEntry2.setAccessTime(referenceEntry.getAccessTime());
            LocalCache.connectAccessOrder(referenceEntry.getPreviousInAccessQueue(), referenceEntry2);
            LocalCache.connectAccessOrder(referenceEntry2, referenceEntry.getNextInAccessQueue());
            LocalCache.nullifyAccessOrder(referenceEntry);
        }

        <K, V> void copyWriteEntry(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            referenceEntry2.setWriteTime(referenceEntry.getWriteTime());
            LocalCache.connectWriteOrder(referenceEntry.getPreviousInWriteQueue(), referenceEntry2);
            LocalCache.connectWriteOrder(referenceEntry2, referenceEntry.getNextInWriteQueue());
            LocalCache.nullifyWriteOrder(referenceEntry);
        }

        EntryFactory(1 var3_3) {
            this();
        }

        static {
            factories = new EntryFactory[]{STRONG, STRONG_ACCESS, STRONG_WRITE, STRONG_ACCESS_WRITE, WEAK, WEAK_ACCESS, WEAK_WRITE, WEAK_ACCESS_WRITE};
        }
    }

    static enum Strength {
        STRONG{

            @Override
            <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, V v, int n) {
                return n == 1 ? new StrongValueReference(v) : new WeightedStrongValueReference(v, n);
            }

            @Override
            Equivalence<Object> defaultEquivalence() {
                return Equivalence.equals();
            }
        }
        ,
        SOFT{

            @Override
            <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, V v, int n) {
                return n == 1 ? new SoftValueReference(segment.valueReferenceQueue, v, referenceEntry) : new WeightedSoftValueReference(segment.valueReferenceQueue, v, referenceEntry, n);
            }

            @Override
            Equivalence<Object> defaultEquivalence() {
                return Equivalence.identity();
            }
        }
        ,
        WEAK{

            @Override
            <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, V v, int n) {
                return n == 1 ? new WeakValueReference(segment.valueReferenceQueue, v, referenceEntry) : new WeightedWeakValueReference(segment.valueReferenceQueue, v, referenceEntry, n);
            }

            @Override
            Equivalence<Object> defaultEquivalence() {
                return Equivalence.identity();
            }
        };


        private Strength() {
        }

        abstract <K, V> ValueReference<K, V> referenceValue(Segment<K, V> var1, ReferenceEntry<K, V> var2, V var3, int var4);

        abstract Equivalence<Object> defaultEquivalence();

        Strength(1 var3_3) {
            this();
        }
    }
}

