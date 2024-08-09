/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
import com.viaversion.viaversion.libs.fastutil.Pair;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrayList;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class Object2ObjectOpenHashMap<K, V>
extends AbstractObject2ObjectMap<K, V>
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient K[] key;
    protected transient V[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Object2ObjectMap.FastEntrySet<K, V> entries;
    protected transient ObjectSet<K> keys;
    protected transient ObjectCollection<V> values;

    public Object2ObjectOpenHashMap(int n, float f) {
        if (f <= 0.0f || f >= 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1");
        }
        if (n < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.f = f;
        this.minN = this.n = HashCommon.arraySize(n, f);
        this.mask = this.n - 1;
        this.maxFill = HashCommon.maxFill(this.n, f);
        this.key = new Object[this.n + 1];
        this.value = new Object[this.n + 1];
    }

    public Object2ObjectOpenHashMap(int n) {
        this(n, 0.75f);
    }

    public Object2ObjectOpenHashMap() {
        this(16, 0.75f);
    }

    public Object2ObjectOpenHashMap(Map<? extends K, ? extends V> map, float f) {
        this(map.size(), f);
        this.putAll(map);
    }

    public Object2ObjectOpenHashMap(Map<? extends K, ? extends V> map) {
        this(map, 0.75f);
    }

    public Object2ObjectOpenHashMap(Object2ObjectMap<K, V> object2ObjectMap, float f) {
        this(object2ObjectMap.size(), f);
        this.putAll(object2ObjectMap);
    }

    public Object2ObjectOpenHashMap(Object2ObjectMap<K, V> object2ObjectMap) {
        this(object2ObjectMap, 0.75f);
    }

    public Object2ObjectOpenHashMap(K[] KArray, V[] VArray, float f) {
        this(KArray.length, f);
        if (KArray.length != VArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + KArray.length + " and " + VArray.length + ")");
        }
        for (int i = 0; i < KArray.length; ++i) {
            this.put(KArray[i], VArray[i]);
        }
    }

    public Object2ObjectOpenHashMap(K[] KArray, V[] VArray) {
        this(KArray, VArray, 0.75f);
    }

    private int realSize() {
        return this.containsNullKey ? this.size - 1 : this.size;
    }

    public void ensureCapacity(int n) {
        int n2 = HashCommon.arraySize(n, this.f);
        if (n2 > this.n) {
            this.rehash(n2);
        }
    }

    private void tryCapacity(long l) {
        int n = (int)Math.min(0x40000000L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil((float)l / this.f))));
        if (n > this.n) {
            this.rehash(n);
        }
    }

    private V removeEntry(int n) {
        V v = this.value[n];
        this.value[n] = null;
        --this.size;
        this.shiftKeys(n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return v;
    }

    private V removeNullEntry() {
        this.containsNullKey = false;
        this.key[this.n] = null;
        V v = this.value[this.n];
        this.value[this.n] = null;
        --this.size;
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return v;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(map.size());
        } else {
            this.tryCapacity(this.size() + map.size());
        }
        super.putAll(map);
    }

    private int find(K k) {
        if (k == null) {
            return this.containsNullKey ? this.n : -(this.n + 1);
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(k.hashCode()) & this.mask;
        K k2 = KArray[n];
        if (k2 == null) {
            return -(n + 1);
        }
        if (k.equals(k2)) {
            return n;
        }
        do {
            if ((k2 = KArray[n = n + 1 & this.mask]) != null) continue;
            return -(n + 1);
        } while (!k.equals(k2));
        return n;
    }

    private void insert(int n, K k, V v) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = k;
        this.value[n] = v;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
    }

    @Override
    public V put(K k, V v) {
        int n = this.find(k);
        if (n < 0) {
            this.insert(-n - 1, k, v);
            return (V)this.defRetValue;
        }
        V v2 = this.value[n];
        this.value[n] = v;
        return v2;
    }

    protected final void shiftKeys(int n) {
        K[] KArray = this.key;
        while (true) {
            K k;
            int n2 = n;
            n = n2 + 1 & this.mask;
            while (true) {
                if ((k = KArray[n]) == null) {
                    KArray[n2] = null;
                    this.value[n2] = null;
                    return;
                }
                int n3 = HashCommon.mix(k.hashCode()) & this.mask;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.mask;
            }
            KArray[n2] = k;
            this.value[n2] = this.value[n];
        }
    }

    @Override
    public V remove(Object object) {
        if (object == null) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return (V)this.defRetValue;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(object.hashCode()) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return (V)this.defRetValue;
        }
        if (object.equals(k)) {
            return this.removeEntry(n);
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return (V)this.defRetValue;
        } while (!object.equals(k));
        return this.removeEntry(n);
    }

    @Override
    public V get(Object object) {
        if (object == null) {
            return (V)(this.containsNullKey ? this.value[this.n] : this.defRetValue);
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(object.hashCode()) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return (V)this.defRetValue;
        }
        if (object.equals(k)) {
            return this.value[n];
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return (V)this.defRetValue;
        } while (!object.equals(k));
        return this.value[n];
    }

    @Override
    public boolean containsKey(Object object) {
        if (object == null) {
            return this.containsNullKey;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(object.hashCode()) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return true;
        }
        if (object.equals(k)) {
            return false;
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return true;
        } while (!object.equals(k));
        return false;
    }

    @Override
    public boolean containsValue(Object object) {
        V[] VArray = this.value;
        K[] KArray = this.key;
        if (this.containsNullKey && Objects.equals(VArray[this.n], object)) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (KArray[n] == null || !Objects.equals(VArray[n], object)) continue;
            return false;
        }
        return true;
    }

    @Override
    public V getOrDefault(Object object, V v) {
        if (object == null) {
            return this.containsNullKey ? this.value[this.n] : v;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(object.hashCode()) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return v;
        }
        if (object.equals(k)) {
            return this.value[n];
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return v;
        } while (!object.equals(k));
        return this.value[n];
    }

    @Override
    public V putIfAbsent(K k, V v) {
        int n = this.find(k);
        if (n >= 0) {
            return this.value[n];
        }
        this.insert(-n - 1, k, v);
        return (V)this.defRetValue;
    }

    @Override
    public boolean remove(Object object, Object object2) {
        if (object == null) {
            if (this.containsNullKey && Objects.equals(object2, this.value[this.n])) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(object.hashCode()) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return true;
        }
        if (object.equals(k) && Objects.equals(object2, this.value[n])) {
            this.removeEntry(n);
            return false;
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return true;
        } while (!object.equals(k) || !Objects.equals(object2, this.value[n]));
        this.removeEntry(n);
        return false;
    }

    @Override
    public boolean replace(K k, V v, V v2) {
        int n = this.find(k);
        if (n < 0 || !Objects.equals(v, this.value[n])) {
            return true;
        }
        this.value[n] = v2;
        return false;
    }

    @Override
    public V replace(K k, V v) {
        int n = this.find(k);
        if (n < 0) {
            return (V)this.defRetValue;
        }
        V v2 = this.value[n];
        this.value[n] = v;
        return v2;
    }

    @Override
    public V computeIfAbsent(K k, Object2ObjectFunction<? super K, ? extends V> object2ObjectFunction) {
        Objects.requireNonNull(object2ObjectFunction);
        int n = this.find(k);
        if (n >= 0) {
            return this.value[n];
        }
        if (!object2ObjectFunction.containsKey(k)) {
            return (V)this.defRetValue;
        }
        V v = object2ObjectFunction.get(k);
        this.insert(-n - 1, k, v);
        return v;
    }

    @Override
    public V computeIfPresent(K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(k);
        if (n < 0) {
            return (V)this.defRetValue;
        }
        if (this.value[n] == null) {
            return (V)this.defRetValue;
        }
        V v = biFunction.apply(k, this.value[n]);
        if (v == null) {
            if (k == null) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return (V)this.defRetValue;
        }
        this.value[n] = v;
        return this.value[n];
    }

    @Override
    public V compute(K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(k);
        V v = biFunction.apply(k, n >= 0 ? (Object)this.value[n] : null);
        if (v == null) {
            if (n >= 0) {
                if (k == null) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return (V)this.defRetValue;
        }
        V v2 = v;
        if (n < 0) {
            this.insert(-n - 1, k, v2);
            return v2;
        }
        this.value[n] = v2;
        return this.value[n];
    }

    @Override
    public V merge(K k, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        Objects.requireNonNull(v);
        int n = this.find(k);
        if (n < 0 || this.value[n] == null) {
            if (n < 0) {
                this.insert(-n - 1, k, v);
            } else {
                this.value[n] = v;
            }
            return v;
        }
        V v2 = biFunction.apply(this.value[n], v);
        if (v2 == null) {
            if (k == null) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return (V)this.defRetValue;
        }
        this.value[n] = v2;
        return this.value[n];
    }

    @Override
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNullKey = false;
        Arrays.fill(this.key, null);
        Arrays.fill(this.value, null);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    public Object2ObjectMap.FastEntrySet<K, V> object2ObjectEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet(this, null);
        }
        return this.entries;
    }

    @Override
    public ObjectSet<K> keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(this, null);
        }
        return this.keys;
    }

    @Override
    public ObjectCollection<V> values() {
        if (this.values == null) {
            this.values = new AbstractObjectCollection<V>(this){
                final Object2ObjectOpenHashMap this$0;
                {
                    this.this$0 = object2ObjectOpenHashMap;
                }

                @Override
                public ObjectIterator<V> iterator() {
                    return new ValueIterator(this.this$0);
                }

                @Override
                public ObjectSpliterator<V> spliterator() {
                    return new ValueSpliterator(this.this$0);
                }

                @Override
                public void forEach(Consumer<? super V> consumer) {
                    if (this.this$0.containsNullKey) {
                        consumer.accept(this.this$0.value[this.this$0.n]);
                    }
                    int n = this.this$0.n;
                    while (n-- != 0) {
                        if (this.this$0.key[n] == null) continue;
                        consumer.accept(this.this$0.value[n]);
                    }
                }

                @Override
                public int size() {
                    return this.this$0.size;
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
                public Spliterator spliterator() {
                    return this.spliterator();
                }

                @Override
                public Iterator iterator() {
                    return this.iterator();
                }
            };
        }
        return this.values;
    }

    public boolean trim() {
        return this.trim(this.size);
    }

    public boolean trim(int n) {
        int n2 = HashCommon.nextPowerOfTwo((int)Math.ceil((float)n / this.f));
        if (n2 >= this.n || this.size > HashCommon.maxFill(n2, this.f)) {
            return false;
        }
        try {
            this.rehash(n2);
        } catch (OutOfMemoryError outOfMemoryError) {
            return true;
        }
        return false;
    }

    protected void rehash(int n) {
        K[] KArray = this.key;
        V[] VArray = this.value;
        int n2 = n - 1;
        Object[] objectArray = new Object[n + 1];
        Object[] objectArray2 = new Object[n + 1];
        int n3 = this.n;
        int n4 = this.realSize();
        while (n4-- != 0) {
            while (KArray[--n3] == null) {
            }
            int n5 = HashCommon.mix(KArray[n3].hashCode()) & n2;
            if (objectArray[n5] != null) {
                while (objectArray[n5 = n5 + 1 & n2] != null) {
                }
            }
            objectArray[n5] = KArray[n3];
            objectArray2[n5] = VArray[n3];
        }
        objectArray2[n] = VArray[this.n];
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = objectArray;
        this.value = objectArray2;
    }

    public Object2ObjectOpenHashMap<K, V> clone() {
        Object2ObjectOpenHashMap object2ObjectOpenHashMap;
        try {
            object2ObjectOpenHashMap = (Object2ObjectOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        object2ObjectOpenHashMap.keys = null;
        object2ObjectOpenHashMap.values = null;
        object2ObjectOpenHashMap.entries = null;
        object2ObjectOpenHashMap.containsNullKey = this.containsNullKey;
        object2ObjectOpenHashMap.key = (Object[])this.key.clone();
        object2ObjectOpenHashMap.value = (Object[])this.value.clone();
        return object2ObjectOpenHashMap;
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.realSize();
        int n3 = 0;
        int n4 = 0;
        while (n2-- != 0) {
            while (this.key[n3] == null) {
                ++n3;
            }
            if (this != this.key[n3]) {
                n4 = this.key[n3].hashCode();
            }
            if (this != this.value[n3]) {
                n4 ^= this.value[n3] == null ? 0 : this.value[n3].hashCode();
            }
            n += n4;
            ++n3;
        }
        if (this.containsNullKey) {
            n += this.value[this.n] == null ? 0 : this.value[this.n].hashCode();
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        K[] KArray = this.key;
        V[] VArray = this.value;
        EntryIterator entryIterator = new EntryIterator(this, null);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = entryIterator.nextEntry();
            objectOutputStream.writeObject(KArray[n2]);
            objectOutputStream.writeObject(VArray[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new Object[this.n + 1];
        Object[] objectArray = this.key;
        this.value = new Object[this.n + 1];
        Object[] objectArray2 = this.value;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            Object object = objectInputStream.readObject();
            Object object2 = objectInputStream.readObject();
            if (object == null) {
                n2 = this.n;
                this.containsNullKey = true;
            } else {
                n2 = HashCommon.mix(object.hashCode()) & this.mask;
                while (objectArray[n2] != null) {
                    n2 = n2 + 1 & this.mask;
                }
            }
            objectArray[n2] = object;
            objectArray2[n2] = object2;
        }
    }

    private void checkTable() {
    }

    @Override
    public ObjectSet object2ObjectEntrySet() {
        return this.object2ObjectEntrySet();
    }

    @Override
    public Collection values() {
        return this.values();
    }

    @Override
    public Set keySet() {
        return this.keySet();
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    static int access$100(Object2ObjectOpenHashMap object2ObjectOpenHashMap) {
        return object2ObjectOpenHashMap.realSize();
    }

    static Object access$400(Object2ObjectOpenHashMap object2ObjectOpenHashMap) {
        return object2ObjectOpenHashMap.removeNullEntry();
    }

    static Object access$500(Object2ObjectOpenHashMap object2ObjectOpenHashMap, int n) {
        return object2ObjectOpenHashMap.removeEntry(n);
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Object2ObjectMap.Entry<K, V>>
    implements Object2ObjectMap.FastEntrySet<K, V> {
        final Object2ObjectOpenHashMap this$0;

        private MapEntrySet(Object2ObjectOpenHashMap object2ObjectOpenHashMap) {
            this.this$0 = object2ObjectOpenHashMap;
        }

        @Override
        public ObjectIterator<Object2ObjectMap.Entry<K, V>> iterator() {
            return new EntryIterator(this.this$0, null);
        }

        @Override
        public ObjectIterator<Object2ObjectMap.Entry<K, V>> fastIterator() {
            return new FastEntryIterator(this.this$0, null);
        }

        @Override
        public ObjectSpliterator<Object2ObjectMap.Entry<K, V>> spliterator() {
            return new EntrySpliterator(this.this$0);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            if (k == null) {
                return this.this$0.containsNullKey && Objects.equals(this.this$0.value[this.this$0.n], v);
            }
            K[] KArray = this.this$0.key;
            int n = HashCommon.mix(k.hashCode()) & this.this$0.mask;
            Object k2 = KArray[n];
            if (k2 == null) {
                return true;
            }
            if (k.equals(k2)) {
                return Objects.equals(this.this$0.value[n], v);
            }
            do {
                if ((k2 = KArray[n = n + 1 & this.this$0.mask]) != null) continue;
                return true;
            } while (!k.equals(k2));
            return Objects.equals(this.this$0.value[n], v);
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            if (k == null) {
                if (this.this$0.containsNullKey && Objects.equals(this.this$0.value[this.this$0.n], v)) {
                    Object2ObjectOpenHashMap.access$400(this.this$0);
                    return false;
                }
                return true;
            }
            K[] KArray = this.this$0.key;
            int n = HashCommon.mix(k.hashCode()) & this.this$0.mask;
            Object k2 = KArray[n];
            if (k2 == null) {
                return true;
            }
            if (k2.equals(k)) {
                if (Objects.equals(this.this$0.value[n], v)) {
                    Object2ObjectOpenHashMap.access$500(this.this$0, n);
                    return false;
                }
                return true;
            }
            do {
                if ((k2 = KArray[n = n + 1 & this.this$0.mask]) != null) continue;
                return true;
            } while (!k2.equals(k) || !Objects.equals(this.this$0.value[n], v));
            Object2ObjectOpenHashMap.access$500(this.this$0, n);
            return false;
        }

        @Override
        public int size() {
            return this.this$0.size;
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }

        @Override
        public void forEach(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(new MapEntry(this.this$0, this.this$0.n));
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] == null) continue;
                consumer.accept(new MapEntry(this.this$0, n));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
            MapEntry mapEntry = new MapEntry(this.this$0);
            if (this.this$0.containsNullKey) {
                mapEntry.index = this.this$0.n;
                consumer.accept(mapEntry);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] == null) continue;
                mapEntry.index = n;
                consumer.accept(mapEntry);
            }
        }

        @Override
        public Spliterator spliterator() {
            return this.spliterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        MapEntrySet(Object2ObjectOpenHashMap object2ObjectOpenHashMap, 1 var2_2) {
            this(object2ObjectOpenHashMap);
        }
    }

    private final class KeySet
    extends AbstractObjectSet<K> {
        final Object2ObjectOpenHashMap this$0;

        private KeySet(Object2ObjectOpenHashMap object2ObjectOpenHashMap) {
            this.this$0 = object2ObjectOpenHashMap;
        }

        @Override
        public ObjectIterator<K> iterator() {
            return new KeyIterator(this.this$0);
        }

        @Override
        public ObjectSpliterator<K> spliterator() {
            return new KeySpliterator(this.this$0);
        }

        @Override
        public void forEach(Consumer<? super K> consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(this.this$0.key[this.this$0.n]);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                Object k = this.this$0.key[n];
                if (k == null) continue;
                consumer.accept(k);
            }
        }

        @Override
        public int size() {
            return this.this$0.size;
        }

        @Override
        public boolean contains(Object object) {
            return this.this$0.containsKey(object);
        }

        @Override
        public boolean remove(Object object) {
            int n = this.this$0.size;
            this.this$0.remove(object);
            return this.this$0.size != n;
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }

        @Override
        public Spliterator spliterator() {
            return this.spliterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        KeySet(Object2ObjectOpenHashMap object2ObjectOpenHashMap, 1 var2_2) {
            this(object2ObjectOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class EntryIterator
    extends MapIterator<Consumer<? super Object2ObjectMap.Entry<K, V>>>
    implements ObjectIterator<Object2ObjectMap.Entry<K, V>> {
        private MapEntry entry;
        final Object2ObjectOpenHashMap this$0;

        private EntryIterator(Object2ObjectOpenHashMap object2ObjectOpenHashMap) {
            this.this$0 = object2ObjectOpenHashMap;
            super(object2ObjectOpenHashMap, null);
        }

        @Override
        public MapEntry next() {
            this.entry = new MapEntry(this.this$0, this.nextEntry());
            return this.entry;
        }

        @Override
        final void acceptOnIndex(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer, int n) {
            this.entry = new MapEntry(this.this$0, n);
            consumer.accept(this.entry);
        }

        @Override
        public void remove() {
            super.remove();
            this.entry.index = -1;
        }

        @Override
        void acceptOnIndex(Object object, int n) {
            this.acceptOnIndex((Consumer)object, n);
        }

        @Override
        public void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }

        @Override
        public Object next() {
            return this.next();
        }

        EntryIterator(Object2ObjectOpenHashMap object2ObjectOpenHashMap, 1 var2_2) {
            this(object2ObjectOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class ValueSpliterator
    extends MapSpliterator<Consumer<? super V>, ValueSpliterator>
    implements ObjectSpliterator<V> {
        private static final int POST_SPLIT_CHARACTERISTICS = 0;
        final Object2ObjectOpenHashMap this$0;

        ValueSpliterator(Object2ObjectOpenHashMap object2ObjectOpenHashMap) {
            this.this$0 = object2ObjectOpenHashMap;
            super(object2ObjectOpenHashMap);
        }

        ValueSpliterator(Object2ObjectOpenHashMap object2ObjectOpenHashMap, int n, int n2, boolean bl, boolean bl2) {
            this.this$0 = object2ObjectOpenHashMap;
            super(object2ObjectOpenHashMap, n, n2, bl, bl2);
        }

        @Override
        public int characteristics() {
            return this.hasSplit ? 0 : 64;
        }

        @Override
        final void acceptOnIndex(Consumer<? super V> consumer, int n) {
            consumer.accept(this.this$0.value[n]);
        }

        @Override
        final ValueSpliterator makeForSplit(int n, int n2, boolean bl) {
            return new ValueSpliterator(this.this$0, n, n2, bl, true);
        }

        @Override
        MapSpliterator makeForSplit(int n, int n2, boolean bl) {
            return this.makeForSplit(n, n2, bl);
        }

        @Override
        void acceptOnIndex(Object object, int n) {
            this.acceptOnIndex((Consumer)object, n);
        }

        @Override
        public ObjectSpliterator trySplit() {
            return (ObjectSpliterator)super.trySplit();
        }

        @Override
        public Spliterator trySplit() {
            return (Spliterator)super.trySplit();
        }

        @Override
        public void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }

        @Override
        public boolean tryAdvance(Consumer consumer) {
            return super.tryAdvance(consumer);
        }
    }

    private final class ValueIterator
    extends MapIterator<Consumer<? super V>>
    implements ObjectIterator<V> {
        final Object2ObjectOpenHashMap this$0;

        public ValueIterator(Object2ObjectOpenHashMap object2ObjectOpenHashMap) {
            this.this$0 = object2ObjectOpenHashMap;
            super(object2ObjectOpenHashMap, null);
        }

        @Override
        final void acceptOnIndex(Consumer<? super V> consumer, int n) {
            consumer.accept(this.this$0.value[n]);
        }

        @Override
        public V next() {
            return this.this$0.value[this.nextEntry()];
        }

        @Override
        void acceptOnIndex(Object object, int n) {
            this.acceptOnIndex((Consumer)object, n);
        }

        @Override
        public void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class KeySpliterator
    extends MapSpliterator<Consumer<? super K>, KeySpliterator>
    implements ObjectSpliterator<K> {
        private static final int POST_SPLIT_CHARACTERISTICS = 1;
        final Object2ObjectOpenHashMap this$0;

        KeySpliterator(Object2ObjectOpenHashMap object2ObjectOpenHashMap) {
            this.this$0 = object2ObjectOpenHashMap;
            super(object2ObjectOpenHashMap);
        }

        KeySpliterator(Object2ObjectOpenHashMap object2ObjectOpenHashMap, int n, int n2, boolean bl, boolean bl2) {
            this.this$0 = object2ObjectOpenHashMap;
            super(object2ObjectOpenHashMap, n, n2, bl, bl2);
        }

        @Override
        public int characteristics() {
            return this.hasSplit ? 1 : 65;
        }

        @Override
        final void acceptOnIndex(Consumer<? super K> consumer, int n) {
            consumer.accept(this.this$0.key[n]);
        }

        @Override
        final KeySpliterator makeForSplit(int n, int n2, boolean bl) {
            return new KeySpliterator(this.this$0, n, n2, bl, true);
        }

        @Override
        MapSpliterator makeForSplit(int n, int n2, boolean bl) {
            return this.makeForSplit(n, n2, bl);
        }

        @Override
        void acceptOnIndex(Object object, int n) {
            this.acceptOnIndex((Consumer)object, n);
        }

        @Override
        public ObjectSpliterator trySplit() {
            return (ObjectSpliterator)super.trySplit();
        }

        @Override
        public Spliterator trySplit() {
            return (Spliterator)super.trySplit();
        }

        @Override
        public void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }

        @Override
        public boolean tryAdvance(Consumer consumer) {
            return super.tryAdvance(consumer);
        }
    }

    private final class KeyIterator
    extends MapIterator<Consumer<? super K>>
    implements ObjectIterator<K> {
        final Object2ObjectOpenHashMap this$0;

        public KeyIterator(Object2ObjectOpenHashMap object2ObjectOpenHashMap) {
            this.this$0 = object2ObjectOpenHashMap;
            super(object2ObjectOpenHashMap, null);
        }

        @Override
        final void acceptOnIndex(Consumer<? super K> consumer, int n) {
            consumer.accept(this.this$0.key[n]);
        }

        @Override
        public K next() {
            return this.this$0.key[this.nextEntry()];
        }

        @Override
        void acceptOnIndex(Object object, int n) {
            this.acceptOnIndex((Consumer)object, n);
        }

        @Override
        public void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class EntrySpliterator
    extends MapSpliterator<Consumer<? super Object2ObjectMap.Entry<K, V>>, EntrySpliterator>
    implements ObjectSpliterator<Object2ObjectMap.Entry<K, V>> {
        private static final int POST_SPLIT_CHARACTERISTICS = 1;
        final Object2ObjectOpenHashMap this$0;

        EntrySpliterator(Object2ObjectOpenHashMap object2ObjectOpenHashMap) {
            this.this$0 = object2ObjectOpenHashMap;
            super(object2ObjectOpenHashMap);
        }

        EntrySpliterator(Object2ObjectOpenHashMap object2ObjectOpenHashMap, int n, int n2, boolean bl, boolean bl2) {
            this.this$0 = object2ObjectOpenHashMap;
            super(object2ObjectOpenHashMap, n, n2, bl, bl2);
        }

        @Override
        public int characteristics() {
            return this.hasSplit ? 1 : 65;
        }

        @Override
        final void acceptOnIndex(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer, int n) {
            consumer.accept(new MapEntry(this.this$0, n));
        }

        @Override
        final EntrySpliterator makeForSplit(int n, int n2, boolean bl) {
            return new EntrySpliterator(this.this$0, n, n2, bl, true);
        }

        @Override
        MapSpliterator makeForSplit(int n, int n2, boolean bl) {
            return this.makeForSplit(n, n2, bl);
        }

        @Override
        void acceptOnIndex(Object object, int n) {
            this.acceptOnIndex((Consumer)object, n);
        }

        @Override
        public ObjectSpliterator trySplit() {
            return (ObjectSpliterator)super.trySplit();
        }

        @Override
        public Spliterator trySplit() {
            return (Spliterator)super.trySplit();
        }

        @Override
        public void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }

        @Override
        public boolean tryAdvance(Consumer consumer) {
            return super.tryAdvance(consumer);
        }
    }

    private abstract class MapSpliterator<ConsumerType, SplitType extends MapSpliterator<ConsumerType, SplitType>> {
        int pos;
        int max;
        int c;
        boolean mustReturnNull;
        boolean hasSplit;
        final Object2ObjectOpenHashMap this$0;

        MapSpliterator(Object2ObjectOpenHashMap object2ObjectOpenHashMap) {
            this.this$0 = object2ObjectOpenHashMap;
            this.pos = 0;
            this.max = this.this$0.n;
            this.c = 0;
            this.mustReturnNull = this.this$0.containsNullKey;
            this.hasSplit = false;
        }

        MapSpliterator(Object2ObjectOpenHashMap object2ObjectOpenHashMap, int n, int n2, boolean bl, boolean bl2) {
            this.this$0 = object2ObjectOpenHashMap;
            this.pos = 0;
            this.max = this.this$0.n;
            this.c = 0;
            this.mustReturnNull = this.this$0.containsNullKey;
            this.hasSplit = false;
            this.pos = n;
            this.max = n2;
            this.mustReturnNull = bl;
            this.hasSplit = bl2;
        }

        abstract void acceptOnIndex(ConsumerType var1, int var2);

        abstract SplitType makeForSplit(int var1, int var2, boolean var3);

        public boolean tryAdvance(ConsumerType ConsumerType) {
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                ++this.c;
                this.acceptOnIndex(ConsumerType, this.this$0.n);
                return false;
            }
            K[] KArray = this.this$0.key;
            while (this.pos < this.max) {
                if (KArray[this.pos] != null) {
                    ++this.c;
                    this.acceptOnIndex(ConsumerType, this.pos++);
                    return false;
                }
                ++this.pos;
            }
            return true;
        }

        public void forEachRemaining(ConsumerType ConsumerType) {
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                ++this.c;
                this.acceptOnIndex(ConsumerType, this.this$0.n);
            }
            K[] KArray = this.this$0.key;
            while (this.pos < this.max) {
                if (KArray[this.pos] != null) {
                    this.acceptOnIndex(ConsumerType, this.pos);
                    ++this.c;
                }
                ++this.pos;
            }
        }

        public long estimateSize() {
            if (!this.hasSplit) {
                return this.this$0.size - this.c;
            }
            return Math.min((long)(this.this$0.size - this.c), (long)((double)Object2ObjectOpenHashMap.access$100(this.this$0) / (double)this.this$0.n * (double)(this.max - this.pos)) + (long)(this.mustReturnNull ? 1 : 0));
        }

        public SplitType trySplit() {
            if (this.pos >= this.max - 1) {
                return null;
            }
            int n = this.max - this.pos >> 1;
            if (n <= 1) {
                return null;
            }
            int n2 = this.pos + n;
            int n3 = this.pos;
            int n4 = n2;
            SplitType SplitType = this.makeForSplit(n3, n4, this.mustReturnNull);
            this.pos = n2;
            this.mustReturnNull = false;
            this.hasSplit = true;
            return SplitType;
        }

        public long skip(long l) {
            if (l < 0L) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + l);
            }
            if (l == 0L) {
                return 0L;
            }
            long l2 = 0L;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                ++l2;
                --l;
            }
            K[] KArray = this.this$0.key;
            while (this.pos < this.max && l > 0L) {
                if (KArray[this.pos++] == null) continue;
                ++l2;
                --l;
            }
            return l2;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class FastEntryIterator
    extends MapIterator<Consumer<? super Object2ObjectMap.Entry<K, V>>>
    implements ObjectIterator<Object2ObjectMap.Entry<K, V>> {
        private final MapEntry entry;
        final Object2ObjectOpenHashMap this$0;

        private FastEntryIterator(Object2ObjectOpenHashMap object2ObjectOpenHashMap) {
            this.this$0 = object2ObjectOpenHashMap;
            super(object2ObjectOpenHashMap, null);
            this.entry = new MapEntry(this.this$0);
        }

        @Override
        public MapEntry next() {
            this.entry.index = this.nextEntry();
            return this.entry;
        }

        @Override
        final void acceptOnIndex(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer, int n) {
            this.entry.index = n;
            consumer.accept(this.entry);
        }

        @Override
        void acceptOnIndex(Object object, int n) {
            this.acceptOnIndex((Consumer)object, n);
        }

        @Override
        public void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }

        @Override
        public Object next() {
            return this.next();
        }

        FastEntryIterator(Object2ObjectOpenHashMap object2ObjectOpenHashMap, 1 var2_2) {
            this(object2ObjectOpenHashMap);
        }
    }

    private abstract class MapIterator<ConsumerType> {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        ObjectArrayList<K> wrapped;
        final Object2ObjectOpenHashMap this$0;

        private MapIterator(Object2ObjectOpenHashMap object2ObjectOpenHashMap) {
            this.this$0 = object2ObjectOpenHashMap;
            this.pos = this.this$0.n;
            this.last = -1;
            this.c = this.this$0.size;
            this.mustReturnNullKey = this.this$0.containsNullKey;
        }

        abstract void acceptOnIndex(ConsumerType var1, int var2);

        public boolean hasNext() {
            return this.c != 0;
        }

        public int nextEntry() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            --this.c;
            if (this.mustReturnNullKey) {
                this.mustReturnNullKey = false;
                this.last = this.this$0.n;
                return this.last;
            }
            K[] KArray = this.this$0.key;
            do {
                if (--this.pos >= 0) continue;
                this.last = Integer.MIN_VALUE;
                Object k = this.wrapped.get(-this.pos - 1);
                int n = HashCommon.mix(k.hashCode()) & this.this$0.mask;
                while (!k.equals(KArray[n])) {
                    n = n + 1 & this.this$0.mask;
                }
                return n;
            } while (KArray[this.pos] == null);
            this.last = this.pos;
            return this.last;
        }

        public void forEachRemaining(ConsumerType ConsumerType) {
            if (this.mustReturnNullKey) {
                this.mustReturnNullKey = false;
                this.last = this.this$0.n;
                this.acceptOnIndex(ConsumerType, this.last);
                --this.c;
            }
            K[] KArray = this.this$0.key;
            while (this.c != 0) {
                if (--this.pos < 0) {
                    this.last = Integer.MIN_VALUE;
                    Object k = this.wrapped.get(-this.pos - 1);
                    int n = HashCommon.mix(k.hashCode()) & this.this$0.mask;
                    while (!k.equals(KArray[n])) {
                        n = n + 1 & this.this$0.mask;
                    }
                    this.acceptOnIndex(ConsumerType, n);
                    --this.c;
                    continue;
                }
                if (KArray[this.pos] == null) continue;
                this.last = this.pos;
                this.acceptOnIndex(ConsumerType, this.last);
                --this.c;
            }
        }

        private void shiftKeys(int n) {
            K[] KArray = this.this$0.key;
            while (true) {
                Object k;
                int n2 = n;
                n = n2 + 1 & this.this$0.mask;
                while (true) {
                    if ((k = KArray[n]) == null) {
                        KArray[n2] = null;
                        this.this$0.value[n2] = null;
                        return;
                    }
                    int n3 = HashCommon.mix(k.hashCode()) & this.this$0.mask;
                    if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                    n = n + 1 & this.this$0.mask;
                }
                if (n < n2) {
                    if (this.wrapped == null) {
                        this.wrapped = new ObjectArrayList(2);
                    }
                    this.wrapped.add(KArray[n]);
                }
                KArray[n2] = k;
                this.this$0.value[n2] = this.this$0.value[n];
            }
        }

        public void remove() {
            if (this.last == -1) {
                throw new IllegalStateException();
            }
            if (this.last == this.this$0.n) {
                this.this$0.containsNullKey = false;
                this.this$0.key[this.this$0.n] = null;
                this.this$0.value[this.this$0.n] = null;
            } else if (this.pos >= 0) {
                this.shiftKeys(this.last);
            } else {
                this.this$0.remove(this.wrapped.set(-this.pos - 1, (Object)null));
                this.last = -1;
                return;
            }
            --this.this$0.size;
            this.last = -1;
        }

        public int skip(int n) {
            int n2 = n;
            while (n2-- != 0 && this.hasNext()) {
                this.nextEntry();
            }
            return n - n2 - 1;
        }

        MapIterator(Object2ObjectOpenHashMap object2ObjectOpenHashMap, 1 var2_2) {
            this(object2ObjectOpenHashMap);
        }
    }

    final class MapEntry
    implements Object2ObjectMap.Entry<K, V>,
    Map.Entry<K, V>,
    Pair<K, V> {
        int index;
        final Object2ObjectOpenHashMap this$0;

        MapEntry(Object2ObjectOpenHashMap object2ObjectOpenHashMap, int n) {
            this.this$0 = object2ObjectOpenHashMap;
            this.index = n;
        }

        MapEntry(Object2ObjectOpenHashMap object2ObjectOpenHashMap) {
            this.this$0 = object2ObjectOpenHashMap;
        }

        @Override
        public K getKey() {
            return this.this$0.key[this.index];
        }

        @Override
        public K left() {
            return this.this$0.key[this.index];
        }

        @Override
        public V getValue() {
            return this.this$0.value[this.index];
        }

        @Override
        public V right() {
            return this.this$0.value[this.index];
        }

        @Override
        public V setValue(V v) {
            Object v2 = this.this$0.value[this.index];
            this.this$0.value[this.index] = v;
            return v2;
        }

        @Override
        public Pair<K, V> right(V v) {
            this.this$0.value[this.index] = v;
            return this;
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            return Objects.equals(this.this$0.key[this.index], entry.getKey()) && Objects.equals(this.this$0.value[this.index], entry.getValue());
        }

        @Override
        public int hashCode() {
            return (this.this$0.key[this.index] == null ? 0 : this.this$0.key[this.index].hashCode()) ^ (this.this$0.value[this.index] == null ? 0 : this.this$0.value[this.index].hashCode());
        }

        public String toString() {
            return this.this$0.key[this.index] + "=>" + this.this$0.value[this.index];
        }
    }
}

