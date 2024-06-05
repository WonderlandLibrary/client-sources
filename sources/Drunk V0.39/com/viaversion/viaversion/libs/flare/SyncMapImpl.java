/*
 * Decompiled with CFR 0.152.
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
import java.util.function.Consumer;
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

    SyncMapImpl(@NonNull IntFunction<Map<K, SyncMap.ExpungingEntry<V>>> function, int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Initial capacity must be greater than 0");
        }
        this.function = function;
        this.read = function.apply(initialCapacity);
    }

    @Override
    public int size() {
        this.promote();
        int size = 0;
        Iterator<SyncMap.ExpungingEntry<V>> iterator = this.read.values().iterator();
        while (iterator.hasNext()) {
            SyncMap.ExpungingEntry<V> value = iterator.next();
            if (!value.exists()) continue;
            ++size;
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        SyncMap.ExpungingEntry<V> value;
        this.promote();
        Iterator<SyncMap.ExpungingEntry<V>> iterator = this.read.values().iterator();
        do {
            if (!iterator.hasNext()) return true;
        } while (!(value = iterator.next()).exists());
        return false;
    }

    @Override
    public boolean containsKey(@Nullable Object key) {
        SyncMap.ExpungingEntry<V> entry = this.getEntry(key);
        if (entry == null) return false;
        if (!entry.exists()) return false;
        return true;
    }

    @Override
    public @Nullable V get(@Nullable Object key) {
        V v;
        SyncMap.ExpungingEntry<V> entry = this.getEntry(key);
        if (entry != null) {
            v = entry.get();
            return v;
        }
        v = null;
        return v;
    }

    @Override
    public @NonNull V getOrDefault(@Nullable Object key, @NonNull V defaultValue) {
        V v;
        Objects.requireNonNull(defaultValue, "defaultValue");
        SyncMap.ExpungingEntry<V> entry = this.getEntry(key);
        if (entry != null) {
            v = entry.getOr(defaultValue);
            return v;
        }
        v = defaultValue;
        return v;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private @Nullable SyncMap.ExpungingEntry<V> getEntry(@Nullable Object key) {
        SyncMap.ExpungingEntry<V> entry = this.read.get(key);
        if (entry != null) return entry;
        if (!this.amended) return entry;
        Object object = this.lock;
        synchronized (object) {
            entry = this.read.get(key);
            if (entry != null) return entry;
            if (!this.amended) return entry;
            if (this.dirty == null) return entry;
            entry = this.dirty.get(key);
            this.missLocked();
            return entry;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public @Nullable V computeIfAbsent(@Nullable K key, @NonNull Function<? super K, ? extends V> mappingFunction) {
        SyncMap.InsertionResult<Object> result;
        Objects.requireNonNull(mappingFunction, "mappingFunction");
        SyncMap.ExpungingEntry<Object> entry = this.read.get(key);
        SyncMap.InsertionResult<V> insertionResult = result = entry != null ? entry.computeIfAbsent(key, mappingFunction) : null;
        if (result != null) {
            if (result.operation() != 2) return result.current();
        }
        Object object = this.lock;
        synchronized (object) {
            entry = this.read.get(key);
            if (entry != null) {
                if (entry.tryUnexpungeAndCompute(key, mappingFunction)) {
                    if (!entry.exists()) return entry.get();
                    this.dirty.put(key, entry);
                    return entry.get();
                }
                result = entry.computeIfAbsent(key, mappingFunction);
            } else if (this.dirty != null && (entry = this.dirty.get(key)) != null) {
                result = entry.computeIfAbsent(key, mappingFunction);
                if (result.current() == null) {
                    this.dirty.remove(key);
                }
                this.missLocked();
            } else {
                V computed;
                if (!this.amended) {
                    this.dirtyLocked();
                    this.amended = true;
                }
                if ((computed = mappingFunction.apply(key)) == null) return computed;
                this.dirty.put(key, new ExpungingEntryImpl<V>(computed));
                return computed;
            }
            return result.current();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public @Nullable V computeIfPresent(@Nullable K key, @NonNull BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        V v;
        SyncMap.InsertionResult<Object> result;
        Objects.requireNonNull(remappingFunction, "remappingFunction");
        SyncMap.ExpungingEntry<Object> entry = this.read.get(key);
        SyncMap.InsertionResult<V> insertionResult = result = entry != null ? entry.computeIfPresent(key, remappingFunction) : null;
        if (result == null || result.operation() == 2) {
            Object object = this.lock;
            synchronized (object) {
                entry = this.read.get(key);
                if (entry != null) {
                    result = entry.computeIfPresent(key, remappingFunction);
                } else if (this.dirty != null && (entry = this.dirty.get(key)) != null) {
                    result = entry.computeIfPresent(key, remappingFunction);
                    if (result.current() == null) {
                        this.dirty.remove(key);
                    }
                    this.missLocked();
                }
            }
        }
        if (result != null) {
            v = result.current();
            return v;
        }
        v = null;
        return v;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public @Nullable V compute(@Nullable K key, @Nullable BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        SyncMap.InsertionResult<Object> result;
        Objects.requireNonNull(remappingFunction, "remappingFunction");
        SyncMap.ExpungingEntry<Object> entry = this.read.get(key);
        SyncMap.InsertionResult<V> insertionResult = result = entry != null ? entry.compute(key, remappingFunction) : null;
        if (result != null) {
            if (result.operation() != 2) return result.current();
        }
        Object object = this.lock;
        synchronized (object) {
            entry = this.read.get(key);
            if (entry != null) {
                if (entry.tryUnexpungeAndCompute(key, remappingFunction)) {
                    if (!entry.exists()) return entry.get();
                    this.dirty.put(key, entry);
                    return entry.get();
                }
                result = entry.compute(key, remappingFunction);
            } else if (this.dirty != null && (entry = this.dirty.get(key)) != null) {
                result = entry.compute(key, remappingFunction);
                if (result.current() == null) {
                    this.dirty.remove(key);
                }
                this.missLocked();
            } else {
                V computed;
                if (!this.amended) {
                    this.dirtyLocked();
                    this.amended = true;
                }
                if ((computed = remappingFunction.apply(key, null)) == null) return computed;
                this.dirty.put(key, new ExpungingEntryImpl<V>(computed));
                return computed;
            }
            return result.current();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public @Nullable V putIfAbsent(@Nullable K key, @NonNull V value) {
        V v;
        SyncMap.InsertionResult<V> result;
        Objects.requireNonNull(value, "value");
        SyncMap.ExpungingEntry<V> entry = this.read.get(key);
        SyncMap.InsertionResult<V> insertionResult = result = entry != null ? entry.setIfAbsent(value) : null;
        if (result == null || result.operation() == 2) {
            Object object = this.lock;
            synchronized (object) {
                entry = this.read.get(key);
                if (entry != null) {
                    if (entry.tryUnexpungeAndSet(value)) {
                        this.dirty.put(key, entry);
                    } else {
                        result = entry.setIfAbsent(value);
                    }
                } else if (this.dirty != null && (entry = this.dirty.get(key)) != null) {
                    result = entry.setIfAbsent(value);
                    this.missLocked();
                } else {
                    if (!this.amended) {
                        this.dirtyLocked();
                        this.amended = true;
                    }
                    this.dirty.put(key, new ExpungingEntryImpl<V>(value));
                }
            }
        }
        if (result != null) {
            v = result.previous();
            return v;
        }
        v = null;
        return v;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public @Nullable V put(@Nullable K key, @NonNull V value) {
        V previous;
        Objects.requireNonNull(value, "value");
        SyncMap.ExpungingEntry<V> entry = this.read.get(key);
        V v = previous = entry != null ? (V)entry.get() : null;
        if (entry != null && entry.trySet(value)) {
            return previous;
        }
        Object object = this.lock;
        synchronized (object) {
            entry = this.read.get(key);
            if (entry != null) {
                previous = entry.get();
                if (entry.tryUnexpungeAndSet(value)) {
                    this.dirty.put(key, entry);
                } else {
                    entry.set(value);
                }
            } else if (this.dirty != null && (entry = this.dirty.get(key)) != null) {
                previous = entry.get();
                entry.set(value);
                this.missLocked();
            } else {
                if (!this.amended) {
                    this.dirtyLocked();
                    this.amended = true;
                }
                this.dirty.put(key, new ExpungingEntryImpl<V>(value));
            }
            return previous;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public @Nullable V remove(@Nullable Object key) {
        V v;
        SyncMap.ExpungingEntry<V> entry = this.read.get(key);
        if (entry == null && this.amended) {
            Object object = this.lock;
            synchronized (object) {
                entry = this.read.get(key);
                if (entry == null && this.amended && this.dirty != null) {
                    entry = this.dirty.remove(key);
                    this.missLocked();
                }
            }
        }
        if (entry != null) {
            v = entry.clear();
            return v;
        }
        v = null;
        return v;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean remove(@Nullable Object key, @NonNull Object value) {
        Objects.requireNonNull(value, "value");
        SyncMap.ExpungingEntry<Object> entry = this.read.get(key);
        if (entry == null && this.amended) {
            Object object = this.lock;
            synchronized (object) {
                entry = this.read.get(key);
                if (entry == null && this.amended && this.dirty != null) {
                    boolean present;
                    entry = this.dirty.get(key);
                    boolean bl = present = entry != null && entry.replace(value, null);
                    if (present) {
                        this.dirty.remove(key);
                    }
                    this.missLocked();
                    return present;
                }
            }
        }
        if (entry == null) return false;
        if (!entry.replace(value, null)) return false;
        return true;
    }

    @Override
    public @Nullable V replace(@Nullable K key, @NonNull V value) {
        V v;
        Objects.requireNonNull(value, "value");
        SyncMap.ExpungingEntry<V> entry = this.getEntry(key);
        if (entry != null) {
            v = entry.tryReplace(value);
            return v;
        }
        v = null;
        return v;
    }

    @Override
    public boolean replace(@Nullable K key, @NonNull V oldValue, @NonNull V newValue) {
        Objects.requireNonNull(oldValue, "oldValue");
        Objects.requireNonNull(newValue, "newValue");
        SyncMap.ExpungingEntry<V> entry = this.getEntry(key);
        if (entry == null) return false;
        if (!entry.replace(oldValue, newValue)) return false;
        return true;
    }

    @Override
    public void forEach(@NonNull BiConsumer<? super K, ? super V> action) {
        Objects.requireNonNull(action, "action");
        this.promote();
        Iterator<Map.Entry<K, SyncMap.ExpungingEntry<V>>> iterator = this.read.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<K, SyncMap.ExpungingEntry<V>> that = iterator.next();
            V value = that.getValue().get();
            if (value == null) continue;
            action.accept(that.getKey(), value);
        }
    }

    @Override
    public void replaceAll(@NonNull BiFunction<? super K, ? super V, ? extends V> function) {
        Objects.requireNonNull(function, "function");
        this.promote();
        Iterator<Map.Entry<K, SyncMap.ExpungingEntry<V>>> iterator = this.read.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<K, SyncMap.ExpungingEntry<V>> that = iterator.next();
            SyncMap.ExpungingEntry<V> entry = that.getValue();
            V value = entry.get();
            if (value == null) continue;
            entry.tryReplace(function.apply(that.getKey(), value));
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
            return;
        }
    }

    @Override
    public @NonNull Set<Map.Entry<K, V>> entrySet() {
        if (this.entrySet != null) {
            return this.entrySet;
        }
        this.entrySet = new EntrySetView();
        return this.entrySet;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void promote() {
        if (!this.amended) return;
        Object object = this.lock;
        synchronized (object) {
            if (!this.amended) return;
            this.promoteLocked();
            return;
        }
    }

    private void promoteLocked() {
        if (this.dirty != null) {
            this.read = this.dirty;
        }
        this.amended = false;
        this.dirty = null;
        this.misses = 0;
    }

    private void missLocked() {
        if (++this.misses < this.dirty.size()) return;
        this.promoteLocked();
    }

    private void dirtyLocked() {
        if (this.dirty != null) {
            return;
        }
        this.dirty = this.function.apply(this.read.size());
        Iterator<Map.Entry<K, SyncMap.ExpungingEntry<V>>> iterator = this.read.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<K, SyncMap.ExpungingEntry<V>> entry = iterator.next();
            if (entry.getValue().tryExpunge()) continue;
            this.dirty.put(entry.getKey(), entry.getValue());
        }
    }

    final class EntryIterator
    implements Iterator<Map.Entry<K, V>> {
        private final Iterator<Map.Entry<K, SyncMap.ExpungingEntry<V>>> backingIterator;
        private Map.Entry<K, V> next;
        private Map.Entry<K, V> current;

        EntryIterator(Iterator<Map.Entry<K, SyncMap.ExpungingEntry<V>>> backingIterator) {
            this.backingIterator = backingIterator;
            this.next = this.nextValue();
        }

        @Override
        public boolean hasNext() {
            if (this.next == null) return false;
            return true;
        }

        @Override
        public @NonNull Map.Entry<K, V> next() {
            this.current = this.next;
            if (this.current == null) {
                throw new NoSuchElementException();
            }
            this.next = this.nextValue();
            return this.current;
        }

        private @Nullable Map.Entry<K, V> nextValue() {
            Map.Entry entry;
            Object value;
            do {
                if (!this.backingIterator.hasNext()) return null;
            } while ((value = (entry = this.backingIterator.next()).getValue().get()) == null);
            return new MapEntry(entry.getKey(), value);
        }

        @Override
        public void remove() {
            if (this.current == null) {
                throw new IllegalStateException();
            }
            SyncMapImpl.this.remove(this.current.getKey());
            this.current = null;
        }

        @Override
        public void forEachRemaining(@NonNull Consumer<? super Map.Entry<K, V>> action) {
            Objects.requireNonNull(action, "action");
            if (this.next != null) {
                action.accept(this.next);
            }
            this.backingIterator.forEachRemaining((? super E entry) -> {
                Object value = ((SyncMap.ExpungingEntry)entry.getValue()).get();
                if (value == null) return;
                action.accept(new MapEntry(entry.getKey(), value));
            });
        }
    }

    final class EntrySetView
    extends AbstractSet<Map.Entry<K, V>> {
        EntrySetView() {
        }

        @Override
        public int size() {
            return SyncMapImpl.this.size();
        }

        @Override
        public boolean contains(@Nullable Object entry) {
            if (!(entry instanceof Map.Entry)) {
                return false;
            }
            Map.Entry mapEntry = (Map.Entry)entry;
            Object value = SyncMapImpl.this.get(mapEntry.getKey());
            if (value == null) return false;
            if (!Objects.equals(value, mapEntry.getValue())) return false;
            return true;
        }

        @Override
        public boolean remove(@Nullable Object entry) {
            if (!(entry instanceof Map.Entry)) {
                return false;
            }
            Map.Entry mapEntry = (Map.Entry)entry;
            if (SyncMapImpl.this.remove(mapEntry.getKey()) == null) return false;
            return true;
        }

        @Override
        public void clear() {
            SyncMapImpl.this.clear();
        }

        @Override
        public @NonNull Iterator<Map.Entry<K, V>> iterator() {
            SyncMapImpl.this.promote();
            return new EntryIterator(SyncMapImpl.this.read.entrySet().iterator());
        }
    }

    final class MapEntry
    implements Map.Entry<K, V> {
        private final K key;
        private V value;

        MapEntry(@NonNull K key, V value) {
            this.key = key;
            this.value = value;
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
        public @Nullable V setValue(@NonNull V value) {
            Objects.requireNonNull(value, "value");
            this.value = value;
            return SyncMapImpl.this.put(this.key, this.value);
        }

        public @NonNull String toString() {
            return "SyncMapImpl.MapEntry{key=" + this.getKey() + ", value=" + this.getValue() + "}";
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Map.Entry)) {
                return false;
            }
            Map.Entry that = (Map.Entry)other;
            if (!Objects.equals(this.getKey(), that.getKey())) return false;
            if (!Objects.equals(this.getValue(), that.getValue())) return false;
            return true;
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

        InsertionResultImpl(byte operation, @Nullable V previous, @Nullable V current) {
            this.operation = operation;
            this.previous = previous;
            this.current = current;
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

        ExpungingEntryImpl(@NonNull V value) {
            this.value = value;
        }

        @Override
        public boolean exists() {
            if (this.value == null) return false;
            if (this.value == EXPUNGED) return false;
            return true;
        }

        @Override
        public @Nullable V get() {
            Object object;
            if (this.value == EXPUNGED) {
                object = null;
                return (V)object;
            }
            object = this.value;
            return (V)object;
        }

        @Override
        public @NonNull V getOr(@NonNull V other) {
            Object object;
            Object value = this.value;
            if (value != null && value != EXPUNGED) {
                object = this.value;
                return object;
            }
            object = other;
            return object;
        }

        @Override
        public @NonNull SyncMap.InsertionResult<V> setIfAbsent(@NonNull V value) {
            do {
                Object previous;
                if ((previous = this.value) == EXPUNGED) {
                    return new InsertionResultImpl<Object>(2, null, null);
                }
                if (previous == null) continue;
                return new InsertionResultImpl<Object>(0, previous, previous);
            } while (!UPDATER.compareAndSet(this, null, value));
            return new InsertionResultImpl<Object>(1, null, value);
        }

        @Override
        public <K> @NonNull SyncMap.InsertionResult<V> computeIfAbsent(@Nullable K key, @NonNull Function<? super K, ? extends V> function) {
            Object next = null;
            do {
                Object previous;
                if ((previous = this.value) == EXPUNGED) {
                    return new InsertionResultImpl<Object>(2, null, null);
                }
                if (previous == null) continue;
                return new InsertionResultImpl<Object>(0, previous, previous);
            } while (!UPDATER.compareAndSet(this, null, next != null ? next : (next = (Object)function.apply(key))));
            return new InsertionResultImpl<Object>(1, null, next);
        }

        @Override
        public <K> @NonNull SyncMap.InsertionResult<V> computeIfPresent(@Nullable K key, @NonNull BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
            Object previous;
            Object next = null;
            do {
                if ((previous = this.value) == EXPUNGED) {
                    return new InsertionResultImpl<Object>(2, null, null);
                }
                if (previous != null) continue;
                return new InsertionResultImpl<Object>(0, null, null);
            } while (!UPDATER.compareAndSet(this, previous, next != null ? next : (next = (Object)remappingFunction.apply(key, previous))));
            return new InsertionResultImpl<Object>(1, previous, next);
        }

        @Override
        public <K> @NonNull SyncMap.InsertionResult<V> compute(@Nullable K key, @NonNull BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
            byte by;
            Object previous;
            Object next = null;
            do {
                if ((previous = this.value) != EXPUNGED) continue;
                return new InsertionResultImpl<Object>(2, null, null);
            } while (!UPDATER.compareAndSet(this, previous, next != null ? next : (next = (Object)remappingFunction.apply(key, previous))));
            if (previous != next) {
                by = 1;
                return new InsertionResultImpl<Object>(by, previous, next);
            }
            by = 0;
            return new InsertionResultImpl<Object>(by, previous, next);
        }

        @Override
        public void set(@NonNull V value) {
            UPDATER.set(this, value);
        }

        @Override
        public boolean replace(@NonNull Object compare, @Nullable V value) {
            Object previous;
            do {
                if ((previous = this.value) == EXPUNGED) return false;
                if (Objects.equals(previous, compare)) continue;
                return false;
            } while (!UPDATER.compareAndSet(this, previous, value));
            return true;
        }

        @Override
        public @Nullable V clear() {
            Object previous;
            do {
                if ((previous = this.value) == null) return null;
                if (previous != EXPUNGED) continue;
                return null;
            } while (!UPDATER.compareAndSet(this, previous, null));
            return (V)previous;
        }

        @Override
        public boolean trySet(@NonNull V value) {
            Object previous;
            do {
                if ((previous = this.value) != EXPUNGED) continue;
                return false;
            } while (!UPDATER.compareAndSet(this, previous, value));
            return true;
        }

        @Override
        public @Nullable V tryReplace(@NonNull V value) {
            Object previous;
            do {
                if ((previous = this.value) == null) return null;
                if (previous != EXPUNGED) continue;
                return null;
            } while (!UPDATER.compareAndSet(this, previous, value));
            return (V)previous;
        }

        @Override
        public boolean tryExpunge() {
            do {
                if (this.value != null) return false;
            } while (!UPDATER.compareAndSet(this, null, EXPUNGED));
            return true;
        }

        @Override
        public boolean tryUnexpungeAndSet(@NonNull V value) {
            return UPDATER.compareAndSet(this, EXPUNGED, value);
        }

        @Override
        public <K> boolean tryUnexpungeAndCompute(@Nullable K key, @NonNull Function<? super K, ? extends V> function) {
            if (this.value != EXPUNGED) return false;
            V value = function.apply(key);
            return UPDATER.compareAndSet(this, EXPUNGED, value);
        }

        @Override
        public <K> boolean tryUnexpungeAndCompute(@Nullable K key, @NonNull BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
            if (this.value != EXPUNGED) return false;
            V value = remappingFunction.apply(key, null);
            return UPDATER.compareAndSet(this, EXPUNGED, value);
        }
    }
}

