/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;

@GwtCompatible
public final class AtomicLongMap<K>
implements Serializable {
    private final ConcurrentHashMap<K, Long> map;
    private transient Map<K, Long> asMap;

    private AtomicLongMap(ConcurrentHashMap<K, Long> concurrentHashMap) {
        this.map = Preconditions.checkNotNull(concurrentHashMap);
    }

    public static <K> AtomicLongMap<K> create() {
        return new AtomicLongMap(new ConcurrentHashMap());
    }

    public static <K> AtomicLongMap<K> create(Map<? extends K, ? extends Long> map) {
        AtomicLongMap<? extends K> atomicLongMap = AtomicLongMap.create();
        atomicLongMap.putAll(map);
        return atomicLongMap;
    }

    public long get(K k) {
        return this.map.getOrDefault(k, 0L);
    }

    @CanIgnoreReturnValue
    public long incrementAndGet(K k) {
        return this.addAndGet(k, 1L);
    }

    @CanIgnoreReturnValue
    public long decrementAndGet(K k) {
        return this.addAndGet(k, -1L);
    }

    @CanIgnoreReturnValue
    public long addAndGet(K k, long l) {
        return this.accumulateAndGet(k, l, Long::sum);
    }

    @CanIgnoreReturnValue
    public long getAndIncrement(K k) {
        return this.getAndAdd(k, 1L);
    }

    @CanIgnoreReturnValue
    public long getAndDecrement(K k) {
        return this.getAndAdd(k, -1L);
    }

    @CanIgnoreReturnValue
    public long getAndAdd(K k, long l) {
        return this.getAndAccumulate(k, l, Long::sum);
    }

    @CanIgnoreReturnValue
    public long updateAndGet(K k, LongUnaryOperator longUnaryOperator) {
        Preconditions.checkNotNull(longUnaryOperator);
        return this.map.compute(k, (arg_0, arg_1) -> AtomicLongMap.lambda$updateAndGet$0(longUnaryOperator, arg_0, arg_1));
    }

    @CanIgnoreReturnValue
    public long getAndUpdate(K k, LongUnaryOperator longUnaryOperator) {
        Preconditions.checkNotNull(longUnaryOperator);
        AtomicLong atomicLong = new AtomicLong();
        this.map.compute(k, (arg_0, arg_1) -> AtomicLongMap.lambda$getAndUpdate$1(atomicLong, longUnaryOperator, arg_0, arg_1));
        return atomicLong.get();
    }

    @CanIgnoreReturnValue
    public long accumulateAndGet(K k, long l, LongBinaryOperator longBinaryOperator) {
        Preconditions.checkNotNull(longBinaryOperator);
        return this.updateAndGet(k, arg_0 -> AtomicLongMap.lambda$accumulateAndGet$2(longBinaryOperator, l, arg_0));
    }

    @CanIgnoreReturnValue
    public long getAndAccumulate(K k, long l, LongBinaryOperator longBinaryOperator) {
        Preconditions.checkNotNull(longBinaryOperator);
        return this.getAndUpdate(k, arg_0 -> AtomicLongMap.lambda$getAndAccumulate$3(longBinaryOperator, l, arg_0));
    }

    @CanIgnoreReturnValue
    public long put(K k, long l) {
        return this.getAndUpdate(k, arg_0 -> AtomicLongMap.lambda$put$4(l, arg_0));
    }

    public void putAll(Map<? extends K, ? extends Long> map) {
        map.forEach(this::put);
    }

    @CanIgnoreReturnValue
    public long remove(K k) {
        Long l = this.map.remove(k);
        return l == null ? 0L : l;
    }

    @Beta
    @CanIgnoreReturnValue
    public boolean removeIfZero(K k) {
        return this.remove(k, 0L);
    }

    public void removeAllZeros() {
        this.map.values().removeIf(AtomicLongMap::lambda$removeAllZeros$5);
    }

    public long sum() {
        return this.map.values().stream().mapToLong(Long::longValue).sum();
    }

    public Map<K, Long> asMap() {
        Map<K, Long> map = this.asMap;
        return map == null ? (this.asMap = this.createAsMap()) : map;
    }

    private Map<K, Long> createAsMap() {
        return Collections.unmodifiableMap(this.map);
    }

    public boolean containsKey(Object object) {
        return this.map.containsKey(object);
    }

    public int size() {
        return this.map.size();
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public void clear() {
        this.map.clear();
    }

    public String toString() {
        return this.map.toString();
    }

    long putIfAbsent(K k, long l) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        Long l2 = this.map.compute(k, (arg_0, arg_1) -> AtomicLongMap.lambda$putIfAbsent$6(atomicBoolean, l, arg_0, arg_1));
        return atomicBoolean.get() ? 0L : l2;
    }

    boolean replace(K k, long l, long l2) {
        if (l == 0L) {
            return this.putIfAbsent(k, l2) == 0L;
        }
        return this.map.replace(k, l, l2);
    }

    boolean remove(K k, long l) {
        return this.map.remove(k, l);
    }

    private static Long lambda$putIfAbsent$6(AtomicBoolean atomicBoolean, long l, Object object, Long l2) {
        if (l2 == null || l2 == 0L) {
            atomicBoolean.set(false);
            return l;
        }
        return l2;
    }

    private static boolean lambda$removeAllZeros$5(Long l) {
        return l == 0L;
    }

    private static long lambda$put$4(long l, long l2) {
        return l;
    }

    private static long lambda$getAndAccumulate$3(LongBinaryOperator longBinaryOperator, long l, long l2) {
        return longBinaryOperator.applyAsLong(l2, l);
    }

    private static long lambda$accumulateAndGet$2(LongBinaryOperator longBinaryOperator, long l, long l2) {
        return longBinaryOperator.applyAsLong(l2, l);
    }

    private static Long lambda$getAndUpdate$1(AtomicLong atomicLong, LongUnaryOperator longUnaryOperator, Object object, Long l) {
        long l2 = l == null ? 0L : l;
        atomicLong.set(l2);
        return longUnaryOperator.applyAsLong(l2);
    }

    private static Long lambda$updateAndGet$0(LongUnaryOperator longUnaryOperator, Object object, Long l) {
        return longUnaryOperator.applyAsLong(l == null ? 0L : l);
    }
}

