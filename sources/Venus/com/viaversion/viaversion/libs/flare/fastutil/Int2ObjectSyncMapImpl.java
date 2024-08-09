/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.flare.fastutil;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMaps;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.IntFunction;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

final class Int2ObjectSyncMapImpl<V>
extends AbstractInt2ObjectMap<V>
implements Int2ObjectSyncMap<V> {
    private static final long serialVersionUID = 1L;
    private final transient Object lock = new Object();
    private volatile transient Int2ObjectMap<Int2ObjectSyncMap.ExpungingEntry<V>> read;
    private volatile transient boolean amended;
    private transient Int2ObjectMap<Int2ObjectSyncMap.ExpungingEntry<V>> dirty;
    private transient int misses;
    private final transient IntFunction<Int2ObjectMap<Int2ObjectSyncMap.ExpungingEntry<V>>> function;
    private transient EntrySetView entrySet;

    Int2ObjectSyncMapImpl(@NonNull IntFunction<Int2ObjectMap<Int2ObjectSyncMap.ExpungingEntry<V>>> intFunction, int n) {
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
        for (Int2ObjectSyncMap.ExpungingEntry expungingEntry : this.read.values()) {
            if (!expungingEntry.exists()) continue;
            ++n;
        }
        return n;
    }

    @Override
    public boolean isEmpty() {
        this.promote();
        for (Int2ObjectSyncMap.ExpungingEntry expungingEntry : this.read.values()) {
            if (!expungingEntry.exists()) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean containsValue(@Nullable Object object) {
        for (Int2ObjectMap.Entry entry : this.int2ObjectEntrySet()) {
            if (!Objects.equals(entry.getValue(), object)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean containsKey(int n) {
        Int2ObjectSyncMap.ExpungingEntry<V> expungingEntry = this.getEntry(n);
        return expungingEntry != null && expungingEntry.exists();
    }

    @Override
    public @Nullable V get(int n) {
        Int2ObjectSyncMap.ExpungingEntry<V> expungingEntry = this.getEntry(n);
        return expungingEntry != null ? (V)expungingEntry.get() : null;
    }

    @Override
    public @NonNull V getOrDefault(int n, @NonNull V v) {
        Objects.requireNonNull(v, "defaultValue");
        Int2ObjectSyncMap.ExpungingEntry<V> expungingEntry = this.getEntry(n);
        return expungingEntry != null ? expungingEntry.getOr(v) : v;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public @Nullable Int2ObjectSyncMap.ExpungingEntry<V> getEntry(int n) {
        Int2ObjectSyncMap.ExpungingEntry expungingEntry = (Int2ObjectSyncMap.ExpungingEntry)this.read.get(n);
        if (expungingEntry == null && this.amended) {
            Object object = this.lock;
            synchronized (object) {
                expungingEntry = (Int2ObjectSyncMap.ExpungingEntry)this.read.get(n);
                if (expungingEntry == null && this.amended && this.dirty != null) {
                    expungingEntry = (Int2ObjectSyncMap.ExpungingEntry)this.dirty.get(n);
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
    public @Nullable V computeIfAbsent(int n, @NonNull IntFunction<? extends V> intFunction) {
        Int2ObjectSyncMap.InsertionResult<Object> insertionResult;
        Objects.requireNonNull(intFunction, "mappingFunction");
        Int2ObjectSyncMap.ExpungingEntry expungingEntry = (Int2ObjectSyncMap.ExpungingEntry)this.read.get(n);
        Int2ObjectSyncMap.InsertionResult<V> insertionResult2 = insertionResult = expungingEntry != null ? expungingEntry.computeIfAbsent(n, intFunction) : null;
        if (insertionResult != null && insertionResult.operation() == 1) {
            return insertionResult.current();
        }
        Object object = this.lock;
        synchronized (object) {
            expungingEntry = (Int2ObjectSyncMap.ExpungingEntry)this.read.get(n);
            if (expungingEntry != null) {
                if (expungingEntry.tryUnexpungeAndCompute(n, intFunction)) {
                    if (expungingEntry.exists()) {
                        this.dirty.put(n, (Int2ObjectSyncMap.ExpungingEntry<Int2ObjectSyncMap.ExpungingEntry>)expungingEntry);
                    }
                    return expungingEntry.get();
                }
                insertionResult = expungingEntry.computeIfAbsent(n, intFunction);
            } else if (this.dirty != null && (expungingEntry = (Int2ObjectSyncMap.ExpungingEntry)this.dirty.get(n)) != null) {
                insertionResult = expungingEntry.computeIfAbsent(n, intFunction);
                if (insertionResult.current() == null) {
                    this.dirty.remove(n);
                }
                this.missLocked();
            } else {
                V v;
                if (!this.amended) {
                    this.dirtyLocked();
                    this.amended = true;
                }
                if ((v = intFunction.apply(n)) != null) {
                    this.dirty.put(n, (Int2ObjectSyncMap.ExpungingEntry<ExpungingEntryImpl<V>>)new ExpungingEntryImpl<V>(v));
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
    public @Nullable V computeIfAbsent(int n, @NonNull Int2ObjectFunction<? extends V> int2ObjectFunction) {
        Int2ObjectSyncMap.InsertionResult<Object> insertionResult;
        Objects.requireNonNull(int2ObjectFunction, "mappingFunction");
        Int2ObjectSyncMap.ExpungingEntry expungingEntry = (Int2ObjectSyncMap.ExpungingEntry)this.read.get(n);
        Int2ObjectSyncMap.InsertionResult<V> insertionResult2 = insertionResult = expungingEntry != null ? expungingEntry.computeIfAbsentPrimitive(n, int2ObjectFunction) : null;
        if (insertionResult != null && insertionResult.operation() == 1) {
            return insertionResult.current();
        }
        Object object = this.lock;
        synchronized (object) {
            expungingEntry = (Int2ObjectSyncMap.ExpungingEntry)this.read.get(n);
            if (expungingEntry != null) {
                if (expungingEntry.tryUnexpungeAndComputePrimitive(n, int2ObjectFunction)) {
                    if (expungingEntry.exists()) {
                        this.dirty.put(n, (Int2ObjectSyncMap.ExpungingEntry<Int2ObjectSyncMap.ExpungingEntry>)expungingEntry);
                    }
                    return expungingEntry.get();
                }
                insertionResult = expungingEntry.computeIfAbsentPrimitive(n, int2ObjectFunction);
            } else if (this.dirty != null && (expungingEntry = (Int2ObjectSyncMap.ExpungingEntry)this.dirty.get(n)) != null) {
                insertionResult = expungingEntry.computeIfAbsentPrimitive(n, int2ObjectFunction);
                if (insertionResult.current() == null) {
                    this.dirty.remove(n);
                }
                this.missLocked();
            } else {
                V v;
                if (!this.amended) {
                    this.dirtyLocked();
                    this.amended = true;
                }
                if ((v = int2ObjectFunction.get(n)) != null) {
                    this.dirty.put(n, (Int2ObjectSyncMap.ExpungingEntry<ExpungingEntryImpl<V>>)new ExpungingEntryImpl<V>(v));
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
    public @Nullable V computeIfPresent(int n, @NonNull BiFunction<? super Integer, ? super V, ? extends V> biFunction) {
        Int2ObjectSyncMap.InsertionResult<Object> insertionResult;
        Objects.requireNonNull(biFunction, "remappingFunction");
        Int2ObjectSyncMap.ExpungingEntry expungingEntry = (Int2ObjectSyncMap.ExpungingEntry)this.read.get(n);
        Int2ObjectSyncMap.InsertionResult<V> insertionResult2 = insertionResult = expungingEntry != null ? expungingEntry.computeIfPresent(n, biFunction) : null;
        if (insertionResult != null && insertionResult.operation() == 1) {
            return insertionResult.current();
        }
        Object object = this.lock;
        synchronized (object) {
            expungingEntry = (Int2ObjectSyncMap.ExpungingEntry)this.read.get(n);
            if (expungingEntry != null) {
                insertionResult = expungingEntry.computeIfPresent(n, biFunction);
            } else if (this.dirty != null && (expungingEntry = (Int2ObjectSyncMap.ExpungingEntry)this.dirty.get(n)) != null) {
                insertionResult = expungingEntry.computeIfPresent(n, biFunction);
                if (insertionResult.current() == null) {
                    this.dirty.remove(n);
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
    public @Nullable V compute(int n, @NonNull BiFunction<? super Integer, ? super V, ? extends V> biFunction) {
        Int2ObjectSyncMap.InsertionResult<Object> insertionResult;
        Objects.requireNonNull(biFunction, "remappingFunction");
        Int2ObjectSyncMap.ExpungingEntry expungingEntry = (Int2ObjectSyncMap.ExpungingEntry)this.read.get(n);
        Int2ObjectSyncMap.InsertionResult<V> insertionResult2 = insertionResult = expungingEntry != null ? expungingEntry.compute(n, biFunction) : null;
        if (insertionResult != null && insertionResult.operation() == 1) {
            return insertionResult.current();
        }
        Object object = this.lock;
        synchronized (object) {
            expungingEntry = (Int2ObjectSyncMap.ExpungingEntry)this.read.get(n);
            if (expungingEntry != null) {
                if (expungingEntry.tryUnexpungeAndCompute(n, biFunction)) {
                    if (expungingEntry.exists()) {
                        this.dirty.put(n, (Int2ObjectSyncMap.ExpungingEntry<Int2ObjectSyncMap.ExpungingEntry>)expungingEntry);
                    }
                    return expungingEntry.get();
                }
                insertionResult = expungingEntry.compute(n, biFunction);
            } else if (this.dirty != null && (expungingEntry = (Int2ObjectSyncMap.ExpungingEntry)this.dirty.get(n)) != null) {
                insertionResult = expungingEntry.compute(n, biFunction);
                if (insertionResult.current() == null) {
                    this.dirty.remove(n);
                }
                this.missLocked();
            } else {
                V v;
                if (!this.amended) {
                    this.dirtyLocked();
                    this.amended = true;
                }
                if ((v = biFunction.apply(n, null)) != null) {
                    this.dirty.put(n, (Int2ObjectSyncMap.ExpungingEntry<ExpungingEntryImpl<V>>)new ExpungingEntryImpl<V>(v));
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
    public @Nullable V putIfAbsent(int n, @NonNull V v) {
        Int2ObjectSyncMap.InsertionResult<V> insertionResult;
        Objects.requireNonNull(v, "value");
        Int2ObjectSyncMap.ExpungingEntry expungingEntry = (Int2ObjectSyncMap.ExpungingEntry)this.read.get(n);
        Int2ObjectSyncMap.InsertionResult<V> insertionResult2 = insertionResult = expungingEntry != null ? expungingEntry.setIfAbsent(v) : null;
        if (insertionResult != null && insertionResult.operation() == 1) {
            return insertionResult.previous();
        }
        Object object = this.lock;
        synchronized (object) {
            expungingEntry = (Int2ObjectSyncMap.ExpungingEntry)this.read.get(n);
            if (expungingEntry != null) {
                if (expungingEntry.tryUnexpungeAndSet(v)) {
                    this.dirty.put(n, (Int2ObjectSyncMap.ExpungingEntry<Int2ObjectSyncMap.ExpungingEntry>)expungingEntry);
                    return null;
                }
                insertionResult = expungingEntry.setIfAbsent(v);
            } else if (this.dirty != null && (expungingEntry = (Int2ObjectSyncMap.ExpungingEntry)this.dirty.get(n)) != null) {
                insertionResult = expungingEntry.setIfAbsent(v);
                this.missLocked();
            } else {
                if (!this.amended) {
                    this.dirtyLocked();
                    this.amended = true;
                }
                this.dirty.put(n, (Int2ObjectSyncMap.ExpungingEntry<ExpungingEntryImpl<V>>)new ExpungingEntryImpl<V>(v));
                return null;
            }
        }
        return insertionResult.previous();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public @Nullable V put(int n, @NonNull V v) {
        V v2;
        Objects.requireNonNull(v, "value");
        Int2ObjectSyncMap.ExpungingEntry expungingEntry = (Int2ObjectSyncMap.ExpungingEntry)this.read.get(n);
        V v3 = v2 = expungingEntry != null ? (V)expungingEntry.get() : null;
        if (expungingEntry != null && expungingEntry.trySet(v)) {
            return v2;
        }
        Object object = this.lock;
        synchronized (object) {
            expungingEntry = (Int2ObjectSyncMap.ExpungingEntry)this.read.get(n);
            if (expungingEntry != null) {
                v2 = expungingEntry.get();
                if (expungingEntry.tryUnexpungeAndSet(v)) {
                    this.dirty.put(n, (Int2ObjectSyncMap.ExpungingEntry<Int2ObjectSyncMap.ExpungingEntry>)expungingEntry);
                } else {
                    expungingEntry.set(v);
                }
            } else if (this.dirty != null && (expungingEntry = (Int2ObjectSyncMap.ExpungingEntry)this.dirty.get(n)) != null) {
                v2 = expungingEntry.get();
                expungingEntry.set(v);
            } else {
                if (!this.amended) {
                    this.dirtyLocked();
                    this.amended = true;
                }
                this.dirty.put(n, (Int2ObjectSyncMap.ExpungingEntry<ExpungingEntryImpl<V>>)new ExpungingEntryImpl<V>(v));
                return null;
            }
        }
        return v2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public @Nullable V remove(int n) {
        Int2ObjectSyncMap.ExpungingEntry expungingEntry = (Int2ObjectSyncMap.ExpungingEntry)this.read.get(n);
        if (expungingEntry == null && this.amended) {
            Object object = this.lock;
            synchronized (object) {
                expungingEntry = (Int2ObjectSyncMap.ExpungingEntry)this.read.get(n);
                if (expungingEntry == null && this.amended && this.dirty != null) {
                    expungingEntry = (Int2ObjectSyncMap.ExpungingEntry)this.dirty.remove(n);
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
    public boolean remove(int n, @NonNull Object object) {
        Objects.requireNonNull(object, "value");
        Int2ObjectSyncMap.ExpungingEntry expungingEntry = (Int2ObjectSyncMap.ExpungingEntry)this.read.get(n);
        if (expungingEntry == null && this.amended) {
            Object object2 = this.lock;
            synchronized (object2) {
                expungingEntry = (Int2ObjectSyncMap.ExpungingEntry)this.read.get(n);
                if (expungingEntry == null && this.amended && this.dirty != null) {
                    boolean bl;
                    expungingEntry = (Int2ObjectSyncMap.ExpungingEntry)this.dirty.get(n);
                    boolean bl2 = bl = expungingEntry != null && expungingEntry.replace(object, null);
                    if (bl) {
                        this.dirty.remove(n);
                    }
                    this.missLocked();
                    return bl;
                }
            }
        }
        return expungingEntry != null && expungingEntry.replace(object, null);
    }

    @Override
    public @Nullable V replace(int n, @NonNull V v) {
        Objects.requireNonNull(v, "value");
        Int2ObjectSyncMap.ExpungingEntry<V> expungingEntry = this.getEntry(n);
        return expungingEntry != null ? (V)expungingEntry.tryReplace(v) : null;
    }

    @Override
    public boolean replace(int n, @NonNull V v, @NonNull V v2) {
        Objects.requireNonNull(v, "oldValue");
        Objects.requireNonNull(v2, "newValue");
        Int2ObjectSyncMap.ExpungingEntry<V> expungingEntry = this.getEntry(n);
        return expungingEntry != null && expungingEntry.replace(v, v2);
    }

    @Override
    public void forEach(@NonNull BiConsumer<? super Integer, ? super V> biConsumer) {
        Objects.requireNonNull(biConsumer, "action");
        this.promote();
        for (Int2ObjectMap.Entry entry : this.read.int2ObjectEntrySet()) {
            Object v = ((Int2ObjectSyncMap.ExpungingEntry)entry.getValue()).get();
            if (v == null) continue;
            biConsumer.accept(entry.getIntKey(), v);
        }
    }

    @Override
    public void putAll(@NonNull Map<? extends Integer, ? extends V> map) {
        Objects.requireNonNull(map, "map");
        for (Map.Entry<Integer, V> entry : map.entrySet()) {
            this.put((int)entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void replaceAll(@NonNull BiFunction<? super Integer, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction, "function");
        this.promote();
        for (Int2ObjectMap.Entry entry : this.read.int2ObjectEntrySet()) {
            Int2ObjectSyncMap.ExpungingEntry expungingEntry = (Int2ObjectSyncMap.ExpungingEntry)entry.getValue();
            Object v = expungingEntry.get();
            if (v == null) continue;
            expungingEntry.tryReplace(biFunction.apply(entry.getIntKey(), v));
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
    public @NonNull ObjectSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
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
        Int2ObjectMaps.fastForEach(this.read, this::lambda$dirtyLocked$0);
    }

    private void lambda$dirtyLocked$0(Int2ObjectMap.Entry entry) {
        if (!((Int2ObjectSyncMap.ExpungingEntry)entry.getValue()).tryExpunge()) {
            this.dirty.put(entry.getIntKey(), (Int2ObjectSyncMap.ExpungingEntry<Int2ObjectSyncMap.ExpungingEntry>)((Int2ObjectSyncMap.ExpungingEntry)entry.getValue()));
        }
    }

    static void access$000(Int2ObjectSyncMapImpl int2ObjectSyncMapImpl) {
        int2ObjectSyncMapImpl.promote();
    }

    static Int2ObjectMap access$100(Int2ObjectSyncMapImpl int2ObjectSyncMapImpl) {
        return int2ObjectSyncMapImpl.read;
    }

    final class EntryIterator
    implements ObjectIterator<Int2ObjectMap.Entry<V>> {
        private final Iterator<Int2ObjectMap.Entry<Int2ObjectSyncMap.ExpungingEntry<V>>> backingIterator;
        private Int2ObjectMap.Entry<V> next;
        private Int2ObjectMap.Entry<V> current;
        final Int2ObjectSyncMapImpl this$0;

        EntryIterator(@NonNull Int2ObjectSyncMapImpl int2ObjectSyncMapImpl, Iterator<Int2ObjectMap.Entry<Int2ObjectSyncMap.ExpungingEntry<V>>> iterator2) {
            this.this$0 = int2ObjectSyncMapImpl;
            this.backingIterator = iterator2;
            this.advance();
        }

        @Override
        public boolean hasNext() {
            return this.next != null;
        }

        @Override
        public @NonNull Int2ObjectMap.Entry<V> next() {
            @NonNull Int2ObjectMap.Entry<V> entry = this.next;
            if (entry == null) {
                throw new NoSuchElementException();
            }
            this.current = entry;
            this.advance();
            return entry;
        }

        @Override
        public void remove() {
            @NonNull Int2ObjectMap.Entry<V> entry = this.current;
            if (entry == null) {
                throw new IllegalStateException();
            }
            this.current = null;
            this.this$0.remove(entry.getIntKey());
        }

        private void advance() {
            this.next = null;
            while (this.backingIterator.hasNext()) {
                Int2ObjectMap.Entry entry = this.backingIterator.next();
                Object v = ((Int2ObjectSyncMap.ExpungingEntry)entry.getValue()).get();
                if (v == null) continue;
                this.next = new MapEntry(this.this$0, entry.getIntKey(), v);
                return;
            }
        }

        @Override
        public @NonNull Object next() {
            return this.next();
        }
    }

    final class EntrySetView
    extends AbstractObjectSet<Int2ObjectMap.Entry<V>> {
        final Int2ObjectSyncMapImpl this$0;

        EntrySetView(Int2ObjectSyncMapImpl int2ObjectSyncMapImpl) {
            this.this$0 = int2ObjectSyncMapImpl;
        }

        @Override
        public int size() {
            return this.this$0.size();
        }

        @Override
        public boolean contains(@Nullable Object object) {
            if (!(object instanceof Int2ObjectMap.Entry)) {
                return true;
            }
            Int2ObjectMap.Entry entry = (Int2ObjectMap.Entry)object;
            Object v = this.this$0.get(entry.getIntKey());
            return v != null && Objects.equals(v, entry.getValue());
        }

        @Override
        public boolean add(@NonNull Int2ObjectMap.Entry<V> entry) {
            Objects.requireNonNull(entry, "entry");
            return this.this$0.put(entry.getIntKey(), entry.getValue()) == null;
        }

        @Override
        public boolean remove(@Nullable Object object) {
            if (!(object instanceof Int2ObjectMap.Entry)) {
                return true;
            }
            Int2ObjectMap.Entry entry = (Int2ObjectMap.Entry)object;
            return this.this$0.remove(entry.getIntKey(), entry.getValue());
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }

        @Override
        public @NonNull ObjectIterator<Int2ObjectMap.Entry<V>> iterator() {
            Int2ObjectSyncMapImpl.access$000(this.this$0);
            return new EntryIterator(this.this$0, Int2ObjectSyncMapImpl.access$100(this.this$0).int2ObjectEntrySet().iterator());
        }

        @Override
        public boolean add(@NonNull Object object) {
            return this.add((Int2ObjectMap.Entry)object);
        }

        @Override
        public @NonNull Iterator iterator() {
            return this.iterator();
        }
    }

    final class MapEntry
    implements Int2ObjectMap.Entry<V> {
        private final int key;
        private V value;
        final Int2ObjectSyncMapImpl this$0;

        MapEntry(Int2ObjectSyncMapImpl int2ObjectSyncMapImpl, @NonNull int n, V v) {
            this.this$0 = int2ObjectSyncMapImpl;
            this.key = n;
            this.value = v;
        }

        @Override
        public int getIntKey() {
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
            return "Int2ObjectSyncMapImpl.MapEntry{key=" + this.getIntKey() + ", value=" + this.getValue() + "}";
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (this == object) {
                return false;
            }
            if (!(object instanceof Int2ObjectMap.Entry)) {
                return true;
            }
            Int2ObjectMap.Entry entry = (Int2ObjectMap.Entry)object;
            return Objects.equals(this.getIntKey(), entry.getIntKey()) && Objects.equals(this.getValue(), entry.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.getIntKey(), this.getValue());
        }
    }

    static final class InsertionResultImpl<V>
    implements Int2ObjectSyncMap.InsertionResult<V> {
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
    implements Int2ObjectSyncMap.ExpungingEntry<V> {
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
        public @NonNull Int2ObjectSyncMap.InsertionResult<V> setIfAbsent(@NonNull V v) {
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
        public @NonNull Int2ObjectSyncMap.InsertionResult<V> computeIfAbsent(int n, @NonNull IntFunction<? extends V> intFunction) {
            Object v = null;
            do {
                Object object;
                if ((object = this.value) == EXPUNGED) {
                    return new InsertionResultImpl<Object>(2, null, null);
                }
                if (object == null) continue;
                return new InsertionResultImpl<Object>(0, object, object);
            } while (!UPDATER.compareAndSet(this, null, v != null ? v : (v = (Object)intFunction.apply(n))));
            return new InsertionResultImpl<Object>(1, null, v);
        }

        @Override
        public @NonNull Int2ObjectSyncMap.InsertionResult<V> computeIfAbsentPrimitive(int n, @NonNull Int2ObjectFunction<? extends V> int2ObjectFunction) {
            Object v = null;
            do {
                Object object;
                if ((object = this.value) == EXPUNGED) {
                    return new InsertionResultImpl<Object>(2, null, null);
                }
                if (object == null) continue;
                return new InsertionResultImpl<Object>(0, object, object);
            } while (!UPDATER.compareAndSet(this, null, v != null ? v : (v = int2ObjectFunction.containsKey(n) ? (Object)int2ObjectFunction.get(n) : null)));
            return new InsertionResultImpl<Object>(1, null, v);
        }

        @Override
        public @NonNull Int2ObjectSyncMap.InsertionResult<V> computeIfPresent(int n, @NonNull BiFunction<? super Integer, ? super V, ? extends V> biFunction) {
            Object object;
            Object v = null;
            do {
                if ((object = this.value) == EXPUNGED) {
                    return new InsertionResultImpl<Object>(2, null, null);
                }
                if (object != null) continue;
                return new InsertionResultImpl<Object>(0, null, null);
            } while (!UPDATER.compareAndSet(this, object, v != null ? v : (v = (Object)biFunction.apply(n, object))));
            return new InsertionResultImpl<Object>(1, object, v);
        }

        @Override
        public @NonNull Int2ObjectSyncMap.InsertionResult<V> compute(int n, @NonNull BiFunction<? super Integer, ? super V, ? extends V> biFunction) {
            Object object;
            Object v = null;
            do {
                if ((object = this.value) != EXPUNGED) continue;
                return new InsertionResultImpl<Object>(2, null, null);
            } while (!UPDATER.compareAndSet(this, object, v != null ? v : (v = (Object)biFunction.apply(n, object))));
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
        public boolean tryUnexpungeAndCompute(int n, @NonNull IntFunction<? extends V> intFunction) {
            if (this.value == EXPUNGED) {
                V v = intFunction.apply(n);
                return UPDATER.compareAndSet(this, EXPUNGED, v);
            }
            return true;
        }

        @Override
        public boolean tryUnexpungeAndComputePrimitive(int n, @NonNull Int2ObjectFunction<? extends V> int2ObjectFunction) {
            if (this.value == EXPUNGED) {
                Object v = int2ObjectFunction.containsKey(n) ? (Object)int2ObjectFunction.get(n) : null;
                return UPDATER.compareAndSet(this, EXPUNGED, v);
            }
            return true;
        }

        @Override
        public boolean tryUnexpungeAndCompute(int n, @NonNull BiFunction<? super Integer, ? super V, ? extends V> biFunction) {
            if (this.value == EXPUNGED) {
                V v = biFunction.apply(n, null);
                return UPDATER.compareAndSet(this, EXPUNGED, v);
            }
            return true;
        }
    }
}

