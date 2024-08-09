/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import it.unimi.dsi.fastutil.shorts.AbstractShort2ReferenceMap;
import it.unimi.dsi.fastutil.shorts.AbstractShortSet;
import it.unimi.dsi.fastutil.shorts.Short2ReferenceMap;
import it.unimi.dsi.fastutil.shorts.ShortArrayList;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortSet;
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
import java.util.function.IntFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Short2ReferenceOpenHashMap<V>
extends AbstractShort2ReferenceMap<V>
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient short[] key;
    protected transient V[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Short2ReferenceMap.FastEntrySet<V> entries;
    protected transient ShortSet keys;
    protected transient ReferenceCollection<V> values;

    public Short2ReferenceOpenHashMap(int n, float f) {
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
        this.key = new short[this.n + 1];
        this.value = new Object[this.n + 1];
    }

    public Short2ReferenceOpenHashMap(int n) {
        this(n, 0.75f);
    }

    public Short2ReferenceOpenHashMap() {
        this(16, 0.75f);
    }

    public Short2ReferenceOpenHashMap(Map<? extends Short, ? extends V> map, float f) {
        this(map.size(), f);
        this.putAll(map);
    }

    public Short2ReferenceOpenHashMap(Map<? extends Short, ? extends V> map) {
        this(map, 0.75f);
    }

    public Short2ReferenceOpenHashMap(Short2ReferenceMap<V> short2ReferenceMap, float f) {
        this(short2ReferenceMap.size(), f);
        this.putAll(short2ReferenceMap);
    }

    public Short2ReferenceOpenHashMap(Short2ReferenceMap<V> short2ReferenceMap) {
        this(short2ReferenceMap, 0.75f);
    }

    public Short2ReferenceOpenHashMap(short[] sArray, V[] VArray, float f) {
        this(sArray.length, f);
        if (sArray.length != VArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + sArray.length + " and " + VArray.length + ")");
        }
        for (int i = 0; i < sArray.length; ++i) {
            this.put(sArray[i], VArray[i]);
        }
    }

    public Short2ReferenceOpenHashMap(short[] sArray, V[] VArray) {
        this(sArray, VArray, 0.75f);
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
        V v = this.value[this.n];
        this.value[this.n] = null;
        --this.size;
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return v;
    }

    @Override
    public void putAll(Map<? extends Short, ? extends V> map) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(map.size());
        } else {
            this.tryCapacity(this.size() + map.size());
        }
        super.putAll(map);
    }

    private int find(short s) {
        if (s == 0) {
            return this.containsNullKey ? this.n : -(this.n + 1);
        }
        short[] sArray = this.key;
        int n = HashCommon.mix(s) & this.mask;
        short s2 = sArray[n];
        if (s2 == 0) {
            return -(n + 1);
        }
        if (s == s2) {
            return n;
        }
        do {
            if ((s2 = sArray[n = n + 1 & this.mask]) != 0) continue;
            return -(n + 1);
        } while (s != s2);
        return n;
    }

    private void insert(int n, short s, V v) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = s;
        this.value[n] = v;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
    }

    @Override
    public V put(short s, V v) {
        int n = this.find(s);
        if (n < 0) {
            this.insert(-n - 1, s, v);
            return (V)this.defRetValue;
        }
        V v2 = this.value[n];
        this.value[n] = v;
        return v2;
    }

    protected final void shiftKeys(int n) {
        short[] sArray = this.key;
        while (true) {
            short s;
            int n2 = n;
            n = n2 + 1 & this.mask;
            while (true) {
                if ((s = sArray[n]) == 0) {
                    sArray[n2] = 0;
                    this.value[n2] = null;
                    return;
                }
                int n3 = HashCommon.mix(s) & this.mask;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.mask;
            }
            sArray[n2] = s;
            this.value[n2] = this.value[n];
        }
    }

    @Override
    public V remove(short s) {
        if (s == 0) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return (V)this.defRetValue;
        }
        short[] sArray = this.key;
        int n = HashCommon.mix(s) & this.mask;
        short s2 = sArray[n];
        if (s2 == 0) {
            return (V)this.defRetValue;
        }
        if (s == s2) {
            return this.removeEntry(n);
        }
        do {
            if ((s2 = sArray[n = n + 1 & this.mask]) != 0) continue;
            return (V)this.defRetValue;
        } while (s != s2);
        return this.removeEntry(n);
    }

    @Override
    public V get(short s) {
        if (s == 0) {
            return (V)(this.containsNullKey ? this.value[this.n] : this.defRetValue);
        }
        short[] sArray = this.key;
        int n = HashCommon.mix(s) & this.mask;
        short s2 = sArray[n];
        if (s2 == 0) {
            return (V)this.defRetValue;
        }
        if (s == s2) {
            return this.value[n];
        }
        do {
            if ((s2 = sArray[n = n + 1 & this.mask]) != 0) continue;
            return (V)this.defRetValue;
        } while (s != s2);
        return this.value[n];
    }

    @Override
    public boolean containsKey(short s) {
        if (s == 0) {
            return this.containsNullKey;
        }
        short[] sArray = this.key;
        int n = HashCommon.mix(s) & this.mask;
        short s2 = sArray[n];
        if (s2 == 0) {
            return true;
        }
        if (s == s2) {
            return false;
        }
        do {
            if ((s2 = sArray[n = n + 1 & this.mask]) != 0) continue;
            return true;
        } while (s != s2);
        return false;
    }

    @Override
    public boolean containsValue(Object object) {
        V[] VArray = this.value;
        short[] sArray = this.key;
        if (this.containsNullKey && VArray[this.n] == object) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (sArray[n] == 0 || VArray[n] != object) continue;
            return false;
        }
        return true;
    }

    @Override
    public V getOrDefault(short s, V v) {
        if (s == 0) {
            return this.containsNullKey ? this.value[this.n] : v;
        }
        short[] sArray = this.key;
        int n = HashCommon.mix(s) & this.mask;
        short s2 = sArray[n];
        if (s2 == 0) {
            return v;
        }
        if (s == s2) {
            return this.value[n];
        }
        do {
            if ((s2 = sArray[n = n + 1 & this.mask]) != 0) continue;
            return v;
        } while (s != s2);
        return this.value[n];
    }

    @Override
    public V putIfAbsent(short s, V v) {
        int n = this.find(s);
        if (n >= 0) {
            return this.value[n];
        }
        this.insert(-n - 1, s, v);
        return (V)this.defRetValue;
    }

    @Override
    public boolean remove(short s, Object object) {
        if (s == 0) {
            if (this.containsNullKey && object == this.value[this.n]) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        short[] sArray = this.key;
        int n = HashCommon.mix(s) & this.mask;
        short s2 = sArray[n];
        if (s2 == 0) {
            return true;
        }
        if (s == s2 && object == this.value[n]) {
            this.removeEntry(n);
            return false;
        }
        do {
            if ((s2 = sArray[n = n + 1 & this.mask]) != 0) continue;
            return true;
        } while (s != s2 || object != this.value[n]);
        this.removeEntry(n);
        return false;
    }

    @Override
    public boolean replace(short s, V v, V v2) {
        int n = this.find(s);
        if (n < 0 || v != this.value[n]) {
            return true;
        }
        this.value[n] = v2;
        return false;
    }

    @Override
    public V replace(short s, V v) {
        int n = this.find(s);
        if (n < 0) {
            return (V)this.defRetValue;
        }
        V v2 = this.value[n];
        this.value[n] = v;
        return v2;
    }

    @Override
    public V computeIfAbsent(short s, IntFunction<? extends V> intFunction) {
        Objects.requireNonNull(intFunction);
        int n = this.find(s);
        if (n >= 0) {
            return this.value[n];
        }
        V v = intFunction.apply(s);
        this.insert(-n - 1, s, v);
        return v;
    }

    @Override
    public V computeIfPresent(short s, BiFunction<? super Short, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(s);
        if (n < 0) {
            return (V)this.defRetValue;
        }
        V v = biFunction.apply(s, this.value[n]);
        if (v == null) {
            if (s == 0) {
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
    public V compute(short s, BiFunction<? super Short, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(s);
        V v = biFunction.apply(s, n >= 0 ? (Object)this.value[n] : null);
        if (v == null) {
            if (n >= 0) {
                if (s == 0) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return (V)this.defRetValue;
        }
        V v2 = v;
        if (n < 0) {
            this.insert(-n - 1, s, v2);
            return v2;
        }
        this.value[n] = v2;
        return this.value[n];
    }

    @Override
    public V merge(short s, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(s);
        if (n < 0 || this.value[n] == null) {
            if (v == null) {
                return (V)this.defRetValue;
            }
            this.insert(-n - 1, s, v);
            return v;
        }
        V v2 = biFunction.apply(this.value[n], v);
        if (v2 == null) {
            if (s == 0) {
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
        Arrays.fill(this.key, (short)0);
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

    public Short2ReferenceMap.FastEntrySet<V> short2ReferenceEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet(this, null);
        }
        return this.entries;
    }

    @Override
    public ShortSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(this, null);
        }
        return this.keys;
    }

    @Override
    public ReferenceCollection<V> values() {
        if (this.values == null) {
            this.values = new AbstractReferenceCollection<V>(this){
                final Short2ReferenceOpenHashMap this$0;
                {
                    this.this$0 = short2ReferenceOpenHashMap;
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
                        if (this.this$0.key[n] == 0) continue;
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
        short[] sArray = this.key;
        V[] VArray = this.value;
        int n2 = n - 1;
        short[] sArray2 = new short[n + 1];
        Object[] objectArray = new Object[n + 1];
        int n3 = this.n;
        int n4 = this.realSize();
        while (n4-- != 0) {
            while (sArray[--n3] == 0) {
            }
            int n5 = HashCommon.mix(sArray[n3]) & n2;
            if (sArray2[n5] != 0) {
                while (sArray2[n5 = n5 + 1 & n2] != 0) {
                }
            }
            sArray2[n5] = sArray[n3];
            objectArray[n5] = VArray[n3];
        }
        objectArray[n] = VArray[this.n];
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = sArray2;
        this.value = objectArray;
    }

    public Short2ReferenceOpenHashMap<V> clone() {
        Short2ReferenceOpenHashMap short2ReferenceOpenHashMap;
        try {
            short2ReferenceOpenHashMap = (Short2ReferenceOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        short2ReferenceOpenHashMap.keys = null;
        short2ReferenceOpenHashMap.values = null;
        short2ReferenceOpenHashMap.entries = null;
        short2ReferenceOpenHashMap.containsNullKey = this.containsNullKey;
        short2ReferenceOpenHashMap.key = (short[])this.key.clone();
        short2ReferenceOpenHashMap.value = (Object[])this.value.clone();
        return short2ReferenceOpenHashMap;
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.realSize();
        int n3 = 0;
        int n4 = 0;
        while (n2-- != 0) {
            while (this.key[n3] == 0) {
                ++n3;
            }
            n4 = this.key[n3];
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
        short[] sArray = this.key;
        V[] VArray = this.value;
        MapIterator mapIterator = new MapIterator(this, null);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeShort(sArray[n2]);
            objectOutputStream.writeObject(VArray[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new short[this.n + 1];
        short[] sArray = this.key;
        this.value = new Object[this.n + 1];
        Object[] objectArray = this.value;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            short s = objectInputStream.readShort();
            Object object = objectInputStream.readObject();
            if (s == 0) {
                n2 = this.n;
                this.containsNullKey = true;
            } else {
                n2 = HashCommon.mix(s) & this.mask;
                while (sArray[n2] != 0) {
                    n2 = n2 + 1 & this.mask;
                }
            }
            sArray[n2] = s;
            objectArray[n2] = object;
        }
    }

    private void checkTable() {
    }

    @Override
    public ObjectSet short2ReferenceEntrySet() {
        return this.short2ReferenceEntrySet();
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

    static Object access$300(Short2ReferenceOpenHashMap short2ReferenceOpenHashMap) {
        return short2ReferenceOpenHashMap.removeNullEntry();
    }

    static Object access$400(Short2ReferenceOpenHashMap short2ReferenceOpenHashMap, int n) {
        return short2ReferenceOpenHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements ObjectIterator<V> {
        final Short2ReferenceOpenHashMap this$0;

        public ValueIterator(Short2ReferenceOpenHashMap short2ReferenceOpenHashMap) {
            this.this$0 = short2ReferenceOpenHashMap;
            super(short2ReferenceOpenHashMap, null);
        }

        @Override
        public V next() {
            return this.this$0.value[this.nextEntry()];
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class KeySet
    extends AbstractShortSet {
        final Short2ReferenceOpenHashMap this$0;

        private KeySet(Short2ReferenceOpenHashMap short2ReferenceOpenHashMap) {
            this.this$0 = short2ReferenceOpenHashMap;
        }

        @Override
        public ShortIterator iterator() {
            return new KeyIterator(this.this$0);
        }

        @Override
        public void forEach(IntConsumer intConsumer) {
            if (this.this$0.containsNullKey) {
                intConsumer.accept(this.this$0.key[this.this$0.n]);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                short s = this.this$0.key[n];
                if (s == 0) continue;
                intConsumer.accept(s);
            }
        }

        @Override
        public int size() {
            return this.this$0.size;
        }

        @Override
        public boolean contains(short s) {
            return this.this$0.containsKey(s);
        }

        @Override
        public boolean remove(short s) {
            int n = this.this$0.size;
            this.this$0.remove(s);
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

        KeySet(Short2ReferenceOpenHashMap short2ReferenceOpenHashMap, 1 var2_2) {
            this(short2ReferenceOpenHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements ShortIterator {
        final Short2ReferenceOpenHashMap this$0;

        public KeyIterator(Short2ReferenceOpenHashMap short2ReferenceOpenHashMap) {
            this.this$0 = short2ReferenceOpenHashMap;
            super(short2ReferenceOpenHashMap, null);
        }

        @Override
        public short nextShort() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Short2ReferenceMap.Entry<V>>
    implements Short2ReferenceMap.FastEntrySet<V> {
        final Short2ReferenceOpenHashMap this$0;

        private MapEntrySet(Short2ReferenceOpenHashMap short2ReferenceOpenHashMap) {
            this.this$0 = short2ReferenceOpenHashMap;
        }

        @Override
        public ObjectIterator<Short2ReferenceMap.Entry<V>> iterator() {
            return new EntryIterator(this.this$0, null);
        }

        @Override
        public ObjectIterator<Short2ReferenceMap.Entry<V>> fastIterator() {
            return new FastEntryIterator(this.this$0, null);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Short)) {
                return true;
            }
            short s = (Short)entry.getKey();
            Object v = entry.getValue();
            if (s == 0) {
                return this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == v;
            }
            short[] sArray = this.this$0.key;
            int n = HashCommon.mix(s) & this.this$0.mask;
            short s2 = sArray[n];
            if (s2 == 0) {
                return true;
            }
            if (s == s2) {
                return this.this$0.value[n] == v;
            }
            do {
                if ((s2 = sArray[n = n + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (s != s2);
            return this.this$0.value[n] == v;
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Short)) {
                return true;
            }
            short s = (Short)entry.getKey();
            Object v = entry.getValue();
            if (s == 0) {
                if (this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == v) {
                    Short2ReferenceOpenHashMap.access$300(this.this$0);
                    return false;
                }
                return true;
            }
            short[] sArray = this.this$0.key;
            int n = HashCommon.mix(s) & this.this$0.mask;
            short s2 = sArray[n];
            if (s2 == 0) {
                return true;
            }
            if (s2 == s) {
                if (this.this$0.value[n] == v) {
                    Short2ReferenceOpenHashMap.access$400(this.this$0, n);
                    return false;
                }
                return true;
            }
            do {
                if ((s2 = sArray[n = n + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (s2 != s || this.this$0.value[n] != v);
            Short2ReferenceOpenHashMap.access$400(this.this$0, n);
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
        public void forEach(Consumer<? super Short2ReferenceMap.Entry<V>> consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(new AbstractShort2ReferenceMap.BasicEntry(this.this$0.key[this.this$0.n], this.this$0.value[this.this$0.n]));
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] == 0) continue;
                consumer.accept(new AbstractShort2ReferenceMap.BasicEntry(this.this$0.key[n], this.this$0.value[n]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Short2ReferenceMap.Entry<V>> consumer) {
            AbstractShort2ReferenceMap.BasicEntry basicEntry = new AbstractShort2ReferenceMap.BasicEntry();
            if (this.this$0.containsNullKey) {
                basicEntry.key = this.this$0.key[this.this$0.n];
                basicEntry.value = this.this$0.value[this.this$0.n];
                consumer.accept(basicEntry);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] == 0) continue;
                basicEntry.key = this.this$0.key[n];
                basicEntry.value = this.this$0.value[n];
                consumer.accept(basicEntry);
            }
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        MapEntrySet(Short2ReferenceOpenHashMap short2ReferenceOpenHashMap, 1 var2_2) {
            this(short2ReferenceOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectIterator<Short2ReferenceMap.Entry<V>> {
        private final MapEntry entry;
        final Short2ReferenceOpenHashMap this$0;

        private FastEntryIterator(Short2ReferenceOpenHashMap short2ReferenceOpenHashMap) {
            this.this$0 = short2ReferenceOpenHashMap;
            super(short2ReferenceOpenHashMap, null);
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

        FastEntryIterator(Short2ReferenceOpenHashMap short2ReferenceOpenHashMap, 1 var2_2) {
            this(short2ReferenceOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntryIterator
    extends MapIterator
    implements ObjectIterator<Short2ReferenceMap.Entry<V>> {
        private MapEntry entry;
        final Short2ReferenceOpenHashMap this$0;

        private EntryIterator(Short2ReferenceOpenHashMap short2ReferenceOpenHashMap) {
            this.this$0 = short2ReferenceOpenHashMap;
            super(short2ReferenceOpenHashMap, null);
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

        EntryIterator(Short2ReferenceOpenHashMap short2ReferenceOpenHashMap, 1 var2_2) {
            this(short2ReferenceOpenHashMap);
        }
    }

    private class MapIterator {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        ShortArrayList wrapped;
        final Short2ReferenceOpenHashMap this$0;

        private MapIterator(Short2ReferenceOpenHashMap short2ReferenceOpenHashMap) {
            this.this$0 = short2ReferenceOpenHashMap;
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
            short[] sArray = this.this$0.key;
            do {
                if (--this.pos >= 0) continue;
                this.last = Integer.MIN_VALUE;
                short s = this.wrapped.getShort(-this.pos - 1);
                int n = HashCommon.mix(s) & this.this$0.mask;
                while (s != sArray[n]) {
                    n = n + 1 & this.this$0.mask;
                }
                return n;
            } while (sArray[this.pos] == 0);
            this.last = this.pos;
            return this.last;
        }

        private void shiftKeys(int n) {
            short[] sArray = this.this$0.key;
            while (true) {
                short s;
                int n2 = n;
                n = n2 + 1 & this.this$0.mask;
                while (true) {
                    if ((s = sArray[n]) == 0) {
                        sArray[n2] = 0;
                        this.this$0.value[n2] = null;
                        return;
                    }
                    int n3 = HashCommon.mix(s) & this.this$0.mask;
                    if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                    n = n + 1 & this.this$0.mask;
                }
                if (n < n2) {
                    if (this.wrapped == null) {
                        this.wrapped = new ShortArrayList(2);
                    }
                    this.wrapped.add(sArray[n]);
                }
                sArray[n2] = s;
                this.this$0.value[n2] = this.this$0.value[n];
            }
        }

        public void remove() {
            if (this.last == -1) {
                throw new IllegalStateException();
            }
            if (this.last == this.this$0.n) {
                this.this$0.containsNullKey = false;
                this.this$0.value[this.this$0.n] = null;
            } else if (this.pos >= 0) {
                this.shiftKeys(this.last);
            } else {
                this.this$0.remove(this.wrapped.getShort(-this.pos - 1));
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

        MapIterator(Short2ReferenceOpenHashMap short2ReferenceOpenHashMap, 1 var2_2) {
            this(short2ReferenceOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Short2ReferenceMap.Entry<V>,
    Map.Entry<Short, V> {
        int index;
        final Short2ReferenceOpenHashMap this$0;

        MapEntry(Short2ReferenceOpenHashMap short2ReferenceOpenHashMap, int n) {
            this.this$0 = short2ReferenceOpenHashMap;
            this.index = n;
        }

        MapEntry(Short2ReferenceOpenHashMap short2ReferenceOpenHashMap) {
            this.this$0 = short2ReferenceOpenHashMap;
        }

        @Override
        public short getShortKey() {
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
        @Deprecated
        public Short getKey() {
            return this.this$0.key[this.index];
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            return this.this$0.key[this.index] == (Short)entry.getKey() && this.this$0.value[this.index] == entry.getValue();
        }

        @Override
        public int hashCode() {
            return this.this$0.key[this.index] ^ (this.this$0.value[this.index] == null ? 0 : System.identityHashCode(this.this$0.value[this.index]));
        }

        public String toString() {
            return this.this$0.key[this.index] + "=>" + this.this$0.value[this.index];
        }

        @Override
        @Deprecated
        public Object getKey() {
            return this.getKey();
        }
    }
}

