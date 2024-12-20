/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReference2IntMap;
import it.unimi.dsi.fastutil.objects.AbstractReferenceSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
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
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.ToIntFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Reference2IntOpenHashMap<K>
extends AbstractReference2IntMap<K>
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
    protected transient Reference2IntMap.FastEntrySet<K> entries;
    protected transient ReferenceSet<K> keys;
    protected transient IntCollection values;

    public Reference2IntOpenHashMap(int n, float f) {
        if (f <= 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
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

    public Reference2IntOpenHashMap(int n) {
        this(n, 0.75f);
    }

    public Reference2IntOpenHashMap() {
        this(16, 0.75f);
    }

    public Reference2IntOpenHashMap(Map<? extends K, ? extends Integer> map, float f) {
        this(map.size(), f);
        this.putAll(map);
    }

    public Reference2IntOpenHashMap(Map<? extends K, ? extends Integer> map) {
        this(map, 0.75f);
    }

    public Reference2IntOpenHashMap(Reference2IntMap<K> reference2IntMap, float f) {
        this(reference2IntMap.size(), f);
        this.putAll(reference2IntMap);
    }

    public Reference2IntOpenHashMap(Reference2IntMap<K> reference2IntMap) {
        this(reference2IntMap, 0.75f);
    }

    public Reference2IntOpenHashMap(K[] KArray, int[] nArray, float f) {
        this(KArray.length, f);
        if (KArray.length != nArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + KArray.length + " and " + nArray.length + ")");
        }
        for (int i = 0; i < KArray.length; ++i) {
            this.put(KArray[i], nArray[i]);
        }
    }

    public Reference2IntOpenHashMap(K[] KArray, int[] nArray) {
        this(KArray, nArray, 0.75f);
    }

    private int realSize() {
        return this.containsNullKey ? this.size - 1 : this.size;
    }

    private void ensureCapacity(int n) {
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
        int n = HashCommon.mix(System.identityHashCode(k)) & this.mask;
        K k2 = KArray[n];
        if (k2 == null) {
            return -(n + 1);
        }
        if (k == k2) {
            return n;
        }
        do {
            if ((k2 = KArray[n = n + 1 & this.mask]) != null) continue;
            return -(n + 1);
        } while (k != k2);
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
            n2 = HashCommon.mix(System.identityHashCode(k)) & this.mask;
            K k2 = KArray[n2];
            if (k2 != null) {
                if (k2 == k) {
                    return this.addToValue(n2, n);
                }
                while ((k2 = KArray[n2 = n2 + 1 & this.mask]) != null) {
                    if (k2 != k) continue;
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
                int n3 = HashCommon.mix(System.identityHashCode(k)) & this.mask;
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
        int n = HashCommon.mix(System.identityHashCode(object)) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return this.defRetValue;
        }
        if (object == k) {
            return this.removeEntry(n);
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return this.defRetValue;
        } while (object != k);
        return this.removeEntry(n);
    }

    @Override
    public int getInt(Object object) {
        if (object == null) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(System.identityHashCode(object)) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return this.defRetValue;
        }
        if (object == k) {
            return this.value[n];
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return this.defRetValue;
        } while (object != k);
        return this.value[n];
    }

    @Override
    public boolean containsKey(Object object) {
        if (object == null) {
            return this.containsNullKey;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(System.identityHashCode(object)) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return true;
        }
        if (object == k) {
            return false;
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return true;
        } while (object != k);
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
        int n2 = HashCommon.mix(System.identityHashCode(object)) & this.mask;
        K k = KArray[n2];
        if (k == null) {
            return n;
        }
        if (object == k) {
            return this.value[n2];
        }
        do {
            if ((k = KArray[n2 = n2 + 1 & this.mask]) != null) continue;
            return n;
        } while (object != k);
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
        int n2 = HashCommon.mix(System.identityHashCode(object)) & this.mask;
        K k = KArray[n2];
        if (k == null) {
            return true;
        }
        if (object == k && n == this.value[n2]) {
            this.removeEntry(n2);
            return false;
        }
        do {
            if ((k = KArray[n2 = n2 + 1 & this.mask]) != null) continue;
            return true;
        } while (object != k || n != this.value[n2]);
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
    public int computeIntIfAbsent(K k, ToIntFunction<? super K> toIntFunction) {
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
    public int mergeInt(K k, int n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n2 = this.find(k);
        if (n2 < 0) {
            this.insert(-n2 - 1, k, n);
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

    public Reference2IntMap.FastEntrySet<K> reference2IntEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet(this, null);
        }
        return this.entries;
    }

    @Override
    public ReferenceSet<K> keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(this, null);
        }
        return this.keys;
    }

    @Override
    public IntCollection values() {
        if (this.values == null) {
            this.values = new AbstractIntCollection(this){
                final Reference2IntOpenHashMap this$0;
                {
                    this.this$0 = reference2IntOpenHashMap;
                }

                @Override
                public IntIterator iterator() {
                    return new ValueIterator(this.this$0);
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
                public Iterator iterator() {
                    return this.iterator();
                }
            };
        }
        return this.values;
    }

    public boolean trim() {
        int n = HashCommon.arraySize(this.size, this.f);
        if (n >= this.n || this.size > HashCommon.maxFill(n, this.f)) {
            return false;
        }
        try {
            this.rehash(n);
        } catch (OutOfMemoryError outOfMemoryError) {
            return true;
        }
        return false;
    }

    public boolean trim(int n) {
        int n2 = HashCommon.nextPowerOfTwo((int)Math.ceil((float)n / this.f));
        if (n2 >= n || this.size > HashCommon.maxFill(n2, this.f)) {
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
            int n5 = HashCommon.mix(System.identityHashCode(KArray[n3])) & n2;
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

    public Reference2IntOpenHashMap<K> clone() {
        Reference2IntOpenHashMap reference2IntOpenHashMap;
        try {
            reference2IntOpenHashMap = (Reference2IntOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        reference2IntOpenHashMap.keys = null;
        reference2IntOpenHashMap.values = null;
        reference2IntOpenHashMap.entries = null;
        reference2IntOpenHashMap.containsNullKey = this.containsNullKey;
        reference2IntOpenHashMap.key = (Object[])this.key.clone();
        reference2IntOpenHashMap.value = (int[])this.value.clone();
        return reference2IntOpenHashMap;
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
                n4 = System.identityHashCode(this.key[n3]);
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
        MapIterator mapIterator = new MapIterator(this, null);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
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
                n2 = HashCommon.mix(System.identityHashCode(object)) & this.mask;
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
    public ObjectSet reference2IntEntrySet() {
        return this.reference2IntEntrySet();
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

    static int access$300(Reference2IntOpenHashMap reference2IntOpenHashMap) {
        return reference2IntOpenHashMap.removeNullEntry();
    }

    static int access$400(Reference2IntOpenHashMap reference2IntOpenHashMap, int n) {
        return reference2IntOpenHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements IntIterator {
        final Reference2IntOpenHashMap this$0;

        public ValueIterator(Reference2IntOpenHashMap reference2IntOpenHashMap) {
            this.this$0 = reference2IntOpenHashMap;
            super(reference2IntOpenHashMap, null);
        }

        @Override
        public int nextInt() {
            return this.this$0.value[this.nextEntry()];
        }
    }

    private final class KeySet
    extends AbstractReferenceSet<K> {
        final Reference2IntOpenHashMap this$0;

        private KeySet(Reference2IntOpenHashMap reference2IntOpenHashMap) {
            this.this$0 = reference2IntOpenHashMap;
        }

        @Override
        public ObjectIterator<K> iterator() {
            return new KeyIterator(this.this$0);
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
        public Iterator iterator() {
            return this.iterator();
        }

        KeySet(Reference2IntOpenHashMap reference2IntOpenHashMap, 1 var2_2) {
            this(reference2IntOpenHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements ObjectIterator<K> {
        final Reference2IntOpenHashMap this$0;

        public KeyIterator(Reference2IntOpenHashMap reference2IntOpenHashMap) {
            this.this$0 = reference2IntOpenHashMap;
            super(reference2IntOpenHashMap, null);
        }

        @Override
        public K next() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Reference2IntMap.Entry<K>>
    implements Reference2IntMap.FastEntrySet<K> {
        final Reference2IntOpenHashMap this$0;

        private MapEntrySet(Reference2IntOpenHashMap reference2IntOpenHashMap) {
            this.this$0 = reference2IntOpenHashMap;
        }

        @Override
        public ObjectIterator<Reference2IntMap.Entry<K>> iterator() {
            return new EntryIterator(this.this$0, null);
        }

        @Override
        public ObjectIterator<Reference2IntMap.Entry<K>> fastIterator() {
            return new FastEntryIterator(this.this$0, null);
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
            int n2 = HashCommon.mix(System.identityHashCode(k)) & this.this$0.mask;
            Object k2 = KArray[n2];
            if (k2 == null) {
                return true;
            }
            if (k == k2) {
                return this.this$0.value[n2] == n;
            }
            do {
                if ((k2 = KArray[n2 = n2 + 1 & this.this$0.mask]) != null) continue;
                return true;
            } while (k != k2);
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
                    Reference2IntOpenHashMap.access$300(this.this$0);
                    return false;
                }
                return true;
            }
            K[] KArray = this.this$0.key;
            int n2 = HashCommon.mix(System.identityHashCode(k)) & this.this$0.mask;
            Object k2 = KArray[n2];
            if (k2 == null) {
                return true;
            }
            if (k2 == k) {
                if (this.this$0.value[n2] == n) {
                    Reference2IntOpenHashMap.access$400(this.this$0, n2);
                    return false;
                }
                return true;
            }
            do {
                if ((k2 = KArray[n2 = n2 + 1 & this.this$0.mask]) != null) continue;
                return true;
            } while (k2 != k || this.this$0.value[n2] != n);
            Reference2IntOpenHashMap.access$400(this.this$0, n2);
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
        public void forEach(Consumer<? super Reference2IntMap.Entry<K>> consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(new AbstractReference2IntMap.BasicEntry(this.this$0.key[this.this$0.n], this.this$0.value[this.this$0.n]));
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] == null) continue;
                consumer.accept(new AbstractReference2IntMap.BasicEntry(this.this$0.key[n], this.this$0.value[n]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Reference2IntMap.Entry<K>> consumer) {
            AbstractReference2IntMap.BasicEntry basicEntry = new AbstractReference2IntMap.BasicEntry();
            if (this.this$0.containsNullKey) {
                basicEntry.key = this.this$0.key[this.this$0.n];
                basicEntry.value = this.this$0.value[this.this$0.n];
                consumer.accept(basicEntry);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] == null) continue;
                basicEntry.key = this.this$0.key[n];
                basicEntry.value = this.this$0.value[n];
                consumer.accept(basicEntry);
            }
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        MapEntrySet(Reference2IntOpenHashMap reference2IntOpenHashMap, 1 var2_2) {
            this(reference2IntOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectIterator<Reference2IntMap.Entry<K>> {
        private final MapEntry entry;
        final Reference2IntOpenHashMap this$0;

        private FastEntryIterator(Reference2IntOpenHashMap reference2IntOpenHashMap) {
            this.this$0 = reference2IntOpenHashMap;
            super(reference2IntOpenHashMap, null);
            this.entry = new MapEntry(this.this$0);
        }

        @Override
        public MapEntry next() {
            this.entry.index = this.nextEntry();
            return this.entry;
        }

        @Override
        public Object next() {
            return this.next();
        }

        FastEntryIterator(Reference2IntOpenHashMap reference2IntOpenHashMap, 1 var2_2) {
            this(reference2IntOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntryIterator
    extends MapIterator
    implements ObjectIterator<Reference2IntMap.Entry<K>> {
        private MapEntry entry;
        final Reference2IntOpenHashMap this$0;

        private EntryIterator(Reference2IntOpenHashMap reference2IntOpenHashMap) {
            this.this$0 = reference2IntOpenHashMap;
            super(reference2IntOpenHashMap, null);
        }

        @Override
        public MapEntry next() {
            this.entry = new MapEntry(this.this$0, this.nextEntry());
            return this.entry;
        }

        @Override
        public void remove() {
            super.remove();
            this.entry.index = -1;
        }

        @Override
        public Object next() {
            return this.next();
        }

        EntryIterator(Reference2IntOpenHashMap reference2IntOpenHashMap, 1 var2_2) {
            this(reference2IntOpenHashMap);
        }
    }

    private class MapIterator {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        ReferenceArrayList<K> wrapped;
        final Reference2IntOpenHashMap this$0;

        private MapIterator(Reference2IntOpenHashMap reference2IntOpenHashMap) {
            this.this$0 = reference2IntOpenHashMap;
            this.pos = this.this$0.n;
            this.last = -1;
            this.c = this.this$0.size;
            this.mustReturnNullKey = this.this$0.containsNullKey;
        }

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
                int n = HashCommon.mix(System.identityHashCode(k)) & this.this$0.mask;
                while (k != KArray[n]) {
                    n = n + 1 & this.this$0.mask;
                }
                return n;
            } while (KArray[this.pos] == null);
            this.last = this.pos;
            return this.last;
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
                    int n3 = HashCommon.mix(System.identityHashCode(k)) & this.this$0.mask;
                    if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                    n = n + 1 & this.this$0.mask;
                }
                if (n < n2) {
                    if (this.wrapped == null) {
                        this.wrapped = new ReferenceArrayList(2);
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

        MapIterator(Reference2IntOpenHashMap reference2IntOpenHashMap, 1 var2_2) {
            this(reference2IntOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Reference2IntMap.Entry<K>,
    Map.Entry<K, Integer> {
        int index;
        final Reference2IntOpenHashMap this$0;

        MapEntry(Reference2IntOpenHashMap reference2IntOpenHashMap, int n) {
            this.this$0 = reference2IntOpenHashMap;
            this.index = n;
        }

        MapEntry(Reference2IntOpenHashMap reference2IntOpenHashMap) {
            this.this$0 = reference2IntOpenHashMap;
        }

        @Override
        public K getKey() {
            return this.this$0.key[this.index];
        }

        @Override
        public int getIntValue() {
            return this.this$0.value[this.index];
        }

        @Override
        public int setValue(int n) {
            int n2 = this.this$0.value[this.index];
            this.this$0.value[this.index] = n;
            return n2;
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
            return this.this$0.key[this.index] == entry.getKey() && this.this$0.value[this.index] == (Integer)entry.getValue();
        }

        @Override
        public int hashCode() {
            return System.identityHashCode(this.this$0.key[this.index]) ^ this.this$0.value[this.index];
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

