/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.flare;

import com.viaversion.viaversion.libs.flare.SyncMap;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

final class SyncMapImpl<K, V>
extends AbstractMap<K, V>
implements SyncMap<K, V> {
    private final transient Object lock = new Object();
    private volatile transient Map<K, SyncMap.ExpungingEntry<V>> read;
    private volatile transient boolean amended;
    private transient Map<K, SyncMap.ExpungingEntry<V>> dirty;
    private transient int misses;
    private final transient IntFunction<Map<K, SyncMap.ExpungingEntry<V>>> function;
    private transient EntrySetView entrySet;

    SyncMapImpl(@NonNull IntFunction<Map<K, SyncMap.ExpungingEntry<V>>> intFunction, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Initial capacity must be greater than 0");
        }
        this.function = intFunction;
        this.read = intFunction.apply(n);
    }

    @Override
    public int size() {
        this.promote();
        int n = 0;
        for (SyncMap.ExpungingEntry<V> expungingEntry : this.read.values()) {
            if (!expungingEntry.exists()) continue;
            ++n;
        }
        return n;
    }

    @Override
    public boolean isEmpty() {
        this.promote();
        for (SyncMap.ExpungingEntry<V> expungingEntry : this.read.values()) {
            if (!expungingEntry.exists()) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean containsKey(@Nullable Object object) {
        SyncMap.ExpungingEntry<V> expungingEntry = this.getEntry(object);
        return expungingEntry != null && expungingEntry.exists();
    }

    @Override
    public @Nullable V get(@Nullable Object object) {
        SyncMap.ExpungingEntry<V> expungingEntry = this.getEntry(object);
        return expungingEntry != null ? (V)expungingEntry.get() : null;
    }

    @Override
    public @NonNull V getOrDefault(@Nullable Object object, @NonNull V v) {
        Objects.requireNonNull(v, "defaultValue");
        SyncMap.ExpungingEntry<V> expungingEntry = this.getEntry(object);
        return expungingEntry != null ? expungingEntry.getOr(v) : v;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private @Nullable SyncMap.ExpungingEntry<V> getEntry(@Nullable Object object) {
        SyncMap.ExpungingEntry<V> expungingEntry = this.read.get(object);
        if (expungingEntry == null && this.amended) {
            Object object2 = this.lock;
            synchronized (object2) {
                expungingEntry = this.read.get(object);
                if (expungingEntry == null && this.amended && this.dirty != null) {
                    expungingEntry = this.dirty.get(object);
                    this.missLocked();
                }
            }
        }
        return expungingEntry;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public @Nullable V computeIfAbsent(@Nullable K k, @NonNull Function<? super K, ? extends V> function) {
        SyncMap.InsertionResult<Object> insertionResult;
        Objects.requireNonNull(function, "mappingFunction");
        SyncMap.ExpungingEntry<Object> expungingEntry = this.read.get(k);
        SyncMap.InsertionResult<V> insertionResult2 = insertionResult = expungingEntry != null ? expungingEntry.computeIfAbsent(k, function) : null;
        if (insertionResult != null && insertionResult.operation() == 1) {
            return insertionResult.current();
        }
        Object object = this.lock;
        synchronized (object) {
            expungingEntry = this.read.get(k);
            if (expungingEntry != null) {
                if (expungingEntry.tryUnexpungeAndCompute(k, function)) {
                    if (expungingEntry.exists()) {
                        this.dirty.put(k, expungingEntry);
                    }
                    return expungingEntry.get();
                }
                insertionResult = expungingEntry.computeIfAbsent(k, function);
            } else if (this.dirty != null && (expungingEntry = this.dirty.get(k)) != null) {
                insertionResult = expungingEntry.computeIfAbsent(k, function);
                if (insertionResult.current() == null) {
                    this.dirty.remove(k);
                }
                this.missLocked();
            } else {
                V v;
                if (!this.amended) {
                    this.dirtyLocked();
                    this.amended = true;
                }
                if ((v = function.apply(k)) != null) {
                    this.dirty.put(k, new ExpungingEntryImpl<V>(v));
                }
                return v;
            }
        }
        return insertionResult.current();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public @Nullable V computeIfPresent(@Nullable K k, @NonNull BiFunction<? super K, ? super V, ? extends V> biFunction) {
        SyncMap.InsertionResult<Object> insertionResult;
        Objects.requireNonNull(biFunction, "remappingFunction");
        SyncMap.ExpungingEntry<Object> expungingEntry = this.read.get(k);
        SyncMap.InsertionResult<V> insertionResult2 = insertionResult = expungingEntry != null ? expungingEntry.computeIfPresent(k, biFunction) : null;
        if (insertionResult != null && insertionResult.operation() == 1) {
            return insertionResult.current();
        }
        Object object = this.lock;
        synchronized (object) {
            expungingEntry = this.read.get(k);
            if (expungingEntry != null) {
                insertionResult = expungingEntry.computeIfPresent(k, biFunction);
            } else if (this.dirty != null && (expungingEntry = this.dirty.get(k)) != null) {
                insertionResult = expungingEntry.computeIfPresent(k, biFunction);
                if (insertionResult.current() == null) {
                    this.dirty.remove(k);
                }
                this.missLocked();
            }
        }
        return insertionResult != null ? (V)insertionResult.current() : null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public @Nullable V compute(@Nullable K k, @Nullable BiFunction<? super K, ? super V, ? extends V> biFunction) {
        SyncMap.InsertionResult<Object> insertionResult;
        Objects.requireNonNull(biFunction, "remappingFunction");
        SyncMap.ExpungingEntry<Object> expungingEntry = this.read.get(k);
        SyncMap.InsertionResult<V> insertionResult2 = insertionResult = expungingEntry != null ? expungingEntry.compute(k, biFunction) : null;
        if (insertionResult != null && insertionResult.operation() == 1) {
            return insertionResult.current();
        }
        Object object = this.lock;
        synchronized (object) {
            expungingEntry = this.read.get(k);
            if (expungingEntry != null) {
                if (expungingEntry.tryUnexpungeAndCompute(k, biFunction)) {
                    if (expungingEntry.exists()) {
                        this.dirty.put(k, expungingEntry);
                    }
                    return expungingEntry.get();
                }
                insertionResult = expungingEntry.compute(k, biFunction);
            } else if (this.dirty != null && (expungingEntry = this.dirty.get(k)) != null) {
                insertionResult = expungingEntry.compute(k, biFunction);
                if (insertionResult.current() == null) {
                    this.dirty.remove(k);
                }
                this.missLocked();
            } else {
                V v;
                if (!this.amended) {
                    this.dirtyLocked();
                    this.amended = true;
                }
                if ((v = biFunction.apply(k, null)) != null) {
                    this.dirty.put(k, new ExpungingEntryImpl<V>(v));
                }
                return v;
            }
        }
        return insertionResult.current();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public @Nullable V putIfAbsent(@Nullable K k, @NonNull V v) {
        SyncMap.InsertionResult<V> insertionResult;
        Objects.requireNonNull(v, "value");
        SyncMap.ExpungingEntry<V> expungingEntry = this.read.get(k);
        SyncMap.InsertionResult<V> insertionResult2 = insertionResult = expungingEntry != null ? expungingEntry.setIfAbsent(v) : null;
        if (insertionResult != null && insertionResult.operation() == 1) {
            return insertionResult.previous();
        }
        Object object = this.lock;
        synchronized (object) {
            expungingEntry = this.read.get(k);
            if (expungingEntry != null) {
                if (expungingEntry.tryUnexpungeAndSet(v)) {
                    this.dirty.put(k, expungingEntry);
                    return null;
                }
                insertionResult = expungingEntry.setIfAbsent(v);
            } else if (this.dirty != null && (expungingEntry = this.dirty.get(k)) != null) {
                insertionResult = expungingEntry.setIfAbsent(v);
                this.missLocked();
            } else {
                if (!this.amended) {
                    this.dirtyLocked();
                    this.amended = true;
                }
                this.dirty.put(k, new ExpungingEntryImpl<V>(v));
                return null;
            }
        }
        return insertionResult.previous();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public @Nullable V put(@Nullable K k, @NonNull V v) {
        V v2;
        Objects.requireNonNull(v, "value");
        SyncMap.ExpungingEntry<V> expungingEntry = this.read.get(k);
        V v3 = v2 = expungingEntry != null ? (V)expungingEntry.get() : null;
        if (expungingEntry != null && expungingEntry.trySet(v)) {
            return v2;
        }
        Object object = this.lock;
        synchronized (object) {
            expungingEntry = this.read.get(k);
            if (expungingEntry != null) {
                v2 = expungingEntry.get();
                if (expungingEntry.tryUnexpungeAndSet(v)) {
                    this.dirty.put(k, expungingEntry);
                } else {
                    expungingEntry.set(v);
                }
            } else if (this.dirty != null && (expungingEntry = this.dirty.get(k)) != null) {
                v2 = expungingEntry.get();
                expungingEntry.set(v);
            } else {
                if (!this.amended) {
                    this.dirtyLocked();
                    this.amended = true;
                }
                this.dirty.put(k, new ExpungingEntryImpl<V>(v));
                return null;
            }
        }
        return v2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public @Nullable V remove(@Nullable Object object) {
        SyncMap.ExpungingEntry<V> expungingEntry = this.read.get(object);
        if (expungingEntry == null && this.amended) {
            Object object2 = this.lock;
            synchronized (object2) {
                expungingEntry = this.read.get(object);
                if (expungingEntry == null && this.amended && this.dirty != null) {
                    expungingEntry = this.dirty.remove(object);
                    this.missLocked();
                }
            }
        }
        return expungingEntry != null ? (V)expungingEntry.clear() : null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean remove(@Nullable Object object, @NonNull Object object2) {
        Objects.requireNonNull(object2, "value");
        SyncMap.ExpungingEntry<Object> expungingEntry = this.read.get(object);
        if (expungingEntry == null && this.amended) {
            Object object3 = this.lock;
            synchronized (object3) {
                expungingEntry = this.read.get(object);
                if (expungingEntry == null && this.amended && this.dirty != null) {
                    boolean bl;
                    expungingEntry = this.dirty.get(object);
                    boolean bl2 = bl = expungingEntry != null && expungingEntry.replace(object2, null);
                    if (bl) {
                        this.dirty.remove(object);
                    }
                    this.missLocked();
                    return bl;
                }
            }
        }
        return expungingEntry != null && expungingEntry.replace(object2, null);
    }

    @Override
    public @Nullable V replace(@Nullable K k, @NonNull V v) {
        Objects.requireNonNull(v, "value");
        SyncMap.ExpungingEntry<V> expungingEntry = this.getEntry(k);
        return expungingEntry != null ? (V)expungingEntry.tryReplace(v) : null;
    }

    @Override
    public boolean replace(@Nullable K k, @NonNull V v, @NonNull V v2) {
        Objects.requireNonNull(v, "oldValue");
        Objects.requireNonNull(v2, "newValue");
        SyncMap.ExpungingEntry<V> expungingEntry = this.getEntry(k);
        return expungingEntry != null && expungingEntry.replace(v, v2);
    }

    @Override
    public void forEach(@NonNull BiConsumer<? super K, ? super V> biConsumer) {
        Objects.requireNonNull(biConsumer, "action");
        this.promote();
        for (Map.Entry<K, SyncMap.ExpungingEntry<V>> entry : this.read.entrySet()) {
            V v = entry.getValue().get();
            if (v == null) continue;
            biConsumer.accept(entry.getKey(), v);
        }
    }

    @Override
    public void replaceAll(@NonNull BiFunction<? super K, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction, "function");
        this.promote();
        for (Map.Entry<K, SyncMap.ExpungingEntry<V>> entry : this.read.entrySet()) {
            SyncMap.ExpungingEntry<V> expungingEntry = entry.getValue();
            V v = expungingEntry.get();
            if (v == null) continue;
            expungingEntry.tryReplace(biFunction.apply(entry.getKey(), v));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void clear() {
        Object object = this.lock;
        synchronized (object) {
            this.read = this.function.apply(this.read.size());
            this.dirty = null;
            this.amended = false;
            this.misses = 0;
        }
    }

    @Override
    public @NonNull Set<Map.Entry<K, V>> entrySet() {
        if (this.entrySet != null) {
            return this.entrySet;
        }
        this.entrySet = new EntrySetView(this);
        return this.entrySet;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void promote() {
        if (this.amended) {
            Object object = this.lock;
            synchronized (object) {
                if (this.amended) {
                    this.promoteLocked();
                }
            }
        }
    }

    private void missLocked() {
        ++this.misses;
        if (this.misses < this.dirty.size()) {
            return;
        }
        this.promoteLocked();
    }

    private void promoteLocked() {
        this.read = this.dirty;
        this.amended = false;
        this.dirty = null;
        this.misses = 0;
    }

    private void dirtyLocked() {
        if (this.dirty != null) {
            return;
        }
        this.dirty = this.function.apply(this.read.size());
        for (Map.Entry<K, SyncMap.ExpungingEntry<V>> entry : this.read.entrySet()) {
            if (entry.getValue().tryExpunge()) continue;
            this.dirty.put(entry.getKey(), entry.getValue());
        }
    }

    static void access$000(SyncMapImpl syncMapImpl) {
        syncMapImpl.promote();
    }

    static Map access$100(SyncMapImpl syncMapImpl) {
        return syncMapImpl.read;
    }

    final class EntryIterator
    implements Iterator<Map.Entry<K, V>> {
        private final Iterator<Map.Entry<K, SyncMap.ExpungingEntry<V>>> backingIterator;
        private Map.Entry<K, V> next;
        private Map.Entry<K, V> current;
        final SyncMapImpl this$0;

        EntryIterator(@NonNull SyncMapImpl syncMapImpl, Iterator<Map.Entry<K, SyncMap.ExpungingEntry<V>>> iterator2) {
            this.this$0 = syncMapImpl;
            this.backingIterator = iterator2;
            this.advance();
        }

        @Override
        public boolean hasNext() {
            return this.next != null;
        }

        @Override
        public @NonNull Map.Entry<K, V> next() {
            Map.Entry entry = this.next;
            if (entry == null) {
                throw new NoSuchElementException();
            }
            this.current = entry;
            this.advance();
            return entry;
        }

        @Override
        public void remove() {
            Map.Entry entry = this.current;
            if (entry == null) {
                throw new IllegalStateException();
            }
            this.current = null;
            this.this$0.remove(entry.getKey());
        }

        private void advance() {
            this.next = null;
            while (this.backingIterator.hasNext()) {
                Map.Entry entry = this.backingIterator.next();
                Object v = entry.getValue().get();
                if (v == null) continue;
                this.next = new MapEntry(this.this$0, entry.getKey(), v);
                return;
            }
        }

        @Override
        public @NonNull Object next() {
            return this.next();
        }
    }

    final class EntrySetView
    extends AbstractSet<Map.Entry<K, V>> {
        final SyncMapImpl this$0;

        EntrySetView(SyncMapImpl syncMapImpl) {
            this.this$0 = syncMapImpl;
        }

        @Override
        public int size() {
            return this.this$0.size();
        }

        @Override
        public boolean contains(@Nullable Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            Object v = this.this$0.get(entry.getKey());
            return v != null && Objects.equals(v, entry.getValue());
        }

        @Override
        public boolean add(@NonNull Map.Entry<K, V> entry) {
            Objects.requireNonNull(entry, "entry");
            return this.this$0.put(entry.getKey(), entry.getValue()) == null;
        }

        @Override
        public boolean remove(@Nullable Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            return this.this$0.remove(entry.getKey(), entry.getValue());
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }

        @Override
        public @NonNull Iterator<Map.Entry<K, V>> iterator() {
            SyncMapImpl.access$000(this.this$0);
            return new EntryIterator(this.this$0, SyncMapImpl.access$100(this.this$0).entrySet().iterator());
        }

        @Override
        public boolean add(@NonNull Object object) {
            return this.add((Map.Entry)object);
        }
    }

    final class MapEntry
    implements Map.Entry<K, V> {
        private final K key;
        private V value;
        final SyncMapImpl this$0;

        MapEntry(@Nullable SyncMapImpl syncMapImpl, @NonNull K k, V v) {
            this.this$0 = syncMapImpl;
            this.key = k;
            this.value = v;
        }

        @Override
        public @Nullable K getKey() {
            return this.key;
        }

        @Override
        public @NonNull V getValue() {
            return this.value;
        }

        @Override
        public @Nullable V setValue(@NonNull V v) {
            Objects.requireNonNull(v, "value");
            Object v2 = this.this$0.put(this.key, v);
            this.value = v;
            return v2;
        }

        public @NonNull String toString() {
            return "SyncMapImpl.MapEntry{key=" + this.getKey() + ", value=" + this.getValue() + "}";
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (this == object) {
                return false;
            }
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            return Objects.equals(this.getKey(), entry.getKey()) && Objects.equals(this.getValue(), entry.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.getKey(), this.getValue());
        }
    }

    static final class InsertionResultImpl<V>
    implements SyncMap.InsertionResult<V> {
        private static final byte UNCHANGED = 0;
        private static final byte UPDATED = 1;
        private static final byte EXPUNGED = 2;
        private final byte operation;
        private final V previous;
        private final V current;

        InsertionResultImpl(byte by, @Nullable V v, @Nullable V v2) {
            this.operation = by;
            this.previous = v;
            this.current = v2;
        }

        @Override
        public byte operation() {
            return this.operation;
        }

        @Override
        public @Nullable V previous() {
            return this.previous;
        }

        @Override
        public @Nullable V current() {
            return this.current;
        }
    }

    static final class ExpungingEntryImpl<V>
    implements SyncMap.ExpungingEntry<V> {
        private static final AtomicReferenceFieldUpdater<ExpungingEntryImpl, Object> UPDATER = AtomicReferenceFieldUpdater.newUpdater(ExpungingEntryImpl.class, Object.class, "value");
        private static final Object EXPUNGED = new Object();
        private volatile Object value;

        ExpungingEntryImpl(@NonNull V v) {
            this.value = v;
        }

        @Override
        public boolean exists() {
            return this.value != null && this.value != EXPUNGED;
        }

        @Override
        public @Nullable V get() {
            return (V)(this.value == EXPUNGED ? null : this.value);
        }

        @Override
        public @NonNull V getOr(@NonNull V v) {
            Object object = this.value;
            return (V)(object != null && object != EXPUNGED ? this.value : v);
        }

        @Override
        public @NonNull SyncMap.InsertionResult<V> setIfAbsent(@NonNull V v) {
            do {
                Object object;
                if ((object = this.value) == EXPUNGED) {
                    return new InsertionResultImpl<Object>(2, null, null);
                }
                if (object == null) continue;
                return new InsertionResultImpl<Object>(0, object, object);
            } while (!UPDATER.compareAndSet(this, null, v));
            return new InsertionResultImpl<Object>(1, null, v);
        }

        @Override
        public <K> @NonNull SyncMap.InsertionResult<V> computeIfAbsent(@Nullable K k, @NonNull Function<? super K, ? extends V> function) {
            Object v = null;
            do {
                Object object;
                if ((object = this.value) == EXPUNGED) {
                    return new InsertionResultImpl<Object>(2, null, null);
                }
                if (object == null) continue;
                return new InsertionResultImpl<Object>(0, object, object);
            } while (!UPDATER.compareAndSet(this, null, v != null ? v : (v = (Object)function.apply(k))));
            return new InsertionResultImpl<Object>(1, null, v);
        }

        @Override
        public <K> @NonNull SyncMap.InsertionResult<V> computeIfPresent(@Nullable K k, @NonNull BiFunction<? super K, ? super V, ? extends V> biFunction) {
            Object object;
            Object v = null;
            do {
                if ((object = this.value) == EXPUNGED) {
                    return new InsertionResultImpl<Object>(2, null, null);
                }
                if (object != null) continue;
                return new InsertionResultImpl<Object>(0, null, null);
            } while (!UPDATER.compareAndSet(this, object, v != null ? v : (v = (Object)biFunction.apply(k, object))));
            return new InsertionResultImpl<Object>(1, object, v);
        }

        @Override
        public <K> @NonNull SyncMap.InsertionResult<V> compute(@Nullable K k, @NonNull BiFunction<? super K, ? super V, ? extends V> biFunction) {
            Object object;
            Object v = null;
            do {
                if ((object = this.value) != EXPUNGED) continue;
                return new InsertionResultImpl<Object>(2, null, null);
            } while (!UPDATER.compareAndSet(this, object, v != null ? v : (v = (Object)biFunction.apply(k, object))));
            return new InsertionResultImpl<Object>(1, object, v);
        }

        @Override
        public void set(@NonNull V v) {
            UPDATER.set(this, v);
        }

        @Override
        public boolean replace(@NonNull Object object, @Nullable V v) {
            Object object2;
            do {
                if ((object2 = this.value) != EXPUNGED && Objects.equals(object2, object)) continue;
                return true;
            } while (!UPDATER.compareAndSet(this, object2, v));
            return false;
        }

        @Override
        public @Nullable V clear() {
            Object object;
            do {
                if ((object = this.value) != null && object != EXPUNGED) continue;
                return null;
            } while (!UPDATER.compareAndSet(this, object, null));
            return (V)object;
        }

        @Override
        public boolean trySet(@NonNull V v) {
            Object object;
            do {
                if ((object = this.value) != EXPUNGED) continue;
                return true;
            } while (!UPDATER.compareAndSet(this, object, v));
            return false;
        }

        @Override
        public @Nullable V tryReplace(@NonNull V v) {
            Object object;
            do {
                if ((object = this.value) != null && object != EXPUNGED) continue;
                return null;
            } while (!UPDATER.compareAndSet(this, object, v));
            return (V)object;
        }

        @Override
        public boolean tryExpunge() {
            while (this.value == null) {
                if (!UPDATER.compareAndSet(this, null, EXPUNGED)) continue;
                return false;
            }
            return this.value == EXPUNGED;
        }

        @Override
        public boolean tryUnexpungeAndSet(@NonNull V v) {
            return UPDATER.compareAndSet(this, EXPUNGED, v);
        }

        @Override
        public <K> boolean tryUnexpungeAndCompute(@Nullable K k, @NonNull Function<? super K, ? extends V> function) {
            if (this.value == EXPUNGED) {
                V v = function.apply(k);
                return UPDATER.compareAndSet(this, EXPUNGED, v);
            }
            return true;
        }

        @Override
        public <K> boolean tryUnexpungeAndCompute(@Nullable K k, @NonNull BiFunction<? super K, ? super V, ? extends V> biFunction) {
            if (this.value == EXPUNGED) {
                V v = biFunction.apply(k, null);
                return UPDATER.compareAndSet(this, EXPUNGED, v);
            }
            return true;
        }
    }
}

