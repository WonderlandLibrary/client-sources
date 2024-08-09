/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.objects.AbstractObject2ReferenceMap;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.Object2ReferenceMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Consumer;

public class Object2ReferenceOpenCustomHashMap<K, V>
extends AbstractObject2ReferenceMap<K, V>
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient K[] key;
    protected transient V[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected Hash.Strategy<K> strategy;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Object2ReferenceMap.FastEntrySet<K, V> entries;
    protected transient ObjectSet<K> keys;
    protected transient ReferenceCollection<V> values;

    public Object2ReferenceOpenCustomHashMap(int n, float f, Hash.Strategy<K> strategy) {
        this.strategy = strategy;
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
        this.value = new Object[this.n + 1];
    }

    public Object2ReferenceOpenCustomHashMap(int n, Hash.Strategy<K> strategy) {
        this(n, 0.75f, strategy);
    }

    public Object2ReferenceOpenCustomHashMap(Hash.Strategy<K> strategy) {
        this(16, 0.75f, strategy);
    }

    public Object2ReferenceOpenCustomHashMap(Map<? extends K, ? extends V> map, float f, Hash.Strategy<K> strategy) {
        this(map.size(), f, strategy);
        this.putAll(map);
    }

    public Object2ReferenceOpenCustomHashMap(Map<? extends K, ? extends V> map, Hash.Strategy<K> strategy) {
        this(map, 0.75f, strategy);
    }

    public Object2ReferenceOpenCustomHashMap(Object2ReferenceMap<K, V> object2ReferenceMap, float f, Hash.Strategy<K> strategy) {
        this(object2ReferenceMap.size(), f, strategy);
        this.putAll(object2ReferenceMap);
    }

    public Object2ReferenceOpenCustomHashMap(Object2ReferenceMap<K, V> object2ReferenceMap, Hash.Strategy<K> strategy) {
        this(object2ReferenceMap, 0.75f, strategy);
    }

    public Object2ReferenceOpenCustomHashMap(K[] KArray, V[] VArray, float f, Hash.Strategy<K> strategy) {
        this(KArray.length, f, strategy);
        if (KArray.length != VArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + KArray.length + " and " + VArray.length + ")");
        }
        for (int i = 0; i < KArray.length; ++i) {
            this.put(KArray[i], VArray[i]);
        }
    }

    public Object2ReferenceOpenCustomHashMap(K[] KArray, V[] VArray, Hash.Strategy<K> strategy) {
        this(KArray, VArray, 0.75f, strategy);
    }

    public Hash.Strategy<K> strategy() {
        return this.strategy;
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
        if (this.strategy.equals(k, null)) {
            return this.containsNullKey ? this.n : -(this.n + 1);
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
        K k2 = KArray[n];
        if (k2 == null) {
            return -(n + 1);
        }
        if (this.strategy.equals(k, k2)) {
            return n;
        }
        do {
            if ((k2 = KArray[n = n + 1 & this.mask]) != null) continue;
            return -(n + 1);
        } while (!this.strategy.equals(k, k2));
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
                int n3 = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.mask;
            }
            KArray[n2] = k;
            this.value[n2] = this.value[n];
        }
    }

    @Override
    public V remove(Object object) {
        if (this.strategy.equals(object, null)) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return (V)this.defRetValue;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(object)) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return (V)this.defRetValue;
        }
        if (this.strategy.equals(object, k)) {
            return this.removeEntry(n);
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return (V)this.defRetValue;
        } while (!this.strategy.equals(object, k));
        return this.removeEntry(n);
    }

    @Override
    public V get(Object object) {
        if (this.strategy.equals(object, null)) {
            return (V)(this.containsNullKey ? this.value[this.n] : this.defRetValue);
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(object)) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return (V)this.defRetValue;
        }
        if (this.strategy.equals(object, k)) {
            return this.value[n];
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return (V)this.defRetValue;
        } while (!this.strategy.equals(object, k));
        return this.value[n];
    }

    @Override
    public boolean containsKey(Object object) {
        if (this.strategy.equals(object, null)) {
            return this.containsNullKey;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(object)) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return true;
        }
        if (this.strategy.equals(object, k)) {
            return false;
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return true;
        } while (!this.strategy.equals(object, k));
        return false;
    }

    @Override
    public boolean containsValue(Object object) {
        V[] VArray = this.value;
        K[] KArray = this.key;
        if (this.containsNullKey && VArray[this.n] == object) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (KArray[n] == null || VArray[n] != object) continue;
            return false;
        }
        return true;
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

    public Object2ReferenceMap.FastEntrySet<K, V> object2ReferenceEntrySet() {
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
    public ReferenceCollection<V> values() {
        if (this.values == null) {
            this.values = new AbstractReferenceCollection<V>(this){
                final Object2ReferenceOpenCustomHashMap this$0;
                {
                    this.this$0 = object2ReferenceOpenCustomHashMap;
                }

                @Override
                public ObjectIterator<V> iterator() {
                    return new ValueIterator(this.this$0);
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
        V[] VArray = this.value;
        int n2 = n - 1;
        Object[] objectArray = new Object[n + 1];
        Object[] objectArray2 = new Object[n + 1];
        int n3 = this.n;
        int n4 = this.realSize();
        while (n4-- != 0) {
            while (KArray[--n3] == null) {
            }
            int n5 = HashCommon.mix(this.strategy.hashCode(KArray[n3])) & n2;
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

    public Object2ReferenceOpenCustomHashMap<K, V> clone() {
        Object2ReferenceOpenCustomHashMap object2ReferenceOpenCustomHashMap;
        try {
            object2ReferenceOpenCustomHashMap = (Object2ReferenceOpenCustomHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        object2ReferenceOpenCustomHashMap.keys = null;
        object2ReferenceOpenCustomHashMap.values = null;
        object2ReferenceOpenCustomHashMap.entries = null;
        object2ReferenceOpenCustomHashMap.containsNullKey = this.containsNullKey;
        object2ReferenceOpenCustomHashMap.key = (Object[])this.key.clone();
        object2ReferenceOpenCustomHashMap.value = (Object[])this.value.clone();
        object2ReferenceOpenCustomHashMap.strategy = this.strategy;
        return object2ReferenceOpenCustomHashMap;
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
                n4 = this.strategy.hashCode(this.key[n3]);
            }
            if (this != this.value[n3]) {
                n4 ^= this.value[n3] == null ? 0 : System.identityHashCode(this.value[n3]);
            }
            n += n4;
            ++n3;
        }
        if (this.containsNullKey) {
            n += this.value[this.n] == null ? 0 : System.identityHashCode(this.value[this.n]);
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        K[] KArray = this.key;
        V[] VArray = this.value;
        MapIterator mapIterator = new MapIterator(this, null);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
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
            if (this.strategy.equals(object, null)) {
                n2 = this.n;
                this.containsNullKey = true;
            } else {
                n2 = HashCommon.mix(this.strategy.hashCode(object)) & this.mask;
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
    public ObjectSet object2ReferenceEntrySet() {
        return this.object2ReferenceEntrySet();
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

    static Object access$300(Object2ReferenceOpenCustomHashMap object2ReferenceOpenCustomHashMap) {
        return object2ReferenceOpenCustomHashMap.removeNullEntry();
    }

    static Object access$400(Object2ReferenceOpenCustomHashMap object2ReferenceOpenCustomHashMap, int n) {
        return object2ReferenceOpenCustomHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements ObjectIterator<V> {
        final Object2ReferenceOpenCustomHashMap this$0;

        public ValueIterator(Object2ReferenceOpenCustomHashMap object2ReferenceOpenCustomHashMap) {
            this.this$0 = object2ReferenceOpenCustomHashMap;
            super(object2ReferenceOpenCustomHashMap, null);
        }

        @Override
        public V next() {
            return this.this$0.value[this.nextEntry()];
        }
    }

    private final class KeySet
    extends AbstractObjectSet<K> {
        final Object2ReferenceOpenCustomHashMap this$0;

        private KeySet(Object2ReferenceOpenCustomHashMap object2ReferenceOpenCustomHashMap) {
            this.this$0 = object2ReferenceOpenCustomHashMap;
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
            this.this$0.remove(object);
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

        KeySet(Object2ReferenceOpenCustomHashMap object2ReferenceOpenCustomHashMap, 1 var2_2) {
            this(object2ReferenceOpenCustomHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements ObjectIterator<K> {
        final Object2ReferenceOpenCustomHashMap this$0;

        public KeyIterator(Object2ReferenceOpenCustomHashMap object2ReferenceOpenCustomHashMap) {
            this.this$0 = object2ReferenceOpenCustomHashMap;
            super(object2ReferenceOpenCustomHashMap, null);
        }

        @Override
        public K next() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Object2ReferenceMap.Entry<K, V>>
    implements Object2ReferenceMap.FastEntrySet<K, V> {
        final Object2ReferenceOpenCustomHashMap this$0;

        private MapEntrySet(Object2ReferenceOpenCustomHashMap object2ReferenceOpenCustomHashMap) {
            this.this$0 = object2ReferenceOpenCustomHashMap;
        }

        @Override
        public ObjectIterator<Object2ReferenceMap.Entry<K, V>> iterator() {
            return new EntryIterator(this.this$0, null);
        }

        @Override
        public ObjectIterator<Object2ReferenceMap.Entry<K, V>> fastIterator() {
            return new FastEntryIterator(this.this$0, null);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            if (this.this$0.strategy.equals(k, null)) {
                return this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == v;
            }
            K[] KArray = this.this$0.key;
            int n = HashCommon.mix(this.this$0.strategy.hashCode(k)) & this.this$0.mask;
            Object k2 = KArray[n];
            if (k2 == null) {
                return true;
            }
            if (this.this$0.strategy.equals(k, k2)) {
                return this.this$0.value[n] == v;
            }
            do {
                if ((k2 = KArray[n = n + 1 & this.this$0.mask]) != null) continue;
                return true;
            } while (!this.this$0.strategy.equals(k, k2));
            return this.this$0.value[n] == v;
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            if (this.this$0.strategy.equals(k, null)) {
                if (this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == v) {
                    Object2ReferenceOpenCustomHashMap.access$300(this.this$0);
                    return false;
                }
                return true;
            }
            K[] KArray = this.this$0.key;
            int n = HashCommon.mix(this.this$0.strategy.hashCode(k)) & this.this$0.mask;
            Object k2 = KArray[n];
            if (k2 == null) {
                return true;
            }
            if (this.this$0.strategy.equals(k2, k)) {
                if (this.this$0.value[n] == v) {
                    Object2ReferenceOpenCustomHashMap.access$400(this.this$0, n);
                    return false;
                }
                return true;
            }
            do {
                if ((k2 = KArray[n = n + 1 & this.this$0.mask]) != null) continue;
                return true;
            } while (!this.this$0.strategy.equals(k2, k) || this.this$0.value[n] != v);
            Object2ReferenceOpenCustomHashMap.access$400(this.this$0, n);
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
        public void forEach(Consumer<? super Object2ReferenceMap.Entry<K, V>> consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(new AbstractObject2ReferenceMap.BasicEntry(this.this$0.key[this.this$0.n], this.this$0.value[this.this$0.n]));
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] == null) continue;
                consumer.accept(new AbstractObject2ReferenceMap.BasicEntry(this.this$0.key[n], this.this$0.value[n]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Object2ReferenceMap.Entry<K, V>> consumer) {
            AbstractObject2ReferenceMap.BasicEntry basicEntry = new AbstractObject2ReferenceMap.BasicEntry();
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

        MapEntrySet(Object2ReferenceOpenCustomHashMap object2ReferenceOpenCustomHashMap, 1 var2_2) {
            this(object2ReferenceOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectIterator<Object2ReferenceMap.Entry<K, V>> {
        private final MapEntry entry;
        final Object2ReferenceOpenCustomHashMap this$0;

        private FastEntryIterator(Object2ReferenceOpenCustomHashMap object2ReferenceOpenCustomHashMap) {
            this.this$0 = object2ReferenceOpenCustomHashMap;
            super(object2ReferenceOpenCustomHashMap, null);
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

        FastEntryIterator(Object2ReferenceOpenCustomHashMap object2ReferenceOpenCustomHashMap, 1 var2_2) {
            this(object2ReferenceOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntryIterator
    extends MapIterator
    implements ObjectIterator<Object2ReferenceMap.Entry<K, V>> {
        private MapEntry entry;
        final Object2ReferenceOpenCustomHashMap this$0;

        private EntryIterator(Object2ReferenceOpenCustomHashMap object2ReferenceOpenCustomHashMap) {
            this.this$0 = object2ReferenceOpenCustomHashMap;
            super(object2ReferenceOpenCustomHashMap, null);
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

        EntryIterator(Object2ReferenceOpenCustomHashMap object2ReferenceOpenCustomHashMap, 1 var2_2) {
            this(object2ReferenceOpenCustomHashMap);
        }
    }

    private class MapIterator {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        ObjectArrayList<K> wrapped;
        final Object2ReferenceOpenCustomHashMap this$0;

        private MapIterator(Object2ReferenceOpenCustomHashMap object2ReferenceOpenCustomHashMap) {
            this.this$0 = object2ReferenceOpenCustomHashMap;
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
                int n = HashCommon.mix(this.this$0.strategy.hashCode(k)) & this.this$0.mask;
                while (!this.this$0.strategy.equals(k, KArray[n])) {
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
                        this.this$0.value[n2] = null;
                        return;
                    }
                    int n3 = HashCommon.mix(this.this$0.strategy.hashCode(k)) & this.this$0.mask;
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

        MapIterator(Object2ReferenceOpenCustomHashMap object2ReferenceOpenCustomHashMap, 1 var2_2) {
            this(object2ReferenceOpenCustomHashMap);
        }
    }

    final class MapEntry
    implements Object2ReferenceMap.Entry<K, V>,
    Map.Entry<K, V> {
        int index;
        final Object2ReferenceOpenCustomHashMap this$0;

        MapEntry(Object2ReferenceOpenCustomHashMap object2ReferenceOpenCustomHashMap, int n) {
            this.this$0 = object2ReferenceOpenCustomHashMap;
            this.index = n;
        }

        MapEntry(Object2ReferenceOpenCustomHashMap object2ReferenceOpenCustomHashMap) {
            this.this$0 = object2ReferenceOpenCustomHashMap;
        }

        @Override
        public K getKey() {
            return this.this$0.key[this.index];
        }

        @Override
        public V getValue() {
            return this.this$0.value[this.index];
        }

        @Override
        public V setValue(V v) {
            Object v2 = this.this$0.value[this.index];
            this.this$0.value[this.index] = v;
            return v2;
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            return this.this$0.strategy.equals(this.this$0.key[this.index], entry.getKey()) && this.this$0.value[this.index] == entry.getValue();
        }

        @Override
        public int hashCode() {
            return this.this$0.strategy.hashCode(this.this$0.key[this.index]) ^ (this.this$0.value[this.index] == null ? 0 : System.identityHashCode(this.this$0.value[this.index]));
        }

        public String toString() {
            return this.this$0.key[this.index] + "=>" + this.this$0.value[this.index];
        }
    }
}

