/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.longs.AbstractLong2ReferenceMap;
import it.unimi.dsi.fastutil.longs.AbstractLongSet;
import it.unimi.dsi.fastutil.longs.Long2ReferenceMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongHash;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
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
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Long2ReferenceOpenCustomHashMap<V>
extends AbstractLong2ReferenceMap<V>
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient long[] key;
    protected transient V[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected LongHash.Strategy strategy;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Long2ReferenceMap.FastEntrySet<V> entries;
    protected transient LongSet keys;
    protected transient ReferenceCollection<V> values;

    public Long2ReferenceOpenCustomHashMap(int n, float f, LongHash.Strategy strategy) {
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
        this.key = new long[this.n + 1];
        this.value = new Object[this.n + 1];
    }

    public Long2ReferenceOpenCustomHashMap(int n, LongHash.Strategy strategy) {
        this(n, 0.75f, strategy);
    }

    public Long2ReferenceOpenCustomHashMap(LongHash.Strategy strategy) {
        this(16, 0.75f, strategy);
    }

    public Long2ReferenceOpenCustomHashMap(Map<? extends Long, ? extends V> map, float f, LongHash.Strategy strategy) {
        this(map.size(), f, strategy);
        this.putAll(map);
    }

    public Long2ReferenceOpenCustomHashMap(Map<? extends Long, ? extends V> map, LongHash.Strategy strategy) {
        this(map, 0.75f, strategy);
    }

    public Long2ReferenceOpenCustomHashMap(Long2ReferenceMap<V> long2ReferenceMap, float f, LongHash.Strategy strategy) {
        this(long2ReferenceMap.size(), f, strategy);
        this.putAll(long2ReferenceMap);
    }

    public Long2ReferenceOpenCustomHashMap(Long2ReferenceMap<V> long2ReferenceMap, LongHash.Strategy strategy) {
        this(long2ReferenceMap, 0.75f, strategy);
    }

    public Long2ReferenceOpenCustomHashMap(long[] lArray, V[] VArray, float f, LongHash.Strategy strategy) {
        this(lArray.length, f, strategy);
        if (lArray.length != VArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + lArray.length + " and " + VArray.length + ")");
        }
        for (int i = 0; i < lArray.length; ++i) {
            this.put(lArray[i], VArray[i]);
        }
    }

    public Long2ReferenceOpenCustomHashMap(long[] lArray, V[] VArray, LongHash.Strategy strategy) {
        this(lArray, VArray, 0.75f, strategy);
    }

    public LongHash.Strategy strategy() {
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
        V v = this.value[this.n];
        this.value[this.n] = null;
        --this.size;
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return v;
    }

    @Override
    public void putAll(Map<? extends Long, ? extends V> map) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(map.size());
        } else {
            this.tryCapacity(this.size() + map.size());
        }
        super.putAll(map);
    }

    private int find(long l) {
        if (this.strategy.equals(l, 0L)) {
            return this.containsNullKey ? this.n : -(this.n + 1);
        }
        long[] lArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(l)) & this.mask;
        long l2 = lArray[n];
        if (l2 == 0L) {
            return -(n + 1);
        }
        if (this.strategy.equals(l, l2)) {
            return n;
        }
        do {
            if ((l2 = lArray[n = n + 1 & this.mask]) != 0L) continue;
            return -(n + 1);
        } while (!this.strategy.equals(l, l2));
        return n;
    }

    private void insert(int n, long l, V v) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = l;
        this.value[n] = v;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
    }

    @Override
    public V put(long l, V v) {
        int n = this.find(l);
        if (n < 0) {
            this.insert(-n - 1, l, v);
            return (V)this.defRetValue;
        }
        V v2 = this.value[n];
        this.value[n] = v;
        return v2;
    }

    protected final void shiftKeys(int n) {
        long[] lArray = this.key;
        while (true) {
            long l;
            int n2 = n;
            n = n2 + 1 & this.mask;
            while (true) {
                if ((l = lArray[n]) == 0L) {
                    lArray[n2] = 0L;
                    this.value[n2] = null;
                    return;
                }
                int n3 = HashCommon.mix(this.strategy.hashCode(l)) & this.mask;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.mask;
            }
            lArray[n2] = l;
            this.value[n2] = this.value[n];
        }
    }

    @Override
    public V remove(long l) {
        if (this.strategy.equals(l, 0L)) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return (V)this.defRetValue;
        }
        long[] lArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(l)) & this.mask;
        long l2 = lArray[n];
        if (l2 == 0L) {
            return (V)this.defRetValue;
        }
        if (this.strategy.equals(l, l2)) {
            return this.removeEntry(n);
        }
        do {
            if ((l2 = lArray[n = n + 1 & this.mask]) != 0L) continue;
            return (V)this.defRetValue;
        } while (!this.strategy.equals(l, l2));
        return this.removeEntry(n);
    }

    @Override
    public V get(long l) {
        if (this.strategy.equals(l, 0L)) {
            return (V)(this.containsNullKey ? this.value[this.n] : this.defRetValue);
        }
        long[] lArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(l)) & this.mask;
        long l2 = lArray[n];
        if (l2 == 0L) {
            return (V)this.defRetValue;
        }
        if (this.strategy.equals(l, l2)) {
            return this.value[n];
        }
        do {
            if ((l2 = lArray[n = n + 1 & this.mask]) != 0L) continue;
            return (V)this.defRetValue;
        } while (!this.strategy.equals(l, l2));
        return this.value[n];
    }

    @Override
    public boolean containsKey(long l) {
        if (this.strategy.equals(l, 0L)) {
            return this.containsNullKey;
        }
        long[] lArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(l)) & this.mask;
        long l2 = lArray[n];
        if (l2 == 0L) {
            return true;
        }
        if (this.strategy.equals(l, l2)) {
            return false;
        }
        do {
            if ((l2 = lArray[n = n + 1 & this.mask]) != 0L) continue;
            return true;
        } while (!this.strategy.equals(l, l2));
        return false;
    }

    @Override
    public boolean containsValue(Object object) {
        V[] VArray = this.value;
        long[] lArray = this.key;
        if (this.containsNullKey && VArray[this.n] == object) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (lArray[n] == 0L || VArray[n] != object) continue;
            return false;
        }
        return true;
    }

    @Override
    public V getOrDefault(long l, V v) {
        if (this.strategy.equals(l, 0L)) {
            return this.containsNullKey ? this.value[this.n] : v;
        }
        long[] lArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(l)) & this.mask;
        long l2 = lArray[n];
        if (l2 == 0L) {
            return v;
        }
        if (this.strategy.equals(l, l2)) {
            return this.value[n];
        }
        do {
            if ((l2 = lArray[n = n + 1 & this.mask]) != 0L) continue;
            return v;
        } while (!this.strategy.equals(l, l2));
        return this.value[n];
    }

    @Override
    public V putIfAbsent(long l, V v) {
        int n = this.find(l);
        if (n >= 0) {
            return this.value[n];
        }
        this.insert(-n - 1, l, v);
        return (V)this.defRetValue;
    }

    @Override
    public boolean remove(long l, Object object) {
        if (this.strategy.equals(l, 0L)) {
            if (this.containsNullKey && object == this.value[this.n]) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        long[] lArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(l)) & this.mask;
        long l2 = lArray[n];
        if (l2 == 0L) {
            return true;
        }
        if (this.strategy.equals(l, l2) && object == this.value[n]) {
            this.removeEntry(n);
            return false;
        }
        do {
            if ((l2 = lArray[n = n + 1 & this.mask]) != 0L) continue;
            return true;
        } while (!this.strategy.equals(l, l2) || object != this.value[n]);
        this.removeEntry(n);
        return false;
    }

    @Override
    public boolean replace(long l, V v, V v2) {
        int n = this.find(l);
        if (n < 0 || v != this.value[n]) {
            return true;
        }
        this.value[n] = v2;
        return false;
    }

    @Override
    public V replace(long l, V v) {
        int n = this.find(l);
        if (n < 0) {
            return (V)this.defRetValue;
        }
        V v2 = this.value[n];
        this.value[n] = v;
        return v2;
    }

    @Override
    public V computeIfAbsent(long l, LongFunction<? extends V> longFunction) {
        Objects.requireNonNull(longFunction);
        int n = this.find(l);
        if (n >= 0) {
            return this.value[n];
        }
        V v = longFunction.apply(l);
        this.insert(-n - 1, l, v);
        return v;
    }

    @Override
    public V computeIfPresent(long l, BiFunction<? super Long, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(l);
        if (n < 0) {
            return (V)this.defRetValue;
        }
        V v = biFunction.apply(l, this.value[n]);
        if (v == null) {
            if (this.strategy.equals(l, 0L)) {
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
    public V compute(long l, BiFunction<? super Long, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(l);
        V v = biFunction.apply(l, n >= 0 ? (Object)this.value[n] : null);
        if (v == null) {
            if (n >= 0) {
                if (this.strategy.equals(l, 0L)) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return (V)this.defRetValue;
        }
        V v2 = v;
        if (n < 0) {
            this.insert(-n - 1, l, v2);
            return v2;
        }
        this.value[n] = v2;
        return this.value[n];
    }

    @Override
    public V merge(long l, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(l);
        if (n < 0 || this.value[n] == null) {
            if (v == null) {
                return (V)this.defRetValue;
            }
            this.insert(-n - 1, l, v);
            return v;
        }
        V v2 = biFunction.apply(this.value[n], v);
        if (v2 == null) {
            if (this.strategy.equals(l, 0L)) {
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
        Arrays.fill(this.key, 0L);
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

    public Long2ReferenceMap.FastEntrySet<V> long2ReferenceEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet(this, null);
        }
        return this.entries;
    }

    @Override
    public LongSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(this, null);
        }
        return this.keys;
    }

    @Override
    public ReferenceCollection<V> values() {
        if (this.values == null) {
            this.values = new AbstractReferenceCollection<V>(this){
                final Long2ReferenceOpenCustomHashMap this$0;
                {
                    this.this$0 = long2ReferenceOpenCustomHashMap;
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
                        if (this.this$0.key[n] == 0L) continue;
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
        long[] lArray = this.key;
        V[] VArray = this.value;
        int n2 = n - 1;
        long[] lArray2 = new long[n + 1];
        Object[] objectArray = new Object[n + 1];
        int n3 = this.n;
        int n4 = this.realSize();
        while (n4-- != 0) {
            while (lArray[--n3] == 0L) {
            }
            int n5 = HashCommon.mix(this.strategy.hashCode(lArray[n3])) & n2;
            if (lArray2[n5] != 0L) {
                while (lArray2[n5 = n5 + 1 & n2] != 0L) {
                }
            }
            lArray2[n5] = lArray[n3];
            objectArray[n5] = VArray[n3];
        }
        objectArray[n] = VArray[this.n];
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = lArray2;
        this.value = objectArray;
    }

    public Long2ReferenceOpenCustomHashMap<V> clone() {
        Long2ReferenceOpenCustomHashMap long2ReferenceOpenCustomHashMap;
        try {
            long2ReferenceOpenCustomHashMap = (Long2ReferenceOpenCustomHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        long2ReferenceOpenCustomHashMap.keys = null;
        long2ReferenceOpenCustomHashMap.values = null;
        long2ReferenceOpenCustomHashMap.entries = null;
        long2ReferenceOpenCustomHashMap.containsNullKey = this.containsNullKey;
        long2ReferenceOpenCustomHashMap.key = (long[])this.key.clone();
        long2ReferenceOpenCustomHashMap.value = (Object[])this.value.clone();
        long2ReferenceOpenCustomHashMap.strategy = this.strategy;
        return long2ReferenceOpenCustomHashMap;
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.realSize();
        int n3 = 0;
        int n4 = 0;
        while (n2-- != 0) {
            while (this.key[n3] == 0L) {
                ++n3;
            }
            n4 = this.strategy.hashCode(this.key[n3]);
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
        long[] lArray = this.key;
        V[] VArray = this.value;
        MapIterator mapIterator = new MapIterator(this, null);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeLong(lArray[n2]);
            objectOutputStream.writeObject(VArray[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new long[this.n + 1];
        long[] lArray = this.key;
        this.value = new Object[this.n + 1];
        Object[] objectArray = this.value;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            long l = objectInputStream.readLong();
            Object object = objectInputStream.readObject();
            if (this.strategy.equals(l, 0L)) {
                n2 = this.n;
                this.containsNullKey = true;
            } else {
                n2 = HashCommon.mix(this.strategy.hashCode(l)) & this.mask;
                while (lArray[n2] != 0L) {
                    n2 = n2 + 1 & this.mask;
                }
            }
            lArray[n2] = l;
            objectArray[n2] = object;
        }
    }

    private void checkTable() {
    }

    @Override
    public ObjectSet long2ReferenceEntrySet() {
        return this.long2ReferenceEntrySet();
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

    static Object access$300(Long2ReferenceOpenCustomHashMap long2ReferenceOpenCustomHashMap) {
        return long2ReferenceOpenCustomHashMap.removeNullEntry();
    }

    static Object access$400(Long2ReferenceOpenCustomHashMap long2ReferenceOpenCustomHashMap, int n) {
        return long2ReferenceOpenCustomHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements ObjectIterator<V> {
        final Long2ReferenceOpenCustomHashMap this$0;

        public ValueIterator(Long2ReferenceOpenCustomHashMap long2ReferenceOpenCustomHashMap) {
            this.this$0 = long2ReferenceOpenCustomHashMap;
            super(long2ReferenceOpenCustomHashMap, null);
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
    extends AbstractLongSet {
        final Long2ReferenceOpenCustomHashMap this$0;

        private KeySet(Long2ReferenceOpenCustomHashMap long2ReferenceOpenCustomHashMap) {
            this.this$0 = long2ReferenceOpenCustomHashMap;
        }

        @Override
        public LongIterator iterator() {
            return new KeyIterator(this.this$0);
        }

        @Override
        public void forEach(LongConsumer longConsumer) {
            if (this.this$0.containsNullKey) {
                longConsumer.accept(this.this$0.key[this.this$0.n]);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                long l = this.this$0.key[n];
                if (l == 0L) continue;
                longConsumer.accept(l);
            }
        }

        @Override
        public int size() {
            return this.this$0.size;
        }

        @Override
        public boolean contains(long l) {
            return this.this$0.containsKey(l);
        }

        @Override
        public boolean remove(long l) {
            int n = this.this$0.size;
            this.this$0.remove(l);
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

        KeySet(Long2ReferenceOpenCustomHashMap long2ReferenceOpenCustomHashMap, 1 var2_2) {
            this(long2ReferenceOpenCustomHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements LongIterator {
        final Long2ReferenceOpenCustomHashMap this$0;

        public KeyIterator(Long2ReferenceOpenCustomHashMap long2ReferenceOpenCustomHashMap) {
            this.this$0 = long2ReferenceOpenCustomHashMap;
            super(long2ReferenceOpenCustomHashMap, null);
        }

        @Override
        public long nextLong() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Long2ReferenceMap.Entry<V>>
    implements Long2ReferenceMap.FastEntrySet<V> {
        final Long2ReferenceOpenCustomHashMap this$0;

        private MapEntrySet(Long2ReferenceOpenCustomHashMap long2ReferenceOpenCustomHashMap) {
            this.this$0 = long2ReferenceOpenCustomHashMap;
        }

        @Override
        public ObjectIterator<Long2ReferenceMap.Entry<V>> iterator() {
            return new EntryIterator(this.this$0, null);
        }

        @Override
        public ObjectIterator<Long2ReferenceMap.Entry<V>> fastIterator() {
            return new FastEntryIterator(this.this$0, null);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Long)) {
                return true;
            }
            long l = (Long)entry.getKey();
            Object v = entry.getValue();
            if (this.this$0.strategy.equals(l, 0L)) {
                return this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == v;
            }
            long[] lArray = this.this$0.key;
            int n = HashCommon.mix(this.this$0.strategy.hashCode(l)) & this.this$0.mask;
            long l2 = lArray[n];
            if (l2 == 0L) {
                return true;
            }
            if (this.this$0.strategy.equals(l, l2)) {
                return this.this$0.value[n] == v;
            }
            do {
                if ((l2 = lArray[n = n + 1 & this.this$0.mask]) != 0L) continue;
                return true;
            } while (!this.this$0.strategy.equals(l, l2));
            return this.this$0.value[n] == v;
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Long)) {
                return true;
            }
            long l = (Long)entry.getKey();
            Object v = entry.getValue();
            if (this.this$0.strategy.equals(l, 0L)) {
                if (this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == v) {
                    Long2ReferenceOpenCustomHashMap.access$300(this.this$0);
                    return false;
                }
                return true;
            }
            long[] lArray = this.this$0.key;
            int n = HashCommon.mix(this.this$0.strategy.hashCode(l)) & this.this$0.mask;
            long l2 = lArray[n];
            if (l2 == 0L) {
                return true;
            }
            if (this.this$0.strategy.equals(l2, l)) {
                if (this.this$0.value[n] == v) {
                    Long2ReferenceOpenCustomHashMap.access$400(this.this$0, n);
                    return false;
                }
                return true;
            }
            do {
                if ((l2 = lArray[n = n + 1 & this.this$0.mask]) != 0L) continue;
                return true;
            } while (!this.this$0.strategy.equals(l2, l) || this.this$0.value[n] != v);
            Long2ReferenceOpenCustomHashMap.access$400(this.this$0, n);
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
        public void forEach(Consumer<? super Long2ReferenceMap.Entry<V>> consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(new AbstractLong2ReferenceMap.BasicEntry(this.this$0.key[this.this$0.n], this.this$0.value[this.this$0.n]));
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] == 0L) continue;
                consumer.accept(new AbstractLong2ReferenceMap.BasicEntry(this.this$0.key[n], this.this$0.value[n]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Long2ReferenceMap.Entry<V>> consumer) {
            AbstractLong2ReferenceMap.BasicEntry basicEntry = new AbstractLong2ReferenceMap.BasicEntry();
            if (this.this$0.containsNullKey) {
                basicEntry.key = this.this$0.key[this.this$0.n];
                basicEntry.value = this.this$0.value[this.this$0.n];
                consumer.accept(basicEntry);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] == 0L) continue;
                basicEntry.key = this.this$0.key[n];
                basicEntry.value = this.this$0.value[n];
                consumer.accept(basicEntry);
            }
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        MapEntrySet(Long2ReferenceOpenCustomHashMap long2ReferenceOpenCustomHashMap, 1 var2_2) {
            this(long2ReferenceOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectIterator<Long2ReferenceMap.Entry<V>> {
        private final MapEntry entry;
        final Long2ReferenceOpenCustomHashMap this$0;

        private FastEntryIterator(Long2ReferenceOpenCustomHashMap long2ReferenceOpenCustomHashMap) {
            this.this$0 = long2ReferenceOpenCustomHashMap;
            super(long2ReferenceOpenCustomHashMap, null);
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

        FastEntryIterator(Long2ReferenceOpenCustomHashMap long2ReferenceOpenCustomHashMap, 1 var2_2) {
            this(long2ReferenceOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntryIterator
    extends MapIterator
    implements ObjectIterator<Long2ReferenceMap.Entry<V>> {
        private MapEntry entry;
        final Long2ReferenceOpenCustomHashMap this$0;

        private EntryIterator(Long2ReferenceOpenCustomHashMap long2ReferenceOpenCustomHashMap) {
            this.this$0 = long2ReferenceOpenCustomHashMap;
            super(long2ReferenceOpenCustomHashMap, null);
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

        EntryIterator(Long2ReferenceOpenCustomHashMap long2ReferenceOpenCustomHashMap, 1 var2_2) {
            this(long2ReferenceOpenCustomHashMap);
        }
    }

    private class MapIterator {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        LongArrayList wrapped;
        final Long2ReferenceOpenCustomHashMap this$0;

        private MapIterator(Long2ReferenceOpenCustomHashMap long2ReferenceOpenCustomHashMap) {
            this.this$0 = long2ReferenceOpenCustomHashMap;
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
            long[] lArray = this.this$0.key;
            do {
                if (--this.pos >= 0) continue;
                this.last = Integer.MIN_VALUE;
                long l = this.wrapped.getLong(-this.pos - 1);
                int n = HashCommon.mix(this.this$0.strategy.hashCode(l)) & this.this$0.mask;
                while (!this.this$0.strategy.equals(l, lArray[n])) {
                    n = n + 1 & this.this$0.mask;
                }
                return n;
            } while (lArray[this.pos] == 0L);
            this.last = this.pos;
            return this.last;
        }

        private void shiftKeys(int n) {
            long[] lArray = this.this$0.key;
            while (true) {
                long l;
                int n2 = n;
                n = n2 + 1 & this.this$0.mask;
                while (true) {
                    if ((l = lArray[n]) == 0L) {
                        lArray[n2] = 0L;
                        this.this$0.value[n2] = null;
                        return;
                    }
                    int n3 = HashCommon.mix(this.this$0.strategy.hashCode(l)) & this.this$0.mask;
                    if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                    n = n + 1 & this.this$0.mask;
                }
                if (n < n2) {
                    if (this.wrapped == null) {
                        this.wrapped = new LongArrayList(2);
                    }
                    this.wrapped.add(lArray[n]);
                }
                lArray[n2] = l;
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
                this.this$0.remove(this.wrapped.getLong(-this.pos - 1));
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

        MapIterator(Long2ReferenceOpenCustomHashMap long2ReferenceOpenCustomHashMap, 1 var2_2) {
            this(long2ReferenceOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Long2ReferenceMap.Entry<V>,
    Map.Entry<Long, V> {
        int index;
        final Long2ReferenceOpenCustomHashMap this$0;

        MapEntry(Long2ReferenceOpenCustomHashMap long2ReferenceOpenCustomHashMap, int n) {
            this.this$0 = long2ReferenceOpenCustomHashMap;
            this.index = n;
        }

        MapEntry(Long2ReferenceOpenCustomHashMap long2ReferenceOpenCustomHashMap) {
            this.this$0 = long2ReferenceOpenCustomHashMap;
        }

        @Override
        public long getLongKey() {
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
        public Long getKey() {
            return this.this$0.key[this.index];
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            return this.this$0.strategy.equals(this.this$0.key[this.index], (Long)entry.getKey()) && this.this$0.value[this.index] == entry.getValue();
        }

        @Override
        public int hashCode() {
            return this.this$0.strategy.hashCode(this.this$0.key[this.index]) ^ (this.this$0.value[this.index] == null ? 0 : System.identityHashCode(this.this$0.value[this.index]));
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

