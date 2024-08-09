/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrayList;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIntPair;
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
import java.util.function.IntConsumer;
import java.util.function.ToIntFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Object2IntOpenHashMap<K>
extends AbstractObject2IntMap<K>
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient K[] key;
    protected transient int[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Object2IntMap.FastEntrySet<K> entries;
    protected transient ObjectSet<K> keys;
    protected transient IntCollection values;

    public Object2IntOpenHashMap(int n, float f) {
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
        this.value = new int[this.n + 1];
    }

    public Object2IntOpenHashMap(int n) {
        this(n, 0.75f);
    }

    public Object2IntOpenHashMap() {
        this(16, 0.75f);
    }

    public Object2IntOpenHashMap(Map<? extends K, ? extends Integer> map, float f) {
        this(map.size(), f);
        this.putAll(map);
    }

    public Object2IntOpenHashMap(Map<? extends K, ? extends Integer> map) {
        this(map, 0.75f);
    }

    public Object2IntOpenHashMap(Object2IntMap<K> object2IntMap, float f) {
        this(object2IntMap.size(), f);
        this.putAll(object2IntMap);
    }

    public Object2IntOpenHashMap(Object2IntMap<K> object2IntMap) {
        this(object2IntMap, 0.75f);
    }

    public Object2IntOpenHashMap(K[] KArray, int[] nArray, float f) {
        this(KArray.length, f);
        if (KArray.length != nArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + KArray.length + " and " + nArray.length + ")");
        }
        for (int i = 0; i < KArray.length; ++i) {
            this.put(KArray[i], nArray[i]);
        }
    }

    public Object2IntOpenHashMap(K[] KArray, int[] nArray) {
        this(KArray, nArray, 0.75f);
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

    private int removeEntry(int n) {
        int n2 = this.value[n];
        --this.size;
        this.shiftKeys(n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return n2;
    }

    private int removeNullEntry() {
        this.containsNullKey = false;
        this.key[this.n] = null;
        int n = this.value[this.n];
        --this.size;
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return n;
    }

    @Override
    public void putAll(Map<? extends K, ? extends Integer> map) {
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

    private void insert(int n, K k, int n2) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = k;
        this.value[n] = n2;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
    }

    @Override
    public int put(K k, int n) {
        int n2 = this.find(k);
        if (n2 < 0) {
            this.insert(-n2 - 1, k, n);
            return this.defRetValue;
        }
        int n3 = this.value[n2];
        this.value[n2] = n;
        return n3;
    }

    private int addToValue(int n, int n2) {
        int n3 = this.value[n];
        this.value[n] = n3 + n2;
        return n3;
    }

    public int addTo(K k, int n) {
        int n2;
        if (k == null) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, n);
            }
            n2 = this.n;
            this.containsNullKey = true;
        } else {
            K[] KArray = this.key;
            n2 = HashCommon.mix(k.hashCode()) & this.mask;
            K k2 = KArray[n2];
            if (k2 != null) {
                if (k2.equals(k)) {
                    return this.addToValue(n2, n);
                }
                while ((k2 = KArray[n2 = n2 + 1 & this.mask]) != null) {
                    if (!k2.equals(k)) continue;
                    return this.addToValue(n2, n);
                }
            }
        }
        this.key[n2] = k;
        this.value[n2] = this.defRetValue + n;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return this.defRetValue;
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
    public int removeInt(Object object) {
        if (object == null) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(object.hashCode()) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return this.defRetValue;
        }
        if (object.equals(k)) {
            return this.removeEntry(n);
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return this.defRetValue;
        } while (!object.equals(k));
        return this.removeEntry(n);
    }

    @Override
    public int getInt(Object object) {
        if (object == null) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(object.hashCode()) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return this.defRetValue;
        }
        if (object.equals(k)) {
            return this.value[n];
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return this.defRetValue;
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
    public boolean containsValue(int n) {
        int[] nArray = this.value;
        K[] KArray = this.key;
        if (this.containsNullKey && nArray[this.n] == n) {
            return false;
        }
        int n2 = this.n;
        while (n2-- != 0) {
            if (KArray[n2] == null || nArray[n2] != n) continue;
            return false;
        }
        return true;
    }

    @Override
    public int getOrDefault(Object object, int n) {
        if (object == null) {
            return this.containsNullKey ? this.value[this.n] : n;
        }
        K[] KArray = this.key;
        int n2 = HashCommon.mix(object.hashCode()) & this.mask;
        K k = KArray[n2];
        if (k == null) {
            return n;
        }
        if (object.equals(k)) {
            return this.value[n2];
        }
        do {
            if ((k = KArray[n2 = n2 + 1 & this.mask]) != null) continue;
            return n;
        } while (!object.equals(k));
        return this.value[n2];
    }

    @Override
    public int putIfAbsent(K k, int n) {
        int n2 = this.find(k);
        if (n2 >= 0) {
            return this.value[n2];
        }
        this.insert(-n2 - 1, k, n);
        return this.defRetValue;
    }

    @Override
    public boolean remove(Object object, int n) {
        if (object == null) {
            if (this.containsNullKey && n == this.value[this.n]) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        K[] KArray = this.key;
        int n2 = HashCommon.mix(object.hashCode()) & this.mask;
        K k = KArray[n2];
        if (k == null) {
            return true;
        }
        if (object.equals(k) && n == this.value[n2]) {
            this.removeEntry(n2);
            return false;
        }
        do {
            if ((k = KArray[n2 = n2 + 1 & this.mask]) != null) continue;
            return true;
        } while (!object.equals(k) || n != this.value[n2]);
        this.removeEntry(n2);
        return false;
    }

    @Override
    public boolean replace(K k, int n, int n2) {
        int n3 = this.find(k);
        if (n3 < 0 || n != this.value[n3]) {
            return true;
        }
        this.value[n3] = n2;
        return false;
    }

    @Override
    public int replace(K k, int n) {
        int n2 = this.find(k);
        if (n2 < 0) {
            return this.defRetValue;
        }
        int n3 = this.value[n2];
        this.value[n2] = n;
        return n3;
    }

    @Override
    public int computeIfAbsent(K k, ToIntFunction<? super K> toIntFunction) {
        Objects.requireNonNull(toIntFunction);
        int n = this.find(k);
        if (n >= 0) {
            return this.value[n];
        }
        int n2 = toIntFunction.applyAsInt(k);
        this.insert(-n - 1, k, n2);
        return n2;
    }

    @Override
    public int computeIfAbsent(K k, Object2IntFunction<? super K> object2IntFunction) {
        Objects.requireNonNull(object2IntFunction);
        int n = this.find(k);
        if (n >= 0) {
            return this.value[n];
        }
        if (!object2IntFunction.containsKey(k)) {
            return this.defRetValue;
        }
        int n2 = object2IntFunction.getInt(k);
        this.insert(-n - 1, k, n2);
        return n2;
    }

    @Override
    public int computeIntIfPresent(K k, BiFunction<? super K, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(k);
        if (n < 0) {
            return this.defRetValue;
        }
        Integer n2 = biFunction.apply(k, this.value[n]);
        if (n2 == null) {
            if (k == null) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return this.defRetValue;
        }
        this.value[n] = n2;
        return this.value[n];
    }

    @Override
    public int computeInt(K k, BiFunction<? super K, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(k);
        Integer n2 = biFunction.apply(k, n >= 0 ? Integer.valueOf(this.value[n]) : null);
        if (n2 == null) {
            if (n >= 0) {
                if (k == null) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return this.defRetValue;
        }
        int n3 = n2;
        if (n < 0) {
            this.insert(-n - 1, k, n3);
            return n3;
        }
        this.value[n] = n3;
        return this.value[n];
    }

    @Override
    public int merge(K k, int n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n2 = this.find(k);
        if (n2 < 0) {
            if (n2 < 0) {
                this.insert(-n2 - 1, k, n);
            } else {
                this.value[n2] = n;
            }
            return n;
        }
        Integer n3 = biFunction.apply((Integer)this.value[n2], (Integer)n);
        if (n3 == null) {
            if (k == null) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n2);
            }
            return this.defRetValue;
        }
        this.value[n2] = n3;
        return this.value[n2];
    }

    @Override
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNullKey = false;
        Arrays.fill(this.key, null);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    public Object2IntMap.FastEntrySet<K> object2IntEntrySet() {
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
    public IntCollection values() {
        if (this.values == null) {
            this.values = new AbstractIntCollection(this){
                final Object2IntOpenHashMap this$0;
                {
                    this.this$0 = object2IntOpenHashMap;
                }

                @Override
                public IntIterator iterator() {
                    return new ValueIterator(this.this$0);
                }

                @Override
                public IntSpliterator spliterator() {
                    return new ValueSpliterator(this.this$0);
                }

                @Override
                public void forEach(IntConsumer intConsumer) {
                    if (this.this$0.containsNullKey) {
                        intConsumer.accept(this.this$0.value[this.this$0.n]);
                    }
                    int n = this.this$0.n;
                    while (n-- != 0) {
                        if (this.this$0.key[n] == null) continue;
                        intConsumer.accept(this.this$0.value[n]);
                    }
                }

                @Override
                public int size() {
                    return this.this$0.size;
                }

                @Override
                public boolean contains(int n) {
                    return this.this$0.containsValue(n);
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
        int[] nArray = this.value;
        int n2 = n - 1;
        Object[] objectArray = new Object[n + 1];
        int[] nArray2 = new int[n + 1];
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
            nArray2[n5] = nArray[n3];
        }
        nArray2[n] = nArray[this.n];
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = objectArray;
        this.value = nArray2;
    }

    public Object2IntOpenHashMap<K> clone() {
        Object2IntOpenHashMap object2IntOpenHashMap;
        try {
            object2IntOpenHashMap = (Object2IntOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        object2IntOpenHashMap.keys = null;
        object2IntOpenHashMap.values = null;
        object2IntOpenHashMap.entries = null;
        object2IntOpenHashMap.containsNullKey = this.containsNullKey;
        object2IntOpenHashMap.key = (Object[])this.key.clone();
        object2IntOpenHashMap.value = (int[])this.value.clone();
        return object2IntOpenHashMap;
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
            n += (n4 ^= this.value[n3]);
            ++n3;
        }
        if (this.containsNullKey) {
            n += this.value[this.n];
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        K[] KArray = this.key;
        int[] nArray = this.value;
        EntryIterator entryIterator = new EntryIterator(this, null);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = entryIterator.nextEntry();
            objectOutputStream.writeObject(KArray[n2]);
            objectOutputStream.writeInt(nArray[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new Object[this.n + 1];
        Object[] objectArray = this.key;
        this.value = new int[this.n + 1];
        int[] nArray = this.value;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            Object object = objectInputStream.readObject();
            int n3 = objectInputStream.readInt();
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
            nArray[n2] = n3;
        }
    }

    private void checkTable() {
    }

    @Override
    public ObjectSet object2IntEntrySet() {
        return this.object2IntEntrySet();
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

    static int access$100(Object2IntOpenHashMap object2IntOpenHashMap) {
        return object2IntOpenHashMap.realSize();
    }

    static int access$400(Object2IntOpenHashMap object2IntOpenHashMap) {
        return object2IntOpenHashMap.removeNullEntry();
    }

    static int access$500(Object2IntOpenHashMap object2IntOpenHashMap, int n) {
        return object2IntOpenHashMap.removeEntry(n);
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Object2IntMap.Entry<K>>
    implements Object2IntMap.FastEntrySet<K> {
        final Object2IntOpenHashMap this$0;

        private MapEntrySet(Object2IntOpenHashMap object2IntOpenHashMap) {
            this.this$0 = object2IntOpenHashMap;
        }

        @Override
        public ObjectIterator<Object2IntMap.Entry<K>> iterator() {
            return new EntryIterator(this.this$0, null);
        }

        @Override
        public ObjectIterator<Object2IntMap.Entry<K>> fastIterator() {
            return new FastEntryIterator(this.this$0, null);
        }

        @Override
        public ObjectSpliterator<Object2IntMap.Entry<K>> spliterator() {
            return new EntrySpliterator(this.this$0);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            Object k = entry.getKey();
            int n = (Integer)entry.getValue();
            if (k == null) {
                return this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == n;
            }
            K[] KArray = this.this$0.key;
            int n2 = HashCommon.mix(k.hashCode()) & this.this$0.mask;
            Object k2 = KArray[n2];
            if (k2 == null) {
                return true;
            }
            if (k.equals(k2)) {
                return this.this$0.value[n2] == n;
            }
            do {
                if ((k2 = KArray[n2 = n2 + 1 & this.this$0.mask]) != null) continue;
                return true;
            } while (!k.equals(k2));
            return this.this$0.value[n2] == n;
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            Object k = entry.getKey();
            int n = (Integer)entry.getValue();
            if (k == null) {
                if (this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == n) {
                    Object2IntOpenHashMap.access$400(this.this$0);
                    return false;
                }
                return true;
            }
            K[] KArray = this.this$0.key;
            int n2 = HashCommon.mix(k.hashCode()) & this.this$0.mask;
            Object k2 = KArray[n2];
            if (k2 == null) {
                return true;
            }
            if (k2.equals(k)) {
                if (this.this$0.value[n2] == n) {
                    Object2IntOpenHashMap.access$500(this.this$0, n2);
                    return false;
                }
                return true;
            }
            do {
                if ((k2 = KArray[n2 = n2 + 1 & this.this$0.mask]) != null) continue;
                return true;
            } while (!k2.equals(k) || this.this$0.value[n2] != n);
            Object2IntOpenHashMap.access$500(this.this$0, n2);
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
        public void forEach(Consumer<? super Object2IntMap.Entry<K>> consumer) {
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
        public void fastForEach(Consumer<? super Object2IntMap.Entry<K>> consumer) {
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

        MapEntrySet(Object2IntOpenHashMap object2IntOpenHashMap, 1 var2_2) {
            this(object2IntOpenHashMap);
        }
    }

    private final class KeySet
    extends AbstractObjectSet<K> {
        final Object2IntOpenHashMap this$0;

        private KeySet(Object2IntOpenHashMap object2IntOpenHashMap) {
            this.this$0 = object2IntOpenHashMap;
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
            this.this$0.removeInt(object);
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

        KeySet(Object2IntOpenHashMap object2IntOpenHashMap, 1 var2_2) {
            this(object2IntOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class EntryIterator
    extends MapIterator<Consumer<? super Object2IntMap.Entry<K>>>
    implements ObjectIterator<Object2IntMap.Entry<K>> {
        private MapEntry entry;
        final Object2IntOpenHashMap this$0;

        private EntryIterator(Object2IntOpenHashMap object2IntOpenHashMap) {
            this.this$0 = object2IntOpenHashMap;
            super(object2IntOpenHashMap, null);
        }

        @Override
        public MapEntry next() {
            this.entry = new MapEntry(this.this$0, this.nextEntry());
            return this.entry;
        }

        @Override
        final void acceptOnIndex(Consumer<? super Object2IntMap.Entry<K>> consumer, int n) {
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

        EntryIterator(Object2IntOpenHashMap object2IntOpenHashMap, 1 var2_2) {
            this(object2IntOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class ValueSpliterator
    extends MapSpliterator<IntConsumer, ValueSpliterator>
    implements IntSpliterator {
        private static final int POST_SPLIT_CHARACTERISTICS = 256;
        final Object2IntOpenHashMap this$0;

        ValueSpliterator(Object2IntOpenHashMap object2IntOpenHashMap) {
            this.this$0 = object2IntOpenHashMap;
            super(object2IntOpenHashMap);
        }

        ValueSpliterator(Object2IntOpenHashMap object2IntOpenHashMap, int n, int n2, boolean bl, boolean bl2) {
            this.this$0 = object2IntOpenHashMap;
            super(object2IntOpenHashMap, n, n2, bl, bl2);
        }

        @Override
        public int characteristics() {
            return this.hasSplit ? 256 : 320;
        }

        @Override
        final void acceptOnIndex(IntConsumer intConsumer, int n) {
            intConsumer.accept(this.this$0.value[n]);
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
            this.acceptOnIndex((IntConsumer)object, n);
        }

        @Override
        public IntSpliterator trySplit() {
            return (IntSpliterator)super.trySplit();
        }

        @Override
        public void forEachRemaining(IntConsumer intConsumer) {
            super.forEachRemaining(intConsumer);
        }

        @Override
        public boolean tryAdvance(IntConsumer intConsumer) {
            return super.tryAdvance(intConsumer);
        }

        @Override
        public Spliterator.OfInt trySplit() {
            return (Spliterator.OfInt)super.trySplit();
        }

        @Override
        public Spliterator.OfPrimitive trySplit() {
            return (Spliterator.OfPrimitive)super.trySplit();
        }

        @Override
        public Spliterator trySplit() {
            return (Spliterator)super.trySplit();
        }
    }

    private final class ValueIterator
    extends MapIterator<IntConsumer>
    implements IntIterator {
        final Object2IntOpenHashMap this$0;

        public ValueIterator(Object2IntOpenHashMap object2IntOpenHashMap) {
            this.this$0 = object2IntOpenHashMap;
            super(object2IntOpenHashMap, null);
        }

        @Override
        final void acceptOnIndex(IntConsumer intConsumer, int n) {
            intConsumer.accept(this.this$0.value[n]);
        }

        @Override
        public int nextInt() {
            return this.this$0.value[this.nextEntry()];
        }

        @Override
        void acceptOnIndex(Object object, int n) {
            this.acceptOnIndex((IntConsumer)object, n);
        }

        @Override
        public void forEachRemaining(IntConsumer intConsumer) {
            super.forEachRemaining(intConsumer);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class KeySpliterator
    extends MapSpliterator<Consumer<? super K>, KeySpliterator>
    implements ObjectSpliterator<K> {
        private static final int POST_SPLIT_CHARACTERISTICS = 1;
        final Object2IntOpenHashMap this$0;

        KeySpliterator(Object2IntOpenHashMap object2IntOpenHashMap) {
            this.this$0 = object2IntOpenHashMap;
            super(object2IntOpenHashMap);
        }

        KeySpliterator(Object2IntOpenHashMap object2IntOpenHashMap, int n, int n2, boolean bl, boolean bl2) {
            this.this$0 = object2IntOpenHashMap;
            super(object2IntOpenHashMap, n, n2, bl, bl2);
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
        final Object2IntOpenHashMap this$0;

        public KeyIterator(Object2IntOpenHashMap object2IntOpenHashMap) {
            this.this$0 = object2IntOpenHashMap;
            super(object2IntOpenHashMap, null);
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
    extends MapSpliterator<Consumer<? super Object2IntMap.Entry<K>>, EntrySpliterator>
    implements ObjectSpliterator<Object2IntMap.Entry<K>> {
        private static final int POST_SPLIT_CHARACTERISTICS = 1;
        final Object2IntOpenHashMap this$0;

        EntrySpliterator(Object2IntOpenHashMap object2IntOpenHashMap) {
            this.this$0 = object2IntOpenHashMap;
            super(object2IntOpenHashMap);
        }

        EntrySpliterator(Object2IntOpenHashMap object2IntOpenHashMap, int n, int n2, boolean bl, boolean bl2) {
            this.this$0 = object2IntOpenHashMap;
            super(object2IntOpenHashMap, n, n2, bl, bl2);
        }

        @Override
        public int characteristics() {
            return this.hasSplit ? 1 : 65;
        }

        @Override
        final void acceptOnIndex(Consumer<? super Object2IntMap.Entry<K>> consumer, int n) {
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
        final Object2IntOpenHashMap this$0;

        MapSpliterator(Object2IntOpenHashMap object2IntOpenHashMap) {
            this.this$0 = object2IntOpenHashMap;
            this.pos = 0;
            this.max = this.this$0.n;
            this.c = 0;
            this.mustReturnNull = this.this$0.containsNullKey;
            this.hasSplit = false;
        }

        MapSpliterator(Object2IntOpenHashMap object2IntOpenHashMap, int n, int n2, boolean bl, boolean bl2) {
            this.this$0 = object2IntOpenHashMap;
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
            return Math.min((long)(this.this$0.size - this.c), (long)((double)Object2IntOpenHashMap.access$100(this.this$0) / (double)this.this$0.n * (double)(this.max - this.pos)) + (long)(this.mustReturnNull ? 1 : 0));
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
    extends MapIterator<Consumer<? super Object2IntMap.Entry<K>>>
    implements ObjectIterator<Object2IntMap.Entry<K>> {
        private final MapEntry entry;
        final Object2IntOpenHashMap this$0;

        private FastEntryIterator(Object2IntOpenHashMap object2IntOpenHashMap) {
            this.this$0 = object2IntOpenHashMap;
            super(object2IntOpenHashMap, null);
            this.entry = new MapEntry(this.this$0);
        }

        @Override
        public MapEntry next() {
            this.entry.index = this.nextEntry();
            return this.entry;
        }

        @Override
        final void acceptOnIndex(Consumer<? super Object2IntMap.Entry<K>> consumer, int n) {
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

        FastEntryIterator(Object2IntOpenHashMap object2IntOpenHashMap, 1 var2_2) {
            this(object2IntOpenHashMap);
        }
    }

    private abstract class MapIterator<ConsumerType> {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        ObjectArrayList<K> wrapped;
        final Object2IntOpenHashMap this$0;

        private MapIterator(Object2IntOpenHashMap object2IntOpenHashMap) {
            this.this$0 = object2IntOpenHashMap;
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
            } else if (this.pos >= 0) {
                this.shiftKeys(this.last);
            } else {
                this.this$0.removeInt(this.wrapped.set(-this.pos - 1, (Object)null));
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

        MapIterator(Object2IntOpenHashMap object2IntOpenHashMap, 1 var2_2) {
            this(object2IntOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Object2IntMap.Entry<K>,
    Map.Entry<K, Integer>,
    ObjectIntPair<K> {
        int index;
        final Object2IntOpenHashMap this$0;

        MapEntry(Object2IntOpenHashMap object2IntOpenHashMap, int n) {
            this.this$0 = object2IntOpenHashMap;
            this.index = n;
        }

        MapEntry(Object2IntOpenHashMap object2IntOpenHashMap) {
            this.this$0 = object2IntOpenHashMap;
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
        public int getIntValue() {
            return this.this$0.value[this.index];
        }

        @Override
        public int rightInt() {
            return this.this$0.value[this.index];
        }

        @Override
        public int setValue(int n) {
            int n2 = this.this$0.value[this.index];
            this.this$0.value[this.index] = n;
            return n2;
        }

        @Override
        public ObjectIntPair<K> right(int n) {
            this.this$0.value[this.index] = n;
            return this;
        }

        @Override
        @Deprecated
        public Integer getValue() {
            return this.this$0.value[this.index];
        }

        @Override
        @Deprecated
        public Integer setValue(Integer n) {
            return this.setValue((int)n);
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            return Objects.equals(this.this$0.key[this.index], entry.getKey()) && this.this$0.value[this.index] == (Integer)entry.getValue();
        }

        @Override
        public int hashCode() {
            return (this.this$0.key[this.index] == null ? 0 : this.this$0.key[this.index].hashCode()) ^ this.this$0.value[this.index];
        }

        public String toString() {
            return this.this$0.key[this.index] + "=>" + this.this$0.value[this.index];
        }

        @Override
        @Deprecated
        public Object setValue(Object object) {
            return this.setValue((Integer)object);
        }

        @Override
        @Deprecated
        public Object getValue() {
            return this.getValue();
        }
    }
}

