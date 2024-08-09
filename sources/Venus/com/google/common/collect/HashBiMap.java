/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMapEntry;
import com.google.common.collect.BiMap;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Hashing;
import com.google.common.collect.ImmutableEntry;
import com.google.common.collect.Maps;
import com.google.common.collect.Serialization;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.RetainedWith;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
public final class HashBiMap<K, V>
extends Maps.IteratorBasedAbstractMap<K, V>
implements BiMap<K, V>,
Serializable {
    private static final double LOAD_FACTOR = 1.0;
    private transient BiEntry<K, V>[] hashTableKToV;
    private transient BiEntry<K, V>[] hashTableVToK;
    private transient BiEntry<K, V> firstInKeyInsertionOrder;
    private transient BiEntry<K, V> lastInKeyInsertionOrder;
    private transient int size;
    private transient int mask;
    private transient int modCount;
    @RetainedWith
    private transient BiMap<V, K> inverse;
    @GwtIncompatible
    private static final long serialVersionUID = 0L;

    public static <K, V> HashBiMap<K, V> create() {
        return HashBiMap.create(16);
    }

    public static <K, V> HashBiMap<K, V> create(int n) {
        return new HashBiMap<K, V>(n);
    }

    public static <K, V> HashBiMap<K, V> create(Map<? extends K, ? extends V> map) {
        HashBiMap<K, V> hashBiMap = HashBiMap.create(map.size());
        hashBiMap.putAll(map);
        return hashBiMap;
    }

    private HashBiMap(int n) {
        this.init(n);
    }

    private void init(int n) {
        CollectPreconditions.checkNonnegative(n, "expectedSize");
        int n2 = Hashing.closedTableSize(n, 1.0);
        this.hashTableKToV = this.createTable(n2);
        this.hashTableVToK = this.createTable(n2);
        this.firstInKeyInsertionOrder = null;
        this.lastInKeyInsertionOrder = null;
        this.size = 0;
        this.mask = n2 - 1;
        this.modCount = 0;
    }

    private void delete(BiEntry<K, V> biEntry) {
        int n = biEntry.keyHash & this.mask;
        BiEntry<K, V> biEntry2 = null;
        BiEntry<K, V> biEntry3 = this.hashTableKToV[n];
        while (true) {
            if (biEntry3 == biEntry) {
                if (biEntry2 == null) {
                    this.hashTableKToV[n] = biEntry.nextInKToVBucket;
                    break;
                }
                biEntry2.nextInKToVBucket = biEntry.nextInKToVBucket;
                break;
            }
            biEntry2 = biEntry3;
            biEntry3 = biEntry3.nextInKToVBucket;
        }
        int n2 = biEntry.valueHash & this.mask;
        biEntry2 = null;
        BiEntry<K, V> biEntry4 = this.hashTableVToK[n2];
        while (true) {
            if (biEntry4 == biEntry) {
                if (biEntry2 == null) {
                    this.hashTableVToK[n2] = biEntry.nextInVToKBucket;
                    break;
                }
                biEntry2.nextInVToKBucket = biEntry.nextInVToKBucket;
                break;
            }
            biEntry2 = biEntry4;
            biEntry4 = biEntry4.nextInVToKBucket;
        }
        if (biEntry.prevInKeyInsertionOrder == null) {
            this.firstInKeyInsertionOrder = biEntry.nextInKeyInsertionOrder;
        } else {
            biEntry.prevInKeyInsertionOrder.nextInKeyInsertionOrder = biEntry.nextInKeyInsertionOrder;
        }
        if (biEntry.nextInKeyInsertionOrder == null) {
            this.lastInKeyInsertionOrder = biEntry.prevInKeyInsertionOrder;
        } else {
            biEntry.nextInKeyInsertionOrder.prevInKeyInsertionOrder = biEntry.prevInKeyInsertionOrder;
        }
        --this.size;
        ++this.modCount;
    }

    private void insert(BiEntry<K, V> biEntry, @Nullable BiEntry<K, V> biEntry2) {
        int n = biEntry.keyHash & this.mask;
        biEntry.nextInKToVBucket = this.hashTableKToV[n];
        this.hashTableKToV[n] = biEntry;
        int n2 = biEntry.valueHash & this.mask;
        biEntry.nextInVToKBucket = this.hashTableVToK[n2];
        this.hashTableVToK[n2] = biEntry;
        if (biEntry2 == null) {
            biEntry.prevInKeyInsertionOrder = this.lastInKeyInsertionOrder;
            biEntry.nextInKeyInsertionOrder = null;
            if (this.lastInKeyInsertionOrder == null) {
                this.firstInKeyInsertionOrder = biEntry;
            } else {
                this.lastInKeyInsertionOrder.nextInKeyInsertionOrder = biEntry;
            }
            this.lastInKeyInsertionOrder = biEntry;
        } else {
            biEntry.prevInKeyInsertionOrder = biEntry2.prevInKeyInsertionOrder;
            if (biEntry.prevInKeyInsertionOrder == null) {
                this.firstInKeyInsertionOrder = biEntry;
            } else {
                biEntry.prevInKeyInsertionOrder.nextInKeyInsertionOrder = biEntry;
            }
            biEntry.nextInKeyInsertionOrder = biEntry2.nextInKeyInsertionOrder;
            if (biEntry.nextInKeyInsertionOrder == null) {
                this.lastInKeyInsertionOrder = biEntry;
            } else {
                biEntry.nextInKeyInsertionOrder.prevInKeyInsertionOrder = biEntry;
            }
        }
        ++this.size;
        ++this.modCount;
    }

    private BiEntry<K, V> seekByKey(@Nullable Object object, int n) {
        BiEntry<K, V> biEntry = this.hashTableKToV[n & this.mask];
        while (biEntry != null) {
            if (n == biEntry.keyHash && Objects.equal(object, biEntry.key)) {
                return biEntry;
            }
            biEntry = biEntry.nextInKToVBucket;
        }
        return null;
    }

    private BiEntry<K, V> seekByValue(@Nullable Object object, int n) {
        BiEntry<K, V> biEntry = this.hashTableVToK[n & this.mask];
        while (biEntry != null) {
            if (n == biEntry.valueHash && Objects.equal(object, biEntry.value)) {
                return biEntry;
            }
            biEntry = biEntry.nextInVToKBucket;
        }
        return null;
    }

    @Override
    public boolean containsKey(@Nullable Object object) {
        return this.seekByKey(object, Hashing.smearedHash(object)) != null;
    }

    @Override
    public boolean containsValue(@Nullable Object object) {
        return this.seekByValue(object, Hashing.smearedHash(object)) != null;
    }

    @Override
    @Nullable
    public V get(@Nullable Object object) {
        return Maps.valueOrNull(this.seekByKey(object, Hashing.smearedHash(object)));
    }

    @Override
    @CanIgnoreReturnValue
    public V put(@Nullable K k, @Nullable V v) {
        return this.put(k, v, false);
    }

    @Override
    @CanIgnoreReturnValue
    public V forcePut(@Nullable K k, @Nullable V v) {
        return this.put(k, v, true);
    }

    private V put(@Nullable K k, @Nullable V v, boolean bl) {
        int n = Hashing.smearedHash(k);
        int n2 = Hashing.smearedHash(v);
        BiEntry<K, V> biEntry = this.seekByKey(k, n);
        if (biEntry != null && n2 == biEntry.valueHash && Objects.equal(v, biEntry.value)) {
            return v;
        }
        BiEntry<K, V> biEntry2 = this.seekByValue(v, n2);
        if (biEntry2 != null) {
            if (bl) {
                this.delete(biEntry2);
            } else {
                throw new IllegalArgumentException("value already present: " + v);
            }
        }
        BiEntry<K, V> biEntry3 = new BiEntry<K, V>(k, n, v, n2);
        if (biEntry != null) {
            this.delete(biEntry);
            this.insert(biEntry3, biEntry);
            biEntry.prevInKeyInsertionOrder = null;
            biEntry.nextInKeyInsertionOrder = null;
            this.rehashIfNecessary();
            return (V)biEntry.value;
        }
        this.insert(biEntry3, null);
        this.rehashIfNecessary();
        return null;
    }

    @Nullable
    private K putInverse(@Nullable V v, @Nullable K k, boolean bl) {
        int n = Hashing.smearedHash(v);
        int n2 = Hashing.smearedHash(k);
        BiEntry<K, V> biEntry = this.seekByValue(v, n);
        if (biEntry != null && n2 == biEntry.keyHash && Objects.equal(k, biEntry.key)) {
            return k;
        }
        BiEntry<K, V> biEntry2 = this.seekByKey(k, n2);
        if (biEntry2 != null) {
            if (bl) {
                this.delete(biEntry2);
            } else {
                throw new IllegalArgumentException("value already present: " + k);
            }
        }
        if (biEntry != null) {
            this.delete(biEntry);
        }
        BiEntry<K, V> biEntry3 = new BiEntry<K, V>(k, n2, v, n);
        this.insert(biEntry3, biEntry2);
        if (biEntry2 != null) {
            biEntry2.prevInKeyInsertionOrder = null;
            biEntry2.nextInKeyInsertionOrder = null;
        }
        this.rehashIfNecessary();
        return Maps.keyOrNull(biEntry);
    }

    private void rehashIfNecessary() {
        BiEntry<K, V>[] biEntryArray = this.hashTableKToV;
        if (Hashing.needsResizing(this.size, biEntryArray.length, 1.0)) {
            int n = biEntryArray.length * 2;
            this.hashTableKToV = this.createTable(n);
            this.hashTableVToK = this.createTable(n);
            this.mask = n - 1;
            this.size = 0;
            BiEntry<K, V> biEntry = this.firstInKeyInsertionOrder;
            while (biEntry != null) {
                this.insert(biEntry, biEntry);
                biEntry = biEntry.nextInKeyInsertionOrder;
            }
            ++this.modCount;
        }
    }

    private BiEntry<K, V>[] createTable(int n) {
        return new BiEntry[n];
    }

    @Override
    @CanIgnoreReturnValue
    public V remove(@Nullable Object object) {
        BiEntry<K, V> biEntry = this.seekByKey(object, Hashing.smearedHash(object));
        if (biEntry == null) {
            return null;
        }
        this.delete(biEntry);
        biEntry.prevInKeyInsertionOrder = null;
        biEntry.nextInKeyInsertionOrder = null;
        return (V)biEntry.value;
    }

    @Override
    public void clear() {
        this.size = 0;
        Arrays.fill(this.hashTableKToV, null);
        Arrays.fill(this.hashTableVToK, null);
        this.firstInKeyInsertionOrder = null;
        this.lastInKeyInsertionOrder = null;
        ++this.modCount;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Set<K> keySet() {
        return new KeySet(this);
    }

    @Override
    public Set<V> values() {
        return this.inverse().keySet();
    }

    @Override
    Iterator<Map.Entry<K, V>> entryIterator() {
        return new Itr<Map.Entry<K, V>>(this){
            final HashBiMap this$0;
            {
                this.this$0 = hashBiMap;
                super(hashBiMap);
            }

            @Override
            Map.Entry<K, V> output(BiEntry<K, V> biEntry) {
                return new MapEntry(this, biEntry);
            }

            @Override
            Object output(BiEntry biEntry) {
                return this.output(biEntry);
            }

            class MapEntry
            extends AbstractMapEntry<K, V> {
                BiEntry<K, V> delegate;
                final 1 this$1;

                MapEntry(1 var1_1, BiEntry<K, V> biEntry) {
                    this.this$1 = var1_1;
                    this.delegate = biEntry;
                }

                @Override
                public K getKey() {
                    return this.delegate.key;
                }

                @Override
                public V getValue() {
                    return this.delegate.value;
                }

                @Override
                public V setValue(V v) {
                    Object object = this.delegate.value;
                    int n = Hashing.smearedHash(v);
                    if (n == this.delegate.valueHash && Objects.equal(v, object)) {
                        return v;
                    }
                    Preconditions.checkArgument(HashBiMap.access$400(this.this$1.this$0, v, n) == null, "value already present: %s", v);
                    HashBiMap.access$200(this.this$1.this$0, this.delegate);
                    BiEntry biEntry = new BiEntry(this.delegate.key, this.delegate.keyHash, v, n);
                    HashBiMap.access$500(this.this$1.this$0, biEntry, this.delegate);
                    this.delegate.prevInKeyInsertionOrder = null;
                    this.delegate.nextInKeyInsertionOrder = null;
                    this.this$1.expectedModCount = HashBiMap.access$100(this.this$1.this$0);
                    if (this.this$1.toRemove == this.delegate) {
                        this.this$1.toRemove = biEntry;
                    }
                    this.delegate = biEntry;
                    return object;
                }
            }
        };
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
        Preconditions.checkNotNull(biConsumer);
        BiEntry<K, V> biEntry = this.firstInKeyInsertionOrder;
        while (biEntry != null) {
            biConsumer.accept(biEntry.key, biEntry.value);
            biEntry = biEntry.nextInKeyInsertionOrder;
        }
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> biFunction) {
        Preconditions.checkNotNull(biFunction);
        BiEntry<K, V> biEntry = this.firstInKeyInsertionOrder;
        this.clear();
        BiEntry<K, V> biEntry2 = biEntry;
        while (biEntry2 != null) {
            this.put(biEntry2.key, biFunction.apply(biEntry2.key, biEntry2.value));
            biEntry2 = biEntry2.nextInKeyInsertionOrder;
        }
    }

    @Override
    public BiMap<V, K> inverse() {
        return this.inverse == null ? (this.inverse = new Inverse(this, null)) : this.inverse;
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Serialization.writeMap(this, objectOutputStream);
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.init(16);
        int n = Serialization.readCount(objectInputStream);
        Serialization.populateMap(this, objectInputStream, n);
    }

    @Override
    public Set entrySet() {
        return super.entrySet();
    }

    @Override
    public Collection values() {
        return this.values();
    }

    static BiEntry access$000(HashBiMap hashBiMap) {
        return hashBiMap.firstInKeyInsertionOrder;
    }

    static int access$100(HashBiMap hashBiMap) {
        return hashBiMap.modCount;
    }

    static void access$200(HashBiMap hashBiMap, BiEntry biEntry) {
        hashBiMap.delete(biEntry);
    }

    static BiEntry access$300(HashBiMap hashBiMap, Object object, int n) {
        return hashBiMap.seekByKey(object, n);
    }

    static BiEntry access$400(HashBiMap hashBiMap, Object object, int n) {
        return hashBiMap.seekByValue(object, n);
    }

    static void access$500(HashBiMap hashBiMap, BiEntry biEntry, BiEntry biEntry2) {
        hashBiMap.insert(biEntry, biEntry2);
    }

    static int access$700(HashBiMap hashBiMap) {
        return hashBiMap.size;
    }

    static Object access$800(HashBiMap hashBiMap, Object object, Object object2, boolean bl) {
        return hashBiMap.putInverse(object, object2, bl);
    }

    private static final class InverseSerializedForm<K, V>
    implements Serializable {
        private final HashBiMap<K, V> bimap;

        InverseSerializedForm(HashBiMap<K, V> hashBiMap) {
            this.bimap = hashBiMap;
        }

        Object readResolve() {
            return this.bimap.inverse();
        }
    }

    private final class Inverse
    extends Maps.IteratorBasedAbstractMap<V, K>
    implements BiMap<V, K>,
    Serializable {
        final HashBiMap this$0;

        private Inverse(HashBiMap hashBiMap) {
            this.this$0 = hashBiMap;
        }

        BiMap<K, V> forward() {
            return this.this$0;
        }

        @Override
        public int size() {
            return HashBiMap.access$700(this.this$0);
        }

        @Override
        public void clear() {
            this.forward().clear();
        }

        @Override
        public boolean containsKey(@Nullable Object object) {
            return this.forward().containsValue(object);
        }

        @Override
        public K get(@Nullable Object object) {
            return Maps.keyOrNull(HashBiMap.access$400(this.this$0, object, Hashing.smearedHash(object)));
        }

        @Override
        @CanIgnoreReturnValue
        public K put(@Nullable V v, @Nullable K k) {
            return HashBiMap.access$800(this.this$0, v, k, false);
        }

        @Override
        public K forcePut(@Nullable V v, @Nullable K k) {
            return HashBiMap.access$800(this.this$0, v, k, true);
        }

        @Override
        public K remove(@Nullable Object object) {
            BiEntry biEntry = HashBiMap.access$400(this.this$0, object, Hashing.smearedHash(object));
            if (biEntry == null) {
                return null;
            }
            HashBiMap.access$200(this.this$0, biEntry);
            biEntry.prevInKeyInsertionOrder = null;
            biEntry.nextInKeyInsertionOrder = null;
            return biEntry.key;
        }

        @Override
        public BiMap<K, V> inverse() {
            return this.forward();
        }

        @Override
        public Set<V> keySet() {
            return new InverseKeySet(this);
        }

        @Override
        public Set<K> values() {
            return this.forward().keySet();
        }

        @Override
        Iterator<Map.Entry<V, K>> entryIterator() {
            return new Itr<Map.Entry<V, K>>(this){
                final Inverse this$1;
                {
                    this.this$1 = inverse;
                    super(inverse.this$0);
                }

                @Override
                Map.Entry<V, K> output(BiEntry<K, V> biEntry) {
                    return new InverseEntry(this, biEntry);
                }

                @Override
                Object output(BiEntry biEntry) {
                    return this.output(biEntry);
                }

                class InverseEntry
                extends AbstractMapEntry<V, K> {
                    BiEntry<K, V> delegate;
                    final 1 this$2;

                    InverseEntry(1 var1_1, BiEntry<K, V> biEntry) {
                        this.this$2 = var1_1;
                        this.delegate = biEntry;
                    }

                    @Override
                    public V getKey() {
                        return this.delegate.value;
                    }

                    @Override
                    public K getValue() {
                        return this.delegate.key;
                    }

                    @Override
                    public K setValue(K k) {
                        Object object = this.delegate.key;
                        int n = Hashing.smearedHash(k);
                        if (n == this.delegate.keyHash && Objects.equal(k, object)) {
                            return k;
                        }
                        Preconditions.checkArgument(HashBiMap.access$300(this.this$2.this$1.this$0, k, n) == null, "value already present: %s", k);
                        HashBiMap.access$200(this.this$2.this$1.this$0, this.delegate);
                        BiEntry biEntry = new BiEntry(k, n, this.delegate.value, this.delegate.valueHash);
                        this.delegate = biEntry;
                        HashBiMap.access$500(this.this$2.this$1.this$0, biEntry, null);
                        this.this$2.expectedModCount = HashBiMap.access$100(this.this$2.this$1.this$0);
                        return object;
                    }
                }
            };
        }

        @Override
        public void forEach(BiConsumer<? super V, ? super K> biConsumer) {
            Preconditions.checkNotNull(biConsumer);
            this.this$0.forEach((arg_0, arg_1) -> Inverse.lambda$forEach$0(biConsumer, arg_0, arg_1));
        }

        @Override
        public void replaceAll(BiFunction<? super V, ? super K, ? extends K> biFunction) {
            Preconditions.checkNotNull(biFunction);
            BiEntry biEntry = HashBiMap.access$000(this.this$0);
            this.clear();
            BiEntry biEntry2 = biEntry;
            while (biEntry2 != null) {
                this.put((V)biEntry2.value, (K)biFunction.apply(biEntry2.value, biEntry2.key));
                biEntry2 = biEntry2.nextInKeyInsertionOrder;
            }
        }

        Object writeReplace() {
            return new InverseSerializedForm(this.this$0);
        }

        @Override
        public Collection values() {
            return this.values();
        }

        private static void lambda$forEach$0(BiConsumer biConsumer, Object object, Object object2) {
            biConsumer.accept(object2, object);
        }

        Inverse(HashBiMap hashBiMap, com.google.common.collect.HashBiMap$1 var2_2) {
            this(hashBiMap);
        }

        private final class InverseKeySet
        extends Maps.KeySet<V, K> {
            final Inverse this$1;

            InverseKeySet(Inverse inverse) {
                this.this$1 = inverse;
                super(inverse);
            }

            @Override
            public boolean remove(@Nullable Object object) {
                BiEntry biEntry = HashBiMap.access$400(this.this$1.this$0, object, Hashing.smearedHash(object));
                if (biEntry == null) {
                    return true;
                }
                HashBiMap.access$200(this.this$1.this$0, biEntry);
                return false;
            }

            @Override
            public Iterator<V> iterator() {
                return new Itr<V>(this){
                    final InverseKeySet this$2;
                    {
                        this.this$2 = inverseKeySet;
                        super(inverseKeySet.this$1.this$0);
                    }

                    @Override
                    V output(BiEntry<K, V> biEntry) {
                        return biEntry.value;
                    }
                };
            }
        }
    }

    private final class KeySet
    extends Maps.KeySet<K, V> {
        final HashBiMap this$0;

        KeySet(HashBiMap hashBiMap) {
            this.this$0 = hashBiMap;
            super(hashBiMap);
        }

        @Override
        public Iterator<K> iterator() {
            return new Itr<K>(this){
                final KeySet this$1;
                {
                    this.this$1 = keySet;
                    super(keySet.this$0);
                }

                @Override
                K output(BiEntry<K, V> biEntry) {
                    return biEntry.key;
                }
            };
        }

        @Override
        public boolean remove(@Nullable Object object) {
            BiEntry biEntry = HashBiMap.access$300(this.this$0, object, Hashing.smearedHash(object));
            if (biEntry == null) {
                return true;
            }
            HashBiMap.access$200(this.this$0, biEntry);
            biEntry.prevInKeyInsertionOrder = null;
            biEntry.nextInKeyInsertionOrder = null;
            return false;
        }
    }

    abstract class Itr<T>
    implements Iterator<T> {
        BiEntry<K, V> next;
        BiEntry<K, V> toRemove;
        int expectedModCount;
        final HashBiMap this$0;

        Itr(HashBiMap hashBiMap) {
            this.this$0 = hashBiMap;
            this.next = HashBiMap.access$000(this.this$0);
            this.toRemove = null;
            this.expectedModCount = HashBiMap.access$100(this.this$0);
        }

        @Override
        public boolean hasNext() {
            if (HashBiMap.access$100(this.this$0) != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            return this.next != null;
        }

        @Override
        public T next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            BiEntry biEntry = this.next;
            this.next = biEntry.nextInKeyInsertionOrder;
            this.toRemove = biEntry;
            return this.output(biEntry);
        }

        @Override
        public void remove() {
            if (HashBiMap.access$100(this.this$0) != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            CollectPreconditions.checkRemove(this.toRemove != null);
            HashBiMap.access$200(this.this$0, this.toRemove);
            this.expectedModCount = HashBiMap.access$100(this.this$0);
            this.toRemove = null;
        }

        abstract T output(BiEntry<K, V> var1);
    }

    private static final class BiEntry<K, V>
    extends ImmutableEntry<K, V> {
        final int keyHash;
        final int valueHash;
        @Nullable
        BiEntry<K, V> nextInKToVBucket;
        @Nullable
        BiEntry<K, V> nextInVToKBucket;
        @Nullable
        BiEntry<K, V> nextInKeyInsertionOrder;
        @Nullable
        BiEntry<K, V> prevInKeyInsertionOrder;

        BiEntry(K k, int n, V v, int n2) {
            super(k, v);
            this.keyHash = n;
            this.valueHash = n2;
        }
    }
}

